package org.vilutis.lt.pts.integrations;

import org.springframework.context.ApplicationEvent;
import org.vilutis.lt.pts.listener.TradeDTO;

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
}
