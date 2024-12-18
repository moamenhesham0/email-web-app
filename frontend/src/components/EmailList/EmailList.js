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
import Contact from "../Contacts/contact";
import { useSelector } from "react-redux";
import { login, selectUser } from "../../features/userSlice";
import { useDispatch } from "react-redux";
import { openSendMessage, selectedType } from "../../features/mailSlice";
import { Button,TextField,Dialog, DialogActions, DialogContent, DialogTitle } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";





function EmailList() {
  const user = useSelector(selectUser);
  const [emails, setEmails] = useState(user.emails || []);
  const [contacts, setcontacts] = useState([]);
  const [contactName, setcontactName] = useState("");
  const [emailAddress, setemailAddress] = useState([]);
  const [iscontacts, setiscontacts] = useState(false);
  const type = useSelector(selectedType);
  const dispatch = useDispatch();
  const [openDialog, setOpenDialog] = useState(false);


  
  useEffect(() => {
    console.log(user.emails);
    if(!user.emails){
      fetchEmails();
    }
    if(user.contacts){
      fetchContacts();
        dispatch(
          login({
            ...user,
            contacts: false,
          })
      );
    }
  }, [user, type]);

  const fetchContacts = async ()=>{
    setEmails([]);
    const queryParams = new URLSearchParams({
      emailAddress: user.email,
    });
    try{
      const response = await fetch(`http://localhost:8080/api/user/getContacts?${queryParams}`,
        {
          method : 'GET',
        }
      );
      if(response.ok)
      {
        const data = await response.json();
        const normalizedData = data.map(contact => ({
          ...contact,
          emailAddress: Array.isArray(contact.emailAddress) ? contact.emailAddress : [], // Default to empty array if missing
        }));
      
        console.log(normalizedData);
        setcontacts(normalizedData);
        setiscontacts(true);
      dispatch(
                  login({
                    ...user,
                    contacts: false,
                  })
            );
        
      }else{
        const errorDetails = await response.json();
        throw new Error(errorDetails.message);
      }
    }catch (error)
    {
      console.error("Error fetching Contacts:", error);
      alert("Failed to fetch Contacts. Please try again.");
    }
  };
    const fetchEmails = async () => {
      setiscontacts(false);
      setcontacts([]);
      console.log(user.email);
      console.log(type);
      const queryParams = new URLSearchParams({
        emailAddress: user.email,
        folderName: type,
      });
  
      try {
        const response = await fetch(`http://localhost:8080/api/user/getEmailByFolder?${queryParams}`, {
          method: "GET", // GET request with query parameters
        });
  
        if (!response.ok) {
          const errorDetails = await response.json();
          throw new Error(errorDetails.message);
        }
        const responseData = await response.json(); // Parse the JSON response
        console.log(responseData);
        setEmails(responseData); // Directly set the list of emails
        dispatch(
                    login({
                      ...user,
                      emails: responseData,
                    })
              );
      } catch (error) {
        console.error("Error fetching emails:", error);
        alert("Failed to fetch emails. Please try again.");
      }
    };

  return (
    <div className="emailList">
      <div className="emailList-settings">
        <div className="emailList-settingsLeft">
          <Checkbox />
          <IconButton>
            <ArrowDropDownIcon />
          </IconButton>
          {!iscontacts &&<IconButton onClick={()=> fetchEmails()}>
            <RedoIcon />
          </IconButton>}
          {iscontacts &&<Button
        startIcon={<AddIcon fontSize="large" />}
        onClick={() => setOpenDialog(true)}
      >
        Add Contact
      </Button>}
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
        {emails.map(({ attachments,id,read,priority:priorityString,recipient,sender,subject, textBody, timeStamp }) => (
          <EmailRow
          attachments={attachments}
          id={id}
          priority={priorityString}
          read={read}
          recipient={recipient}
          sender={sender}
          subject={subject}
          textBody={textBody}
          timeStamp={timeStamp}
          />
        ))}
        {contacts.map(({ userName,emailAdress }) => (
          <Contact
          userName={userName}
          emailAddress={emailAdress}
          />
        ))}

      </div>
      <Dialog open={openDialog} onClose={() => setOpenDialog(false)}>
        <DialogTitle>Add a Contact</DialogTitle>
        <DialogContent>
          <TextField
            label="Contact Name"
            fullWidth
            value={contactName}
            onChange={(e) => setcontactName(e.target.value)}
            autoFocus
          />
          <TextField
            label="Emails"
            fullWidth
            value={emailAddress}
            onChange={(e) => setemailAddress(e.target.value)}
            autoFocus
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenDialog(false)} color="primary">
            Cancel
          </Button>
          <Button  color="primary">
            Add
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}

export default EmailList;
