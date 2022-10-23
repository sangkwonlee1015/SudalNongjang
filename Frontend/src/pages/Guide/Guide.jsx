import Navbar from "../../components/Navbar/Navbar";
import "./Guide.css";
import Button from "@material-ui/core/Button";
import fallingGame from "../../assets/images/falling-game-thumbnail.png";
import cardGame from "../../assets/images/CardGame/card-guide.PNG"
import tetrisGame from "../../assets/images/tetris-guide.png";
import { Link } from "react-router-dom";
import { fontWeight } from "@mui/system";
const Guide = () => {
  return (
    <div className="guide">
      <Navbar></Navbar>
      <div className="description">
        <div className="title">수달농장</div>
        <div className="line"></div>
        <p className="content">
          수달농장은 게임을 통해 수달 머니💰를 모아서 나만의 수달(NFT)을 분양받는 서비스에요<br/><br/>
          게임을 플레이해 보거나 수달샵에서 다른 사람들의 수달을 구경하는 것은 계정연결 없이도 가능해요<br/><br/>
          하지만 나만의 수달을 얻고 싶다면 상단 제일 오른쪽에 있는 <span style={{color: "red"}}>*계정연결*</span>을 눌러주세요!<br/><br/>
          계정연결을 하기 위해서는 메타마스크가 필요해요! 메타마스크 이용방법을 모르겠다고요? 다음의 안내에 따라주세요
          <Link to="/metaGuide" style={{textDecoration: "none"}}>
            <Button
              style={{
                fontFamily: "neo",
                fontWeight: "bold",
                backgroundColor: "#DAB49D",
                marginLeft: "10px",
              }}
              variant="contained"
              href="/game"
            >
            이용방법
            </Button>
          </Link>
        </p>
        <div className="title">수달꾸미기</div>
        <div className="line"></div>
        <p className="content">
          게임을 통해서 열심히 수달머니를 모으셨나요? 그럼 나만의 수달을 꾸며 볼 차례에요!<br/><br/>
          수달을 꾸밀 수 있는 부위는 머리(👒) 눈(🕶) 입(👅) 손(🍭) 옷(👚) 이렇게 다섯 부위에요<br/><br/>
          각 부위마다 아이템을 뽑을 수 있고 <span style={{color: "#757575", fontWeight: "bold"}}>노말(50%) </span>  
          <span style={{color: "#409bf6", fontWeight: "bold"}}>레어(40%) </span> 
          <span style={{color: "rgb(229 131 19)", fontWeight: "bold"}}>에픽(10%)</span>의 확률로 나누어져 있어요!
        </p>
        <div className="title">수달샵</div>
        <div className="line"></div>
        <p className="content">
          <p style={{fontWeight: "bold", fontSize: "25px"}}>수달 분양하기</p>
          <span>수달하우스에서 내가 가진 수달들 중에 더 좋은 주인을 만났으면 하는 수달이 생기셨나요?</span><br/><br/>
          <span>그렇다면 최소 이 정도를 받고 보내야겠다! 하는 분양가와 분양을 마감할 날짜를 정하고 분양하기를 해보세요!</span><br/><br/>
          <span>다른 사람들이 원한다면 입찰을 시도할 것이고 마감 날짜에 제일 높은 입찰가를 받고 수달을 떠나 보내게 될 거에요</span>
          <p style={{fontWeight: "bold", fontSize: "25px"}}>수달 분양받기</p>
          <span>엇 저기 수달 너무 갖고 싶다! 하는 생각이 드셨나요? 그렇다면 수달의 분양 상태를 확인해보세요!</span><br/><br/>
          <span>아쉽게도 미분양인가요? 그러면 이 가격 정도에 분양을 해달라고 요청을 하며 소유자에게 어필을 할 수 있어요</span><br/><br/>
          <span>아니면 현재 분양중인가요? 그럼 입찰을 시도하면 됩니다! 인기 있는 수달이라면 마감 날짜까지 내가 최고가인지 잘 확인해야겠죠?</span>
        </p>
        <div className="title">
          게임하기
          <Button
            style={{
              fontFamily: "neo",
              fontWeight: "bold",
              backgroundColor: "#DAB49D",
              marginLeft: "10px",
            }}
            variant="contained"
            href="/game"
          >
            게임하러 가기
          </Button>
        </div>
        <div className="line"></div>
        <div className="game">
          <Link to="/fallingGame">
            <img src={fallingGame} alt="game" className="game-thumbnail"></img>
          </Link>
          <p className="content">
            <strong>수달은 아직도 배고프다</strong>
            <br />
            <br />
            먹어도 먹어도 배가 고픈 수달은 오늘도 열심히 먹이(🦞🐟🐚)를 찾아다니고 있어요
            <br/>
            <br/>
            하늘에서 떨어지는 먹이를 받아 수달을 배부르게 해주세요🍴
          </p>
        </div>
        <div className="game">
          <Link to="/cardGame">
            <img src={cardGame} alt="game" className="game-thumbnail"></img>
          </Link>
          <p className="content">
            <strong>같은 수달 찾기</strong>
            <br />
            <br />
            패션에 대해 관심이 많은 수달은 다양한 스타일👕👖🧦을 추구하고 있어요
            <br/>
            <br/>
            그 중에 똑같이 입은 수달을 찾아 맞춰보세요🔍
          </p>
        </div>
        <div className="game">
          <Link to="/tetris">
            <img src={tetrisGame} alt="game" className="game-thumbnail"></img>
          </Link>
          <p className="content">
            <strong>테트리스</strong>
            <br />
            <br />
            하늘에서 하나씩 떨어지는 블록을 바닥에서부터 차근차근 쌓아갈 수 있어요
            <br />
            <br />
            한 줄을 꽉 채운다면 그 줄의 블록이 사라지고 점수를 얻을 수 있어요
            <br/>
            <br/>
            점수를 쌓을수록 블록이 떨어지는 속도가 빨라지니 주의하면서 블록이 맨 위까지 닿지 않도록 열심히 해주세요!🎮
          </p>
        </div>
      </div>
    </div>
  );
};

export default Guide;
