import React, { useState } from "react";
import Badge from "@material-ui/core/Badge";
import NotificationsIcon from "@material-ui/icons/Notifications";

export default function Notice() {
  const [isChecked, setIsChecked] = useState(false);
  const onClick = () => {
    setIsChecked(true);
  };
  // store로 관리하기
  return (
    <div>
      {!isChecked ? (
        <Badge color="secondary" variant="dot">
          <NotificationsIcon onClick={onClick} />
        </Badge>
      ) : (
        <NotificationsIcon />
      )}
    </div>
  );
}
