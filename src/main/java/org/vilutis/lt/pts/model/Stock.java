package org.vilutis.lt.pts.model;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Stock {

    @Id
    @NonNull
    @ApiModelProperty("Stock name")
    private final String stock;

    private String description;

}
