package com.a606.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvatarDto {
    @ApiModelProperty(name = "name", example = "수달이")
    private String name;
    @ApiModelProperty(name = "head item id", example = "1")
    private Long head;
    @ApiModelProperty(name = "eyes item id", example = "1")
    private Long eyes;
    @ApiModelProperty(name = "mouth item id", example = "1")
    private Long mouth;
    @ApiModelProperty(name = "hands item id", example = "1")
    private Long hands;
    @ApiModelProperty(name = "fashion item id", example = "1")
    private Long fashion;
    @ApiModelProperty(name = "tokenURI(ipfs)", example = "ipfs://")
    private String tokenURI;
}
