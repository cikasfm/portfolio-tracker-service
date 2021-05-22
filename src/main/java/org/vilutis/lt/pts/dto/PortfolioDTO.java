package org.vilutis.lt.pts.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.vilutis.lt.pts.model.Holding;
import org.vilutis.lt.pts.model.Trade;

/**
 * Portfolio
 */
@Data
@Builder
public class PortfolioDTO {

    /**
     * Get holdings
     *
     * @return holdings
     **/
    @ApiModelProperty(value = "Current stock holdings")
    private List<Holding> holdings;

    /**
     * Recent Purchases
     *
     * @return purchases
     **/
    @ApiModelProperty(value = "Recent Purchases")
    private List<Trade> purchases;

    /**
     * Recent Liquidations
     *
     * @return liquidations
     **/
    @ApiModelProperty(value = "Recent Liquidations")
    private List<Trade> liquidations;

    /**
     * Get summary
     *
     * @return summary
     **/
    @ApiModelProperty(value = "")
    private SummaryDTO summary;

}

