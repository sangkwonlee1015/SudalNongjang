import React, { useEffect, useState } from "react";
import ReactPaginate from "react-paginate";
import "./MyItem.css";
import axios from "axios";
const apiUrl = "https://j7a606.p.ssafy.io/";

function Mouth({ itemsPerPage, setUrl, setMouthId }) {
  const [data, setData] = useState([]);
  const [len, setLen] = useState(0);
  const handleInfo = (data, len) => {
    const result = [];
    for (let i = 0; i < len; i++) {
      if (data[i].type === 3) {
        result.push(data[i]);
      }
    }
    setData(result);
    setLen(result.length);
  };
  const [currentItems, setCurrentItems] = useState([]);
  const [pageCount, setPageCount] = useState(0);
  const [itemOffset, setItemOffset] = useState(0);
  useEffect(() => {
    const endOffset = itemOffset + itemsPerPage;
    axios
      .get(apiUrl + "api/item/inventory", {
        headers: {
          Authorization: "Bearer " + window.localStorage.getItem("token"),
        },
      })
      .then((res) => handleInfo(res.data, res.data.length));
  }, []);

  useEffect(() => {
    const endOffset = itemOffset + itemsPerPage;
    console.log(`Loading items from ${itemOffset} to ${endOffset}`);
    setCurrentItems(data.slice(itemOffset, endOffset));

    setPageCount(Math.ceil(data.length / itemsPerPage));
  }, [itemOffset, itemsPerPage, data]);

  const handlePageClick = (event) => {
    const newOffset = (event.selected * itemsPerPage) % data.length;
    console.log(
      `User requested page number ${event.selected}, which is offset ${newOffset}`
    );

    setItemOffset(newOffset);
  };

  return (
    <div className="inventory">
      <div className="items">
        {currentItems.map((info, idx) =>
          info.howMany > 0 ? (
            <div style={{ position: "relative", marginRight: "3%" }}>
              <img
                className={`parts ${
                  info.rare === 1 ? "normal" : info.rare === 2 ? "rare" : "epic"
                }`}
                key={idx}
                alt=""
                src={`${process.env.PUBLIC_URL}/assets/images/items/Mouth/03_${info.number}_${info.rgb}_${info.rare}.png`}
                onClick={() => {
                  setUrl(
                    `${process.env.PUBLIC_URL}/assets/images/items/Mouth/03_${info.number}_${info.rgb}_${info.rare}.png`
                  );
                  setMouthId(info.itemId);
                }}
              />
              <span style={{ position: "absolute", top: "75%", left: "90%" }}>
                {info.howMany}
              </span>
            </div>
          ) : null
        )}
      </div>
      <ReactPaginate
        className="paginate"
        breakLabel="..."
        nextLabel=">"
        onPageChange={handlePageClick}
        pageRangeDisplayed={1}
        pageCount={pageCount}
        previousLabel="<"
        renderOnZeroPageCount={null}
      />
    </div>
  );
}

export default Mouth;
