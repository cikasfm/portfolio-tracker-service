package org.vilutis.lt.pts.events;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

public class TradeEvent extends ApplicationEvent {

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param trade the object on which the event initially occurred or with which the event is
     * associated (never {@code null})
     */
    public TradeEvent(TradeDTO trade) {
        super(trade);
    }

    @Override
    public TradeDTO getSource() {
        return (TradeDTO) super.getSource();
    }

    @Data
    @Builder
    public static class TradeDTO {

        private final String stock;
        private final BigDecimal price;
        private final BigDecimal quantity;
        private final Date timestamp;

    }
}
