import React from "react";
import "./Main.css";
import sign from "../../assets/images/sign.png";
import MainNavbar from "../../components/MainNavbar/MainNavbar";
import { motion } from "framer-motion";

function Main() {
  return (
    <div
      className="mainPage"
      style={{ display: "flex", flexDirection: "column" }}
    >
      <MainNavbar />
      <div className="menuDiv">
        <motion.div
          className="shopBox"
          onClick={() => {
            window.location.href = "/shop";
          }}
          whileHover={{ scale: 1.1 }}
          whileTap={{ scale: 0.9 }}
        >
          <img src={sign} alt="shopSign" className="shopSign" />
          <span className="shopBtn">수달샵</span>
        </motion.div>
        <motion.div
          className="gameBox"
          onClick={() => {
            window.location.href = "/game";
          }}
          whileHover={{ scale: 1.1 }}
          whileTap={{ scale: 0.9 }}
        >
          <img src={sign} alt="gameSign" className="gameSign" />
          <span className="gameBtn">게임하기</span>
        </motion.div>
        <motion.div
          className="itemBox"
          onClick={() => {
            window.location.href = "/item";
          }}
          whileHover={{ scale: 1.1 }}
          whileTap={{ scale: 0.9 }}
        >
          <img src={sign} alt="itemSign" className="itemSign" />
          <span className="itemBtn">
            수달
            <br />
            꾸미기
          </span>
        </motion.div>
        <motion.div
          className="guideBox"
          onClick={() => {
            window.location.href = "/guide";
          }}
          whileHover={{ scale: 1.1 }}
          whileTap={{ scale: 0.9 }}
        >
          <img src={sign} alt="guideSign" className="guideSign" />
          <span className="guideBtn">이용안내</span>
        </motion.div>
      </div>
    </div>
  );
}

export default Main;
