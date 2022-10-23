package com.a606.api.service;

import com.a606.api.dto.InventoryDto;
import com.a606.api.dto.ItemDto;
import com.a606.api.dto.MyNFTDto;
import com.a606.db.entity.*;
import com.a606.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ShopServiceImpl implements ShopService{
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    IssuedAvatarRepository issuedAvatarRepository;
    @Autowired
    NFTRepository nftRepository;

    @Autowired
    ContractService contractService;

    @Override
    public Long checkDuplicated(long head, long eyes, long mouth, long hands, long fashion) {
        Optional<IssuedAvatar> issuedAvatar = issuedAvatarRepository.findByHeadAndEyesAndMouthAndHandsAndFashion(head, eyes, mouth, hands, fashion);
        if(issuedAvatar.isPresent()){
            return issuedAvatar.get().getNftId();
        }

        return null;
    }

    @Override
    public boolean checkItems(User user, List<Long> itemIds) {
        int type = 0;
        for(Long itemId : itemIds){
            type++;
            if(itemId == 0) { continue; }
            // item이 존재하지 않거나 타입에 맞지 않는 경우
            Optional<Item> item = itemRepository.findById(itemId);
            if(!item.isPresent() || item.get().getType() != type){ return false; }

            // item을 보유하고 있지 않은 경우
            Optional<Inventory> inventory = inventoryRepository.findByUserAndItem(user, item.get());
            if(!inventory.isPresent() || inventory.get().getHowMany() < 1){ return false; }
            System.out.println(type + " clear");
        }
        return true;
    }

    @Override
    public Long createNFT(User user, List<Long> itemIds, String tokenURI, String name) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("1");
        for(Long itemId : itemIds){
            if(itemId == 0) {
                stringBuilder.append(String.format("%02d", 0000));
                continue;
            }
            Item item = itemRepository.findById(itemId).get();
            Inventory inventory = inventoryRepository.findByUserAndItem(user, item).get();
            inventory.setHowMany(inventory.getHowMany() - 1);
            inventoryRepository.save(inventory);
            stringBuilder.append(String.format("%02d", item.getNumber())).append(String.format("%02d", item.getRgb()));
        }
        String tokenId = contractService.createNFT(user.getWallet(), stringBuilder.toString(), tokenURI);
        if (tokenId.equals("")) {
            return null;
        }

        NFT nft = new NFT();
        nft.setTokenId(tokenId);
        nft.setSaled(false);
        nft.setName(name);
        nft.setLikeCount(0);
        nft.setTokenURI(tokenURI);
        nft.setOwner(user.getWallet());
        nft = nftRepository.save(nft);

        IssuedAvatar issuedAvatar = new IssuedAvatar();
        issuedAvatar.setHead(itemIds.get(0));
        issuedAvatar.setEyes(itemIds.get(1));
        issuedAvatar.setMouth(itemIds.get(2));
        issuedAvatar.setHands(itemIds.get(3));
        issuedAvatar.setFashion(itemIds.get(4));
        issuedAvatar.setNftId(nft.getId());
        issuedAvatar.setFirstUserId(user.getId());
        issuedAvatarRepository.save(issuedAvatar);

        return nft.getId();
    }
}
