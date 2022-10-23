import React, { useState, useEffect } from "react";
import BidList from "./BidList.jsx";

import shop from "../../api/shop";

import "./OnSale.css";

import Web3 from "web3";
import {
  ERC20ABI,
  ERC20Address,
  SudalAuctionABI,
  SudalAuctionAddress,
} from "../../util/web3abi";

import Modal from "../Modal/BidModal";
//가격
function Price(props) {
  const [price, setPrice] = useState(0);
  const bidPriceChange = (e) => {
    const changePrice = e.target.value
    setPrice(changePrice)
  }
  //입찰 버튼 클릭
  const bidClick = () => {
    if(currentPrice >= price) {
      alert("현재 가격보다 더 높은 가격으로 입찰해주세요")
    }
    else {
      for(let i = 0; i < price.length; i++) {
        if(price.slice(i, i+1) === '.') {
          alert("소수점 값은 입력 불가능합니다")
          return
        }
      }
      bidTest()
    }
  }

  let currentPrice = Math.floor(props.price)
  useEffect(() => {
    if(price === 0) {
      currentPrice = Math.floor(props.price)
      setPrice(currentPrice + 1)
    }
  })

  const tokenId = localStorage.getItem("tokenId");
  const userId = localStorage.getItem("userId");
  const id = props.id;
  const [loading, setLoading] = useState(false);
  const bidTest = async () => {
    setLoading(true);
    let web3 = new Web3(window.ethereum);
    const accounts = await web3.eth.requestAccounts();
    const ERC20Contract = new web3.eth.Contract(ERC20ABI, ERC20Address);
    // 싸피 토큰 권한 전달
    const approval = await ERC20Contract.methods
      .approve(SudalAuctionAddress, price)
      .send({ from: accounts[0] });
    if (approval.status) {
      // 입찰
      const SudalAuctionContract = new web3.eth.Contract(
        SudalAuctionABI,
        SudalAuctionAddress
      );
      let bid = await SudalAuctionContract.methods
        .bidOnAuction(tokenId, price)
        .send({ from: accounts[0] });
    }
    await setLoading(false);
    alert("입찰을 완료했달! 반영은 조금 걸릴 수 있달!");
  };

    return(
      <div className="price-info">
        <div className="current">
          <span>현재가</span>
          <span>{currentPrice} SSF</span>
        </div>
        <Modal open={loading} />
          {id != userId ? (
              <div className="bid">
                <span>입찰가</span>
                <div>
                  <input type="number" min={currentPrice + 1} defaultValue={currentPrice + 1} onChange={bidPriceChange} className="bid-input"
                  style={{marginRight: "10px", textAlign: "right", fontFamily: 'neo', fontSize: '20px', width: '205px'}}/>
                  <button className="bid-btn" onClick={bidClick}>입찰</button>
                </div>
                <span style={{color: "red", fontSize: "15px", marginLeft: "336px"}}>소수점 입력 불가</span>
              </div>
          ) : (<div></div>)}
        </div>
    )
}

function OnSale(props) {
  //분양 내역 조회
  const [dealInfo, setDealInfo] = useState(null);

  useEffect(() => {
    const params = props.nftInfo.id;
    shop
      .nftOnsaleOne(params)
      .then((result) => {
        setDealInfo(result.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);
  if (dealInfo !== null) {
    return (
      <div>
        <div className="sale-info">
          <h3>분양 정보</h3>
          <div className="auction-date">
            <p>진행기간</p>
            <p>
              {dealInfo.start.substring(0, 19)} ~{" "}
              {dealInfo.end.substring(0, 19)}
            </p>
          </div>
        </div>
        <hr />
        <Price price={props.nftInfo.price} id={props.nftInfo.userId} />
        <hr />
        <BidList
          title="입찰 내역"
          date="입찰 시간"
          price="입찰 가격(SSF)"
          bidLog={dealInfo.bidLogs}
        />
      </div>
    );
  }
}

export default OnSale;
