import * as React from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import otter from "../../assets/images/logo.png"
import {useState} from "react";
import { useNavigate } from 'react-router-dom';
import "./Modal2.css"
const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: "10%",
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
  display:"flex",
  alignItems:"center",
  justifyContent :"center"
};


export default function BasicModal({open}) {
  const navigate = useNavigate();
  const [open2, setOpen2] =useState(false);
//   const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen2(true);
  const handleNavi = () => {
    navigate("/myPage")
  }
  return (
    <div>
     
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          
            로딩중이달
       
            <img className="otterSpin" src= {otter} alt="" style={{width:"20%", marginLeft:"10%"}}/>
      
        </Box>
      </Modal>

    </div>
  );
}
