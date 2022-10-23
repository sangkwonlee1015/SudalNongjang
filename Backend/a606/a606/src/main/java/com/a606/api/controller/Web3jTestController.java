package com.a606.api.controller;

import com.a606.contract.SudalFarm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalListAccounts;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.*;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@Api(value = "Web3j API", tags = {"Web3j."})
@RestController
@RequestMapping("/api/web3j")
public class Web3jTestController {
    /*
     web3j 테스트용 컨트롤러
     Mapping 막 했으니 신경쓰지 말기

    HttpService : 블록체인 네트워크 주소 넣기

     변수 설명
     contract : 배포한 컨트랙트 주소
     key : 팀 지갑 PRIVATE KEY
     */

    @PostMapping()
    @ApiOperation(value = "Web3j 테스트", notes = "Web3j 테스트")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "unauthenticated"),
            @ApiResponse(code = 403, message = "unauthorized")
    })
    public List<String> web3ClientVersion() throws IOException {
        Admin admin = Admin.build(new HttpService("http://20.196.209.2:8545"));
        PersonalListAccounts personalListAccounts = admin.personalListAccounts().send();
        List<String> addressList = personalListAccounts.getAccountIds();

        return addressList;
    }

    @GetMapping()
    @ApiOperation(value = "Web3j 체인 iD", notes = "Web3j 테스트")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "unauthenticated"),
            @ApiResponse(code = 403, message = "unauthorized")
    })
    public String getWeb3ChainID() throws IOException {
        Web3j web3j = Web3j.build(new HttpService("http://localhost:7545"));

        return web3j.netVersion().send().getNetVersion();
    }

    @PostMapping("/contract")
    @ApiOperation(value = "Web3j contract get sudals by owner", notes = "Web3j 테스트")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "unauthenticated"),
            @ApiResponse(code = 403, message = "unauthorized")
    })
    public ResponseEntity<?> getWeb3Contract(@RequestParam String address) throws Exception {
        String contract = "0x6777F33e2B408D748FA93BeaB6B40fD732802E6a";
        String key = "7de9b804253e76a421515313536b138ca1addd3e9aaaf07152d9155b71edaafa";
        Web3j web3j = Web3j.build(new HttpService("http://localhost:7545"));
        Credentials credentials = Credentials.create(key);
        ContractGasProvider gasProvider = new DefaultGasProvider();
        FastRawTransactionManager manager = new FastRawTransactionManager(web3j, credentials,
                new PollingTransactionReceiptProcessor(web3j, 3000, 3));

        SudalFarm sudalFarm = SudalFarm.load(contract, web3j, manager, gasProvider);

        Tuple2<List<BigInteger>, List<BigInteger>> tuple2 = sudalFarm.getSudalsByOwner(address).send();

        return ResponseEntity.status(200).body(tuple2);
    }

    @GetMapping("/contract")
    @ApiOperation(value = "Web3j contract get sudals", notes = "Web3j 테스트")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "unauthenticated"),
            @ApiResponse(code = 403, message = "unauthorized")
    })
    public ResponseEntity<?> getWeb3SudalsByOwner() throws Exception {
        String contract = "0xB816Cb6893dB99A599896AFa400bcB2581bfd7F4";
        String key = "2c835aeb997e4d1269513a83efadc44de96a30a56c1b58cb7f8ce8897511c79f";
        Web3j web3j = Web3j.build(new HttpService("http://20.196.209.2:8545"));
        
//        String contract = "0xB816Cb6893dB99A599896AFa400bcB2581bfd7F4";
//        String key = "2c835aeb997e4d1269513a83efadc44de96a30a56c1b58cb7f8ce8897511c79f";
//
//        Web3j web3j = Web3j.build(new HttpService("http://20.196.209.2:8545"));
        Credentials credentials = Credentials.create(key);
        FastRawTransactionManager manager = new FastRawTransactionManager(web3j, credentials,
                new PollingTransactionReceiptProcessor(web3j, 3000, 3));
        ContractGasProvider gasProvider = new DefaultGasProvider();

        SudalFarm sudalFarm = SudalFarm.load(contract, web3j, manager, gasProvider);
        Tuple2<List<BigInteger>, List<BigInteger>> tuple2 = sudalFarm.getSudals().send();

        List<BigInteger> ids = (List<BigInteger>) tuple2.component1();
        List<BigInteger> dnas = (List<BigInteger>) tuple2.component2();

        System.out.println(ids);
        System.out.println(dnas);

        return ResponseEntity.status(200).body(tuple2);
    }

    @PostMapping("/nft")
    @ApiOperation(value = "Web3j contract create sudal nft", notes = "Web3j 테스트")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "unauthenticated"),
            @ApiResponse(code = 403, message = "unauthorized")
    })
    public ResponseEntity<?> createNFT(@RequestParam String toAddress, String sudalDNA, String tokenURI) throws Exception {
//        String contract = "0x6777F33e2B408D748FA93BeaB6B40fD732802E6a";
//        String key = "7de9b804253e76a421515313536b138ca1addd3e9aaaf07152d9155b71edaafa";
//        Web3j web3j = Web3j.build(new HttpService("http://localhost:7545"));

        String contract = "0xB816Cb6893dB99A599896AFa400bcB2581bfd7F4";
        String key = "2c835aeb997e4d1269513a83efadc44de96a30a56c1b58cb7f8ce8897511c79f";

        Web3j web3j = Web3j.build(new HttpService("http://20.196.209.2:8545"));
        Credentials credentials = Credentials.create(key);
        ContractGasProvider gasProvider = new StaticGasProvider(BigInteger.valueOf(11_000_000_000_000L), BigInteger.valueOf(4_300_000));
//        ContractGasProvider gasProvider = new StaticGasProvider(BigInteger.ZERO, DefaultGasProvider.GAS_LIMIT);
        FastRawTransactionManager manager = new FastRawTransactionManager(web3j, credentials,
                new PollingTransactionReceiptProcessor(web3j, 3000, 3));

        SudalFarm sudalFarm = SudalFarm.load(contract, web3j, manager, gasProvider);

        TransactionReceipt transactionReceipt = sudalFarm.createSudal(toAddress, new BigInteger(sudalDNA), tokenURI).send();
        List<Log> logs =  transactionReceipt.getLogs();

        Event newSudal = new Event("NewSudal", Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint>(false) {}, new TypeReference<Uint>(false) {}));
        String newSudalHash = EventEncoder.encode(newSudal);
        for(Log log : logs) {
            if (newSudalHash.equals(log.getTopics().get(0))){
                System.out.println(log);
                System.out.println(log.getTopics());
                System.out.println(log.getData());
                String address = FunctionReturnDecoder.decodeIndexedValue(log.getTopics().get(1), new TypeReference<Address>() {}).toString();
                List<Type> datas = FunctionReturnDecoder.decode(log.getData(), newSudal.getNonIndexedParameters());
                System.out.println(datas.get(0).getTypeAsString());
                System.out.println(datas.get(0).getValue());
                System.out.println(datas.get(1).getTypeAsString());
                System.out.println(datas.get(1).getValue());
                String id = ((Uint) datas.get(0)).toString();
                String dna = datas.get(1).toString();
//                String id = FunctionReturnDecoder.decodeIndexedValue(log.getTopics().get(2), new TypeReference<Uint>() {}).toString();
//                String dna = FunctionReturnDecoder.decodeIndexedValue(log.getTopics().get(3), new TypeReference<Uint>() {}).toString();
                System.out.println(address);
                System.out.println(id);
                System.out.println(dna);
            }
        }
        transactionReceipt.getLogs().get(0).getTopics().get(0);

        return ResponseEntity.status(200).body("success");
    }
}
