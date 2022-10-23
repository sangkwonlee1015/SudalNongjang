import Navbar from "../../components/Navbar/Navbar";
import "./MetaGuide.css";
import metaGuide1 from "../../assets/images/Guide/meta-guide1.PNG"
import metaGuide2 from "../../assets/images/Guide/meta-guide2.PNG"
import metaGuide3 from "../../assets/images/Guide/meta-guide3.PNG"
import metaGuide4 from "../../assets/images/Guide/meta-guide4.PNG"
import metaGuide5 from "../../assets/images/Guide/meta-guide5.png"
import metaGuide6 from "../../assets/images/Guide/meta-guide6.PNG"

const MetaGuide = () => {
    return (
        <div className="guide">
            <Navbar></Navbar>
            <div className="description">
                <div className="title">메타마스크 이용방법🦊</div>
                <div className="line"></div>
                <div className="meta-guide">
                    <div className="meta-guide-wrap">
                        <h3>1. 크롬 익스텐션 설치</h3>
                        <div>
                            <img src={metaGuide1} alt="guide1" className="meta-guide-img"></img>
                        </div>
                    </div>
                    <div className="meta-guide-wrap">
                        <h3>2. 지갑 가져오기 또는 생성</h3>
                        <div>
                            <img src={metaGuide2} alt="guide2" className="meta-guide-img"></img>
                        </div>
                    </div>
                </div>
                <div className="title">싸피 네트워크 연결</div>
                <div className="line"></div>
                <div className="meta-guide">
                    <div className="meta-guide-wrap">
                        <h3>1. 싸피 네트워크 추가</h3>
                        <div>
                            <img src={metaGuide3} alt="guide3" className="meta-guide-img"></img>
                        </div>
                    </div>
                    <div className="meta-guide-wrap">
                        <h3>2. 네트워크 수동 추가</h3>
                        <div style={{display: "flex", alignItems: "center"}}>
                            <img src={metaGuide4} alt="guide4" className="meta-guide-img" style={{width: "50%"}}></img>
                            <span style={{fontSize: "20px", marginLeft: "30px"}}>
                                1. 네트워크 이름 : SSAFY<br/><br/>
                                2. 새 RPC URL(아래 3개 중 하나 선택)<br/><br/>
                                ① http://20.196.209.2:8545 <br/><br/>
                                ② http://52.141.42.92:8545 <br/><br/>
                                ③ http://20.41.85.203:8545 <br/><br/>
                                3. 체인 ID : 31221<br/><br/>
                                4. 통화 기호 : SSF
                            </span>
                        </div>
                    </div>
                </div>
                <div className="title">싸피 토큰 가져오기</div>
                <div className="line"></div>
                <div className="meta-guide">
                    <div className="meta-guide-wrap">
                        <h3>1. 토큰 가져오기</h3>
                        <div>
                            <img src={metaGuide5} alt="guide5" className="meta-guide-img"></img>
                        </div>
                    </div>
                    <div className="meta-guide-wrap">
                        <h3>2. 맞춤형(SSF) 토큰 가져오기</h3>
                        <div style={{display: "flex", alignItems: "center"}}>
                            <img src={metaGuide6} alt="guide4" className="meta-guide-img" style={{width: "50%"}}></img>
                            <span style={{fontSize: "20px", marginLeft: "30px"}}>
                                토큰 계약 주소<br/><br/>
                                <span style={{color: "red"}}>0x0c54E456CE9E4501D2c43C38796ce3F06846C966</span><br/><br/>
                                토큰 기호는 자동으로 'SSF'로 입력됨<br/><br/>
                                바로 맞춤형 토큰 추가 버튼 클릭
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default MetaGuide;
