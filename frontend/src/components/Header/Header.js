import React, { useState } from "react";
import { Avatar, IconButton } from "@mui/material";
import "./Header.css";
import MenuIcon from "@mui/icons-material/Menu";
import SearchIcon from "@mui/icons-material/Search";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { login, selectUser } from "../../features/userSlice";


function Header() {
  const [searchInput, setSearchInput] = useState(""); // To hold the search keyword
  const user = useSelector(selectUser);
  const dispatch = useDispatch();

  const handleSearch = async () => {
    const emailAddress = user.email; // Replace with the logged-in user's email address
    const searchByCriteria = [
      "body",
      "subject",
      "sender",
      "recipient",
      "priority",
      "timestamp",
      "attachments",
    ]; // All the specified categories
    const folderName = "Inbox"; // Define the folder to search in

    try {
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
          results.push(...data);
        } else {
          console.error(`Search failed for ${searchBy}:`, response.statusText);
        }
      }
      if (results.length > 0) {
        dispatch(
          login({
            ...user,
            emails: results,
            search: true,
          })
        );
      }
    } catch (error) {
      console.error("Error performing search:", error);
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
      </div>
      <div className="header-right">
        <Avatar />
      </div>
    </div>
  );
}

export default Header;
