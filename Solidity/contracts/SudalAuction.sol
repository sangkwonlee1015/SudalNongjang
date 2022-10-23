// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./@openzeppelin/contracts/token/ERC20/ERC20.sol";
import "./SudalFarm.sol";

contract SudalAuction is Ownable {

    event AuctionCreated(address indexed seller, uint tokenId);
    event AuctionEnded(address indexed getter, uint tokenId);

    address private _erc20ContractAddress;
    address private _sudalFarmContractAddress;

    // 토큰 id와 경매 매핑
    mapping(uint => Auction) public auctions;

    // 토큰 id와 입찰 매핑
    mapping(uint => Bid[]) public bids;

    // 토큰 id와 입찰 인덱스 매핑
    mapping(uint => uint) public bidIndexes;


    struct Auction {
        address seller;
        uint start;
        uint end;
        uint firstPrice;
        bool isActive;
    }

        struct Bid {
        address bidder;
        uint bidTime;
        uint price;
    }

    modifier isSudalOwner(address _addr , uint _tokenId) {
        address _tokenOwner = SudalFarm(_sudalFarmContractAddress).ownerOf(_tokenId);
        require(_tokenOwner == _addr);
        _;
    }

    function onERC721Received(address, address, uint256, bytes calldata) external pure returns (bytes4) {
        return IERC721Receiver.onERC721Received.selector;
    }

    function setErc20(address _contractAddress) public onlyOwner {
        // erc20 토큰 contract 연결
        _erc20ContractAddress = _contractAddress;
    }

    function setSudalFarm(address _contractAddress) public onlyOwner {
        // 수달 농장 contract 연결
        _sudalFarmContractAddress = _contractAddress;
    }

    
    function createAuction(uint _tokenId, uint _time, uint _firstPrice) public isSudalOwner(msg.sender, _tokenId) returns(bool) {
        // NFT 전송 받기
        SudalFarm(_sudalFarmContractAddress).safeTransferFrom(msg.sender, address(this), uint256(_tokenId));

        // 경매 정보 저장
        Auction memory newAuction;
        newAuction.seller = msg.sender;
        newAuction.start = block.timestamp;
        newAuction.end = block.timestamp + _time;
        newAuction.firstPrice = _firstPrice;
        newAuction.isActive = true;

        auctions[_tokenId] = newAuction;
        bidIndexes[_tokenId] = 0;

        emit AuctionCreated(msg.sender, _tokenId);
        return true;
    }

    function getAuctionByTokenId(uint _tokenId) external view returns(address, uint, uint, uint, bool) {
        Auction memory auction = auctions[_tokenId];
        return (auction.seller, auction.start, auction.end, auction.firstPrice, auction.isActive);
    }

    function bidOnAuction(uint _tokenId, uint _price) public payable {
        IERC20 erc20 = IERC20(_erc20ContractAddress);

        // 경매 마감 전인지 확인
        require(auctions[_tokenId].end > block.timestamp);
        // 토큰이 충분한지 확인
        require(erc20.balanceOf(msg.sender) > _price);

        Bid[] memory bidArray = bids[_tokenId];
        uint bidIndex = bidIndexes[_tokenId];

        // 시작가 또는 마지막 입찰가를 넘겼는지 확인
        if (bidIndex == 0) {
            require(_price > auctions[_tokenId].firstPrice);
        } else {
            require(_price > bidArray[bidIndex - 1].price);
        }

        erc20.transferFrom(msg.sender, address(this), _price);

        // 마지막 입찰 환불
        if (bidIndex > 0) {
            Bid memory lastBid = bidArray[bidIndex - 1];
            erc20.transfer(lastBid.bidder, lastBid.price);
        }

        // 입찰 정보 저장
        Bid memory newBid;
        newBid.bidder = msg.sender;
        newBid.bidTime = block.timestamp;
        newBid.price = _price;

        if (bids[_tokenId].length > bidIndex) {
            bids[_tokenId][bidIndex] = newBid;
        } else {
            bids[_tokenId].push(newBid);
        }

        bidIndexes[_tokenId] = bidIndex + 1;
    }

    function getBidByTokenId(uint _tokenId) external view returns(address[] memory, uint[] memory, uint[] memory) {
        address[] memory bidders = new address[](bidIndexes[_tokenId]);
        uint[] memory bidTimes = new uint[](bidIndexes[_tokenId]);
        uint[] memory prices = new uint[](bidIndexes[_tokenId]);

        for (uint i = 0; i < bidIndexes[_tokenId]; i++){
            bidders[i] = bids[_tokenId][i].bidder;
            bidTimes[i] = bids[_tokenId][i].bidTime;
            prices[i] = bids[_tokenId][i].price;
        }
        return(bidders, bidTimes, prices);
    }

    function approveAndTransfer(address _from, address _to, uint _tokenId) internal returns(bool) {
        SudalFarm sudalFarm = SudalFarm(_sudalFarmContractAddress);
        sudalFarm.approve(_to, _tokenId);
        sudalFarm.transferFrom(_from, _to, _tokenId);
        return true;
    }

    function endAuction(uint _tokenId) public {
        Auction memory auction = auctions[_tokenId];
        Bid[] memory bidArray = bids[_tokenId];
        uint bidIndex = bidIndexes[_tokenId];

        // 경매 중인지 확인
        require(auction.isActive);
        // 경매 마감 시간이 지났는지 확인
        require(block.timestamp > auction.end);

         // 입찰이 있는 경우
        if (bidIndex > 0) {
            // 판매자에게 토큰 전송
            IERC20(_erc20ContractAddress).transfer(auction.seller, bidArray[bidIndex - 1].price);

            // 구매자에게 NFT 전송
            approveAndTransfer(address(this), bidArray[bidIndex - 1].bidder, _tokenId);
            emit AuctionEnded(bidArray[bidIndex - 1].bidder, _tokenId);
        }
        // 입찰이 없는 경우
        else {
            // 원래 주인에게 NFT 전송
            approveAndTransfer(address(this), auction.seller, _tokenId);
            emit AuctionEnded(auction.seller, _tokenId);
        }

        auctions[_tokenId].isActive = false;
    }

}