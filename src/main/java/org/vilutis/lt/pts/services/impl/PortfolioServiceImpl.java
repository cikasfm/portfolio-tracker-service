package org.vilutis.lt.pts.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vilutis.lt.pts.dto.HoldingDTO;
import org.vilutis.lt.pts.dto.PortfolioDTO;
import org.vilutis.lt.pts.dto.SummaryDTO;
import org.vilutis.lt.pts.model.Holding;
import org.vilutis.lt.pts.model.StockPrice;
import org.vilutis.lt.pts.services.HoldingsService;
import org.vilutis.lt.pts.services.PortfolioService;
import org.vilutis.lt.pts.services.StockService;
import org.vilutis.lt.pts.services.TradeService;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    private final TradeService tradeService;
    private final HoldingsService holdingsService;
    private final StockService stockService;

    @Autowired
    public PortfolioServiceImpl(TradeService tradeService,
      HoldingsService holdingsService,
      StockService stockService) {
        this.tradeService = tradeService;
        this.holdingsService = holdingsService;
        this.stockService = stockService;
    }

    @Override
    public PortfolioDTO getAccountPortfolio(String accountNumber) {
        List<Holding> holdings = holdingsService.getHoldings(accountNumber);

        List<String> stocks = holdings.stream().map(Holding::getStock)
          .collect(Collectors.toList());

        Map<String, StockPrice> prices = stockService.getCurrentPrices(stocks);

        // verify...
        stocks.forEach( stock -> prices.computeIfAbsent( stock, key -> {
            throw new RuntimeException("Stock not in the database!?");
        }));

        return PortfolioDTO.builder()
          .holdings(buildHoldings(holdings, prices))
          .purchases(tradeService.getPurchases(accountNumber))
          .liquidations(tradeService.getLiquidations(accountNumber))
          .summary(buildSummary(holdings, prices))
          .build();
    }

    private List<HoldingDTO> buildHoldings(List<Holding> holdings, Map<String, StockPrice> priceMap) {
        return holdings.stream()
          .map(h -> HoldingDTO.builder()
            .stock(h.getStock())
            .quantity(h.getQuantity())
            .avgPrice(h.getAvgPrice())
            .currentPrice(priceMap.get(h.getStock()).getPrice())
            .build()
          )
          .collect(Collectors.toList());
    }

    private SummaryDTO buildSummary(List<Holding> holdings, Map<String, StockPrice> priceMap) {
        return SummaryDTO.builder()
          .purchasePrice(BigDecimal.valueOf(holdings.stream()
            .mapToDouble(h -> h.getQuantity().multiply(h.getAvgPrice()).doubleValue())
            .sum()).setScale(2, RoundingMode.HALF_UP))
          .value(BigDecimal.valueOf(holdings.stream()
            .mapToDouble(h -> h.getQuantity().multiply(priceMap.get(h.getStock()).getPrice()).doubleValue())
            .sum()).setScale(2, RoundingMode.HALF_UP))
          .build();
    }

}
