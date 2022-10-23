import { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import "./MainNavbar.css";
import logo from "../../assets/images/logo.png";
import { TransactionContext } from "../../context/TransactionContext";
import Notice from "../Notice/Notice";

const MainNavbar = () => {
  const { connectWallet, currentAccount } = useContext(TransactionContext);
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
    <nav className="mainNavbar">
      <div className="main">
        <div className="logoDiv">
          <img src={logo} alt="logo" className="logoImg" />
        </div>
        <span style={{ fontSize: "30px" }}>수달농장</span>
      </div>
      {!currentAccount ? (
        <div onClick={connectWallet} className="login">
          계정연결
        </div>
      ) : (
        <div className="account">
          <Link
            to="/myPage"
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

export default MainNavbar;
