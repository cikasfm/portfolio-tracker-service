package org.vilutis.lt.pts.services.mock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.vilutis.lt.pts.model.Holding;
import org.vilutis.lt.pts.repository.HoldingRepository;
import org.vilutis.lt.pts.services.HoldingsService;

@Service
@Profile("mock")
public class HoldingsServiceMock implements HoldingsService {

    private final List<Holding> MOCK_HOLDINGS = Arrays.asList(
      Holding.builder()
        .accountNumber("acc1")
        .stock("AAPL")
        .amount(BigDecimal.TEN)
        .avgPrice(BigDecimal.valueOf(120D).setScale(2, RoundingMode.HALF_UP))
        .build()
    );

    @Override
    public List<Holding> getHoldings(String accountNumber, Pageable page) {
        return MOCK_HOLDINGS.stream()
          .filter(h -> h.getAccountNumber().equals(accountNumber))
          .collect(Collectors.toList());
    }
}