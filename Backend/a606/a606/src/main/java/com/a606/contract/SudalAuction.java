package com.a606.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class SudalAuction extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b5061002d61002261003260201b60201c565b61003a60201b60201c565b6100fe565b600033905090565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050816000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a35050565b6121d5806200010e6000396000f3fe6080604052600436106100dd5760003560e01c80637b3c4baa1161007f578063b9a2de3a11610059578063b9a2de3a146102e3578063cda4beef1461030c578063ee9b80a414610349578063f2fde38b14610372576100dd565b80637b3c4baa1461025d5780638da5cb5b1461029c5780639f635120146102c7576100dd565b80631f568b08116100bb5780631f568b081461019b578063571a26a0146101c4578063715018a614610205578063774132671461021c576100dd565b80630bf876c4146100e2578063150b7a021461011f57806316437d211461015c575b600080fd5b3480156100ee57600080fd5b50610109600480360381019061010491906118ac565b61039b565b60405161011691906118e8565b60405180910390f35b34801561012b57600080fd5b50610146600480360381019061014191906119c6565b6103b3565b6040516101539190611a89565b60405180910390f35b34801561016857600080fd5b50610183600480360381019061017e91906118ac565b6103c8565b60405161019293929190611c20565b60405180910390f35b3480156101a757600080fd5b506101c260048036038101906101bd9190611c6c565b610682565b005b3480156101d057600080fd5b506101eb60048036038101906101e691906118ac565b6106ce565b6040516101fc959493929190611cc3565b60405180910390f35b34801561021157600080fd5b5061021a610731565b005b34801561022857600080fd5b50610243600480360381019061023e91906118ac565b610745565b604051610254959493929190611cc3565b60405180910390f35b34801561026957600080fd5b50610284600480360381019061027f9190611d16565b61082a565b60405161029393929190611d56565b60405180910390f35b3480156102a857600080fd5b506102b1610891565b6040516102be9190611d8d565b60405180910390f35b6102e160048036038101906102dc9190611d16565b6108ba565b005b3480156102ef57600080fd5b5061030a600480360381019061030591906118ac565b610deb565b005b34801561031857600080fd5b50610333600480360381019061032e9190611da8565b6111cc565b6040516103409190611dfb565b60405180910390f35b34801561035557600080fd5b50610370600480360381019061036b9190611c6c565b6114c5565b005b34801561037e57600080fd5b5061039960048036038101906103949190611c6c565b611511565b005b60056020528060005260406000206000915090505481565b600063150b7a0260e01b905095945050505050565b60608060606000600560008681526020019081526020016000205467ffffffffffffffff8111156103fc576103fb611e16565b5b60405190808252806020026020018201604052801561042a5781602001602082028036833780820191505090505b5090506000600560008781526020019081526020016000205467ffffffffffffffff81111561045c5761045b611e16565b5b60405190808252806020026020018201604052801561048a5781602001602082028036833780820191505090505b5090506000600560008881526020019081526020016000205467ffffffffffffffff8111156104bc576104bb611e16565b5b6040519080825280602002602001820160405280156104ea5781602001602082028036833780820191505090505b50905060005b600560008981526020019081526020016000205481101561066e576004600089815260200190815260200160002081815481106105305761052f611e45565b5b906000526020600020906003020160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1684828151811061057557610574611e45565b5b602002602001019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff16815250506004600089815260200190815260200160002081815481106105d4576105d3611e45565b5b9060005260206000209060030201600101548382815181106105f9576105f8611e45565b5b60200260200101818152505060046000898152602001908152602001600020818154811061062a57610629611e45565b5b90600052602060002090600302016002015482828151811061064f5761064e611e45565b5b602002602001018181525050808061066690611ea3565b9150506104f0565b508282829550955095505050509193909250565b61068a611594565b80600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b60036020528060005260406000206000915090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010154908060020154908060030154908060040160009054906101000a900460ff16905085565b610739611594565b6107436000611612565b565b600080600080600080600360008881526020019081526020016000206040518060a00160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820154815260200160028201548152602001600382015481526020016004820160009054906101000a900460ff161515151581525050905080600001518160200151826040015183606001518460800151955095509550955095505091939590929450565b6004602052816000526040600020818154811061084657600080fd5b9060005260206000209060030201600091509150508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010154908060020154905083565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690504260036000858152602001908152602001600020600201541161090357600080fd5b818173ffffffffffffffffffffffffffffffffffffffff166370a08231336040518263ffffffff1660e01b815260040161093d9190611d8d565b602060405180830381865afa15801561095a573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061097e9190611f00565b1161098857600080fd5b600060046000858152602001908152602001600020805480602002602001604051908101604052809291908181526020016000905b82821015610a5957838290600052602060002090600302016040518060600160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160018201548152602001600282015481525050815260200190600101906109bd565b50505050905060006005600086815260200190815260200160002054905060008103610aa65760036000868152602001908152602001600020600301548411610aa157600080fd5b610add565b81600182610ab49190611f2d565b81518110610ac557610ac4611e45565b5b6020026020010151604001518411610adc57600080fd5b5b8273ffffffffffffffffffffffffffffffffffffffff166323b872dd3330876040518463ffffffff1660e01b8152600401610b1a93929190611f61565b6020604051808303816000875af1158015610b39573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610b5d9190611fc4565b506000811115610c1b57600082600183610b779190611f2d565b81518110610b8857610b87611e45565b5b602002602001015190508373ffffffffffffffffffffffffffffffffffffffff1663a9059cbb826000015183604001516040518363ffffffff1660e01b8152600401610bd5929190611ff1565b6020604051808303816000875af1158015610bf4573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610c189190611fc4565b50505b610c236117ee565b33816000019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff168152505042816020018181525050848160400181815250508160046000888152602001908152602001600020805490501115610d245780600460008881526020019081526020016000208381548110610cb357610cb2611e45565b5b906000526020600020906003020160008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506020820151816001015560408201518160020155905050610dbf565b6004600087815260200190815260200160002081908060018154018082558091505060019003906000526020600020906003020160009091909190915060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550602082015181600101556040820151816002015550505b600182610dcc919061201a565b6005600088815260200190815260200160002081905550505050505050565b6000600360008381526020019081526020016000206040518060a00160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820154815260200160028201548152602001600382015481526020016004820160009054906101000a900460ff1615151515815250509050600060046000848152602001908152602001600020805480602002602001604051908101604052809291908181526020016000905b82821015610f6d57838290600052602060002090600302016040518060600160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820154815260200160028201548152505081526020019060010190610ed1565b5050505090506000600560008581526020019081526020016000205490508260800151610f9957600080fd5b82604001514211610fa957600080fd5b600081111561113457600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663a9059cbb8460000151846001856110039190611f2d565b8151811061101457611013611e45565b5b6020026020010151604001516040518363ffffffff1660e01b815260040161103d929190611ff1565b6020604051808303816000875af115801561105c573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906110809190611fc4565b506110b630836001846110939190611f2d565b815181106110a4576110a3611e45565b5b602002602001015160000151866116d6565b50816001826110c59190611f2d565b815181106110d6576110d5611e45565b5b60200260200101516000015173ffffffffffffffffffffffffffffffffffffffff167fdaec4582d5d9595688c8c98545fdd1c696d41c6aeaeb636737e84ed2f5c00eda8560405161112791906118e8565b60405180910390a2611197565b611143308460000151866116d6565b50826000015173ffffffffffffffffffffffffffffffffffffffff167fdaec4582d5d9595688c8c98545fdd1c696d41c6aeaeb636737e84ed2f5c00eda8560405161118e91906118e8565b60405180910390a25b60006003600086815260200190815260200160002060040160006101000a81548160ff02191690831515021790555050505050565b600033846000600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16636352211e836040518263ffffffff1660e01b815260040161122d91906118e8565b602060405180830381865afa15801561124a573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061126e9190612063565b90508273ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16146112a857600080fd5b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166342842e0e33308a6040518463ffffffff1660e01b815260040161130793929190611f61565b600060405180830381600087803b15801561132157600080fd5b505af1158015611335573d6000803e3d6000fd5b50505050611341611825565b33816000019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff168152505042816020018181525050864261138f919061201a565b81604001818152505085816060018181525050600181608001901515908115158152505080600360008a815260200190815260200160002060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010155604082015181600201556060820151816003015560808201518160040160006101000a81548160ff0219169083151502179055509050506000600560008a8152602001908152602001600020819055503373ffffffffffffffffffffffffffffffffffffffff167f3249a06bd478adf780bb7930214ad005e9cfa517d63221c0b77f27199d1a58b3896040516114ae91906118e8565b60405180910390a260019450505050509392505050565b6114cd611594565b80600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b611519611594565b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1603611588576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161157f90612113565b60405180910390fd5b61159181611612565b50565b61159c6117e6565b73ffffffffffffffffffffffffffffffffffffffff166115ba610891565b73ffffffffffffffffffffffffffffffffffffffff1614611610576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016116079061217f565b60405180910390fd5b565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050816000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a35050565b600080600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff1663095ea7b385856040518363ffffffff1660e01b8152600401611739929190611ff1565b600060405180830381600087803b15801561175357600080fd5b505af1158015611767573d6000803e3d6000fd5b505050508073ffffffffffffffffffffffffffffffffffffffff166323b872dd8686866040518463ffffffff1660e01b81526004016117a893929190611f61565b600060405180830381600087803b1580156117c257600080fd5b505af11580156117d6573d6000803e3d6000fd5b5050505060019150509392505050565b600033905090565b6040518060600160405280600073ffffffffffffffffffffffffffffffffffffffff16815260200160008152602001600081525090565b6040518060a00160405280600073ffffffffffffffffffffffffffffffffffffffff1681526020016000815260200160008152602001600081526020016000151581525090565b600080fd5b600080fd5b6000819050919050565b61188981611876565b811461189457600080fd5b50565b6000813590506118a681611880565b92915050565b6000602082840312156118c2576118c161186c565b5b60006118d084828501611897565b91505092915050565b6118e281611876565b82525050565b60006020820190506118fd60008301846118d9565b92915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061192e82611903565b9050919050565b61193e81611923565b811461194957600080fd5b50565b60008135905061195b81611935565b92915050565b600080fd5b600080fd5b600080fd5b60008083601f84011261198657611985611961565b5b8235905067ffffffffffffffff8111156119a3576119a2611966565b5b6020830191508360018202830111156119bf576119be61196b565b5b9250929050565b6000806000806000608086880312156119e2576119e161186c565b5b60006119f08882890161194c565b9550506020611a018882890161194c565b9450506040611a1288828901611897565b935050606086013567ffffffffffffffff811115611a3357611a32611871565b5b611a3f88828901611970565b92509250509295509295909350565b60007fffffffff0000000000000000000000000000000000000000000000000000000082169050919050565b611a8381611a4e565b82525050565b6000602082019050611a9e6000830184611a7a565b92915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b611ad981611923565b82525050565b6000611aeb8383611ad0565b60208301905092915050565b6000602082019050919050565b6000611b0f82611aa4565b611b198185611aaf565b9350611b2483611ac0565b8060005b83811015611b55578151611b3c8882611adf565b9750611b4783611af7565b925050600181019050611b28565b5085935050505092915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b611b9781611876565b82525050565b6000611ba98383611b8e565b60208301905092915050565b6000602082019050919050565b6000611bcd82611b62565b611bd78185611b6d565b9350611be283611b7e565b8060005b83811015611c13578151611bfa8882611b9d565b9750611c0583611bb5565b925050600181019050611be6565b5085935050505092915050565b60006060820190508181036000830152611c3a8186611b04565b90508181036020830152611c4e8185611bc2565b90508181036040830152611c628184611bc2565b9050949350505050565b600060208284031215611c8257611c8161186c565b5b6000611c908482850161194c565b91505092915050565b611ca281611923565b82525050565b60008115159050919050565b611cbd81611ca8565b82525050565b600060a082019050611cd86000830188611c99565b611ce560208301876118d9565b611cf260408301866118d9565b611cff60608301856118d9565b611d0c6080830184611cb4565b9695505050505050565b60008060408385031215611d2d57611d2c61186c565b5b6000611d3b85828601611897565b9250506020611d4c85828601611897565b9150509250929050565b6000606082019050611d6b6000830186611c99565b611d7860208301856118d9565b611d8560408301846118d9565b949350505050565b6000602082019050611da26000830184611c99565b92915050565b600080600060608486031215611dc157611dc061186c565b5b6000611dcf86828701611897565b9350506020611de086828701611897565b9250506040611df186828701611897565b9150509250925092565b6000602082019050611e106000830184611cb4565b92915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000611eae82611876565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8203611ee057611edf611e74565b5b600182019050919050565b600081519050611efa81611880565b92915050565b600060208284031215611f1657611f1561186c565b5b6000611f2484828501611eeb565b91505092915050565b6000611f3882611876565b9150611f4383611876565b9250828203905081811115611f5b57611f5a611e74565b5b92915050565b6000606082019050611f766000830186611c99565b611f836020830185611c99565b611f9060408301846118d9565b949350505050565b611fa181611ca8565b8114611fac57600080fd5b50565b600081519050611fbe81611f98565b92915050565b600060208284031215611fda57611fd961186c565b5b6000611fe884828501611faf565b91505092915050565b60006040820190506120066000830185611c99565b61201360208301846118d9565b9392505050565b600061202582611876565b915061203083611876565b925082820190508082111561204857612047611e74565b5b92915050565b60008151905061205d81611935565b92915050565b6000602082840312156120795761207861186c565b5b60006120878482850161204e565b91505092915050565b600082825260208201905092915050565b7f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160008201527f6464726573730000000000000000000000000000000000000000000000000000602082015250565b60006120fd602683612090565b9150612108826120a1565b604082019050919050565b6000602082019050818103600083015261212c816120f0565b9050919050565b7f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572600082015250565b6000612169602083612090565b915061217482612133565b602082019050919050565b600060208201905081810360008301526121988161215c565b905091905056fea264697066735822122017ac38895240fbbaa71bb26af0053ca49ed22cb446e13ccd996b1ed6bda89c2564736f6c63430008100033";

    public static final String FUNC_AUCTIONS = "auctions";

    public static final String FUNC_BIDINDEXES = "bidIndexes";

    public static final String FUNC_BIDS = "bids";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_ONERC721RECEIVED = "onERC721Received";

    public static final String FUNC_SETERC20 = "setErc20";

    public static final String FUNC_SETSUDALFARM = "setSudalFarm";

    public static final String FUNC_CREATEAUCTION = "createAuction";

    public static final String FUNC_GETAUCTIONBYTOKENID = "getAuctionByTokenId";

    public static final String FUNC_BIDONAUCTION = "bidOnAuction";

    public static final String FUNC_GETBIDBYTOKENID = "getBidByTokenId";

    public static final String FUNC_ENDAUCTION = "endAuction";

    public static final Event AUCTIONCREATED_EVENT = new Event("AuctionCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event AUCTIONENDED_EVENT = new Event("AuctionEnded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("1337", "0xEdD9801198Ee41e4E84C3785226849a0cF4d0007");
        _addresses.put("202112031219", "0x8B1686593F8f4Df69D9b4dE42C80716703460d06");
    }

    @Deprecated
    protected SudalAuction(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SudalAuction(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SudalAuction(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SudalAuction(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<AuctionCreatedEventResponse> getAuctionCreatedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(AUCTIONCREATED_EVENT, transactionReceipt);
        ArrayList<AuctionCreatedEventResponse> responses = new ArrayList<AuctionCreatedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AuctionCreatedEventResponse typedResponse = new AuctionCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AuctionCreatedEventResponse> auctionCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, AuctionCreatedEventResponse>() {
            @Override
            public AuctionCreatedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(AUCTIONCREATED_EVENT, log);
                AuctionCreatedEventResponse typedResponse = new AuctionCreatedEventResponse();
                typedResponse.log = log;
                typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AuctionCreatedEventResponse> auctionCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AUCTIONCREATED_EVENT));
        return auctionCreatedEventFlowable(filter);
    }

    public List<AuctionEndedEventResponse> getAuctionEndedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(AUCTIONENDED_EVENT, transactionReceipt);
        ArrayList<AuctionEndedEventResponse> responses = new ArrayList<AuctionEndedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AuctionEndedEventResponse typedResponse = new AuctionEndedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.getter = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AuctionEndedEventResponse> auctionEndedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, AuctionEndedEventResponse>() {
            @Override
            public AuctionEndedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(AUCTIONENDED_EVENT, log);
                AuctionEndedEventResponse typedResponse = new AuctionEndedEventResponse();
                typedResponse.log = log;
                typedResponse.getter = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AuctionEndedEventResponse> auctionEndedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AUCTIONENDED_EVENT));
        return auctionEndedEventFlowable(filter);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public RemoteFunctionCall<Tuple5<String, BigInteger, BigInteger, BigInteger, Boolean>> auctions(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_AUCTIONS, 
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple5<String, BigInteger, BigInteger, BigInteger, Boolean>>(function,
                new Callable<Tuple5<String, BigInteger, BigInteger, BigInteger, Boolean>>() {
                    @Override
                    public Tuple5<String, BigInteger, BigInteger, BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, BigInteger, BigInteger, BigInteger, Boolean>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (Boolean) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> bidIndexes(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BIDINDEXES, 
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple3<String, BigInteger, BigInteger>> bids(BigInteger param0, BigInteger param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BIDS, 
                Arrays.<Type>asList(new Uint256(param0),
                new Uint256(param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<String, BigInteger, BigInteger>>(function,
                new Callable<Tuple3<String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new Address(newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> onERC721Received(String param0, String param1, BigInteger param2, byte[] param3) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ONERC721RECEIVED, 
                Arrays.<Type>asList(new Address(param0),
                new Address(param1),
                new Uint256(param2),
                new org.web3j.abi.datatypes.DynamicBytes(param3)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes4>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> setErc20(String _contractAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETERC20, 
                Arrays.<Type>asList(new Address(_contractAddress)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setSudalFarm(String _contractAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETSUDALFARM, 
                Arrays.<Type>asList(new Address(_contractAddress)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> createAuction(BigInteger _tokenId, BigInteger _time, BigInteger _firstPrice) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CREATEAUCTION, 
                Arrays.<Type>asList(new Uint256(_tokenId),
                new Uint256(_time),
                new Uint256(_firstPrice)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple5<String, BigInteger, BigInteger, BigInteger, Boolean>> getAuctionByTokenId(BigInteger _tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETAUCTIONBYTOKENID, 
                Arrays.<Type>asList(new Uint256(_tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple5<String, BigInteger, BigInteger, BigInteger, Boolean>>(function,
                new Callable<Tuple5<String, BigInteger, BigInteger, BigInteger, Boolean>>() {
                    @Override
                    public Tuple5<String, BigInteger, BigInteger, BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, BigInteger, BigInteger, BigInteger, Boolean>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (Boolean) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> bidOnAuction(BigInteger _tokenId, BigInteger _price) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BIDONAUCTION, 
                Arrays.<Type>asList(new Uint256(_tokenId),
                new Uint256(_price)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple3<List<String>, List<BigInteger>, List<BigInteger>>> getBidByTokenId(BigInteger _tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETBIDBYTOKENID, 
                Arrays.<Type>asList(new Uint256(_tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<Tuple3<List<String>, List<BigInteger>, List<BigInteger>>>(function,
                new Callable<Tuple3<List<String>, List<BigInteger>, List<BigInteger>>>() {
                    @Override
                    public Tuple3<List<String>, List<BigInteger>, List<BigInteger>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<List<String>, List<BigInteger>, List<BigInteger>>(
                                convertToNative((List<Address>) results.get(0).getValue()), 
                                convertToNative((List<Uint256>) results.get(1).getValue()), 
                                convertToNative((List<Uint256>) results.get(2).getValue()));
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> endAuction(BigInteger _tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ENDAUCTION, 
                Arrays.<Type>asList(new Uint256(_tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static SudalAuction load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SudalAuction(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SudalAuction load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SudalAuction(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SudalAuction load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SudalAuction(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SudalAuction load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SudalAuction(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SudalAuction> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SudalAuction.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SudalAuction> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SudalAuction.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<SudalAuction> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SudalAuction.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SudalAuction> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SudalAuction.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class AuctionCreatedEventResponse extends BaseEventResponse {
        public String seller;

        public BigInteger tokenId;
    }

    public static class AuctionEndedEventResponse extends BaseEventResponse {
        public String getter;

        public BigInteger tokenId;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
