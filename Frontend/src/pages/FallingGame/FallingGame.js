import React, {
  useCallback,
  useEffect,
  useRef,
  useState,
  useContext,
} from "react";
import { useRecoilState } from "recoil";
import { TransactionContext } from "../../context/TransactionContext";
import {
  TYPES,
  SPEED_STEP,
  SPAWN_INTERVAL,
} from "../../components/FallingGame/constants";
import {
  controlOptions,
  dotsState,
  timeState,
} from "../../components/FallingGame/atom";
import { createDot, calculatePoints } from "../../components/FallingGame/utils";
import Item from "../../components/FallingGame/Item";
import Score from "../../components/FallingGame/Score";
import Timer from "../../components/FallingGame/Timer";
import "./FallingGame.css";
import Header from "../../components/GameHeader/GameHeader";
import otter from "../../assets/images/otter-basket.png";
import Button from "@material-ui/core/Button";
import game from "../../api/game";

const FallingGame = () => {
  const [dots, updateDots] = useRecoilState(dotsState);
  const [controlState, setControlState] = useRecoilState(controlOptions);
  const [time, setTime] = useRecoilState(timeState);
  const [score, setScore] = useState(0);
  const [showGuide, setGuide] = useState(false);
  const { currentAccount } = useContext(TransactionContext);
  const intervalRef = useRef();
  const fieldRef = useRef();
  const requestRef = useRef();

  const OtterStyle = {
    position: "absolute",
    bottom: 0,
    left: "630px",
    height: "150px",
  };

  // 아이템 컨트롤
  const advanceStep = useCallback(() => {
    const addScore = (dot) => {
      setScore((prev) => prev + calculatePoints(dot));
    };
    updateDots((oldDots) => {
      const newDots = [];
      oldDots.map((dot) => {
        const dotX =
          ((fieldRef.current.offsetWidth - dot.height) * dot.x) / 100;
        const y = dot.y + (SPEED_STEP * controlState.speed) / 60;
        const otterX = parseInt(otterRef.current.style.left.slice(0, -2));
        var hasEaten = false;
        if (
          Math.abs(dotX - otterX) <= otterRef.current.offsetWidth &&
          y >=
            fieldRef.current.offsetHeight -
              otterRef.current.offsetHeight -
              dot.height / 2
        ) {
          hasEaten = true;
          addScore(dot);
        }

        const newY = dot.y + (SPEED_STEP * controlState.speed) / 60;
        if (
          newY <= fieldRef.current.offsetHeight - dot.height / 2 &&
          !hasEaten
        ) {
          newDots.push({
            ...dot,
            y: newY,
          });
        }
      });

      return newDots;
    });

    requestRef.current = requestAnimationFrame(advanceStep);
  }, [controlState.speed, updateDots]);

  // 게임 시작
  const onStart = useCallback(() => {
    setControlState({ ...controlState, isRunning: true });
  }, [controlState, setControlState]);

  const spawnDot = useCallback(() => {
    updateDots((oldDots) => [...oldDots, createDot()]);
  }, [updateDots]);

  useEffect(() => {
    const stop = () => {
      intervalRef.current && clearInterval(intervalRef.current);
      requestRef.current && cancelAnimationFrame(requestRef.current);
    };
    if (controlState.isRunning) {
      intervalRef.current = setInterval(spawnDot, SPAWN_INTERVAL);
      requestRef.current = requestAnimationFrame(advanceStep);
    } else {
      stop();
    }
    return () => stop();
  }, [controlState.isRunning, advanceStep, spawnDot]);

  // 리셋
  const clear = useCallback(() => {
    setControlState({ ...controlState, isRunning: false, speed: 10 });
    updateDots([]);
    setScore(0);
    setTime(30);
    setGuide(false);
  }, [setControlState, setScore, updateDots, controlState, setTime]);

  // 수달 좌우로 움직이기
  const otterRef = useRef();
  let moveBy = 15;
  useEffect(() => {
    const moveOtter = (e) => {
      let newLeft;
      switch (e.key) {
        case "ArrowLeft":
          newLeft = parseInt(otterRef.current.style.left) - moveBy;
          if (newLeft < 0) {
            newLeft = 0;
          }
          break;
        case "ArrowRight":
          newLeft = parseInt(otterRef.current.style.left) + moveBy;
          if (newLeft > fieldRef.current.offsetWidth * 0.9) {
            newLeft = fieldRef.current.offsetWidth * 0.9;
          }
          break;
        default:
          break;
      }
      otterRef.current.style.left = `${newLeft}px`;
    };

    document.addEventListener("keydown", moveOtter);

    return () => {
      document.removeEventListener("keydown", moveOtter);
    };
  }, []);

  // 1초씩 제한시간 감소
  useEffect(() => {
    if (controlState.isRunning && time > 0) {
      const interval = setInterval(() => {
        setTime(time - 1);
      }, 1000);

      return () => clearInterval(interval);
    }
  }, [controlState.isRunning, time]);

  // 게임 방법 화면 제어
  const handleGuide = () => {
    setGuide(true);
  };

  // 게임 포인트 저장
  useEffect(() => {
    if (time <= 0 && currentAccount) {
      game
        .sendPoint(score)
        .then((result) => console.log(result))
        .catch((error) => console.log(error));
    }
  }, [time]);

  return (
    <div className="falling-game">
      <Header title={"수달은 아직도 배고프다"} />
      {/* 게임 시작 전 */}
      {!controlState.isRunning ? (
        <div className="game-background-wrap">
          <div className="game-background"></div>
          {!showGuide ? (
            <div>
              {/* 첫 화면 */}
              <h1>수달은 아직도 배고프다</h1>
              <div className="game-button-wrap">
                <Button
                  style={{
                    fontFamily: "neo",
                    fontWeight: "bold",
                    backgroundColor: "#DAB49D",
                    marginLeft: "10px",
                  }}
                  variant="contained"
                  onClick={handleGuide}
                >
                  게임방법
                </Button>
                <Button
                  style={{
                    fontFamily: "neo",
                    fontWeight: "bold",
                    backgroundColor: "#DAB49D",
                    marginLeft: "10px",
                  }}
                  variant="contained"
                  onClick={onStart}
                >
                  시작하기
                </Button>
              </div>
            </div>
          ) : (
            <div className="game-guide-content">
              {/* 게임 방법 화면 */}
              <div className="game-title">수달은 아직도 배고프다</div>
              <h3>게임설명</h3>
              <p className="game-guide">
                안녕 나 애기 수달
                <br />
                나는 언제나 배가 고프지🍴
                <br />
                <strong>좌, 우 방향키</strong>로 나를 움직여서
                <br />
                <span style={{ color: "red" }}>제한 시간</span>동안 더 많은
                먹이를 먹게 해줘
                <br />
                그러면 보답으로 얻은 <strong>점수만큼의 수달머니</strong>를 줄게
                <br />
              </p>
              <h3>아이템 별 점수</h3>
              <div className="game-guide-item">
                {TYPES.map((item, index) => {
                  return (
                    <div>
                      <img
                        style={{ width: "60px" }}
                        src={item}
                        alt={`item${index}`}
                      />
                    </div>
                  );
                })}
              </div>
              <div className="game-guide-item">
                <div>조개 : 20점</div>
                <div>가재 : 50점</div>
                <div>물고기 : 70점</div>
              </div>

              <Button
                style={{
                  fontFamily: "neo",
                  fontWeight: "bold",
                  backgroundColor: "#DAB49D",
                  marginLeft: "10px",
                }}
                variant="contained"
                onClick={onStart}
              >
                시작하기
              </Button>
            </div>
          )}
        </div>
      ) : (
        <div>
          {/* 게임 시작 후 */}
          {time > 0 ? (
            <div className="game-main">
              {/* 게임중 */}
              <div className="panel">
                <Score score={score} />
                <Timer />
              </div>
              <div className="field" ref={fieldRef}>
                {dots.map((dot, index) => {
                  const x =
                    ((fieldRef.current.offsetWidth - dot.height) * dot.x) / 100;
                  return (
                    <Item key={`dot-${index}`} {...dot} x={x} index={index} />
                  );
                })}
                <img
                  style={OtterStyle}
                  src={otter}
                  alt="basket otter"
                  ref={otterRef}
                />
              </div>
            </div>
          ) : (
            <div className="game-background-wrap">
              {/* 게임 종료 */}
              <div className="game-background"></div>
              <div>
                <h1>Game Over!!!</h1>
                <Score style={{ fontSize: "50px" }} score={score} />
                <Button
                  style={{
                    fontFamily: "neo",
                    fontWeight: "bold",
                    backgroundColor: "#DAB49D",
                    marginLeft: "10px",
                  }}
                  variant="contained"
                  onClick={clear}
                >
                  다시하기
                </Button>
              </div>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default FallingGame;
