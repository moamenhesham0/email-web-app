import logo from './logo.svg';
import './App.css';
import Header from "./components/Header/Header";
import Sidebar from "./components/Sidebar/Sidebar";
import EmailList from "./components/EmailList/EmailList";

function App() {
  return (
    <div className="App">
      <Header />
      <div className="app-body">
        <Sidebar />
        <EmailList />
      </div>
    </div>
  );
}

export default App;
