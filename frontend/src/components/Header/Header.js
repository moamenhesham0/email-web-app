import React from "react";
import { Avatar, IconButton } from "@mui/material";
import "./Header.css";
import MenuIcon from '@mui/icons-material/Menu';
import SearchIcon from '@mui/icons-material/Search';
function Header(){
    return(
    <div className="header">
        <div className="header-left">
            <IconButton>
                <MenuIcon />
            </IconButton>
            <img
            src= "logo.jpeg"
            alt="gmail logo"
            />
            </div>
            <div className="header-middle">
                <SearchIcon />
                <input type="text" placeholder="Search mail" />
            </div>
            <div className="header-right">
                <Avatar />
        </div>
    </div>
    );
}
export default Header;
