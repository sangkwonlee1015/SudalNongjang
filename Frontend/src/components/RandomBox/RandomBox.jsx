import React, { useEffect, useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import "./RandomBox.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";
const backdrop = {
  visible: { opacity: 1 },
  hidden: { opacity: 0 },
};
const apiUrl = "https://j7a606.p.ssafy.io/";
const RandomBox = ({
  showBox,
  setShowBox,
  part,
  showImg,
  setShowImg,
  num,
  rare,
  rgb,
}) => {
  const navigate = useNavigate();
  const handleNavigate = () => {
    navigate("/item");
  };
  return (
    <AnimatePresence exitBeforeEnter>
      {showBox && (
        <motion.div
          className="backdrop"
          variants={backdrop}
          initial="hidden"
          animate="visible"
        >
          <div className="modal">
            {!showImg ? (
              <img
                className="giftBox"
                alt="giftbox"
                src={require("../../assets/images/present.png")}
              />
            ) : (
              <div className="gottenItem">
                {part === "01" ? (
                  <img
                    className={`itemImg ${
                      rare === 1 ? "normal" : rare === 2 ? "rare" : "epic"
                    }`}
                    src={`${process.env.PUBLIC_URL}/assets/images/items/Head/01_${num}_${rgb}_${rare}.png`}
                    alt="gottenItem"
                  />
                ) : part === "02" ? (
                  <img
                    className={`itemImg ${
                      rare === 1 ? "normal" : rare === 2 ? "rare" : "epic"
                    }`}
                    src={`${process.env.PUBLIC_URL}/assets/images/items/Eye/02_${num}_${rgb}_${rare}.png`}
                    alt="gottenItem"
                  />
                ) : part === "03" ? (
                  <img
                    className={`itemImg ${
                      rare === 1 ? "normal" : rare === 2 ? "rare" : "epic"
                    }`}
                    src={`${process.env.PUBLIC_URL}/assets/images/items/Mouth/03_${num}_${rgb}_${rare}.png`}
                    alt="gottenItem"
                  />
                ) : part === "04" ? (
                  <img
                    className={`itemImg ${
                      rare === 1 ? "normal" : rare === 2 ? "rare" : "epic"
                    }`}
                    src={`${process.env.PUBLIC_URL}/assets/images/items/Hand/04_${num}_${rgb}_${rare}.png`}
                    alt="gottenItem"
                  />
                ) : (
                  <img
                    className={`itemImg ${
                      rare === 1 ? "normal" : rare === 2 ? "rare" : "epic"
                    }`}
                    src={`${process.env.PUBLIC_URL}/assets/images/items/Cloth/05_${num}_${rgb}_${rare}.png`}
                    alt="gottenItem"
                  />
                )}
                <img
                  className="lightImg"
                  src={require("../../assets/images/light.png")}
                  alt="light"
                />
                <button
                  onClick={() => {
                    setShowBox(false);
                    setShowImg(false);
                    window.location.href = "/item";
                  }}
                  className="btn"
                  style={{ zIndex: "3", cursor: "pointer" }}
                >
                  확인
                </button>
              </div>
            )}
          </div>
        </motion.div>
      )}
    </AnimatePresence>
  );
};

export default RandomBox;
