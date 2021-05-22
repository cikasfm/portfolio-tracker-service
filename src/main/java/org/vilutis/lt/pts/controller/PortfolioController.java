package org.vilutis.lt.pts.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vilutis.lt.pts.model.Holding;
import org.vilutis.lt.pts.dto.PortfolioDTO;
import org.vilutis.lt.pts.dto.SummaryDTO;
import org.vilutis.lt.pts.model.Trade;
import org.vilutis.lt.pts.model.DirectionEnum;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final List<Trade> MOCK_PURCHASES = Arrays.asList(
      Trade.builder()
        .accountNumber("acc1")
        .timestamp("2020-03-04 12:34:56+08:00")
        .stock("AAPL")
        .direction(DirectionEnum.BUY)
        .price(BigDecimal.valueOf(120D).setScale(2, RoundingMode.HALF_UP))
        .quantity(BigDecimal.TEN)
        .build()
    );

    private final List<Trade> MOCK_LIQUIDATIONS = Arrays.asList(
      Trade.builder()
        .accountNumber("acc1")
        .timestamp("2020-01-02 12:34:56+08:00")
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

    private final SummaryDTO MOCK_SUMMARY = SummaryDTO.builder()
      .purchasePrice(BigDecimal.valueOf(MOCK_HOLDINGS.stream()
        .mapToDouble(h -> h.getAmount().multiply(h.getAvgPrice()).doubleValue())
        .sum()).setScale(2, RoundingMode.HALF_UP))
      .value(BigDecimal.valueOf(MOCK_HOLDINGS.stream()
        .mapToDouble(h -> h.getAmount().multiply(h.getCurrentPrice()).doubleValue())
        .sum()).setScale(2, RoundingMode.HALF_UP))
      .build();

    @GetMapping
    public PortfolioDTO getPortfolio() {
        return PortfolioDTO.builder()
          .holdings(MOCK_HOLDINGS)
          .purchases(MOCK_PURCHASES)
          .liquidations(MOCK_LIQUIDATIONS)
          .summary(MOCK_SUMMARY)
          .build();
    }

}