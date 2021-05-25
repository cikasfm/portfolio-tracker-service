package org.vilutis.lt.pts.listener;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import org.vilutis.lt.pts.model.DirectionEnum;

@Data
@Builder
public class TradeDTO {

    private final String stock;
    private final BigDecimal price;
    private final BigDecimal quantity;
    private final Date timestamp;

}
