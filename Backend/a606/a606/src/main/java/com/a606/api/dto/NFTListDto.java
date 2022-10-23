package com.a606.api.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NFTListDto {

    private long count;
    private List<NFTDto> nftList;
}
