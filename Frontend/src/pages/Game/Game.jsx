import React from "react";
import { Link } from "react-router-dom";
import cardgame from "../../assets/images/cardgame.gif";
import fallingGame from "../../assets/images/falling-game.gif";
import Navbar from "../../components/Navbar/Navbar";
import Tetris from "../TetrisGame/Tetris";
import tetris from "../../assets/images/tetris.gif";
function Game() {
  const games = [
    // 게임 추가할때마다 여기에서 추가하면 됨~~
    {
      id: 1,
      gameTitle: "수달은 아직도 배고프다",
      gameImg: fallingGame,
      gameLink: "/fallingGame",
    },
    {
      id: 2,
      gameTitle: "같은 수달 찾기",
      gameImg: cardgame,
      gameLink: "/cardGame",
    },
    {
      id: 3,
      gameTitle: "테트리스",
      gameImg: tetris,
      gameLink: "/tetris",
    },
  ];

  return (
    <>
      <Navbar />
      <h1 style={{ padding: "20px 0px 20px 50px" }}>게임목록</h1>
      {games.map((game) => (
        <GameItem game={game} key={game.id} />
      ))}
    </>
  );
}

/** 각 게임 컴포넌트 */
function GameItem({ game }) {
  // 게임 컴포넌트 스타일
  const gameItemStyle = {
    margin: "70px",
    textAlign: "center",
  };
  const titleStyle = {
    fontSize: "20px",
    color: "black",
  };

  // 게임별 컴포넌트
  return (
    <div style={gameItemStyle}>
      <Link to={game.gameLink} style={{ textDecoration: "none" }}>
        <img src={game.gameImg} alt="게임" />
        <p style={titleStyle}>{game.gameTitle}</p>
      </Link>
    </div>
  );
}

export default Game;
