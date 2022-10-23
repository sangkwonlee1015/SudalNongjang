package com.a606.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppealDto {
    @ApiModelProperty(name = "NFT Id", example = "1")
    private Long nftId;
    @ApiModelProperty(name = "가격", example = "100")
    private Double price;
}
