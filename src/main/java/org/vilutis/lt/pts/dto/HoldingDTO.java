package org.vilutis.lt.pts.dto;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Holding
 */
@Data
@Builder
public class HoldingDTO {

    /**
     * Stock name
     **/
    @ApiModelProperty(value = "Stock name")
    private @NonNull String stock;

    /**
     * Average Stock price at the time of purchase
     **/
    @ApiModelProperty(value = "Average Stock price at the time of purchase")
    private @NonNull BigDecimal avgPrice;

    /**
     * Current Stock price
     **/
    @ApiModelProperty(value = "Current Stock price")
    private @NonNull BigDecimal currentPrice;

    /**
     * Total amount of stocks in current portfolio
     **/
    @ApiModelProperty(value = "Total amount of stocks in current portfolio")
    private @NonNull BigDecimal quantity;

    /**
     * Value of stocks
     **/
    @ApiModelProperty(value = "Value of stocks")
    public BigDecimal getValue() {
        return getCurrentPrice().multiply(quantity).setScale(2, RoundingMode.HALF_UP);
    }

}

