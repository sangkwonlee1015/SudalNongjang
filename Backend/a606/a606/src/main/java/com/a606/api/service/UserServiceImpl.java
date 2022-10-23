package com.a606.api.service;

import com.a606.api.dto.MyNFTDto;
import com.a606.api.dto.UserDto;
import com.a606.db.entity.Board;
import com.a606.db.entity.NFT;
import com.a606.db.entity.User;
import com.a606.db.repository.BoardRepository;
import com.a606.db.repository.NFTRepository;
import com.a606.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NFTRepository nftRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ContractService contractService;

    private String getRandomNonce() {
        return String.valueOf((int)(Math.random()*1000000));
    }

    @Override
    public String getNonceByWallet(String userWallet) {
        Optional<User> oUser = userRepository.findByWallet(userWallet);
        if(oUser.isPresent()) {
            return oUser.get().getNonce();
        }
        return null;
    }

    @Override
    public User createUser(String userWallet) {
        User user = new User();
        user.setNickname("Unknown");
        user.setWallet(userWallet);
        user.setGamePoint(0l);
        user.setCreatedDate(LocalDateTime.now());
        user.setLastModifiedDate(LocalDateTime.now());
        user.setNonce(getRandomNonce());
        user.setProfile(0l);
        userRepository.save(user);
        return user;
    }


    @Override
    public List<MyNFTDto> getUserPageById(long userId) throws Exception {
        User user = userRepository.findById(userId).get();
        // web3를 통해서 solidity랑 통신해서 보유한 NFT의 tokenID를 얻어서
        // DB에서 tokenID로 검색한 NFT들을 List에 담아서 반환
        List<NFT> nftList = nftRepository.findAllByOwner(user.getWallet());
        List<MyNFTDto> nfts = new ArrayList<>();
        for (NFT nft : nftList) {
            MyNFTDto myNFTDto = new MyNFTDto();
            myNFTDto.setId(nft.getId());
            myNFTDto.setTokenId(nft.getTokenId());
            myNFTDto.setTokenURI(nft.getTokenURI());
            myNFTDto.setSaled(nft.isSaled());
            myNFTDto.setLikeCount(nft.getLikeCount());
            myNFTDto.setName(nft.getName());
            nfts.add(myNFTDto);
        }

        return nfts;
    }

    @Override
    public String getProfileById(long userId) throws Exception {
        User user = userRepository.findById(userId).get();
        Optional<NFT> nft = nftRepository.findById(user.getProfile());
        if (!nft.isPresent()) {
            return null;
        }
        String address = nft.get().getOwner();
        // 토큰을 보유중일 경우
        if(address.equalsIgnoreCase(user.getWallet())){
            return nft.get().getTokenURI();
        }
        return null;
    }

    @Override
    public User getUserByWallet(String userWallet) {
        Optional<User> user = userRepository.findByWallet(userWallet);
        return user.orElse(null);
    }

    @Override
    public User updateProfile(long userId, long nftTokenId) throws Exception {
        User user = userRepository.findById(userId).get();
        Optional<NFT> oNft = nftRepository.findById(nftTokenId);
        if (!oNft.isPresent()) {
            return null;
        }
        NFT nft = oNft.get();
        String address = nft.getOwner();
        if (!address.equalsIgnoreCase(user.getWallet())) {
            return null;
        }
        user.setProfile(nftTokenId);
        user = userRepository.save(user);

        return user;
    }

    @Override
    public String updateNickname(long userId, String nickname) {
        User user = userRepository.findById(userId).get();
        user.setNickname(nickname);
        user = userRepository.save(user);

        return user.getNickname();
    }

    @Override
    public void setNonce(long userId) {
        User user = userRepository.findById(userId).get();
        user.setNonce(getRandomNonce());
        userRepository.save(user);
    }

    @Override
    public Long getPoint(long userId) {
        User user = userRepository.findById(userId).get();
        return user.getGamePoint();
    }

    @Override
    public String getNickname(long userId) {
        User user = userRepository.findById(userId).get();
        return user.getNickname();
    }
}
