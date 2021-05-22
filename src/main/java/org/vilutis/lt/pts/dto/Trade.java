package org.vilutis.lt.pts.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
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
    private String timestamp = null;

    /**
     * Stock name
     *
     * @return stock
     **/
    @ApiModelProperty(value = "Stock name")
    private String stock = null;

    /**
     * Trade direction:  * `buy` - Stock purchase ( buy )  * `sell` - Stock liquidation ( sell )
     *
     * @return direction
     **/
    @ApiModelProperty(value = "Trade direction:  * `buy` - Stock purchase ( buy )  * `sell` - Stock liquidation ( sell ) ")
    private DirectionEnum direction = null;


    /**
     * Stock price
     *
     * @return price
     **/
    @ApiModelProperty(value = "Stock price")
    private BigDecimal price = null;

    /**
     * Amount of stocks traded
     *
     * @return quantity
     **/
    @ApiModelProperty(value = "Amount of stocks traded")
    private BigDecimal quantity = null;

    /**
     * Total dollar value of stocks traded ( quantity * price )
     *
     * @return value
     **/
    @ApiModelProperty(value = "Total dollar value of stocks traded ( quantity * price )")
    private BigDecimal value = null;

}

