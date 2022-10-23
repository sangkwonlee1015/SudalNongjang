import React from "react";

function ShowSale(props) {
  return <div className="sale">{props.isOnSale ? "분양중" : "미분양"}</div>;
}

export default ShowSale;
