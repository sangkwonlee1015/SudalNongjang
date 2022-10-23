package com.a606.api.service;

import com.a606.api.dto.AppealDto;
import com.a606.api.dto.BidBoardDto;
import com.a606.api.dto.LogsDto;
import com.a606.api.dto.NFTDto;
import com.a606.db.entity.User;

import java.util.List;

public interface BoardService {

    List<NFTDto> getNFTList(User user, String tab, String order, boolean isDesc, int pageNo, int npp) throws Exception;

    NFTDto getNFTDetail(User user, Long nftId) throws Exception;

    BidBoardDto getBid(Long nftId);

    List<LogsDto> getAppeals(Long nftId);

    void createAppeals(User user, AppealDto appealDto) throws Exception;

    Boolean clickLikes(User user, Long nftId);

    long getNFTCount(String tab);
}
