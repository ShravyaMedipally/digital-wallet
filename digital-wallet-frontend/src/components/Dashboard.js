import React from "react";
import { useNavigate } from "react-router-dom";
import { Send, History, DollarSign, LogOut } from "lucide-react";
import "./Dashboard.css";

export default function Dashboard() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("username");
    localStorage.removeItem("password");
    navigate("/Login");
  };

  return (
    <div className="dashboard-container">
      <div className="dashboard-box">
        {/* Logout button top right */}
        <button className="logout-btn" onClick={handleLogout}>
          <LogOut size={18} /> Logout
        </button>

        <h1>💳 Digital Wallet Dashboard</h1>
        <div className="button-grid">
          <button onClick={() => navigate("/balance")} className="btn btn-blue">
            <DollarSign size={20} /> Check Balance
          </button>

          <button
            onClick={() => navigate("/transfer")}
            className="btn btn-green"
          >
            <Send size={20} /> Transfer Money
          </button>

          <button
            onClick={() => navigate("/transactions")}
            className="btn btn-purple"
          >
            <History size={20} /> View Transactions
          </button>
        </div>
      </div>
    </div>
  );
}
