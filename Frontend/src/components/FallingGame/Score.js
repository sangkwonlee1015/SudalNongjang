import React from "react";
// import { useRecoilValue } from "recoil";

// import { scoreState } from "./atom";

const Score = (props) => {
  // const score = useRecoilValue(scoreState);

  return <div className="score">{`Score: ${props.score}`}</div>;
};

export default React.memo(Score);
