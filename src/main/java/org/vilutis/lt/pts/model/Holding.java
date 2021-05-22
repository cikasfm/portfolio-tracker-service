package org.vilutis.lt.pts.model;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Holding
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Holding {

    private @Id @Setter(AccessLevel.PROTECTED) String id;

    /**
     * Stock name
     *
     * @return stock
     **/
    @ApiModelProperty(value = "Stock name")
    private @NonNull String stock;

    /**
     * Average Stock price at the time of purchase
     *
     * @return avgPrice
     **/
    @ApiModelProperty(value = "Average Stock price at the time of purchase")
    private @NonNull BigDecimal avgPrice;

    /**
     * Current Stock price
     *
     * @return currentPrice
     **/
    @ApiModelProperty(value = "Current Stock price")
    private @NonNull BigDecimal currentPrice;

    /**
     * Total amount of stocks in current portfolio
     *
     * @return amount
     **/
    @ApiModelProperty(value = "Total amount of stocks in current portfolio")
    private @NonNull BigDecimal amount;

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

