package org.vilutis.lt.pts.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vilutis.lt.pts.dto.Trade;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

    @GetMapping(path = "/purchases")
    public List<Trade> getPurchases() {
        return new ArrayList<>();
    }

    @GetMapping(path = "/liquidations")
    public List<Trade> getLiquidations() {
        return new ArrayList<>();
    }

}