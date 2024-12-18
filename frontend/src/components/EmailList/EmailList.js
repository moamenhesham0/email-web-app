import { Checkbox, IconButton } from "@mui/material";
import React, { useEffect, useState } from "react";
import "./EmailList.css";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import RedoIcon from "@mui/icons-material/Redo";
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import KeyboardHideIcon from "@mui/icons-material/KeyboardHide";
import SettingsIcon from "@mui/icons-material/Settings";
import EmailRow from "../EmailRow/EmailRow";


function EmailList() {
  const [emails, setEmails] = useState([]);

  useEffect(() => {
  const fetchEmails = async () => {

    
    // Replace this with your API call
    const mockEmails = [
      {
        id: "1",
        data: {
          to: "user1@example.com",
          subject: "Welcome to our service!",
          message: "Thank you for signing up.",
          timestamp: { seconds: 1673452800 }, // Mock timestamp
        },
      },
      {
        id: "2",
        data: {
          to: "user2@example.com",
          subject: "Your Invoice",
          message: "Here is your invoice for this month.",
          timestamp: { seconds: 1673539200 },
        },
      },
    ];
    setEmails(mockEmails);
  };

  fetchEmails();
}, []);


  return (
    <div className="emailList">
      <div className="emailList-settings">
        <div className="emailList-settingsLeft">
          <Checkbox />
          <IconButton>
            <ArrowDropDownIcon />
          </IconButton>
          <IconButton>
            <RedoIcon />
          </IconButton>
          <IconButton>
            <MoreVertIcon />
          </IconButton>
        </div>
        <div className="emailList-settingsRight">
          <IconButton>
            <ChevronLeftIcon />
          </IconButton>
          <IconButton>
            <ChevronRightIcon />
          </IconButton>
          <IconButton>
            <KeyboardHideIcon />
          </IconButton>
          <IconButton>
            <SettingsIcon />
          </IconButton>
        </div>
      </div>

      <div className="emailList-list">
        {emails.map(({ id, data: { to, subject, message, timestamp } }) => (
          <EmailRow
            id={id}
            key={id}
            title={to}
            subject={subject}
            description={message}
            time={new Date(timestamp?.seconds * 1000).toUTCString()}
          />
        ))}
      </div>
    </div>
  );
}

export default EmailList;
