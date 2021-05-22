package org.vilutis.lt.pts.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vilutis.lt.pts.dto.Trade;
import org.vilutis.lt.pts.dto.Trade.DirectionEnum;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

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

    @GetMapping(path = "/purchases")
    public List<Trade> getPurchases() {
        return MOCK_PURCHASES;
    }

    @GetMapping(path = "/liquidations")
    public List<Trade> getLiquidations() {
        return MOCK_LIQUIDATIONS;
    }

}