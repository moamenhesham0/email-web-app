import React from "react";
import "./contact.css";
import { Checkbox, IconButton } from "@mui/material";
import StarBorderOutlinedIcon from "@mui/icons-material/StarBorderOutlined";
import LabelImportantOutlinedIcon from "@mui/icons-material/LabelImportantOutlined";
import { useNavigate } from 'react-router-dom';
import { selectMail } from "../../features/mailSlice";
import { useDispatch } from "react-redux";

function Contact({userName,emailAddress }) {

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const openMail = () => {
    console.log(emailAddress)
    const sender = userName;
    const textBody = emailAddress.join(', ');
    dispatch(
      selectMail({
        sender,
        textBody,
      })
    );
    navigate("/mail");
  };

  return (
    <div onClick={openMail} className="emailRow">
      <h3 className="emailRow-title">{userName}</h3>
    </div>
  );
}

export default Contact;
