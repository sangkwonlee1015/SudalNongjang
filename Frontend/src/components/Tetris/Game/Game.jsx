import Menu from "../../../components/Tetris/Menu/Menu";
import Tetris from "../../../components/Tetris/Tetris/Tetris";
import ResetGame from "../ResetGame/ResetGame";
import { useGameOver } from "../../../hooks/useGameOver";
import { useGameStats } from "../../../hooks/useGameStats";
const Game = ({ rows, columns }) => {
  const [gameOver, setGameOver, resetGameOver] = useGameOver();
  const [gameStats, addLinesCleared] = useGameStats();
  const start = () => resetGameOver();

  return (
    <div className="Game">
      {gameOver ? (
        <Tetris
          rows={rows}
          columns={columns}
          setGameOver={setGameOver}
          gameStats={gameStats}
          addLinesCleared={addLinesCleared}
        />
      ) : (
        <ResetGame onClick={start} gameStats={gameStats} />
      )}
    </div>
  );
};

export default Game;
