import React, { useState, useEffect } from "react";
import Web3 from "web3";
import {
  SudalAuctionABI,
  SudalAuctionAddress,
  SudalFarmABI,
  SudalFarmAddress,
} from "../../util/web3abi";
import Modal from "../Modal/LoadingModal";
import DateTimePicker from "react-datetime-picker";

import "./UnSoldOwner.css";
import BidList from "./BidList.jsx";

import shop from "../../api/shop";
import { Navigate, useNavigate } from "react-router-dom";
function UnSoldOwner(props) {
  const navigate = useNavigate();
  const [resInfo, setResInfo] = useState(null);
  useEffect(() => {
    const params = props.nftId;
    shop
      .nftUnsoldOne(params)
      .then((result) => {
        setResInfo(result.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);
  //가격
  const [price, setPrice] = useState(1);
  const sellPriceChange = (e) => {
    setPrice(e.target.value);
  };

  //분양하기 클릭할 경우
  const auctionClick = () => {
    if (price > 0) {
      for(let i = 0; i < price.length; i++) {
        if(price.slice(i, i+1) === '.') {
            alert("소수점 값은 입력 불가능합니다")
            return
        }
      }
      auctionTest();
    }
    else if(price < 0) {
      alert("음수는 입력 불가능합니다")
    }
    else {
      alert("1 이상의 숫자 값만 입력해주세요")
    }
  };

  //초기 날짜 = 지금 현재 시간
  const currentDate = new Date();
  const [auctionTime, setAuctionTime] = useState(currentDate);

  //날짜를 선택했을 경우
  function handleDateChange(value) {
    setAuctionTime(value);
  }

  const tokenId = localStorage.getItem("tokenId");
  // const [account, setAccount] = useState()
  const [loading, setLoading] = useState(false);

  const auctionTest = async () => {
    let web3 = new Web3(window.ethereum);
    const accounts = await web3.eth.requestAccounts();
    const SudalFarmContract = new web3.eth.Contract(
      SudalFarmABI,
      SudalFarmAddress
    );
    //모달 띄우기
    setLoading(true);
    // nft 권한 허용
    const approval = await SudalFarmContract.methods
      .approve(SudalAuctionAddress, tokenId)
      .send({ from: accounts[0] });

    if (approval.status) {
      // 경매 시작
      const SudalAuctionContract = new web3.eth.Contract(
        SudalAuctionABI,
        SudalAuctionAddress
      );
      await SudalAuctionContract.methods
        .createAuction(
          tokenId,
          Math.floor((auctionTime.getTime() - new Date().getTime()) / 1000),
          price
        )
        .send({ from: accounts[0] });
    }
    await setLoading(false);
    alert("분양을 완료했달! 확인을 누르면 마이페이지로 넘어간달!");
    navigate("/myPage");
  };
  if (resInfo !== null) {
    return (
      <div>
        <Modal open={loading} setLoading={setLoading} />
        <BidList
          style={{ margin: "30px 0" }}
          title="요청 내역"
          date="요청 시간"
          price="제안가(SSF)"
          bidLog={resInfo}
        />
        <hr />
        <div className="sell-request">
          <h3>분양하기</h3>
          <div className="bid">
            <span>분양가</span>
            <div>
              <input
                type="number"
                min={1}
                defaultValue={1}
                onChange={sellPriceChange}
                className="bid-input"
                style={{
                  margin: "0",
                  textAlign: "right",
                  fontFamily: "neo",
                  fontSize: "20px",
                  width: "205px",
                }}
              />
              <span> SSF</span>
            </div>
            <span style={{color: "red", fontSize: "15px", marginLeft: "336px"}}>소수점 입력 불가</span>
          </div>
          <div className="sell-end-date">
            <span>분양 마감일</span>
            <DateTimePicker
              format="yyyy-MM-dd HH:mm"
              minDate={currentDate}
              onChange={handleDateChange}
              value={auctionTime}
            />
          </div>
          <div className="request-btn-wrap">
            <button className="request-btn" onClick={auctionClick}>
              분양하기
            </button>
          </div>
        </div>
      </div>
    );
  }
}

export default UnSoldOwner;
