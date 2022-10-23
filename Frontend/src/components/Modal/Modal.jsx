import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Modal from "@material-ui/core/Modal";
import Backdrop from "@material-ui/core/Backdrop";
import Fade from "@material-ui/core/Fade";
import "./Modal.css";
const useStyles = makeStyles((theme) => ({
  modal: {
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
  },
  paper: {
    backgroundColor: theme.palette.background.paper,

    boxShadow: theme.shadows[5],
    borderRadius: "20px",
    padding: theme.spacing(2, 4, 3),
  },
}));

export default function TransitionsModal() {
  const classes = useStyles();
  const [open, setOpen] = React.useState(false);

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <div>
      <button type="button" className="modalbtn" onClick={handleOpen}>
        게임방법
      </button>
      <Modal
        aria-labelledby="transition-modal-title"
        aria-describedby="transition-modal-description"
        className={classes.modal}
        open={open}
        onClose={handleClose}
        closeAfterTransition
        BackdropComponent={Backdrop}
        BackdropProps={{
          timeout: 500,
        }}
      >
        <Fade in={open}>
          <div className={classes.paper} style={{ textAlign: "center" }}>
            <h2 id="transition-modal-title">Welcom to Tetris</h2>
            <p id="transition-modal-description">
              제한시간은 총 2분!
              <br />
              한줄을 없앨때마다 100점!
              <br />
              100포인트는 10수달머니로 교환된다~
              <br />
              그럼 즐거운 시간 보내!
              <br />* 혹시 블럭이 움직이지 않는다면 tab키를 눌러봐라!
            </p>
          </div>
        </Fade>
      </Modal>
    </div>
  );
}
