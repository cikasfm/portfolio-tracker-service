package org.vilutis.lt.pts.services.impl;

import static org.vilutis.lt.pts.services.StockService.stripTime;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.vilutis.lt.pts.events.TradeEvent;
import org.vilutis.lt.pts.events.TradeEvent.TradeDTO;
import org.vilutis.lt.pts.services.StockService;

@Service
@Slf4j
public class StockManagerService {

    private final StockService stockService;
    private final LoadingCache<String, BigDecimal> stockPriceCache;

    @Autowired
    public StockManagerService(StockService stockService) {
        this.stockService = stockService;
        this.stockPriceCache = Caffeine.newBuilder()
          .expireAfterAccess(10, TimeUnit.MINUTES)
          .maximumSize(10_000)
          // load from DB function
          .build(key -> stockService.getCurrentPrice(key));
        // TODO warmup cache?
    }

    @Async
    @EventListener(TradeEvent.class)
    public void handleTradeEvent(TradeEvent event) {
        TradeDTO trade = event.getSource();
        log.info("Event received:" + trade);
        Date date = stripTime(trade.getTimestamp());
        // cache update
        stockPriceCache.put(trade.getStock(), trade.getPrice());
        // DB update
        // TODO async DB update?
        stockService.update(trade.getStock(), date, trade.getPrice());
    }

    public Map<String, BigDecimal> getCurrentPrices(List<String> stocks) {
        return stockPriceCache.getAll(stocks);
    }

}
