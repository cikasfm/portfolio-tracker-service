package org.vilutis.lt.pts.services;

import static org.vilutis.lt.pts.services.api.StockService.stripTime;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.vilutis.lt.pts.dto.StockPriceAlertDTO;
import org.vilutis.lt.pts.dto.TradeDTO;
import org.vilutis.lt.pts.events.TradeEvent;
import org.vilutis.lt.pts.services.api.StockService;

@Service
@Slf4j
public class StockManagerService {

    private final StockService stockService;
    private final AlertService alertService;
    private final LoadingCache<String, BigDecimal> currentPriceCache;
    private final LoadingCache<String, BigDecimal> previousPriceCache;
    private final LoadingCache<String, StockPriceAlertDTO> priceAlertCache;

    private final BigDecimal alertThreshold;

    private final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    @Autowired
    public StockManagerService(StockService stockService,
      AlertService alertService,
      @Value("${alert.threshold:10}") BigDecimal alertThreshold) {
        this.stockService = stockService;
        this.alertService = alertService;
        this.alertThreshold = alertThreshold;
        this.currentPriceCache = Caffeine.newBuilder()
          .expireAfterAccess(10, TimeUnit.MINUTES)
          .maximumSize(10_000)
          // load from DB function
          .build(key -> stockService.getCurrentPrice(key));
        this.previousPriceCache = Caffeine.newBuilder()
          .expireAfterAccess(10, TimeUnit.MINUTES)
          .maximumSize(10_000)
          .build(key -> stockService.getPreviousPrice(key));
        this.priceAlertCache = Caffeine.newBuilder()
          .expireAfterAccess(10, TimeUnit.MINUTES)
          .maximumSize(10_000)
          .build(key -> Optional.ofNullable(stockService.getStockPrice(key))
            .map(StockPriceAlertDTO::of)
            .orElse(null));
    }

    @Async
    @EventListener(TradeEvent.class)
    public void handleTradeEvent(TradeEvent event) {
        TradeDTO trade = event.getSource();
        log.info("Event received:" + trade);
        Date date = stripTime(trade.getTimestamp());
        // cache update
        currentPriceCache.put(trade.getStock(), trade.getPrice());
        // DB update
        // TODO async DB update?
        stockService.update(trade.getStock(), date, trade.getPrice());

        alertIfChangeMoreThanThreshold(trade);
    }

    private void alertIfChangeMoreThanThreshold(TradeDTO trade) {
        String stock = trade.getStock();
        BigDecimal price = trade.getPrice();
        BigDecimal previousPrice = previousPriceCache.get(stock);
        if (price.compareTo(previousPrice) > 0
          && price.compareTo(addThreshold(previousPrice)) >= 0) {

            StockPriceAlertDTO alertDTO = priceAlertCache.get(stock);
            // only send once
            if (alertDTO != null && !alertDTO.isIncreaseAlertSent()) {
                // trigger alert
                alertDTO.setIncreaseAlertSent(true);
                alertService.priceIncreased(trade, previousPrice, alertThreshold);
                stockService.setIncreaseAlertSent(stock, alertDTO.getDate());
            }
        }
        if (price.compareTo(previousPrice) < 0
          && price.compareTo(subtractThreshold(previousPrice)) <= 0) {
            StockPriceAlertDTO alertDTO = priceAlertCache.get(stock);
            // only send once
            if (alertDTO != null && !alertDTO.isDecreaseAlertSent()) {
                // trigger alert
                alertDTO.setDecreaseAlertSent(true);
                alertService.priceDecreased(trade, previousPrice, alertThreshold);
                stockService.setDecreaseAlertSent(stock, alertDTO.getDate());
            }
        }
    }

    private BigDecimal addThreshold(BigDecimal previousPrice) {
        return previousPrice.multiply(
          BigDecimal.ONE.add(alertThreshold.divide(ONE_HUNDRED, 4, BigDecimal.ROUND_HALF_UP))
        );
    }

    private BigDecimal subtractThreshold(BigDecimal previousPrice) {
        return previousPrice.multiply(
          BigDecimal.ONE.subtract(alertThreshold.divide(ONE_HUNDRED, 4, BigDecimal.ROUND_HALF_UP))
        );
    }

    public Map<String, BigDecimal> getCurrentPrices(List<String> stocks) {
        return new HashMap<>(currentPriceCache.getAll(stocks));
    }

    public Map<String, BigDecimal> getPreviousPrices(List<String> stocks) {
        return new HashMap<>(previousPriceCache.getAll(stocks));
    }

}
