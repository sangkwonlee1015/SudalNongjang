import React, { useEffect } from "react";
import "./ResetGame.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";
function ResetGame({ onClick, gameStats }) {
  const navigate = useNavigate();
  const { level, points, linesCompleted, linesPerLevel } = gameStats;
  const point = points / 10;
  const token = window.localStorage.getItem("token");
  useEffect(() => {
    axios
      .put(`https://j7a606.p.ssafy.io/api/game/point/${point}`, point, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => console.log(res));
  }, []);
  return (
    <div className="gameOver">
      <span style={{ fontSize: "5em" }}>GameOver</span>
      <span style={{ fontSize: "4em" }}>score {points}</span>
      <div className="btnDiv">
        <button className="retryBtn" onClick={onClick}>
          다시하기
        </button>
      </div>
    </div>
  );
}

export default ResetGame;
