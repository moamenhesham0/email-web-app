import React, { useEffect, useState } from "react";
import "./Sidebar.css";
import { Button, IconButton, TextField, Dialog, DialogActions, DialogContent, DialogTitle } from "@mui/material";
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
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";



function Sidebar() {
  const dispatch = useDispatch();
  const [selected,setselected] = useState(true);
  const [selectedOption, setSelectedOption] = useState(null);
  const user = useSelector(selectUser);
  const [openDialog, setOpenDialog] = useState(false);
  const [folderName, setFolderName] = useState("");
  const [folders, setFolders] = useState([]);
  const [isfolder, setisfolder] = useState(true);
  const [renameDialogOpen, setRenameDialogOpen] = useState(false);
const [currentFolder, setCurrentFolder] = useState("");
const [newFolderName, setNewFolderName] = useState("");

  useEffect(() => {
    const fetchFolders = async () => {
      if (isfolder) {
        const queryParams = new URLSearchParams({ emailAddress: user.email });
        try {
          const response = await fetch(`http://localhost:8080/api/user/loadFolders?${queryParams}`, {
            method: "GET",
          });
  
          if (!response.ok) {
            const errorDetails = await response.json();
            throw new Error(errorDetails.message);
          }
  
          const data = await response.json();
          setFolders(data);
          setFolders((prevFolders) => prevFolders.slice(4));
          setisfolder(false);
        } catch (error) {
          console.error(error);
        }
      }
    };
  
    fetchFolders();
  }, [isfolder, user]);

  const handleDeleteFolder = async (folderName) => {
    try {
      const queryParams = new URLSearchParams({
        emailAddress: user.email,
        folderName: folderName,
      });
  
      const response = await fetch(`http://localhost:8080/api/user/deleteFolder?${queryParams}`, {
        method: "DELETE",
      });
  
      if (!response.ok) {
        const errorDetails = await response.json();
        throw new Error(errorDetails.message);
      }
  
      // Update the folders state
      setFolders((prevFolders) => prevFolders.filter((folder) => folder !== folderName));
      console.log("Folder Deleted:", folderName);
    } catch (error) {
      console.error(error);
      alert("Failed to delete folder. Please try again.");
    }
  };

const handleContacts = () => {
  setselected(false);
  setSelectedOption("Contacts");
  dispatch(selectType("Contacts"));
  dispatch(
    login({
      ...user,
      contacts: true,
    })
);
}

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

  const handleCreateFolder = async() => {
    // You can implement the logic for folder creation here
    try {
      const formData = new FormData();
      formData.append("emailAddress", user.email)
      formData.append("folderName", folderName)
      const response = await fetch("http://localhost:8080/api/user/addFolder", {
        method: "POST",
        body: formData,
      });

      if (!response.ok) {
        const errorDetails = await response.json();
        throw new Error(errorDetails.message);
      }
    } catch (error) {
      console.error(error);
      alert("Invalid email or password. Please try again.");
      }
    console.log("Folder Created: ", folderName);
    setFolders([...folders, folderName]);
    setFolderName("");
    setOpenDialog(false);
  };
  const handleOpenRenameDialog = (folder) => {
    setCurrentFolder(folder);
    setNewFolderName(folder);
    setRenameDialogOpen(true);
  };
  // Function to rename a folder
  const handleRenameFolder = async () => {
    try {
      const queryParams = new URLSearchParams({
        emailAddress: user.email,
        folderName: currentFolder,
        folderNewName: newFolderName,
      });
      const response = await fetch(`http://localhost:8080/api/user/renameFolder?${queryParams}`, {
        method: "POST",
      });
      if (!response.ok) {
        const errorDetails = await response.json();
        throw new Error(errorDetails.message);
      }
      // Update folders state with the new folder name
      setFolders((prevFolders) =>
        prevFolders.map((folder) =>
          folder === currentFolder ? newFolderName : folder
        )
      );
      console.log("Folder Renamed:", currentFolder, "->", newFolderName);
      setRenameDialogOpen(false);
    } catch (error) {
      console.error(error);
      alert("Failed to rename folder. Please try again.");
    }
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
        onClick={() => setOpenDialog(true)}
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
      title="Trash" 
      number={12} 
      onClick={() => handleOptionClick("Trash")}
      selected={selectedOption === "Trash"}
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
      title="Draft"
      number={5} 
      onClick={() => handleOptionClick("Draft")}
      selected={selectedOption === "Draft"}
      />
            <SidebarOption
      Icon={NoteIcon}
      title="Contacts"
      number={5} 
      onClick={() => handleContacts()}
      selected={selectedOption === "Contacts"}
      />
      {folders.map((folder, index) => (
  <div key={index} style={{ display: "flex", alignItems: "center", justifyContent: "space-between" }}>
    {/* SidebarOption to show the folder name */}
    <SidebarOption
      title={folder}
      onClick={() => handleOptionClick(folder)}
      selected={selectedOption === folder}
    />
    
    {/* Buttons for Delete and Rename */}
    <div style={{ display: "flex", gap: "5px" }}>
      <IconButton
        onClick={() => handleDeleteFolder(folder)}
        aria-label={`Delete ${folder}`}
        size="small"
      >
        <DeleteIcon fontSize="small" />
      </IconButton>
      <IconButton
        onClick={() => handleOpenRenameDialog(folder)}
        aria-label={`Rename ${folder}`}
        size="small"
      >
        <EditIcon fontSize="small" />
      </IconButton>
    </div>
  </div>
))}
      
      <Dialog open={openDialog} onClose={() => setOpenDialog(false)}>
        <DialogTitle>Create a Folder</DialogTitle>
        <DialogContent>
          <TextField
            label="Folder Name"
            fullWidth
            value={folderName}
            onChange={(e) => setFolderName(e.target.value)}
            autoFocus
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenDialog(false)} color="primary">
            Cancel
          </Button>
          <Button onClick={handleCreateFolder} color="primary">
            Create
          </Button>
        </DialogActions>
      </Dialog>

      <Dialog
    open={renameDialogOpen}
    onClose={() => setRenameDialogOpen(false)}
    aria-labelledby="rename-folder-dialog-title"
  >
    <DialogTitle id="rename-folder-dialog-title">Rename Folder</DialogTitle>
    <DialogContent>
      <TextField
        autoFocus
        margin="dense"
        label="New Folder Name"
        type="text"
        fullWidth
        value={newFolderName}
        onChange={(e) => setNewFolderName(e.target.value)}
      />
    </DialogContent>
    <DialogActions>
      <Button onClick={() => setRenameDialogOpen(false)} color="secondary">
        Cancel
      </Button>
      <Button onClick={handleRenameFolder} color="primary">
        Rename
      </Button>
    </DialogActions>
  </Dialog>
    </div>
  );
}

export default Sidebar;
