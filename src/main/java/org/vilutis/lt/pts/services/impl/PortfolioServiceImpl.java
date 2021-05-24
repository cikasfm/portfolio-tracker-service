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

        List<StockPrice> prices = stockService.getCurrentPrices(stocks);
        Map<String, BigDecimal> priceMap = new HashMap<String, BigDecimal>(){{
            prices.forEach(p -> this.put(p.getStock(), p.getPrice()));
        }};

        return PortfolioDTO.builder()
          .holdings(buildHoldings(holdings, priceMap))
          .purchases(tradeService.getPurchases(accountNumber))
          .liquidations(tradeService.getLiquidations(accountNumber))
          .summary(buildSummary(holdings, priceMap))
          .build();
    }

    private List<HoldingDTO> buildHoldings(List<Holding> holdings, Map<String, BigDecimal> priceMap) {
        return holdings.stream()
          .map(h -> HoldingDTO.builder()
            .stock(h.getStock())
            .amount(h.getAmount())
            .avgPrice(h.getAvgPrice())
            .currentPrice(priceMap.get(h.getStock()))
            .build()
          )
          .collect(Collectors.toList());
    }

    private SummaryDTO buildSummary(List<Holding> holdings, Map<String, BigDecimal> priceMap) {
        return SummaryDTO.builder()
          .purchasePrice(BigDecimal.valueOf(holdings.stream()
            .mapToDouble(h -> h.getAmount().multiply(h.getAvgPrice()).doubleValue())
            .sum()).setScale(2, RoundingMode.HALF_UP))
          .value(BigDecimal.valueOf(holdings.stream()
            .mapToDouble(h -> h.getAmount().multiply(priceMap.get(h.getStock())).doubleValue())
            .sum()).setScale(2, RoundingMode.HALF_UP))
          .build();
    }

}