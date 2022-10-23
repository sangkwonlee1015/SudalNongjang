import React, { useEffect, useState } from "react";
import FavoriteIcon from "@material-ui/icons/Favorite";
import FavoriteBorderIcon from "@material-ui/icons/FavoriteBorder";
import shop from "../../api/shop";

function Like(props) {
  const nftId = props.nftId;

  const [liked, setLiked] = useState(false);
  const [likeCnt, setLikeCnt] = useState(0);

  useEffect(() => {
    setLiked(props.liked);
    setLikeCnt(props.likeCnt);
  }, [props]);

  const handleLike = (value) => {
    setLiked(value);
    if (value) {
      setLikeCnt(likeCnt + 1);
    } else {
      setLikeCnt(likeCnt - 1);
    }
  };

  //좋아요 클릭
  const likeClick = () => {
    const params = {
      nftId: nftId,
    };
    shop
      .nftLikeOne(params)
      .then((result) => {
        handleLike(result.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div
      style={{
        display: "flex",
        alignItems: "center",
        fontSize: "20px",
        cursor: "pointer",
      }}
      onClick={likeClick}
    >
      {liked ? (
        <FavoriteIcon style={{ color: "red", marginRight: "5px" }} />
      ) : (
        <FavoriteBorderIcon style={{ color: "red", marginRight: "5px" }} />
      )}
      {likeCnt}
    </div>
  );
}

export default Like;
