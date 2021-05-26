package org.vilutis.lt.pts.services;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vilutis.lt.pts.dto.TradeDTO;

@Component
@Slf4j
public class AlertService {

    public void priceIncreased(TradeDTO trade, BigDecimal previousPrice,
      BigDecimal alertThreshold) {
        log.warn("[ALERT] stock {} price {} increased by more than {}% from previous price {}",
          trade.getStock(), trade.getPrice(), alertThreshold, previousPrice);
    }

    public void priceDecreased(TradeDTO trade, BigDecimal previousPrice,
      BigDecimal alertThreshold) {
        log.warn("[ALERT] stock {} price {} decreased by more than {}% from previous price {}",
          trade.getStock(), trade.getPrice(), alertThreshold, previousPrice);
    }
}
