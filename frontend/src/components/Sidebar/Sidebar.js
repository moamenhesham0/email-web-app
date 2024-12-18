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
import { useDispatch } from "react-redux";
import { openSendMessage, selectType } from "../../features/mailSlice";
import { login, selectUser } from "../../features/userSlice";
import { useSelector } from "react-redux";



function Sidebar() {
  const dispatch = useDispatch();
  const [selected,setselected] = useState(true);
  const [selectedOption, setSelectedOption] = useState(null);
  const user = useSelector(selectUser);
  const [openDialog, setOpenDialog] = useState(false);
  const [folderName, setFolderName] = useState("");

  const handleOptionClick = (title) => {
    setselected(false);
    setSelectedOption(title);
    dispatch(selectType(title));
    dispatch(
                        login({
                          ...user,
                          emails: null,
                        })
                  );
  };

  const handleCreateFolder = () => {
    // You can implement the logic for folder creation here
    console.log("Folder Created: ", folderName);
    setFolderName("");
    setOpenDialog(false);
  };

  return (
    <div className="sidebar">
      <div>
      <Button
        className="sidebar-compose"
        onClick={() => dispatch(openSendMessage())}
        startIcon={<AddIcon fontSize="large" />}
      >
        Compose
      </Button>
      <Button
        className="sidebar-compose"
        startIcon={<AddIcon fontSize="large" />}
        onClick={() => folderName()}
      >
        Create Folder
      </Button>
      </div>
        <SidebarOption
          Icon={InboxIcon}
          title="Inbox"
          number={100}
          onClick={() => handleOptionClick("Inbox")}
          selected={selected || selectedOption === "Inbox"}
        />

      <SidebarOption
      Icon={StarIcon} 
      title="Starred" 
      number={12} 
      onClick={() => handleOptionClick("Starred")}
      selected={selectedOption === "Starred"}
      />
      <SidebarOption
      Icon={LabelImportantIcon}
      title="Important"
      number={12} 
      onClick={() => handleOptionClick("Important")}
      selected={selectedOption === "Important"}
      />
      <SidebarOption
      Icon={NearMeIcon}
      title="Sent"
      number={81}
      onClick={() => handleOptionClick("Sent")}
      selected={selectedOption === "Sent"}
      />
      <SidebarOption
      Icon={NoteIcon}
      title="Drafts"
      number={5} 
      onClick={() => handleOptionClick("Drafts")}
      selected={selectedOption === "Drafts"}
      />
      <SidebarOption Icon={ExpandMoreIcon} title="More" />
      
    </div>
  );
}

export default Sidebar;
