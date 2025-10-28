import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./components/Login";
import Register from "./components/Register";
import AddMoney from "./components/AddMoney";
import Dashboard from "./components/Dashboard";
import Balance from "./components/Balance";
import Transfer from "./components/Transfer";
import Transactions from "./components/Transactions";
import Navbar from "./components/Navbar";
import "./styles.css";

function App() {
  const [auth, setAuth] = useState({ username: "", password: "" });

  return (
    <Router>
      {auth.username && <Navbar />}
      <Routes>
        <Route path="/" element={<Login setAuth={setAuth} />} />
        <Route path="/register" element={<Register />} />
        <Route path="/dashboard" element={<Dashboard auth={auth} />} />
        <Route path="/add-money" element={<AddMoney auth={auth} />} />
        <Route path="/balance" element={<Balance auth={auth} />} />
        <Route path="/transfer" element={<Transfer auth={auth} />} />
        <Route path="/transactions" element={<Transactions auth={auth} />} />
      </Routes>
    </Router>
  );
}

export default App;
