package org.vilutis.lt.pts.dto;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SummaryDTO {

    @ApiModelProperty(value = "Total stock purchase price")
    BigDecimal purchasePrice;
    @ApiModelProperty(value = "Total current stock value")
    BigDecimal value;
    @ApiModelProperty(value = "Recent delta")
    BigDecimal delta;
}
