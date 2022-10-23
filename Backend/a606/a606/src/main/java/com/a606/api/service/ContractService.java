package com.a606.api.service;

import com.a606.api.dto.MyNFTDto;

import java.util.List;

public interface ContractService {

    List<MyNFTDto> getNFTbyAddress(String Address) throws Exception;

    String getAddressbyTokenId(String tokenId) throws Exception;

    String getTokenURIbyTokenId(String tokenId) throws Exception;

    String createNFT(String toAddress, String sudalDNA, String tokenURI) throws Exception;

    void checkAuction(String tokenId) throws Exception;
}
