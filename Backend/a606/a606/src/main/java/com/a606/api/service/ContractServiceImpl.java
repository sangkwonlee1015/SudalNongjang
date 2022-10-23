package com.a606.api.service;

import com.a606.api.dto.MyNFTDto;
import com.a606.contract.SudalAuction;
import com.a606.contract.SudalFarm;
import com.a606.db.entity.BidLog;
import com.a606.db.entity.Board;
import com.a606.db.entity.NFT;
import com.a606.db.entity.User;
import com.a606.db.repository.BidLogRepository;
import com.a606.db.repository.BoardRepository;
import com.a606.db.repository.NFTRepository;
import com.a606.db.repository.UserRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service("contractService")
public class ContractServiceImpl implements ContractService{

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NFTRepository nftRepository;
    @Autowired
    BidLogRepository bidLogRepository;

    //    싸피 네트워크
    @Value("${web3.sudalFarmAddress}")
    private String sudalFarmContract;
    @Value("${web3.sudalAuctionAddress}")
    private String sudalAuctionContract;
    @Value("${web3.key}")
    private String key;
    @Value("${web3.network}")
    private String url;

    @Override
    public List<MyNFTDto> getNFTbyAddress(String Address) throws Exception {
        Web3j web3j = Web3j.build(new HttpService(url));
        Credentials credentials = Credentials.create(key);
        ContractGasProvider gasProvider = new DefaultGasProvider();
        FastRawTransactionManager manager = new FastRawTransactionManager(web3j, credentials,
                new PollingTransactionReceiptProcessor(web3j, 3000, 3));

        SudalFarm sudalFarm = SudalFarm.load(sudalFarmContract, web3j, manager, gasProvider);
        SudalAuction sudalAuction = SudalAuction.load(sudalAuctionContract, web3j, manager, gasProvider);

        Tuple2<List<BigInteger>, List<BigInteger>> tuple2 = sudalFarm.getSudalsByOwner(Address).send();

        List<BigInteger> ids = (List<BigInteger>) tuple2.component1();
        List<BigInteger> dnas = (List<BigInteger>) tuple2.component2();

        List<MyNFTDto> mynfts = new ArrayList<MyNFTDto>();

        for(int i = 0; i < ids.size(); i++){
            MyNFTDto mynft = new MyNFTDto();
            mynft.setTokenId(String.valueOf(ids.get(i)));
            mynft.setTokenURI(sudalFarm.tokenURI(ids.get(i)).send());
            mynfts.add(mynft);
        }

        return mynfts;
    }

    @Override
    public String getAddressbyTokenId(String tokenId) throws Exception {
        Web3j web3j = Web3j.build(new HttpService(url));
        Credentials credentials = Credentials.create(key);
        ContractGasProvider gasProvider = new DefaultGasProvider();
        FastRawTransactionManager manager = new FastRawTransactionManager(web3j, credentials,
                new PollingTransactionReceiptProcessor(web3j, 3000, 3));

        SudalFarm sudalFarm = SudalFarm.load(sudalFarmContract, web3j, manager, gasProvider);
        SudalAuction sudalAuction = SudalAuction.load(sudalAuctionContract, web3j, manager, gasProvider);

        Tuple5<String, BigInteger, BigInteger, BigInteger, Boolean> tuple5 = sudalAuction.getAuctionByTokenId(new BigInteger(tokenId)).send();

        String address = "";
        if (tuple5.component5()) {
            address = tuple5.component1();
        } else {
            address = sudalFarm.ownerOf(new BigInteger(tokenId)).send();
        }

        return address;
    }

    @Override
    public String getTokenURIbyTokenId(String tokenId) throws Exception {
        Web3j web3j = Web3j.build(new HttpService(url));
        Credentials credentials = Credentials.create(key);
        ContractGasProvider gasProvider = new DefaultGasProvider();
        FastRawTransactionManager manager = new FastRawTransactionManager(web3j, credentials,
                new PollingTransactionReceiptProcessor(web3j, 3000, 3));

        SudalFarm sudalFarm = SudalFarm.load(sudalFarmContract, web3j, manager, gasProvider);

        String address = sudalFarm.tokenURI(new BigInteger(tokenId)).send();
        return address;
    }

    @Override
    public String createNFT(String toAddress, String sudalDNA, String tokenURI) throws Exception {
        Web3j web3j = Web3j.build(new HttpService(url));
        Credentials credentials = Credentials.create(key);
        ContractGasProvider gasProvider = new StaticGasProvider(BigInteger.ZERO, DefaultGasProvider.GAS_LIMIT);
        FastRawTransactionManager manager = new FastRawTransactionManager(web3j, credentials,
                new PollingTransactionReceiptProcessor(web3j, 3000, 3));


        SudalFarm sudalFarm = SudalFarm.load(sudalFarmContract, web3j, manager, gasProvider);

        TransactionReceipt transactionReceipt = sudalFarm.createSudal(toAddress, new BigInteger(sudalDNA), tokenURI).send();
        List<Log> logs =  transactionReceipt.getLogs();

        String tokenId = "";

        // 트랜잭션 영수증에서 event log
        Event newSudal = new Event("NewSudal", Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint>(false) {}, new TypeReference<Uint>(false) {}));
        String newSudalHash = EventEncoder.encode(newSudal);
        for(Log log : logs) {
            if (newSudalHash.equals(log.getTopics().get(0))){
                String address = FunctionReturnDecoder.decodeIndexedValue(log.getTopics().get(1), new TypeReference<Address>() {}).toString();
                List<Type> datas = FunctionReturnDecoder.decode(log.getData(), newSudal.getNonIndexedParameters());
                tokenId = String.valueOf(datas.get(0).getValue());
                String dna = String.valueOf(datas.get(1).getValue());
            }
        }

        return tokenId;
    }

    @Override
    public void checkAuction(String tokenId) throws Exception {
        Web3j web3j = Web3j.build(new HttpService(url));
        Credentials credentials = Credentials.create(key);
        ContractGasProvider gasProvider = new StaticGasProvider(BigInteger.ZERO, DefaultGasProvider.GAS_LIMIT);
        FastRawTransactionManager manager = new FastRawTransactionManager(web3j, credentials,
                new PollingTransactionReceiptProcessor(web3j, 3000, 3));
        SudalFarm sudalFarm = SudalFarm.load(sudalFarmContract, web3j, manager, gasProvider);
        SudalAuction sudalAuction = SudalAuction.load(sudalAuctionContract, web3j, manager, gasProvider);

        // 경매 정보
        Tuple5<String, BigInteger, BigInteger, BigInteger, Boolean> tuple5 = sudalAuction.getAuctionByTokenId(new BigInteger(tokenId)).send();
        // 입찰 정보
        Tuple3<List<String>, List<BigInteger>, List<BigInteger>> tuple3 = sudalAuction.getBidByTokenId(new BigInteger(tokenId)).send();

        Optional<NFT> oNft = nftRepository.findByTokenId(tokenId);
        // 경매 정보와 nft 테이블의 is_saled 맞추기
        if (oNft.isPresent()) {
            NFT nft = oNft.get();
            if (tuple5.component5() != nft.isSaled()) {
                nft.setSaled(tuple5.component5());
                nftRepository.save(nft);
            }
        }

        // 경매 진행 중이 아닐 때
        if (!tuple5.component5()){ return; }
        LocalDateTime endTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(tuple5.component3().longValue()), TimeZone.getDefault().toZoneId());

        // 경매 종료 시간이 지난 경우 경매 종료 처리
        if (endTime.isBefore(LocalDateTime.now())) {
            TransactionReceipt transactionReceipt = sudalAuction.endAuction(new BigInteger(tokenId)).send();
            List<Log> logs =  transactionReceipt.getLogs();
            Event auctionEnded = new Event("AuctionEnded", Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint>(false) {}));
            String auctionEndedHash = EventEncoder.encode(auctionEnded);
            for(Log log : logs) {
                if (auctionEndedHash.equals(log.getTopics().get(0))) {
                    String address = FunctionReturnDecoder.decodeIndexedValue(log.getTopics().get(1), new TypeReference<Address>() {}).toString();
                    List<Type> datas = FunctionReturnDecoder.decode(log.getData(), auctionEnded.getNonIndexedParameters());
                    tokenId = String.valueOf(datas.get(0).getValue());
                    if (oNft.isPresent()) {
                        NFT nft = oNft.get();
                        nft.setOwner(address);
                        nftRepository.save(nft);
                    }
                }
            }
        }

        if (oNft.isPresent()) {
            NFT nft = oNft.get();
            Optional<Board> oBoard = boardRepository.findByNftAndStart(nft, LocalDateTime.ofInstant(Instant.ofEpochSecond(tuple5.component2().longValue()), TimeZone.getDefault().toZoneId()));
            Optional<User> oUser = userRepository.findByWallet(tuple5.component1());
            // 새로운 경매일 때 경매글 추가
            if (!oBoard.isPresent() && oUser.isPresent()) {
                Board newBoard = new Board();
                newBoard.setNft(nft);
                newBoard.setStart(LocalDateTime.ofInstant(Instant.ofEpochSecond(tuple5.component2().longValue()), TimeZone.getDefault().toZoneId()));
                newBoard.setEnd(LocalDateTime.ofInstant(Instant.ofEpochSecond(tuple5.component3().longValue()), TimeZone.getDefault().toZoneId()));
                newBoard.setFirst_price(tuple5.component4().doubleValue());
                newBoard.setUser(oUser.get());
                boardRepository.save(newBoard);
            }

            // 입찰 내역 확인
            if (oBoard.isPresent()) {
                int idx = -1;
                for (String bidder : tuple3.component1()) {
                    idx++;
                    oUser = userRepository.findByWallet(bidder);
                    if (oUser.isPresent()) {
                        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochSecond(tuple3.component2().get(idx).longValue()), TimeZone.getDefault().toZoneId());
                        Optional<BidLog> oBidLog = bidLogRepository.findByUserAndBoardAndDate(oUser.get(), oBoard.get(), ldt);
                        // 새로운 입찰 내역일 경우 추가
                        if (!oBidLog.isPresent()) {
                            BidLog newBidLog = new BidLog();
                            newBidLog.setDate(ldt);
                            newBidLog.setPrice(tuple3.component3().get(idx).doubleValue());
                            newBidLog.setUser(oUser.get());
                            newBidLog.setBoard(oBoard.get());
                            newBidLog.setNft(oBoard.get().getNft());
                            bidLogRepository.save(newBidLog);
                        }
                    }
                }
            }
        }

    }

}
