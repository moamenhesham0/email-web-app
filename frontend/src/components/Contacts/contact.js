import React, { useState } from "react";
import "./contact.css";
import { Checkbox, IconButton } from "@mui/material";
import StarBorderOutlinedIcon from "@mui/icons-material/StarBorderOutlined";
import LabelImportantOutlinedIcon from "@mui/icons-material/LabelImportantOutlined";
import { useNavigate } from 'react-router-dom';
import { selectMail } from "../../features/mailSlice";
import { useDispatch } from "react-redux";
import { useSelectedContacts } from "./contactContext";
import EditIcon from "@mui/icons-material/Edit";


function Contact({userName,emailAddress }) {
  const {selectedContacts , addContact , removeContact} = useSelectedContacts();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [checked , setCheck] = useState(selectedContacts.includes(userName))
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

  const handleCheckboxChange = () => {
    if(checked) // Toggle email selection on checkbox change
      removeContact(userName);
    else{
      addContact(userName);
    }
    setCheck(!checked);
  };

  return (
    <div  className="emailRow">
      <Checkbox
                checked={checked}
                onChange={handleCheckboxChange}
              />
      <IconButton>
        <EditIcon fontSize="small" />
      </IconButton>
      <h3 onClick={openMail} className="emailRow-title">{userName}</h3>
    </div>
  );
}

export default Contact;
