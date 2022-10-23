import * as React from "react";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import otter from "../../assets/images/logo.png";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Modal2.css";
const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: "20%",
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
  display: "flex",
  alignItems: "center",
  justifyContent: "center",
  borderRadius: "20px",
};

export default function BasicModal({ open, setLoading }) {
  //   const handleOpen = () => setOpen(true);
  const handleClose = () => setLoading(false);

  return (
    <div>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          분양 과정을 진행한달!
          <br />
          시간이 조금 걸린달!
          <br />
          창이 두번 뜨고 완료 창이 뜰때까지 기다려달!
          <img
            className="otterSpin"
            src={otter}
            alt=""
            style={{ width: "20%", marginLeft: "10%" }}
          />
        </Box>
      </Modal>
    </div>
  );
}
