import React from "react";
import OnSale from "../../components/NFTDetail/OnSale";
import UnSoldOwner from "../../components/NFTDetail/UnSoldOwner";
import UnSold from "../../components/NFTDetail/UnSold";

function DealInfo(props) {
    const nftInfo = props.dealInfo
    const userId = localStorage.getItem("userId");
    if(nftInfo !== '') {
        return (
            <div>
                {nftInfo.saled ? (
                    <div className="sale-wrap">
                        <OnSale nftInfo={nftInfo}/>
                    </div>
                ) : userId == nftInfo.userId ? (
                    <div className="sale-wrap">
                        <UnSoldOwner nftId={nftInfo.id}/>
                    </div>
                ) : (
                    <div className="sale-wrap">
                        <UnSold nftId={nftInfo.id}/>
                    </div>
                )}
            </div>
        )
    }
}

export default DealInfo;