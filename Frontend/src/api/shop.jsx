import api from "./api";

const END_POINT = "board";

const shop = {
  //nft 전체 목록 조회(수달샵)
  nftList(params) {
    return api({
      method: "get",
      url: `${END_POINT}/list`,
      params: params,
    });
  },
  //nft 상세 조회
  nftDetailOne(params) {
    return api({
      method: "get",
      url: `${END_POINT}/details/${params}`,
    });
  },
  //미분양 nft 요청 내역 정보 조회
  nftUnsoldOne(params) {
    return api({
      method: "get",
      url: `${END_POINT}/appeal/${params}`,
    });
  },
  //분양중인 nft 경매 내역 정보 조회
  nftOnsaleOne(params) {
    return api({
      method: "get",
      url: `${END_POINT}/bid/${params}`,
    });
  },
  //좋아요 클릭
  nftLikeOne(params) {
    return api({
      method: "post",
      url: `${END_POINT}/likes`,
      data: params,
    });
  },
  //nft 판매 요청
  nftReqSale(params) {
    return api({
      method: "post",
      url: `${END_POINT}/appeal`,
      data: params,
    });
  },
};

export default shop;
