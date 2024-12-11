import React, { useEffect, useState } from "react";
import "./Sidebar.css";
import { Button, IconButton } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import InboxIcon from "@mui/icons-material/Inbox";
import StarIcon from "@mui/icons-material/Star";
import LabelImportantIcon from "@mui/icons-material/LabelImportant";
import NearMeIcon from "@mui/icons-material/NearMe";
import NoteIcon from "@mui/icons-material/Note";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import SidebarOption from "./SidebarOption";

function Sidebar() {
    const [selected,setSelected] = useState(false);

  return (
    <div className="sidebar">
      <Button
        className="sidebar-compose"
        startIcon={<AddIcon fontSize="large" />}
      >
        Compose
      </Button>
        <SidebarOption
          Icon={InboxIcon}
          title="Inbox"
          number={100}
          selected={false}
        />

      <SidebarOption Icon={StarIcon} title="Starred" number={12} />
      <SidebarOption Icon={LabelImportantIcon} title="Important" number={12} />
      <SidebarOption Icon={NearMeIcon} title="Sent" number={81} />
      <SidebarOption Icon={NoteIcon} title="Drafts" number={5} />
      <SidebarOption Icon={ExpandMoreIcon} title="More" />

      
    </div>
  );
}

export default Sidebar;
