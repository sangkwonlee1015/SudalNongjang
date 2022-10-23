package com.a606.api.service;

import com.a606.api.dto.AppealDto;
import com.a606.api.dto.BidBoardDto;
import com.a606.api.dto.LogsDto;
import com.a606.api.dto.NFTDto;
import com.a606.db.entity.*;
import com.a606.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("boardService")
public class BoardServiceImpl implements BoardService{

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    AppealRepository appealRepository;

    @Autowired
    NFTRepository nftRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LikesRepository likesRepository;

    @Autowired
    ContractService contractService;

    @Autowired
    LetterService letterService;

    @Override
    public List<NFTDto> getNFTList(User user, String tab, String order, boolean isDesc, int pageNo, int pageSize) throws Exception {
        String properties = "";
        switch (order) {
            case "likeCount":
                properties = "likeCount";
                break;
            case "id":
                properties = "id";
                break;
            default:
                return null;
        }
        Sort sort = Sort.by(properties);
        if (isDesc) {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        List<NFT> nfts = new ArrayList<NFT>();
        switch (tab) {
            case "all":
                nfts = nftRepository.findAll(pageable).toList();
                break;
            case "saled":
                nfts = nftRepository.findAllByIsSaled(true, pageable).toList();
                break;
            case "unsaled":
                nfts = nftRepository.findAllByIsSaled(false, pageable).toList();
                break;
            default:
                return null;
        }
        List<NFTDto> list = new ArrayList<NFTDto>();
        for (NFT nft : nfts) {
            NFTDto nftDto = new NFTDto();
            nftDto.setId(nft.getId());
            nftDto.setTokenId(nft.getTokenId());
            nftDto.setSaled(nft.isSaled());
            nftDto.setName(nft.getName());
            nftDto.setLikeCount(nft.getLikeCount());
            nftDto.setTokenURI(nft.getTokenURI());

            if (user == null || !likesRepository.findByUserAndNft(user, nft).isPresent()){
                nftDto.setLiked(false);
            } else {
                nftDto.setLiked(true);
            }

            if (nftDto.isSaled()) {
                // 가격 찾기
                Board lastBoard = nft.getBoards().get(nft.getBoards().size() - 1);
                nftDto.setPrice(String.valueOf(lastBoard.getFirst_price()));
                if (lastBoard.getBidLogs().size() > 0) {
                    BidLog lastBidLog = nft.getBidLogs().get(nft.getBidLogs().size() - 1);
                    nftDto.setPrice(String.valueOf(lastBidLog.getPrice()));
                }
            }
            Optional<User> oUser = userRepository.findByWallet(nft.getOwner());
            if (oUser.isPresent()) {
                User wUser = oUser.get();
                nftDto.setUserId(wUser.getId());
                nftDto.setUserNickname(wUser.getNickname());
            } else {    // 유저 정보가 없을 떄
                nftDto.setUserId(0l);
                nftDto.setUserNickname("guest");
            }
            list.add(nftDto);
        }
        return list;
    }

    @Override
    public NFTDto getNFTDetail(User user, Long nftId) throws Exception {
        Optional<NFT> oNfts = nftRepository.findById(nftId);
        if (!oNfts.isPresent()) { return null; }
        NFT nft = oNfts.get();
        NFTDto nftDto = new NFTDto();
        nftDto.setId(nft.getId());
        nftDto.setTokenId(nft.getTokenId());
        nftDto.setSaled(nft.isSaled());
        nftDto.setName(nft.getName());
        nftDto.setLikeCount(nft.getLikeCount());
        nftDto.setTokenURI(nft.getTokenURI());

        if (user == null || !likesRepository.findByUserAndNft(user, nft).isPresent()){
            nftDto.setLiked(false);
        } else {
            nftDto.setLiked(true);
        }

        if (nftDto.isSaled()) {
            // 가격 찾기
            Board lastBoard = nft.getBoards().get(nft.getBoards().size() - 1);
            nftDto.setPrice(String.valueOf(lastBoard.getFirst_price()));
            if (lastBoard.getBidLogs().size() > 0) {
                BidLog lastBidLog = nft.getBidLogs().get(nft.getBidLogs().size() - 1);
                nftDto.setPrice(String.valueOf(lastBidLog.getPrice()));
            }
        }
        Optional<User> oUser = userRepository.findByWallet(nft.getOwner());
        if (oUser.isPresent()) {
            User wUser = oUser.get();
            nftDto.setUserId(wUser.getId());
            nftDto.setUserNickname(wUser.getNickname());
        } else {    // 유저 정보가 없을 떄
            nftDto.setUserId(0l);
            nftDto.setUserNickname("guest");
        }

        return nftDto;
    }

    @Override
    public BidBoardDto getBid(Long nftId) {
        Optional<NFT> oNft = nftRepository.findById(nftId);
        if (!oNft.isPresent()) { return null; }
        Optional<Board> oBoard = boardRepository.findByNftAndStartBeforeAndEndAfter(oNft.get(), LocalDateTime.now(), LocalDateTime.now());
        if (!oBoard.isPresent()) { return null; }
        Board board = oBoard.get();
        BidBoardDto bidBoardDto = new BidBoardDto();
        bidBoardDto.setId(board.getId());
        ZonedDateTime zdtStart = board.getStart().atZone(ZoneId.systemDefault());
        ZonedDateTime zdtEnd = board.getEnd().atZone(ZoneId.systemDefault());
        bidBoardDto.setStart(zdtStart.withZoneSameInstant(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        bidBoardDto.setEnd(zdtEnd.withZoneSameInstant(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        bidBoardDto.setFirst_price(board.getFirst_price());
        List<LogsDto> bidLogsDtos = new ArrayList<>();
        for (BidLog log : board.getBidLogs()) {
            ZonedDateTime zdt = log.getDate().atZone(ZoneId.systemDefault());
            String dateString = zdt.withZoneSameInstant(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            bidLogsDtos.add(new LogsDto(dateString, log.getPrice()));
        }
        bidBoardDto.setBidLogs(bidLogsDtos);

        return bidBoardDto;
    }

    @Override
    public List<LogsDto> getAppeals(Long nftId) {
        Optional<NFT> oNft = nftRepository.findById(nftId);
        if (!oNft.isPresent()) { return null; }
        List<Appeal> appeals = appealRepository.findAllByNft(oNft.get());
        List<LogsDto> logsDtos = new ArrayList<>();
        for (Appeal appeal : appeals) {
            ZonedDateTime zdt = appeal.getDate().atZone(ZoneId.systemDefault());
            String dateString = zdt.withZoneSameInstant(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            logsDtos.add(new LogsDto(dateString, appeal.getPrice()));
        }
        return logsDtos;
    }

    @Override
    public void createAppeals(User user, AppealDto appealDto) throws Exception {
        Appeal appeal = new Appeal();
        NFT nft = nftRepository.findById(appealDto.getNftId()).get();
        appeal.setUser(user);
        appeal.setNft(nft);
        appeal.setPrice(appealDto.getPrice());
        appeal.setDate(LocalDateTime.now());
        appealRepository.save(appeal);
        //
        User seller = userRepository.findByWallet(contractService.getAddressbyTokenId(nft.getTokenId())).get();
        String msg = user.getNickname() + "님이 해당 NFT의 판매를 요청하였습니다.";
        letterService.createLetter(seller, msg);
    }

    @Override
    public Boolean clickLikes(User user, Long nftId) {
        Optional<NFT> oNft = nftRepository.findById(nftId);
        if (!oNft.isPresent()) {
            return false;
        }
        NFT nft = oNft.get();
        Optional<Likes> oLikes = likesRepository.findByUserAndNft(user, nft);
        if (oLikes.isPresent()) {
            likesRepository.delete(oLikes.get());
            nft.setLikeCount(nft.getLikeCount() - 1);
            nftRepository.save(nft);
            return false;
        } else {
            Likes likes = new Likes();
            likes.setUser(user);
            likes.setNft(nft);
            likesRepository.save(likes);
            nft.setLikeCount(nft.getLikeCount() + 1);
            nftRepository.save(nft);
            return true;
        }
    }

    @Override
    public long getNFTCount(String tab) {
        long result = 0;
        switch (tab) {
            case "all":
                result = nftRepository.count();
                break;
            case "saled":
                result = nftRepository.countByIsSaled(true);
                break;
            case "unsaled":
                result = nftRepository.countByIsSaled(false);
                break;
            default:
                return 0;
        }
        return result;
    }
}
