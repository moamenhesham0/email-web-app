import './App.css';
import Header from "./components/Header/Header";
import Sidebar from "./components/Sidebar/Sidebar";
import EmailList from "./components/EmailList/EmailList";
import SendMail from "./components/SendMail/SendMail";
import Login from "./components/Login/Login";
import SignUp from "./components/SignUp/SignUp";
import Mail from "./components/Mail/Mail";
import { login, selectUser } from "./features/userSlice";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { useSelector } from "react-redux";
import { selectSendMessageIsOpen } from "./features/mailSlice";


function App() {
  const user = useSelector(selectUser);
  const sendMessageIsOpen = useSelector(selectSendMessageIsOpen);

  return (
    <Router>
      {!user ? (
        <Routes>
              <Route path="/" element={<Login/>} />
              <Route path="/signup" element={<SignUp />} />
            </Routes>
        ) : (
        <div className="app">
          <Header />
          <div className="app-body">
            <Sidebar />
            
            <Routes>
              <Route path="/emaillist" element={<EmailList />} />
              <Route path="/mail" element={<Mail />} />
            </Routes>
          </div>

          {sendMessageIsOpen && <SendMail />}
        </div>
      )}
    </Router>
  );
}

export default App;
