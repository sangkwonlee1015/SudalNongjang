import { useContext } from "react";
import { Link } from "react-router-dom";
import "./Navbar.css";
import logo from "../../assets/images/logo.png";
import { TransactionContext } from "../../context/TransactionContext";
import Notice from "../Notice/Notice";
import axios from "axios";
import { useState, useEffect } from "react";
import Web3 from "web3";
import { motion } from "framer-motion";
const Navbar = () => {
  const { connectWallet, currentAccount } = useContext(TransactionContext);
  const [res, setRes] = useState();
  const [message, setMessage] = useState("");
  // let web3 = new Web3(window.ethereum);
  // web3 = new Web3(window.web3.currentProvider);

  // const handleLogin=() =>{

  //     const response = axios.get(apiUrl+`api/user/nonce/${currentAccount}`).then((res)=> {
  //         setMessage(web3.eth.sign(web3.utils.sha3(res)), currentAccount)
  //     }).then(console.log(message))

  // }
  const apiUrl = "https://j7a606.p.ssafy.io/";
  const userId = window.localStorage.getItem("userId");
  const [nick, setNick] = useState();
  const token = window.localStorage.getItem("token");
  useEffect(() => {
    axios
      .get(apiUrl + "api/user/nickname", {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((res) => setNick(res.data));
  }, []);
  return (
    <nav className="navbar">
      <div className="main">
        <motion.div
          className="logoDiv"
          whileHover={{ scale: 1.1 }}
          whileTap={{ scale: 0.9 }}
        >
          <Link to="/main">
            <img src={logo} alt="logo" className="logo" />
          </Link>
        </motion.div>
        <div className="menu">
          <motion.div whileHover={{ scale: 1.2 }} whileTap={{ scale: 0.9 }}>
            <Link to="/game" className="menuItem">
              게임하기
            </Link>
          </motion.div>
          <motion.div whileHover={{ scale: 1.2 }} whileTap={{ scale: 0.9 }}>
            <Link to="/shop" className="menuItem">
              수달샵
            </Link>
          </motion.div>
          <motion.div whileHover={{ scale: 1.2 }} whileTap={{ scale: 0.9 }}>
            <Link to="/item" className="menuItem">
              수달꾸미기
            </Link>
          </motion.div>
          <motion.div whileHover={{ scale: 1.2 }} whileTap={{ scale: 0.9 }}>
            <Link to="/guide" className="menuItem">
              이용 안내
            </Link>
          </motion.div>
        </div>
      </div>
      {!currentAccount ? (
        <div
          onClick={() => {
            connectWallet();
          }}
          className="login"
        >
          계정연결
        </div>
      ) : (
        <div className="account">
          <Link
            to={"/myPage"}
            className="myPage"
            style={{ textDecoration: "none", color: "black" }}
          >
            {nick} 님의 수달하우스
          </Link>
          {/* <Link to="/noti" className="noti-icon">
            <Notice />
          </Link> */}
        </div>
      )}
    </nav>
  );
};

export default Navbar;
