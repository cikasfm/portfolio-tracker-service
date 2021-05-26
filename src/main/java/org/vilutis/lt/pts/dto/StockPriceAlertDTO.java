package org.vilutis.lt.pts.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Data;
import org.vilutis.lt.pts.model.StockPrice;

@Data
@Builder
public class StockPriceAlertDTO {

    public static StockPriceAlertDTO of(StockPrice stockPrice) {
        return StockPriceAlertDTO.builder()
          .stock(stockPrice.getStock())
          .date(stockPrice.getDate())
          .increaseAlertSent(stockPrice.isIncreaseAlertSent())
          .decreaseAlertSent(stockPrice.isDecreaseAlertSent())
          .build();
    }

    String stock;
    Date date;

    boolean increaseAlertSent;
    boolean decreaseAlertSent;

}
