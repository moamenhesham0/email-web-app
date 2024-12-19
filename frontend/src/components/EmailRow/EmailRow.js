import React from "react";
import "./EmailRow.css";
import { Checkbox, IconButton } from "@mui/material";
import StarBorderOutlinedIcon from "@mui/icons-material/StarBorderOutlined";
import LabelImportantOutlinedIcon from "@mui/icons-material/LabelImportantOutlined";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { selectMail } from "../../features/mailSlice";
import {useState} from 'react';
import { useSelectedEmails } from "../Context/selectedEmailsContext";

function EmailRow({ id, isChecked, attachments, priority, read, recipient, sender, subject, textBody, timeStamp }) {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { selectedEmails, toggleEmailSelection } = useSelectedEmails(); // Use the context
  const [checked , setCheck] = useState(selectedEmails.includes(id));

  const openMail = () => {
    dispatch(selectMail({ attachments, id, priority, read, recipient, sender, subject, textBody, timeStamp }));
    navigate("/mail");
  };

  const handleCheckboxChange = () => {
    toggleEmailSelection(id); // Toggle email selection on checkbox change
    setCheck(!checked);
  };
  
  // Check if the current email is selected
  
  

  return (
    <div className="emailRow">
      <div className="emailRow-options">
        <Checkbox
          checked={checked}
          onChange={handleCheckboxChange}
        />
        <IconButton>
          <StarBorderOutlinedIcon />
        </IconButton>
        <IconButton>
          <LabelImportantOutlinedIcon />
        </IconButton>
      </div>
  
      {/* Apply onClick only to these elements */}
      <h3 className="emailRow-title" onClick={openMail}>{sender}</h3>
      <div className="emailRow-message" onClick={openMail}>
        <h4>
          {subject}{" "}
          <span className="emailRow-description"> - {textBody}</span>
        </h4>
      </div>
      <p className="emailRow-time" onClick={openMail}>{timeStamp}</p>
    </div>
  );
  
}

export default EmailRow;