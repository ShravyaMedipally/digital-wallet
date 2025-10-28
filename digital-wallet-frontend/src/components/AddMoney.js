import React, { useState } from "react";
import { api } from "../api/walletApi";

export default function AddMoney() {
  const [amount, setAmount] = useState("");
  const [loading, setLoading] = useState(false);

  const handleAddMoney = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      await api.post("/add", { amount: parseFloat(amount) });
      alert(`✅ ₹${amount} added successfully!`);
      setAmount("");
    } catch (err) {
      console.error(err);
      alert(
        "❌ Failed to add money. Please check your credentials or server logs."
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex flex-col items-center justify-center h-screen">
      <form
        onSubmit={handleAddMoney}
        className="bg-white p-8 rounded-lg shadow-lg w-80 text-center"
      >
        <h2 className="text-2xl font-bold mb-4">Add Money</h2>

        <input
          type="number"
          placeholder="Enter amount"
          className="w-full border p-2 mb-3 rounded"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
          required
        />

        <button
          type="submit"
          disabled={loading}
          className="w-full bg-blue-500 text-white p-2 rounded hover:bg-blue-600 disabled:opacity-50"
        >
          {loading ? "Adding..." : "Add Money"}
        </button>
      </form>
    </div>
  );
}
