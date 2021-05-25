package org.vilutis.lt.pts.services.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.vilutis.lt.pts.model.Stock;
import org.vilutis.lt.pts.model.StockPrice;
import org.vilutis.lt.pts.services.StockService;

@Service
@Profile("mock")
public class StockServiceMock implements StockService {

    private final List<StockPrice> MOCK_STOCK_PRICES;
    private final List<Stock> MOCK_ACTIVE_STOCKS;

    @Autowired
    public StockServiceMock(ObjectMapper mapper) throws IOException {
        File src = new File("src/main/resources/mockData/stockPrices.json");
        StockPrice[] stockPrices = mapper.readValue(src, StockPrice[].class);
        MOCK_STOCK_PRICES = Collections.unmodifiableList(Arrays.asList(stockPrices));
        MOCK_ACTIVE_STOCKS = Collections.unmodifiableList(
          MOCK_STOCK_PRICES.stream()
            .map(s -> Stock.builder().stock(s.getStock()).build())
            .collect(Collectors.toList()));
    }

    @Override
    public Map<String, StockPrice> getCurrentPrices(List<String> stocks) {
        return new HashMap<String, StockPrice>() {{
            MOCK_STOCK_PRICES.stream()
              .filter(sp -> stocks.contains(sp.getStock()))
              .forEach(sp -> put(sp.getStock(), sp));
        }};
    }

    @Override
    public List<Stock> getActiveStocks() {
        return MOCK_ACTIVE_STOCKS;
    }
}
