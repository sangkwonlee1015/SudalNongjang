package com.a606.api.controller;

import com.a606.api.dto.*;
import com.a606.api.service.BoardService;
import com.a606.common.util.SudalUserDetails;
import com.a606.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "Board API", tags = {"Board."})
@RestController
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/list")
    @ApiOperation(value = "NFT 목록 조회",
            notes = "탭(전체(all)/분양중(saled)/미분양(unsaled)), 정렬기준(좋아요순(likeCount)/등록순(id)), 정렬순서(높은순/낮은순), 페이지 번호, 페이지 당 NFT 수 로 NFT 목록을 조회한다.")
    public ResponseEntity<?> searchList(@ApiIgnore Authentication authentication,
                                              @RequestParam(required = true) @ApiParam(example = "all") String tab,
                                              @RequestParam(required = true) @ApiParam(example = "id") String order,
                                              @RequestParam(required = true) @ApiParam(example = "false") boolean isDesc,
                                              @RequestParam(required = true) @ApiParam(example = "1") int pageNo,
                                              @RequestParam(required = true) @ApiParam(example = "10") int pageSize) throws Exception {
        User user = null;
        if (authentication != null) user = ((SudalUserDetails) authentication.getDetails()).getUser();
        return new ResponseEntity<NFTListDto>(new NFTListDto(boardService.getNFTCount(tab) , boardService.getNFTList(user, tab, order, isDesc, pageNo, pageSize)), HttpStatus.OK);
    }

    @GetMapping("/details/{nftId}")
    @ApiOperation(value = "NFT 상세 조회",
            notes = "nftId로 NFT 상세 정보를 조회한다.")
    public ResponseEntity<NFTDto> getNFTDetails(@ApiIgnore Authentication authentication,
                                                @PathVariable Long nftId) throws Exception {
        User user = null;
        if (authentication != null) user = ((SudalUserDetails) authentication.getDetails()).getUser();
        return new ResponseEntity<>(boardService.getNFTDetail(user, nftId), HttpStatus.OK);
    }

    @GetMapping("/bid/{nftId}")
    @ApiOperation(value = "분양중인 NFT 분양 정보 조회",
            notes = "nftId로 NFT 분양 정보를 조회한다.")
    public ResponseEntity<BidBoardDto> getBid(@PathVariable Long nftId) throws Exception {
        return new ResponseEntity<>(boardService.getBid(nftId), HttpStatus.OK);
    }

    @GetMapping("/appeal/{nftId}")
    @ApiOperation(value = "미분양중인 NFT 요청 내역 정보 조회",
            notes = "nftId로 NFT 분양 정보를 조회한다.")
    public ResponseEntity<List<LogsDto>> getAppeals(@PathVariable Long nftId) throws Exception {
        return new ResponseEntity<>(boardService.getAppeals(nftId), HttpStatus.OK);
    }

    @PostMapping("/appeal")
    @ApiOperation(value = "NFT 판매 요청",
            notes = "NFT 판매 요청을 한다.")
    public ResponseEntity<?> newAppeals(@ApiIgnore Authentication authentication,
                                        @RequestBody @ApiParam(value = "판매 요청 정보", required = true) AppealDto appealDto) throws Exception {
        if (authentication == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        User user = ((SudalUserDetails) authentication.getDetails()).getUser();
        boardService.createAppeals(user, appealDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/likes")
    @ApiOperation(value = "좋아요 클릭",
            notes = "좋아요를 누른다. 이미 눌렀을 경우 취소한다.")
    public ResponseEntity<?> clickLikes(@ApiIgnore Authentication authentication,
                                                @RequestBody @ApiParam(value = "NFT ID", required = true) LikesDto likesDto) throws Exception {
        if (authentication == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        User user = ((SudalUserDetails) authentication.getDetails()).getUser();
        return new ResponseEntity<Boolean>(boardService.clickLikes(user, likesDto.getNftId()), HttpStatus.OK);
    }

}
