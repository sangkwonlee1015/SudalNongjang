import React, { useEffect, useState } from "react";
import { ethers } from "ethers";
import Web3 from "web3";
import axios from "axios";

export const TransactionContext = React.createContext();

const { ethereum } = window;

const getEthereumContract = () => {
  const provider = new ethers.providers.Web3Provider(ethereum);
  const signer = provider.geetSigner();

  console.log({
    provider,
    signer,
  });
};
// let web3 = new Web3(window.ethereum);
// web3 = new Web3(window.web3.currentProvider);
const apiUrl = "https://j7a606.p.ssafy.io/";

export const TransactionProvider = ({ children }) => {
  const [currentAccount, setCurrentAccount] = useState("");
  const [message, setMessage] = useState();
  const [imgUrl, setImgUrl] = useState("");
  const [token, setToken] = useState("");

  const checkIfWalletIsConnected = async () => {
    try {
      // if (!ethereum) return alert("메타마스크를 설치해 주세요!");

      const accounts = await ethereum.request({ method: "eth_accounts" });
      if (accounts.length) {
        setCurrentAccount(accounts[0]);
      } else {
        console.log("No accounts found");
      }
      console.log(accounts);
    } catch (error) {
      console.log(error);
      throw new Error("No etheruem object");
    }
  };

  const connectWallet = async () => {
    if (!ethereum) return alert("메타마스크를 설치해 주세요!");
    let message = "";
    let web3 = new Web3(window.ethereum);
    web3 = new Web3(window.web3.currentProvider);
    const accounts = await web3.eth.requestAccounts();
    console.log(accounts);
    axios.get(apiUrl + `api/user/nonce/${accounts[0]}`).then(async (res) => {
      console.log("hi");
      console.log(typeof res.data);
      console.log(res.data);
      message = await web3.eth.sign(
        web3.utils.sha3(res.data.toString()),
        accounts[0]
      );
      axios
        .post(apiUrl + "api/user", { message: message, wallet: accounts[0] })
        .then((res) => {
          console.log(res.data);
          window.localStorage.setItem("token", res.data.jwt);
          window.localStorage.setItem("userId", res.data.userId);
        });
    });

    // const message = await web3.eth.sign(
    //   web3.utils.sha3("675319"),
    //   accounts[0]
    // );

    setCurrentAccount(accounts[0]);
    setMessage(message);

    // try {
    //   if (!ethereum) return alert("메타마스크를 설치해주세요.");
    //   const accounts = await ethereum.request({
    //     method: "eth_requestAccounts",
    //   });
    //   setCurrentAccount(accounts[0]);
    // //   const response =await axios.get(apiUrl+`api/user/nonce/${accounts[0]}`).then((res)=> {
    // //     setMessage(web3.eth.sign(web3.utils.sha3(res)), accounts[0])
    // // }).then(console.log(message))

    // } catch (error) {
    //   console.log(error);
    //   throw new Error("No etheruem object");
    // }
  };

  useEffect(() => {
    checkIfWalletIsConnected();
  }, []);
  return (
    <TransactionContext.Provider
      value={{ connectWallet, currentAccount, setImgUrl, imgUrl, token }}
    >
      {children}
    </TransactionContext.Provider>
  );
};
