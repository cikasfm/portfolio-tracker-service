package org.vilutis.lt.pts.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockPriceAlertDTO {

    String stock;
    Date date;
    /**
     * <dl>
     *     <dt><code>direction &gt; 0</code></dt>
     *     <dd>Price increased above threshold</dd>
     *     <dt><code>direction &lt; 0</code></dt>
     *     <dd>Price decreased decreased below threshold</dd>
     * </dl>
     */
    int direction;
    boolean alertSent;

}
