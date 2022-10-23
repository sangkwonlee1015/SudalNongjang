import { TYPES } from "./constants";
export const createDot = () => {
  // pick random drop item
  const randIndex = Math.floor(Math.random() * TYPES.length);
  const type = TYPES[randIndex];
  let height;
  let width;
  if (randIndex === 0) {
    height = 65;
    width = 85;
  } else if (randIndex === 1) {
    height = 90;
    width = 70;
  } else if (randIndex === 2) {
    height = 65;
    width = 110;
  }
  let x = Math.floor(Math.random() * 100);
  return {
    type,
    randIndex,
    height,
    width,
    x,
    y: 0,
  };
};
export const removeDot = (dots, index) => {
  const newDots = [...dots];
  newDots.splice(index, 1);
  return newDots;
};
export const calculatePoints = (dot) => {
  if (dot.randIndex === 0) {
    return 20;
  } else if (dot.randIndex === 1) {
    return 50;
  } else if (dot.randIndex === 2) {
    return 70;
  }
};
