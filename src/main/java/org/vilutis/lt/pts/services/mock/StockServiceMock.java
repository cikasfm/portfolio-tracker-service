package org.vilutis.lt.pts.services.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.vilutis.lt.pts.model.StockPrice;
import org.vilutis.lt.pts.services.StockService;

@Service
@Profile("mock")
public class StockServiceMock implements StockService {

    private final List<StockPrice> MOCK_STOCK_PRICES;

    @Autowired
    public StockServiceMock(ObjectMapper mapper) throws IOException {
        File src = new File("src/main/resources/mockData/stockPrices.json");
        StockPrice[] stockPrices = mapper.readValue(src, StockPrice[].class);
        MOCK_STOCK_PRICES = Collections.unmodifiableList(Arrays.asList(stockPrices));
    }

    @Override
    public List<StockPrice> getCurrentPrices(List<String> stocks) {
        return MOCK_STOCK_PRICES;
    }

}
