import React, { useEffect, useState } from "react";
import Navbar from "../../components/Navbar/Navbar";
import { Buffer } from "buffer";
import "./Issue.css";
import { create } from "ipfs-http-client";

// import ipfsApi from "ipfs-api";
import html2canvas from "html2canvas";
// import { internal_processStyles } from "@mui/styled-engine";
function Issue() {
  let imgUrl = JSON.parse(window.localStorage.getItem("nft")).split(",")[1];
  const dataURLtoFile = (dataurl, fileName) => {
    let arr = dataurl.split(","),
      mime = arr[0].match(/:(.*?);/)[1],
      bstr = atob(arr[1]),
      n = bstr.length,
      u8arr = new Uint8Array(n);

    while (n--) {
      u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], fileName, { type: mime });
  };
  let file = dataURLtoFile(
    JSON.parse(window.localStorage.getItem("nft")),
    "otterNft.png"
  );

  const [disabled, setDisabled] = useState(false);
  const disableHandler = () => {
    if (disabled) {
      setDisabled(false);
    } else {
      setDisabled(true);
    }
  };
  const [buffer, setBuffer] = useState(null);
  const b64toBlob = (b64Data, contentType, sliceSize) => {
    if (b64Data === "" || b64Data === undefined) return null;
    contentType = contentType || "";
    sliceSize = sliceSize || 512;
    let byteCharacters = atob(b64Data);
    let byteArrays = [];
    for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
      let slice = byteCharacters.slice(offset + sliceSize);
      let byteNumbers = new Array(slice.length);
      for (let i = 0; i < slice.length; i++) {
        byteNumbers[i] = slice.charCodeAt(i);
      }
      let byteArray = new Uint8Array(byteNumbers);
      byteArrays.push(byteArray);
    }
    let blob = new Blob(byteArrays, { type: contentType });
    return blob;
  };
  const projectId = "2FHqbLTE55XfCP0BjQ8er0prKtE";
  const projectSecret = "0291ff7b1c1bd3704962f9cd27e9fba8";
  const auth =
    "Basic " + Buffer.from(projectId + ":" + projectSecret).toString("base64");
  const client = create({
    host: "ipfs.infura.io",
    port: 5001,
    protocol: "https",
    apiPath: "/api/v0",
    headers: {
      authorization: auth,
    },
  });

  const handleSubmit = async (e) => {
    console.log(auth);
    console.log(name, price, date, sale);
    try {
      const created = await client.add(file);
      const url = `https://www.infura-ipfs.io/ipfs/${created.path}`;
      console.log(url);
    } catch (error) {
      console.log(error);
    }
  };
  const makenft = async () => {
    await html2canvas(document.getElementById("comp")).then((canvas) => {
      let data = canvas.toDataURL("image/png");
      let block = data.split(";");
      let contentType = block[0].split(":")[1];
      let realData = block[1].split(",")[1];
      let blob = b64toBlob(realData, contentType);
      // window.localStorage.removeItem("nft");
      // console.log(data);
      console.log(blob);
      const reader = new window.FileReader();
      reader.readAsArrayBuffer(blob);
      reader.onloadend = () => {
        setBuffer(Buffer(reader.result));
      };
      handleSubmit();
    });
  };

  // let ipfs = ipfsApi("localhost", "5001", { protocol: "https" });
  const [saleDisabled, setSaleDisabled] = useState(true);
  const [selected, setSelected] = useState(true);
  const [notSaleDisabled, setNotSaleDisabled] = useState(false);
  const [name, setName] = useState("");
  const [price, setPrice] = useState("");
  const [date, setDate] = useState();
  const [sale, setSale] = useState(true);
  const handleName = (e) => {
    setName(e.target.value);
  };
  const handelPrice = (e) => {
    setPrice(e.target.value);
  };
  const handleDate = (e) => {
    setDate(e.target.value);
  };
  return (
    <div>
      <Navbar />
      <div className="nftTitle">당신의 수달이 태어났어요</div>
      <div className="settingNft">
        <div className="nftInfo">
          <img
            id="comp"
            width="300px"
            height="400px"
            alt="nftimg"
            // src={`data:image/png;base64,${imgUrl}`}
            src={`data:image/png;base64,${imgUrl}`}
          />
          <span>컨트랙트주소</span>
        </div>
        <div className="setting">
          <form className="forsale" onSubmit={handleSubmit}>
            <div className="saleornot">
              <input
                id="ForSale"
                type="checkbox"
                style={{ scale: "2", cursor: "pointer" }}
                value="true"
                checked={selected}
                onChange={() => {
                  setSelected(!selected);
                  setSaleDisabled(!saleDisabled);
                  disableHandler();
                  setNotSaleDisabled(!notSaleDisabled);
                  setSale(true);
                }}
                disabled={saleDisabled}
              />

              <label className="isSale" htmlFor="ForSale">
                분양
              </label>

              <input
                id="notSale"
                type="checkbox"
                style={{ scale: "2", marginLeft: "7%", cursor: "pointer" }}
                value="false"
                onChange={() => {
                  disableHandler();
                  setSelected(!selected);
                  setSaleDisabled(!saleDisabled);
                  setNotSaleDisabled(!notSaleDisabled);
                  setSale(false);
                }}
                disabled={notSaleDisabled}
                checked={!selected}
              />
              <label className="isSale" htmlFor="notSale">
                미분양
              </label>
            </div>
            <div>
              <input
                id="nftName"
                className="border"
                type="text"
                placeholder="이름"
                style={{ width: "50%", marginTop: "5%", height: "4vh" }}
                onChange={handleName}
                required
              />
            </div>
            <input
              id="nftPrice"
              className="border"
              type="number"
              placeholder="분양가(SSF)"
              style={{ width: "50%", marginTop: "5%", height: "4vh" }}
              disabled={disabled}
              onChange={handelPrice}
              required
            />
            <div style={{ display: "flex", alignItems: "center" }}>
              <label
                htmlFor="nftDate"
                style={{ marginRight: "7%", fontSize: "1vw" }}
              >
                경매마감일
              </label>
              <input
                id="nftDate"
                className="border"
                type="date"
                style={{
                  width: "30%",
                  marginTop: "5%",
                  height: "4vh",
                  marginBottom: "5%",
                  borderRadius: "5%",
                  cursor: "pointer",
                }}
                disabled={disabled}
                onChange={handleDate}
                required
              />
            </div>
            <button
              type="submit"
              className="saveBtn"
              onClick={(e) => {
                e.preventDefault();
                makenft();
                console.log("save");
              }}
              style={{ cursor: "pointer" }}
            >
              완료
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Issue;
