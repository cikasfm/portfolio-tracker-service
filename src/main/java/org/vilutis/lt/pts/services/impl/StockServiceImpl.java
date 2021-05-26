package org.vilutis.lt.pts.services.impl;

import static org.vilutis.lt.pts.services.api.StockService.stripTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vilutis.lt.pts.model.Stock;
import org.vilutis.lt.pts.model.StockPrice;
import org.vilutis.lt.pts.repository.StockPriceRepository;
import org.vilutis.lt.pts.repository.StockRepository;
import org.vilutis.lt.pts.services.api.StockService;

@Service
@Slf4j
@Profile("!mock")
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final StockPriceRepository stockPriceRepository;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository,
      StockPriceRepository stockPriceRepository) {
        this.stockRepository = stockRepository;
        this.stockPriceRepository = stockPriceRepository;
    }

    @Override
    public BigDecimal getCurrentPrice(String stock) {
        // try find one for stripTime's date
        Optional<StockPrice> stockPriceOptional = stockPriceRepository
          .findOneByStockAndDate(stock, stripTime(new Date()));

        if (stockPriceOptional.isPresent()) {
            return stockPriceOptional.map(StockPrice::getPrice).get();
        }

        Optional<StockPrice> latestOptional = stockPriceRepository
          .findOneByStockOrderByDateDesc(stock);

        if (latestOptional.isPresent()) {
            return latestOptional.map(StockPrice::getPrice).get();
        }

        return null;
    }

    @Override
    @Cacheable("stocks")
    public List<Stock> getActiveStocks() {
        return new ArrayList<Stock>() {{
            stockRepository.findAll().forEach(this::add);
        }};
    }

    @Override
    @Transactional
    public void update(String stock, Date date, BigDecimal price) {
        Optional<StockPrice> priceOptional = stockPriceRepository
          .findOneByStockAndDate(stock, date);

        if (priceOptional.isPresent()) {
            StockPrice stockPrice = priceOptional.get();
            stockPrice.setPrice(price);
            stockPriceRepository.save(stockPrice);
        } else {
            stockPriceRepository.save(
              StockPrice.builder()
                .stock(stock)
                .date(date)
                .price(price)
                .build()
            );
        }
    }

    @Override
    public BigDecimal getPreviousPrice(String stock) {
        return stockPriceRepository.findOneBeforeDate(stock, stripTime(new Date()))
          .map(StockPrice::getPrice).orElseGet(null);
    }
}
