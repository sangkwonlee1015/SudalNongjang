import React, { useEffect } from "react";
import Navbar from "../../components/Navbar/Navbar";
import "./Item.css";
import { motion } from "framer-motion";
import { useState } from "react";
import { Buffer } from "buffer";
import MyItem from "../../components/MyItem/MyItem";
import Eyes from "../../components/MyItem/Eyes";
import Mouth from "../../components/MyItem/Mouth";
import Hands from "../../components/MyItem/Hands";
import Cloth from "../../components/MyItem/Cloth";
import { useNavigate } from "react-router-dom";
import RandomBox from "../../components/RandomBox/RandomBox";
import { create } from "ipfs-http-client";
import html2canvas from "html2canvas";
import axios from "axios";
import logo from "../../assets/images/logo.png";
import { height } from "@mui/system";
import TransitionsModal from "../../components/Modal/Modal";
import BasicModal from "../../components/Modal/Modal2";

// modal
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import { EPIC, RARE, NORMAL } from "../../components/Items/Items";

// tab
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";

const useStyles = makeStyles((theme) => ({
  tabRoot: {
    flexGrow: 1,
  },
  appbar: {
    backgroundColor: "inherit",
    boxShadow: "none",
  },
  tab: {
    color: "black",
    fontSize: "20px",
    fontFamily: "neo",
  },
}));

function Item() {
  const parts = ["머리", "눈", "입", "손", "옷"];
  const [selected, setSelected] = useState(Array(parts.length).fill(false));
  useEffect(() => {
    setSelected([false, false, false, false, true]);
  }, []);

  const navigate = useNavigate();
  const [url, setUrl] = useState(null);
  const [eyeUrl, setEyeUrl] = useState(null);
  const [mouthUrl, setMouthUrl] = useState(null);
  const [handUrl, setHandUrl] = useState(null);
  const [clothUrl, setClothUrl] = useState(null);
  const apiUrl = "https://j7a606.p.ssafy.io/";
  const [showBox, setShowBox] = useState(false);
  const [part, setPart] = useState("");
  const onClick = (idx) => {
    const newArr = Array(parts.length).fill(false);
    newArr[idx] = true;
    setSelected(newArr);
  };
  const [number, setNumber] = useState();
  const [rgb, setRgb] = useState();
  const [rare, setRare] = useState();
  const handleInfo = (num, rgb, rare) => {
    setNumber(num);
    setRgb(rgb);
    setRare(rare);
  };
  const [showImg, setShowImg] = useState(false);
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
  const [name, setName] = useState("");
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
  const getIndex = (imgurl) => {
    //이미지 경로를 넣으면 split해서 몇번째 아이템인지 아이디를 가져오는 함수
    if (imgurl !== null) {
      return parseInt(imgurl.split("/")[3].split("_")[1], 10);
    } else {
      return 0;
    }
  };

  const [sudalPt, setSudalPt] = useState(0);

  const [itemId, setItemId] = useState(0);
  const [eyeId, setEyeId] = useState(0);
  const [mouthId, setMouthId] = useState(0);
  const [handId, setHandId] = useState(0);
  const [clothId, setClothId] = useState(0);
  const token = window.localStorage.getItem("token");
  const [response, setResponse] = useState();
  const [loading, setLoading] = useState(false);
  const nftHandler = async (e) => {
    setLoading(true);
    e.preventDefault();
    let file = "";
    if (name.length !== 0) {
      await html2canvas(document.getElementById("otterImg")).then((canvas) => {
        let data = canvas.toDataURL();
        console.log("data" + data);

        file = dataURLtoFile(data, "otterNft");
        // window.localStorage.setItem("nft", JSON.stringify(data));
      });

      const created = await client.add(file);
      const tokenURI = `https://www.infura-ipfs.io/ipfs/${created.path}`;
      console.log(tokenURI);
      console.log(token);
      const eyes = eyeId;
      const fashion = clothId;
      const hands = handId;
      const head = itemId;
      const mouth = mouthId;
      const params = {
        eyes: eyes,
        fashion: fashion,
        hands: hands,
        head: head,
        mouth: mouth,
        name: name,
        tokenURI: tokenURI,
      };

      axios
        .put(
          apiUrl + "api/shop/nft",

          params,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          },
          { withCredentials: true }
        )
        .then((res) => {
          setLoading(false);

          if (res.status === 200) {
            if (!loading) {
              alert("발급 성공!");
              navigate("/myPage");
            }
          }
        })
        .catch((e) => {
          setLoading(false);
          if (e.response.status === 409) {
            if (!loading) {
              alert("이미 있는 NFT입니다!");
            }
          }
        });
    } else {
      setLoading(false);
      alert("수달 이름을 정해주세요");
    }
  };
  const handleInput = (e) => {
    setName(e.target.value);
    if (e.target.value.length >= 8) {
      alert("이름은 최대 7자 입니다.");
    }
  };
  const onSubmit = () => {};
  const minus = -500;
  useEffect(() => {
    axios
      .get(apiUrl + "api/user/point", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => setSudalPt(res.data));
  }, [token]);

  const [open, setOpen] = React.useState(false);
  const handleOpen = () => {
    setOpen(true);
    console.log(EPIC.head);
  };
  const handleClose = () => setOpen(false);

  const style = {
    position: "absolute",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: "60%",
    height: "85%",
    overflowY: "scroll",
    bgcolor: "background.paper",
    border: "2px solid #000",
    boxShadow: 24,
    p: 4,
  };

  // tab
  const classes = useStyles();
  const [value, setValue] = useState(0);
  const handleTab = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <div className="pageBox">
      <BasicModal open={loading} />
      <Navbar />
      <div className="item-header-wrap">
        <h1 className="item-title">수달 꾸미기</h1>
        <div className="item-modal">
          <Button
            style={{ fontFamily: "neo", fontSize: "15px" }}
            onClick={handleOpen}
          >
            확률보기
          </Button>
          <Modal
            open={open}
            onClose={handleClose}
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
          >
            <Box sx={style}>
              <Typography id="modal-modal-title" variant="h4" component="h1">
                아이템 정보
              </Typography>
              <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                <div className={classes.tabRoot}>
                  <AppBar position="static" className={classes.appbar}>
                    <Tabs
                      value={value}
                      onChange={handleTab}
                      aria-label="simple tabs example"
                      TabIndicatorProps={{
                        style: { backgroundColor: "#C08552" },
                      }}
                    >
                      <Tab className={classes.tab} label="Epic" />
                      <Tab className={classes.tab} label="Rare" />
                      <Tab className={classes.tab} label="Normal" />
                    </Tabs>
                  </AppBar>
                </div>
                {value == 0 && (
                  <div className="epic">
                    <h2>Epic </h2>
                    <div className="line"></div>
                    <p>당첨확률 : 10%</p>
                    <p>[ 머리 ]</p>
                    {EPIC.head.map((item, index) => (
                      <img src={item} alt="item" />
                    ))}
                    <p>[ 눈 ]</p>
                    {EPIC.eye.map((item, index) => (
                      <img src={item} alt="item" />
                    ))}
                    <p>[ 입 ]</p>
                    {EPIC.mouth.map((item, index) => (
                      <img src={item} alt="item" />
                    ))}
                    <p>[ 손 ]</p>
                    {EPIC.hand.map((item, index) => (
                      <img src={item} alt="item" />
                    ))}
                    <p>[ 옷 ]</p>
                    {EPIC.cloth.map((item, index) => (
                      <img src={item} alt="item" />
                    ))}
                  </div>
                )}
                {value == 1 && (
                  <div className="rare">
                    <h2>Rare </h2>
                    <div className="line"></div>
                    <p>당첨확률 : 40%</p>
                    <p>[ 머리 ]</p>
                    {RARE.head.map((item, index) => (
                      <img src={item} alt="item" />
                    ))}
                    <p>[ 눈 ]</p>
                    {RARE.eye.map((item, index) => (
                      <img src={item} alt="item" />
                    ))}
                    <p>[ 입 ]</p>
                    {RARE.mouth.map((item, index) => (
                      <img src={item} alt="item" />
                    ))}
                    <p>[ 손 ]</p>
                    {RARE.hand.map((item, index) => (
                      <img src={item} alt="item" />
                    ))}
                    <p>[ 옷 ]</p>
                    {RARE.cloth.map((item, index) => (
                      <img src={item} alt="item" />
                    ))}
                  </div>
                )}
                {value == 2 && (
                  <div className="normal">
                    <h2>Normal </h2>
                    <div className="line"></div>
                    <p>당첨확률 : 50%</p>
                    <p>[ 머리 ]</p>
                    {NORMAL.head.map((item, index) => (
                      <img src={item} alt="item" />
                    ))}
                    <p>[ 눈 ]</p>
                    {NORMAL.eye.map((item, index) => (
                      <img src={item} alt="item" />
                    ))}
                    <p>[ 입 ]</p>
                    {NORMAL.mouth.map((item, index) => (
                      <img src={item} alt="item" />
                    ))}
                    <p>[ 손 ]</p>
                    {NORMAL.hand.map((item, index) => (
                      <img src={item} alt="item" />
                    ))}
                    <p>[ 옷 ]</p>
                    {NORMAL.cloth.map((item, index) => (
                      <img src={item} alt="item" />
                    ))}
                  </div>
                )}
              </Typography>
            </Box>
          </Modal>
        </div>
      </div>
      <div className="itemPage">
        <div className="myNft">
          <div className="otterNft">
            <div id="otterImg">
              <img
                className="char base"
                src={require("../../assets/images/otter.png")}
                alt="base"
              />
              <img className="char head" src={url} alt="" title="" />
              <img className="char eye" src={eyeUrl} alt="" title="" />
              <img className="char mouth" src={mouthUrl} alt="" title="" />
              <img className="char hand" src={handUrl} alt="" title="" />
              <img className="char cloth" src={clothUrl} alt="" title="" />
            </div>

            <form
              style={{
                height: "10%",
                marginTop: "90%",
                width: "150%",
                display: "flex",
                justifyContent: "space-between",
              }}
              onSubmit={onSubmit}
            >
              <input
                id="otterName"
                placeholder="수달이름"
                maxLength={7}
                style={{
                  marginTop: "10px",
                  marginRight: "10%",
                  width: "96%",
                  fontFamily: "neo",
                  borderRadius: "10px",
                  height: "100%",
                }}
                onChange={handleInput}
              />
              <div style={{ width: "100%", display: "flex" }}>
                <button
                  style={{
                    zIndex: "5",
                    // marginLeft: "80%",
                    height: "100%",
                    width: "30%",
                    marginTop: "10px",
                    backgroundColor: "#f3e9dc",
                    fontFamily: "neo",
                    cursor: "pointer",
                    borderRadius: "20px",
                    marginRight: "5%",
                  }}
                  onClick={(e) => {
                    e.preventDefault();
                    setUrl(null);
                    setEyeUrl(null);
                    setMouthUrl(null);
                    setHandUrl(null);
                    setClothUrl(null);
                  }}
                >
                  초기화
                </button>

                <button
                  type="submit"
                  style={{
                    zIndex: "5",
                    // marginLeft: "80%",
                    height: "100%",
                    width: "30%",
                    marginTop: "10px",
                    backgroundColor: "#f3e9dc",
                    fontFamily: "neo",
                    cursor: "pointer",
                    borderRadius: "20px",
                  }}
                  onClick={nftHandler}
                >
                  발급하기
                </button>
              </div>
            </form>
          </div>
          <div className="select">
            {parts.map((part, idx) => (
              <motion.div
                whileHover={{ scale: 1.2 }}
                whileTap={{ scale: 0.9 }}
                className={"part " + (selected[idx] ? "chosen" : null)}
                key={idx}
                onClick={() => onClick(idx)}
              >
                <div>{part}</div>
              </motion.div>
            ))}
          </div>
          {selected[0] ? (
            <MyItem itemsPerPage={8} setUrl={setUrl} setItemId={setItemId} />
          ) : selected[1] ? (
            <Eyes itemsPerPage={8} setUrl={setEyeUrl} setEyeId={setEyeId} />
          ) : selected[2] ? (
            <Mouth
              itemsPerPage={8}
              setUrl={setMouthUrl}
              setMouthId={setMouthId}
            />
          ) : selected[3] ? (
            <Hands itemsPerPage={8} setUrl={setHandUrl} setHandId={setHandId} />
          ) : (
            <Cloth
              itemsPerPage={8}
              setUrl={setClothUrl}
              setClothId={setClothId}
            />
          )}
        </div>

        <div className="itemShop">
          <div className="shopName">
            <img src={logo} alt="" style={{ width: "10%" }} /> 내 수달머니{" "}
            {sudalPt}P
          </div>
          <div className="shoppart">
            <RandomBox
              showBox={showBox}
              setShowBox={setShowBox}
              part={part}
              showImg={showImg}
              setShowImg={setShowImg}
              num={number}
              rgb={rgb}
              rare={rare}
            />
            <div
              className="giftDiv"
              style={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
              }}
            >
              <span>머리</span>
              <img
                className="giftbox"
                alt="giftbox"
                src={require("../../assets/images/present.png")}
                style={{ width: "30%" }}
              />
              <button
                className="selectBtn"
                onClick={() => {
                  if (sudalPt >= 500) {
                    setShowBox(true);
                    setPart("01");
                    setTimeout(() => {
                      setShowImg(true);
                    }, 4000);
                    console.log(typeof 1);

                    axios
                      .get(apiUrl + `api/item/${1}`, {
                        headers: { Authorization: `Bearer ${token}` },
                      })
                      .then((res) =>
                        handleInfo(res.data.number, res.data.rgb, res.data.rare)
                      );

                    axios.put(apiUrl + `api/game/point/${minus}`, minus, {
                      headers: {
                        Authorization: `Bearer ${token}`,
                      },
                    });
                  } else {
                    alert("수달머니가 부족합니다.");
                  }
                }}
              >
                뽑기 500 <img src={logo} style={{ width: "20%" }} alt="" />
              </button>
            </div>
            <div
              className="giftDiv"
              style={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
              }}
            >
              <span>눈</span>
              <img
                className="giftbox"
                alt="giftbox"
                src={require("../../assets/images/present.png")}
                style={{ width: "30%" }}
              />
              <button
                className="selectBtn"
                onClick={() => {
                  if (sudalPt >= 500) {
                    setShowBox(true);
                    setPart("02");
                    setTimeout(() => {
                      setShowImg(true);
                    }, 4000);
                    axios
                      .get(apiUrl + `api/item/${2}`, {
                        headers: { Authorization: `Bearer ${token}` },
                      })
                      .then((res) =>
                        handleInfo(res.data.number, res.data.rgb, res.data.rare)
                      );
                    axios.put(apiUrl + `api/game/point/${-500}`, minus, {
                      headers: {
                        Authorization: `Bearer ${token}`,
                      },
                    });
                  } else {
                    alert("수달머니가 부족합니다.");
                  }
                }}
              >
                뽑기 500 <img src={logo} style={{ width: "20%" }} alt="" />
              </button>
            </div>
            <div
              className="giftDiv"
              style={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
              }}
            >
              <span>입</span>
              <img
                className="giftbox"
                alt="giftbox"
                src={require("../../assets/images/present.png")}
                style={{ width: "30%" }}
              />
              <button
                className="selectBtn"
                onClick={() => {
                  if (sudalPt >= 500) {
                    setShowBox(true);
                    setPart("03");
                    setTimeout(() => {
                      setShowImg(true);
                    }, 4000);
                    axios
                      .get(apiUrl + `api/item/${3}`, {
                        headers: { Authorization: `Bearer ${token}` },
                      })
                      .then((res) =>
                        handleInfo(res.data.number, res.data.rgb, res.data.rare)
                      );
                    axios.put(apiUrl + `api/game/point/${-500}`, minus, {
                      headers: {
                        Authorization: `Bearer ${token}`,
                      },
                    });
                  } else {
                    alert("수달머니가 부족합니다.");
                  }
                }}
              >
                뽑기 500 <img src={logo} style={{ width: "20%" }} alt="" />
              </button>
            </div>
            <div
              className="giftDiv"
              style={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
              }}
            >
              <span>손</span>
              <img
                className="giftbox"
                alt="giftbox"
                src={require("../../assets/images/present.png")}
                style={{ width: "30%" }}
              />
              <button
                className="selectBtn"
                onClick={() => {
                  if (sudalPt >= 500) {
                    setShowBox(true);
                    setPart("04");
                    setTimeout(() => {
                      setShowImg(true);
                    }, 4000);
                    axios
                      .get(apiUrl + `api/item/${4}`, {
                        headers: { Authorization: `Bearer ${token}` },
                      })
                      .then((res) =>
                        handleInfo(res.data.number, res.data.rgb, res.data.rare)
                      );
                    axios.put(apiUrl + `api/game/point/${-500}`, minus, {
                      headers: {
                        Authorization: `Bearer ${token}`,
                      },
                    });
                  } else {
                    alert("수달머니가 부족합니다.");
                  }
                }}
              >
                뽑기 500 <img src={logo} style={{ width: "20%" }} alt="" />
              </button>
            </div>
            <div
              className="giftDiv"
              style={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
              }}
            >
              <span>옷</span>
              <img
                className="giftbox"
                alt="giftbox"
                src={require("../../assets/images/present.png")}
                style={{ width: "30%" }}
              />
              <button
                className="selectBtn"
                onClick={() => {
                  if (sudalPt >= 500) {
                    setShowBox(true);
                    setPart("05");
                    setTimeout(() => {
                      setShowImg(true);
                    }, 4000);
                    axios
                      .get(apiUrl + `api/item/${5}`, {
                        headers: { Authorization: `Bearer ${token}` },
                      })
                      .then((res) =>
                        handleInfo(res.data.number, res.data.rgb, res.data.rare)
                      );
                    axios.put(apiUrl + `api/game/point/${-500}`, minus, {
                      headers: {
                        Authorization: `Bearer ${token}`,
                      },
                    });
                  } else {
                    alert("수달머니가 부족합니다.");
                  }
                }}
              >
                뽑기 500 <img src={logo} style={{ width: "20%" }} alt="" />
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Item;
