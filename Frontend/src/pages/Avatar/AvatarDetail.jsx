import React, { useState } from "react";
import Navbar from "../../components/Navbar/Navbar";

// import OnSale from "../../components/NFTDetail/OnSale";
import NFTInfo from "../../components/NFTDetail/NFTInfo";
import DealInfo from "../../components/NFTDetail/DealInfo";
// import UnSoldOwner from "../../components/NFTDetail/UnSoldOwner";
// import UnSold from "../../components/NFTDetail/UnSold";

import "./AvatarDetail.css";
import { useEffect } from "react";
import { useLocation } from "react-router-dom";

import shop from "../../api/shop";

function AvatarDetail() {
  const location = useLocation();
  const [nftInfo, setNftInfo] = useState('');

  const nftId = location.state.nftId;
  useEffect(() => {
    shop
      .nftDetailOne(nftId)
      .then((result) => {
        setNftInfo(result.data)
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <div className="avatar-wrap">
      <Navbar />
      <div className="detail-content">
        <NFTInfo nftInfo={nftInfo}/>
        <DealInfo dealInfo={nftInfo}/>
        {/* {nftInfo.saled ? (
          <div className="sale-wrap">
            <OnSale />
          </div>
        ) : userId == nftInfo.userId ? (
          <div className="sale-wrap">
            <UnSoldOwner />
          </div>
        ) : (
          <div className="sale-wrap">
            <UnSold />
          </div>
        )} */}
      </div>
    </div>
  );
}

export default AvatarDetail;
