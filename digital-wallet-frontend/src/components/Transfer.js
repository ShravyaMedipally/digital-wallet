import React, { useState } from "react";
import { api } from "../api/walletApi";

export default function Transfer() {
  const [receiver, setReceiver] = useState("");
  const [amount, setAmount] = useState("");

  const handleTransfer = async (e) => {
    e.preventDefault();

    const username = localStorage.getItem("username");
    const password = localStorage.getItem("password");

    try {
      await api.post(
        "/transfer",
        {
          to: receiver,
          amount: parseFloat(amount),
        },
        {
          auth: { username, password },
        }
      );

      alert("Transfer successful!");
      setReceiver("");
      setAmount("");
    } catch (err) {
      console.error("Transfer failed:", err);
      alert("Transfer failed. Check details or server logs.");
    }
  };

  return (
    <div className="flex flex-col items-center justify-center h-screen">
      <form
        onSubmit={handleTransfer}
        className="bg-white p-8 rounded-lg shadow-md w-80"
      >
        <h2 className="text-2xl font-bold mb-4 text-center">Transfer Money</h2>
        <input
          type="text"
          placeholder="Receiver Username"
          className="w-full border p-2 mb-3 rounded"
          value={receiver}
          onChange={(e) => setReceiver(e.target.value)}
          required
        />
        <input
          type="number"
          placeholder="Amount"
          className="w-full border p-2 mb-3 rounded"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
          required
        />
        <button
          type="submit"
          className="w-full bg-green-500 text-white p-2 rounded hover:bg-green-600"
        >
          Transfer
        </button>
      </form>
    </div>
  );
}
