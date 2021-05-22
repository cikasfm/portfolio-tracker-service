package org.vilutis.lt.pts.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Builder;
import lombok.Data;

/**
 * Trade
 */
@Data
@Builder
public class Trade {

    /**
     * Trade direction:  * `buy` - Stock purchase ( buy )  * `sell` - Stock liquidation ( sell )
     */
    public enum DirectionEnum {
        BUY("buy"),

        SELL("sell");

        private String value;

        DirectionEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static DirectionEnum fromValue(String text) {
            for (DirectionEnum b : DirectionEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    /**
     * Date/Time of the transaction
     *
     * @return timestamp
     **/
    @ApiModelProperty(value = "Date/Time of the transaction")
    private final String timestamp;

    /**
     * Stock name
     *
     * @return stock
     **/
    @ApiModelProperty(value = "Stock name")
    private final String stock;

    /**
     * Trade direction:  * `buy` - Stock purchase ( buy )  * `sell` - Stock liquidation ( sell )
     *
     * @return direction
     **/
    @ApiModelProperty(value = "Trade direction:  * `buy` - Stock purchase ( buy )  * `sell` - Stock liquidation ( sell ) ")
    private final DirectionEnum direction;


    /**
     * Stock price
     *
     * @return price
     **/
    @ApiModelProperty(value = "Stock price")
    private final BigDecimal price;

    /**
     * Amount of stocks traded
     *
     * @return quantity
     **/
    @ApiModelProperty(value = "Amount of stocks traded")
    private final BigDecimal quantity;

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

