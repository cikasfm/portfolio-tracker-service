package org.vilutis.lt.pts.services.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.vilutis.lt.pts.integrations.TradeEvent;
import org.vilutis.lt.pts.listener.TradeDTO;
import org.vilutis.lt.pts.model.Stock;
import org.vilutis.lt.pts.model.StockPrice;
import org.vilutis.lt.pts.repository.StockPriceRepository;
import org.vilutis.lt.pts.repository.StockRepository;
import org.vilutis.lt.pts.services.StockService;

@Service
@Slf4j
@Profile("!mock")
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final StockPriceRepository stockPriceRepository;

    private final Cache<String, StockPrice> stockPriceCache;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository,
      StockPriceRepository stockPriceRepository) {
        this.stockRepository = stockRepository;
        this.stockPriceRepository = stockPriceRepository;
        this.stockPriceCache = Caffeine.newBuilder()
          .expireAfterWrite(10, TimeUnit.MINUTES)
          .maximumSize(10_000)
          .build(this::getStockPrice);
    }

    @EventListener(TradeEvent.class)
    @Async
    public void listen(TradeEvent event) {
        TradeDTO trade = event.getSource();
        log.info("Event received:" + trade);
        StockPrice stockPrice = stockPriceRepository.findById(trade.getStock())
          .orElseGet(() ->
            StockPrice.builder()
              .stock(trade.getStock())
              .price(trade.getPrice())
              .date(stripTime(trade.getTimestamp()))
              .build());
        stockPrice.setPrice(trade.getPrice());
        // DB update
        // TODO async DB update?
        stockPriceRepository.save(stockPrice);
        // cache update
        stockPriceCache.put(trade.getStock(), stockPrice);
    }

    private StockPrice getStockPrice(String stock) {
        // try find one for stripTime's date
        Optional<StockPrice> stockPriceOptional = stockPriceRepository
          .findOneByStockAndDate(stock, stripTime(new Date()));

        return stockPriceOptional.orElseGet(() -> {
            // if not present, try & find the latest one
            Optional<StockPrice> latestOptional = stockPriceRepository
              .findOneByStockOrderByDateDesc(stock);

            return latestOptional.orElse(null);
        });
    }

    @Override
    public Map<String, StockPrice> getCurrentPrices(List<String> stocks) {
        return stockPriceCache.getAll(stocks, allStocks -> new HashMap<String, StockPrice>() {{
            allStocks.forEach(stock -> put(stock, getStockPrice(stock)));
        }});
    }

    @Override
    @Cacheable("stocks")
    public List<Stock> getActiveStocks() {
        return new ArrayList<Stock>() {{
            stockRepository.findAll().forEach(this::add);
        }};
    }

    // Old school java time :)
    private Date stripTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
