import { Button, TextField } from "@mui/material";
import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { useNavigate, Link } from 'react-router-dom';
import { login } from "../../features/userSlice";
import "./SignUp.css";

function Login() {
  const dispatch = useDispatch();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [FN, setFN] = useState("");
  const [LN, setLN] = useState("");
  const navigate = useNavigate();


  const signUp = () => {
    // Simulate backend validation
    if (email === "test@example.com" && password === "password123") {
      const mockUser = {
        displayName: "Test",
        email: email,
        photoUrl: "https://via.placeholder.com/150",
      };

      dispatch(
        login({
          displayName: mockUser.displayName,
          email: mockUser.email,
          photoUrl: mockUser.photoUrl,
        })
      );
    } else {
      alert("Invalid email or password. Please try again.");
    }
  };

  return (
    <div className="login">
      <div className="login-container">
        <img
          src="https://static.dezeen.com/uploads/2020/10/gmail-google-logo-rebrand-workspace-design_dezeen_2364_col_0.jpg"
          alt="Logo"
        />
        <TextField
          label="Firstname"
          variant="outlined"
          fullWidth
          margin="normal"
          value={FN}
          onChange={(e) => setFN(e.target.value)}
        />
        <TextField
          label="Lastname"
          variant="outlined"
          fullWidth
          margin="normal"
          value={LN}
          onChange={(e) => setLN(e.target.value)}
        />
        <TextField
          label="Email"
          variant="outlined"
          fullWidth
          margin="normal"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <TextField
          label="Password"
          type="password"
          variant="outlined"
          fullWidth
          margin="normal"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <Button
          variant="contained"
          color="primary"
          onClick={() => {
            signUp();
            navigate("/emaillist");
          }}
          style={{ marginTop: "20px" }}
        >
          SignUp
        </Button>
        <p className="signin">
        Already have an account? <Link to="/">Sign in</Link>
        </p>
        </div>
    </div>
  );
}

export default Login;
