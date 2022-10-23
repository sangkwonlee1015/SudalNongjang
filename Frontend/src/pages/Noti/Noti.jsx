import React, { useState } from "react";
import Navbar from "../../components/Navbar/Navbar";
import "../Noti/Noti.css";

// checkbox with label
import FormGroup from "@mui/material/FormGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";

// Pagination
import Pagination from "@mui/material/Pagination";
import Stack from "@mui/material/Stack";

const noties = [
  {
    userId: "1최수빈",
    nftName: "벌크업 수달",
    msg: "좋아요를 눌렀습니다.",
  },
  {
    userId: "2이선민",
    nftName: "벌크업 수달",
    msg: "분양요청을 하였습니다.",
  },
  {
    userId: "3최수빈",
    nftName: "벌크업 수달",
    msg: "좋아요를 눌렀습니다.",
  },
  {
    userId: "4최수빈",
    nftName: "벌크업 수달",
    msg: "좋아요를 눌렀습니다.",
  },
  {
    userId: "5이선민",
    nftName: "벌크업 수달",
    msg: "분양요청을 하였습니다.",
  },
  {
    userId: "6최수빈",
    nftName: "벌크업 수달",
    msg: "좋아요를 눌렀습니다.",
  },
  {
    userId: "7최수빈",
    nftName: "벌크업 수달",
    msg: "좋아요를 눌렀습니다.",
  },
  {
    userId: "8이선민",
    nftName: "벌크업 수달",
    msg: "분양요청을 하였습니다.",
  },
  {
    userId: "9최수빈",
    nftName: "벌크업 수달",
    msg: "좋아요를 눌렀습니다.",
  },
];
// 현재 페이지에 보여줄 데이터 목록
function currentData(currentPage, itemsPerPage) {
  const begin = (currentPage - 1) * itemsPerPage;
  const end = begin + itemsPerPage;
  return noties.slice(begin, end);
}
function Noti() {
  const itemsPerPage = 3;
  const [currentPage, setCurrentPage] = useState(1);
  const maxPage = Math.ceil(noties.length / itemsPerPage);
  const currentNoti = currentData(currentPage, itemsPerPage);

  const handlePage = (event, value) => {
    setCurrentPage(value);
  };

  // 알림 클릭 시
  const handleNoti = (event, value) => {
    console.log(value);
  };

  return (
    <div>
      <Navbar></Navbar>
      <div className="noti">
        <div className="shop-title">알림내용</div>
        <div className="noti-list">
          {noties.length === 0 && (
            <div className="noti-item">알림이 없습니다.</div>
          )}
          {currentNoti.map((noti, index) => (
            <FormGroup key={index} style={{ marginBottom: "40px" }}>
              <FormControlLabel
                control={
                  <Checkbox
                    style={{
                      color: "#C08552",
                    }}
                    onClick={(event) => handleNoti(event, { noti })}
                  />
                }
                label={
                  <div className="noti-item">
                    {noti.userId} 님이 내 수달({noti.nftName})에 {noti.msg}
                  </div>
                }
              />
              <div className="line"></div>
            </FormGroup>
          ))}
        </div>
        <Stack spacing={2} alignItems="center">
          <Pagination count={maxPage} size="large" onChange={handlePage} />
        </Stack>
      </div>
    </div>
  );
}

export default Noti;
