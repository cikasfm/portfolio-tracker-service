package org.vilutis.lt.pts.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vilutis.lt.pts.dto.Holding;
import org.vilutis.lt.pts.dto.Portfolio;
import org.vilutis.lt.pts.dto.Summary;
import org.vilutis.lt.pts.dto.Trade;
import org.vilutis.lt.pts.dto.Trade.DirectionEnum;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final List<Trade> MOCK_PURCHASES = Arrays.asList(
      Trade.builder()
        .stock("AAPL")
        .direction(DirectionEnum.BUY)
        .price(BigDecimal.valueOf(120D).setScale(2, RoundingMode.HALF_UP))
        .quantity(BigDecimal.TEN)
        .build()
    );

    private final List<Trade> MOCK_LIQUIDATIONS = Arrays.asList(
      Trade.builder()
        .stock("TSLA")
        .direction(DirectionEnum.SELL)
        .price(BigDecimal.valueOf(700D).setScale(2, RoundingMode.HALF_UP))
        .quantity(BigDecimal.ONE)
        .build()
    );

    private final List<Holding> MOCK_HOLDINGS = Arrays.asList(
      Holding.builder()
        .stock("AAPL")
        .amount(BigDecimal.TEN)
        .avgPrice(BigDecimal.valueOf(120D).setScale(2, RoundingMode.HALF_UP))
        .currentPrice(BigDecimal.valueOf(130D).setScale(2, RoundingMode.HALF_UP))
        .build()
    );

    private final Summary MOCK_SUMMARY = Summary.builder()
      .purchasePrice(BigDecimal.valueOf(MOCK_HOLDINGS.stream()
        .mapToDouble(h -> h.getAmount().multiply(h.getAvgPrice()).doubleValue())
        .sum()).setScale(2, RoundingMode.HALF_UP))
      .value(BigDecimal.valueOf(MOCK_HOLDINGS.stream()
        .mapToDouble(h -> h.getAmount().multiply(h.getCurrentPrice()).doubleValue())
        .sum()).setScale(2, RoundingMode.HALF_UP))
      .build();

    @GetMapping
    public Portfolio getPortfolio() {
        return Portfolio.builder()
          .holdings(MOCK_HOLDINGS)
          .purchases(MOCK_PURCHASES)
          .liquidations(MOCK_LIQUIDATIONS)
          .summary(MOCK_SUMMARY)
          .build();
    }

}