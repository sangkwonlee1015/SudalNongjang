package com.a606.api.controller;

import com.a606.api.dto.AvatarDto;
import com.a606.api.dto.MyNFTDto;
import com.a606.api.service.ShopService;
import com.a606.api.service.UserService;
import com.a606.common.util.SudalUserDetails;
import com.a606.db.entity.User;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Api(value = "상점 API", tags = {"Shop"})
@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class ShopController {

    @Autowired
    private final ShopService shopService;

    @PutMapping("shop/nft")
    @ApiOperation(value = "nft 발급", notes = "장착한 아이템, tokenURI로 nft 발급")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = Long.class),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 403, message = "해당 아이템을 갖고 있지 않음"),
            @ApiResponse(code = 409, message = "이미 같은 상태로 발급된 nft가 존재", response = Long.class)
    })
    public ResponseEntity<?> mintNFT(@ApiIgnore Authentication authentication,
                                    @RequestBody @ApiParam(value = "입고 있는 아이템", required = true) AvatarDto avatarDto) throws Exception {
        if (authentication == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        User user = ((SudalUserDetails) authentication.getDetails()).getUser();

        // 중복 체크
        Long dupl = shopService.checkDuplicated(avatarDto.getHead(), avatarDto.getEyes(), avatarDto.getMouth(), avatarDto.getHands(), avatarDto.getFashion());
        if (dupl != null) {
            return new ResponseEntity<>(dupl, HttpStatus.CONFLICT);
        }
        List<Long> itemIds = new ArrayList<>();
        itemIds.add(avatarDto.getHead());
        itemIds.add(avatarDto.getEyes());
        itemIds.add(avatarDto.getMouth());
        itemIds.add(avatarDto.getHands());
        itemIds.add(avatarDto.getFashion());
        if (!shopService.checkItems(user, itemIds)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Long nftId = shopService.createNFT(user, itemIds, avatarDto.getTokenURI(), avatarDto.getName());

        return new ResponseEntity<>(nftId, HttpStatus.OK);
    }


}
