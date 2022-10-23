import React, { useEffect, useState } from "react";
// import NumberFormat from 'react-number-format';

import BidList from "./BidList.jsx"

import shop from "../../api/shop";

function UnSold(props) {
    const nftId = props.nftId
    //nft 판매 요청하는 값
    const [price, setPrice] = useState(0)
    const requestPriceChange = (e) => {
        setPrice(e.target.value)
    }
    const [reqInfo, setReqInfo] = useState(null);
    //요청하기 클릭했을 경우
    const requestClick = () => {
        if (price > 0) {
            for(let i = 0; i < price.length; i++) {
                if(price.slice(i, i+1) === '.') {
                    alert("소수점 값은 입력 불가능합니다")
                    return
                }
            }
            const params = {
                nftId: nftId,
                price : price
            }
            shop
            .nftReqSale(params)
            .then((result) => {
                window.location.reload()
            })
            .catch((error) => {
                console.log(error);
            });
        }
        else if(price < 0) {
            alert("음수는 입력 불가능합니다")
        }
        else {
            alert("1 이상의 숫자 값만 입력해주세요")
        }
    }
    //요청 내역 조회
    useEffect(() => {
        const params = nftId;
        shop
        .nftUnsoldOne(params)
        .then((result) => {
            setReqInfo(result.data)
        })
        .catch((error) => {
            console.log(error)
        })
    }, [])


    if(reqInfo !== null) {
        return( 
            <div>
                <div className="sell-request">
                    <h3>분양 요청</h3>
                    <div className="bid">
                        <span>요청가</span>
                        <div>
                            <input type="number" onChange={requestPriceChange} className="bid-input"
                            min={1} defaultValue={1}
                            style={{marginRight: "10px", textAlign: "right", fontFamily: 'neo', fontSize: '20px', width: '205px'}}/>
                            <button className="bid-btn" onClick={requestClick}>요청</button>
                        </div>
                        <span style={{color: "red", fontSize: "15px", marginLeft: "336px"}}>소수점 입력 불가</span>
                    </div>
                </div>
                <hr/>
                <BidList title="요청 내역" time="요청 시간" price="제안가(SSF)" bidLog={reqInfo}/>
            </div>
        )
    }
}

export default UnSold;