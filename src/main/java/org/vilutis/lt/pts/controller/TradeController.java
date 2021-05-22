package org.vilutis.lt.pts.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vilutis.lt.pts.dto.Trade;
import org.vilutis.lt.pts.dto.Trade.DirectionEnum;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

    private final List<Trade> MOCK_HISTORY = Arrays.asList(
      Trade.builder()
        .stock("AAPL")
        .direction(DirectionEnum.BUY)
        .price(BigDecimal.valueOf(120D).setScale(2, RoundingMode.HALF_UP))
        .quantity(BigDecimal.TEN)
        .build(),
      Trade.builder()
        .stock("TSLA")
        .direction(DirectionEnum.SELL)
        .price(BigDecimal.valueOf(700D).setScale(2, RoundingMode.HALF_UP))
        .quantity(BigDecimal.ONE)
        .build()
    );

    @GetMapping(path = "/{stock}")
    public List<Trade> getHistory(@RequestParam String stock) {
        return MOCK_HISTORY.stream()
          .filter(t -> t.getStock().equals(stock))
          .collect(Collectors.toList());
    }

}