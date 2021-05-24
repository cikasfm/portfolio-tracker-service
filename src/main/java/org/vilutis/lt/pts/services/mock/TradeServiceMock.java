package org.vilutis.lt.pts.services.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.vilutis.lt.pts.model.DirectionEnum;
import org.vilutis.lt.pts.model.Trade;
import org.vilutis.lt.pts.services.TradeService;

@Service
@Profile("mock")
public class TradeServiceMock implements TradeService {

    private final List<Trade> MOCK_HISTORY;

    @Autowired
    public TradeServiceMock(ObjectMapper mapper) throws IOException {
        File src = new File("src/main/resources/mockData/trades.json");
        Trade[] trades = mapper.readValue(src, Trade[].class);
        MOCK_HISTORY = Collections.unmodifiableList(Arrays.asList(trades));
    }

    @Override
    public List<Trade> getHistory(String accountNumber, String stock, Pageable page) {
        return MOCK_HISTORY.stream()
          .filter(t -> t.getAccountNumber().equals(accountNumber))
          .collect(Collectors.toList());
    }

    @Override
    public List<Trade> getPurchases(String accountNumber, Pageable page) {
        return MOCK_HISTORY.stream()
          .filter(t -> t.getAccountNumber().equals(accountNumber))
          .filter(t -> t.getDirection().equals(DirectionEnum.BUY))
          .collect(Collectors.toList());
    }

    @Override
    public List<Trade> getLiquidations(String accountNumber, Pageable page) {
        return MOCK_HISTORY.stream()
          .filter(t -> t.getAccountNumber().equals(accountNumber))
          .filter(t -> t.getDirection().equals(DirectionEnum.SELL))
          .collect(Collectors.toList());
    }
}
