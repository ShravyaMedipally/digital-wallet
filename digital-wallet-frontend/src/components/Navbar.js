import React from "react";
import { Link, useNavigate } from "react-router-dom";

export default function Navbar() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.clear();
    navigate("/");
  };

  return (
    <nav className="bg-blue-600 p-4 text-white flex justify-between items-center">
      <h1 className="font-bold text-lg">💳 Digital Wallet</h1>
      <div className="flex gap-4">
        <Link to="/dashboard" className="hover:underline">
          Dashboard
        </Link>
        <Link to="/balance" className="hover:underline">
          Balance
        </Link>
        <Link to="/add-money" className="hover:underline">
          Add Money
        </Link>
        <Link to="/transfer" className="hover:underline">
          Transfer
        </Link>
        <Link to="/transactions" className="hover:underline">
          Transactions
        </Link>
        <button
          onClick={handleLogout}
          className="bg-red-500 px-3 py-1 rounded hover:bg-red-600"
        >
          Logout
        </button>
      </div>
    </nav>
  );
}
