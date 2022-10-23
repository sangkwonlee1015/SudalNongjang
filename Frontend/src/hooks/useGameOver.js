import { useState, useCallback } from "react";

export const useGameOver = () => {
  const [gameOver, setGameOver] = useState(true);

  const resetGameOver = useCallback(() => {
    setGameOver(true);
  }, []);

  return [gameOver, setGameOver, resetGameOver];
};
