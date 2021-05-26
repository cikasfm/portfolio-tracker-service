package org.vilutis.lt.pts.services.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.vilutis.lt.pts.model.Stock;
import org.vilutis.lt.pts.model.StockPrice;
import org.vilutis.lt.pts.services.api.StockService;

@Service
@Profile("mock")
public class StockServiceMock implements StockService {

    private final List<StockPrice> stockPrices;
    private final Map<String, BigDecimal> stockPriceCache = new HashMap<>();
    private final List<Stock> stockList;

    @Autowired
    public StockServiceMock(ObjectMapper mapper) throws IOException {
        File src = new File("src/main/resources/mockData/stockPrices.json");
        StockPrice[] stockPrices = mapper.readValue(src, StockPrice[].class);
        this.stockPrices = Arrays.asList(stockPrices);
        this.stockList = this.stockPrices.stream()
          .map(s -> Stock.builder().stock(s.getStock()).build())
          .collect(Collectors.toList());
        this.stockPrices.forEach(sp -> stockPriceCache.put(sp.getStock(), sp.getPrice()));
    }

    @Override
    public BigDecimal getCurrentPrice(String stock) {
        return stockPriceCache.get(stock);
    }

    @Override
    public List<Stock> getActiveStocks() {
        return stockList;
    }

    @Override
    public void update(String stock, Date date, BigDecimal price) {
        this.stockPriceCache.put(stock, price);
    }

    @Override
    public BigDecimal getPreviousPrice(String stock) {
        Date today = StockService.stripTime(new Date());
        return stockPrices.stream()
          .filter(sp -> sp.getStock().equals(stock))
          .filter(sp -> sp.getDate().getTime() < today.getTime())
          .sorted((s1, s2) -> Long.signum(s2.getDate().getTime() - s1.getDate().getTime()))
          .findFirst()
          .map(StockPrice::getPrice)
          .orElse(null);
    }
}
