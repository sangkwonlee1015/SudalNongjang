import React from "react";

const Item = (props) => {
  const { type, x, y, height, width, index, onClick } = props;
  const ItemStyle = {
    left: `${x}px`,
    top: `${y}px`,
  };
  return (
    <div className="item" style={ItemStyle} onClick={() => onClick(index)}>
      <img
        src={type}
        style={{
          height: `${height}px`,
          width: `${width}px`,
        }}
        alt="item"
      />
    </div>
  );
};
export default Item;
