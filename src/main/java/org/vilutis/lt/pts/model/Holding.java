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
     * Account number
     **/
    private @NonNull String accountNumber;

    /**
     * Stock name
     **/
    private @NonNull String stock;

    /**
     * Average Stock price at the time of purchase
     **/
    private @NonNull BigDecimal avgPrice;

    /**
     * Total amount of stocks in current portfolio
     **/
    private @NonNull BigDecimal quantity;

}

