import React, { useEffect } from "react";
import "./EmailRow.css";
import { Checkbox, IconButton } from "@mui/material";
import StarBorderOutlinedIcon from "@mui/icons-material/StarBorderOutlined";
import LabelImportantOutlinedIcon from "@mui/icons-material/LabelImportantOutlined";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { selectMail } from "../../features/mailSlice";
import {useState} from 'react';
import { useSelectedEmails } from "../Context/selectedEmailsContext";
import { openSendMessage, selectType } from "../../features/mailSlice";


function EmailRow({ id, isChecked, attachments, priority, read, recipient, sender, subject, textBody, timeStamp }) {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { selectedEmails, removeEmail , addEmail } = useSelectedEmails(); // Use the context
  const [checked , setCheck] = useState(isChecked);

  const openMail = () => {
    dispatch(selectMail({ attachments, id, priority, read, recipient, sender, subject, textBody, timeStamp }));
    navigate("/mail");
    if(!subject || !textBody){
      dispatch(openSendMessage());
    }
  };

  useEffect(() => {
    setCheck(isChecked); // Update checked state when isChecked prop changes
  }, [isChecked]);

  const handleCheckboxChange = () => {
    if(checked) // Toggle email selection on checkbox change
      removeEmail(id);
    else{
      addEmail(id);
    }
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