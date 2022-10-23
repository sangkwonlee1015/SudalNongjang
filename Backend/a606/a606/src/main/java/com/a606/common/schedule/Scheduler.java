package com.a606.common.schedule;

import com.a606.api.service.ContractService;
import com.a606.db.entity.NFT;
import com.a606.db.repository.NFTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Scheduler {

    @Autowired
    NFTRepository nftRepository;

    @Autowired
    ContractService contractService;

    @Scheduled(fixedDelay = 5000)
    public void checkSudalAuction() throws Exception {
        List<NFT> nftList = nftRepository.findAll();
        for(NFT nft : nftList) {
            contractService.checkAuction(nft.getTokenId());
        }
    }
}
