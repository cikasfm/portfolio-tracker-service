package org.vilutis.lt.pts.model;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Trade
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Trade {

    @Id
    @Setter(AccessLevel.PROTECTED)
    private String txId;

    @NonNull
    private String accountNumber;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;

    /**
     * Date/Time of the transaction
     *
     * @return timestamp
     **/
    @ApiModelProperty(value = "Date/Time of the transaction")
    @NonNull
    private String timestamp;

    /**
     * Stock name
     *
     * @return stock
     **/
    @ApiModelProperty(value = "Stock name")
    @NonNull
    private String stock;

    /**
     * Trade direction:  * `buy` - Stock purchase ( buy )  * `sell` - Stock liquidation ( sell )
     *
     * @return direction
     **/
    @ApiModelProperty(value = "Trade direction:  * `buy` - Stock purchase ( buy )  * `sell` - Stock liquidation ( sell ) ")
    @NonNull
    private DirectionEnum direction;


    /**
     * Stock price
     *
     * @return price
     **/
    @ApiModelProperty(value = "Stock price")
    @NonNull
    private BigDecimal price;

    /**
     * Amount of stocks traded
     *
     * @return quantity
     **/
    @ApiModelProperty(value = "Amount of stocks traded")
    @NonNull
    private BigDecimal quantity;

    /**
     * Total dollar value of stocks traded ( quantity * price )
     *
     * @return value
     **/
    @ApiModelProperty(value = "Total dollar value of stocks traded ( quantity * price )")
    public BigDecimal getValue() {
        return price.multiply(quantity).setScale(2, RoundingMode.HALF_UP);
    }

}

