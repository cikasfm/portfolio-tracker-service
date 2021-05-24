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

    /**
     * Date/Time of the transaction
     **/
    @ApiModelProperty(value = "Date/Time of the transaction")
    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    /**
     * Stock name
     **/
    @ApiModelProperty(value = "Stock name")
    @NonNull
    private String stock;

    /**
     * Trade direction:
     * <dl>
     *     <dt><code>buy</code></dt>
     *     <dd>Stock purchase ( buy )</dd>
     *     <dt><code>sell</code></dt>
     *     <dd>Stock liquidation ( sell )</dd>
     * </dl>
     **/
    @ApiModelProperty(value = "Trade direction:  * `buy` - Stock purchase ( buy )  * `sell` - Stock liquidation ( sell ) ")
    @NonNull
    private DirectionEnum direction;

    /**
     * Stock price
     **/
    @ApiModelProperty(value = "Stock price")
    @NonNull
    private BigDecimal price;

    /**
     * Amount of stocks traded
     **/
    @ApiModelProperty(value = "Amount of stocks traded")
    @NonNull
    private BigDecimal quantity;

    /**
     * Total dollar value of stocks traded ( quantity * price )
     **/
    @ApiModelProperty(value = "Total dollar value of stocks traded ( quantity * price )")
    public BigDecimal getValue() {
        return price.multiply(quantity).setScale(2, RoundingMode.HALF_UP);
    }

}

