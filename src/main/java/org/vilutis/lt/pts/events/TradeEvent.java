package org.vilutis.lt.pts.events;

import org.springframework.context.ApplicationEvent;
import org.vilutis.lt.pts.dto.TradeDTO;

public class TradeEvent extends ApplicationEvent {

    public static TradeEvent with(TradeDTO trade) {
        return new TradeEvent(trade);
    }

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param trade the object on which the event initially occurred or with which the event is
     * associated (never {@code null})
     */
    private TradeEvent(TradeDTO trade) {
        super(trade);
    }

    @Override
    public TradeDTO getSource() {
        return (TradeDTO) super.getSource();
    }

}
