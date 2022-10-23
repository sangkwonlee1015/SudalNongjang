package com.a606.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikesDto {
    @ApiModelProperty(name = "NFT Id", example = "1")
    private Long nftId;
}
