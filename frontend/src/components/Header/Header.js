import React, { useState } from "react";
import { Avatar, IconButton } from "@mui/material";
import "./Header.css";
import MenuIcon from "@mui/icons-material/Menu";
import SearchIcon from "@mui/icons-material/Search";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { login, selectUser } from "../../features/userSlice";
import { selectedType, selectType } from "../../features/mailSlice"; 
import {
  MenuItem,
  Select,
  FormControl,
  InputLabel,
} from "@mui/material";


function Header() {
  const [searchInput, setSearchInput] = useState(""); // To hold the search keyword
  const user = useSelector(selectUser);
  const folderType = useSelector(selectedType);
  const dispatch = useDispatch();
const [selectedOption, setSelectedOption] = useState("");
  const handleOptionChange = (value) => {
  setSelectedOption(value);
    handleSort(selectedOption);
  }
  const handleSearch = async () => {
    const emailAddress = user.email; // Replace with the logged-in user's email address
    const searchByCriteria = [
      "body",
      "subject",
      "sender",
      "recipient",
      "priority",
      "attachments",
    ]; // All the specified categories
    const folderName = folderType; // Define the folder to search in
    console.log(folderName);
    if(folderName === "Contacts"){
      try {
        const results = [];
          // Perform a search for each criterion
          const formData = new FormData();
          formData.append("emailAddress", emailAddress);
          formData.append("query", searchInput);
  
          const response = await fetch("http://localhost:8080/api/user/searchContacts", {
            method: "POST",
            body: formData,
          });
  
          if (response.ok) {
            const data = await response.json();
            console.log(data);
            results.push(...data);
          } else {
            console.error(`Search failed for:`, response.statusText);
          }
        if (results.length > 0) {
          dispatch(
            login({
              ...user,
              emails: results,
              Csearch: true,
            })
          );
        }
      } catch (error) {
        console.error("Error performing search:", error);
      }
    }
    else{
    try {
      const emailSet = new Set();
      const results = [];
      for (const searchBy of searchByCriteria) {
        // Perform a search for each criterion
        const formData = new FormData();
        formData.append("emailAddress", emailAddress);
        formData.append("searchBy", searchBy);
        formData.append("folderName", folderName);
        formData.append("keyword", searchInput);

        const response = await fetch("http://localhost:8080/api/search/emails", {
          method: "POST",
          body: formData,
        });

        if (response.ok) {
          const data = await response.json();
          console.log(data);
          data.forEach((email) => {
            if (!emailSet.has(email.id)) {
              emailSet.add(email.id);
              results.push(email);
            }
          });
        } else {
          console.error(`Search failed for ${searchBy}:`, response.statusText);
        }
      }
      if (results.length > 0) {
        dispatch(
          login({
            ...user,
            emails: results,
            Esearch: true,
          })
        );
      }
    } catch (error) {
      console.error("Error performing search:", error);
    }
  }
  };
  const handleSort = async (sortBy) => {
    const emailAddress = user.email; // Replace with the logged-in user's email address
    const folderName = folderType; // Define the folder to sort in
  
    try {
      const formData = new FormData();
      console.log(sortBy);
      formData.append("emailAddress", emailAddress);
      formData.append("sortBy", sortBy);
      formData.append("folderName", folderName);
      formData.append("order", false); // Pass sorting order (true for ascending, false for descending)
  
      const response = await fetch("http://localhost:8080/api/sort/emails", {
        method: "POST",
        body: formData,
      });
  
      if (response.ok) {
        const sortedEmails = await response.json();
        console.log("Sorted Emails:", sortedEmails);
        dispatch(
          login({
            ...user,
            emails: sortedEmails,
            Esearch: true,
          })
        );
      } else {
        console.error("Sorting failed:", response.statusText);
      }
    } catch (error) {
      console.error("Error performing sort:", error);
    }
  };
  return (
    <div className="header">
      <div className="header-left">
        <IconButton>
          <MenuIcon />
        </IconButton>
        <img src="logo.jpeg" alt="gmail logo" />
      </div>
      <div className="header-middle">
        <SearchIcon />
        <input
          type="text"
          placeholder="Search mail"
          value={searchInput}
          onChange={(e) => setSearchInput(e.target.value)} // Update the searchInput state
        />
        <button onClick={handleSearch}>Search</button>
          <FormControl className="sendMail-dropdown" size="small">
          <InputLabel id="option-select-label">Sort By:</InputLabel>
          <Select
            labelId="option-select-label"
            value={selectedOption}
            onChange={(event) =>  {handleSort(event.target.value);setSelectedOption(event.target.value)}}
            >
            <MenuItem value="body" >body</MenuItem>
            <MenuItem value="subject">subject</MenuItem>
            <MenuItem value="sender">sender</MenuItem>
            <MenuItem value="recipient">recipient</MenuItem>
            <MenuItem value="priority">priority</MenuItem>
            <MenuItem value="timestamp">timestamp</MenuItem>
            <MenuItem value="Attachments">Attachments</MenuItem>
          </Select>
        </FormControl>
      </div>
          
      <div className="header-right">
        <Avatar />
      </div>
    </div>
  );
}

export default Header;
