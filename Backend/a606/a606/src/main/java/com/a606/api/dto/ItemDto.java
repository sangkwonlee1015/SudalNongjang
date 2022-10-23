package com.a606.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private long id;
    private int type;
    private int number;
    private int rgb;
    private int rare;
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class createItemRequest {
        private int type;
        private int number;
        private int rgb;
        private int rare;
    }
}
