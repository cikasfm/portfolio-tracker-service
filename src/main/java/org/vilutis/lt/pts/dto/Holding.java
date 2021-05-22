package org.vilutis.lt.pts.dto;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * Holding
 */
@Validated
@Data
@Builder
public class Holding {

    /**
     * Stock name
     *
     * @return stock
     **/
    @ApiModelProperty(value = "Stock name")
    private final String stock;

    /**
     * Average Stock price at the time of purchase
     *
     * @return avgPrice
     **/
    @ApiModelProperty(value = "Average Stock price at the time of purchase")
    private final BigDecimal avgPrice;

    /**
     * Current Stock price
     *
     * @return currentPrice
     **/
    @ApiModelProperty(value = "Current Stock price")
    private final BigDecimal currentPrice;

    /**
     * Total amount of stocks in current portfolio
     *
     * @return amount
     **/
    @ApiModelProperty(value = "Total amount of stocks in current portfolio")
    private final BigDecimal amount;

    /**
     * Value of stocks
     *
     * @return value
     **/
    @ApiModelProperty(value = "Value of stocks")
    public BigDecimal getValue() {
        return getCurrentPrice().multiply(amount).setScale(2, RoundingMode.HALF_UP);
    }

}

