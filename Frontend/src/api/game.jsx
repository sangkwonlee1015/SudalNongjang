import api from "./api";

const END_POINT = "game";

const game = {
  sendPoint(point) {
    return api({
      method: "put",
      url: `${END_POINT}/point/${point}`,
    });
  },
};

export default game;
