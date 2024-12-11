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
        {/* {emails.map(({ id, data: { to, subject, message, timestamp } }) => (
          <EmailRow
            id={id}
            key={id}
            title={to}
            subject={subject}
            description={message}
            time={new Date(timestamp?.seconds * 1000).toUTCString()}
          />
        ))} */}
        <EmailRow
          title="Twitch"
          subject="Hey fellow streamer!!"
          description="This is a DOPE"
          time="10pm"
        />
        <EmailRow
          title="Epic Games"
          subject="Update to our Player Agreements"
          description="Re: Update to our Player Agreement In June, we shared that we’re making some updates to our End User License Agreement (EULA) for Fortnite. This took longer than expected and the updated agreement will go into effect on December 13, 2024, when we’ll ask you to review and accept the terms the next time you log into Fortnite.
We’ve posted these changes online so you can take a look at them before they go into effect. To review them, click “Read New Terms” at the top of the Fortnite End User License Agreement webpage."
          time="5pm"
        />
        <EmailRow
          title="Course Hero "
          subject="Welcome to Course Hero"
          description="Welcome to studying, superpowered
You made the first step toward smarter studying—way to go. See all the ways you can superpower your studying with Course Hero."
          time="7am"
        />
      </div>
    </div>
  );
}

export default EmailList;
