import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";

const useStyles = makeStyles((theme) => ({
  header: {
    display: "flex",
    justifyContent: "space-between",
    width: "80%",
    margin: "0 auto",
  },
}));

function GameHeader(props) {
  const classes = useStyles();
  return (
    <div className={classes.header}>
      <div className="game-title">{props.title}</div>
      <Button
        style={{
          fontFamily: "neo",
          fontWeight: "bold",
          backgroundColor: "#DAB49D",
          marginLeft: "10px",
        }}
        variant="contained"
        href="/game"
      >
        목록
      </Button>
    </div>
  );
}

export default GameHeader;
