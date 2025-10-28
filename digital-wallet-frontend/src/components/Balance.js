import React, { useEffect, useState } from "react";
import axios from "axios";

const Balance = () => {
  const [balance, setBalance] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchBalance = async () => {
      try {
        const username = localStorage.getItem("username");
        const password = localStorage.getItem("password");
        const response = await axios.get(
          "http://localhost:8088/api/wallet/balance",
          {
            auth: { username, password },
          }
        );
        setBalance(response.data);
      } catch (err) {
        setError("Failed to fetch wallet balance.");
      }
    };
    fetchBalance();
  }, []);

  return (
    <div className="text-center mt-10">
      <h2>Wallet Balance</h2>
      {error ? (
        <p style={{ color: "red" }}>{error}</p>
      ) : balance !== null ? (
        <p>₹{balance}</p>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
};

export default Balance;
