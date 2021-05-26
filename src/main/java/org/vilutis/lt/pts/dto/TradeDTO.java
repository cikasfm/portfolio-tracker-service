package org.vilutis.lt.pts.dto;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString()
public class TradeDTO {

    private final String stock;
    private final BigDecimal price;
    private final BigDecimal quantity;
    private final Date timestamp;

}
