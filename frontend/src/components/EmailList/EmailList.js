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
import { useSelector } from "react-redux";
import { login, selectUser } from "../../features/userSlice";
import { useDispatch } from "react-redux";
import { selectedType } from "../../features/mailSlice";




function EmailList() {
  const user = useSelector(selectUser);
  const [emails, setEmails] = useState(user.emails || []);
  const type = useSelector(selectedType);
  const dispatch = useDispatch();

  useEffect(() => {
    console.log(user.emails);
    if(!user.emails){
      fetchEmails();
    }
  }, [user, type]);
    const fetchContacts = async ()=>{
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
          setEmails(data); // Directly set the list of emails
        dispatch(
                    login({
                      ...user,
                      emails: data,
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
          <IconButton onClick={()=> fetchEmails()}>
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
      </div>
    </div>
  );
}

export default EmailList;
