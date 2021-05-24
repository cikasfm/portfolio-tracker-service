package org.vilutis.lt.pts.model;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
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
 * Historical stock price by date on market close
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class StockPrice {

    private @Id @Setter(AccessLevel.PROTECTED) String id;

    /**
     * Stock name
     **/
    @ApiModelProperty(value = "Stock name")
    private @NonNull String stock;

    /**
     * Date of the stock price
     **/
    @ApiModelProperty(value = "Date of the stock price")
    @NonNull
    @Temporal(TemporalType.DATE)
    private Date date;

    /**
     * Stock price on this date
     **/
    @ApiModelProperty(value = "stock price on market close")
    private @NonNull BigDecimal price;

}

