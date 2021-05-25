package org.vilutis.lt.pts.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.vilutis.lt.pts.integrations.TradeEvent;

/**
 * Listen to stock market updates
 */

@Component
@Slf4j
public class StockTradeListener {

    @EventListener(TradeEvent.class)
    @Async
    public void listen(TradeEvent event) {
        log.info("Event received:" + event.getSource());
    }

}
