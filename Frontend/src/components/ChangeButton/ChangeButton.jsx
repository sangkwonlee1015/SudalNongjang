import React from "react";
import Modal from "../Modal/Modal";
import { motion } from "framer-motion";

function ChangeButton({ handleId, id, issaled, param }) {
  const ids = id;

  return (
    <motion.div whileHover={{ scale: 1.2 }} whileTap={{ scale: 0.9 }}>
      <button
        style={{
          backgroundColor: "#DAB49D",
          borderRadius: "10px",
          fontFamily: "neo",
          border: "none",
          height: "5vh",
          marginTop: "10%",
          marginBottom: "5%",
          cursor: "pointer",
          zIndex: "100",
        }}
        onClick={(e) => {
          if (issaled) {
            alert("판매중인 상품입니다.");
          } else {
            handleId(ids);
            window.location.reload();
          }
        }}
      >
        대표수달지정
      </button>
    </motion.div>
  );
}

export default ChangeButton;
