import React from "react";
import "./EmailRow.css";
import { Checkbox, IconButton } from "@mui/material";
import StarBorderOutlinedIcon from "@mui/icons-material/StarBorderOutlined";
import LabelImportantOutlinedIcon from "@mui/icons-material/LabelImportantOutlined";
import { useNavigate } from 'react-router-dom';
import { selectMail } from "../../features/mailSlice";
import { useDispatch } from "react-redux";

function EmailRow({attachments, id, priority,read,recipient,sender,subject, textBody, timeStamp }) {

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const openMail = () => {
    dispatch(
      selectMail({
        attachments,
        id,
        priority,
        read,
        recipient,
        sender,
        subject,
        textBody,
        timeStamp
      })
    );
    navigate("/mail");
  };

  return (
    <div onClick={openMail} className="emailRow">
      <div className="emailRow-options">
        <Checkbox />
        <IconButton>
          <StarBorderOutlinedIcon />
        </IconButton>
        <IconButton>
          <LabelImportantOutlinedIcon />
        </IconButton>
      </div>
      <h3 className="emailRow-title">{sender}</h3>
      <div className="emailRow-message">
        <h4>
          {subject}{" "}
          <span className="emailRow-description"> - {textBody}</span>
        </h4>
      </div>
      <p className="emailRow-time">{timeStamp}</p>
    </div>
  );
}

export default EmailRow;
