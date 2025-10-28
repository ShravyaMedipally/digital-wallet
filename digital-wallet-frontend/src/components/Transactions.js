import React, { useEffect, useState } from "react";
import { api } from "../api/walletApi";

export default function Transactions() {
  const [transactions, setTransactions] = useState([]);

  useEffect(() => {
    const fetchTx = async () => {
      const username = localStorage.getItem("username");
      const password = localStorage.getItem("password");

      try {
        const res = await api.get("/transactions", {
          auth: { username, password },
        });
        setTransactions(res.data);
      } catch (err) {
        console.error("Failed to fetch transactions:", err);
        alert("Unable to fetch transactions. Please check credentials.");
      }
    };

    fetchTx();
  }, []);

  return (
    <div className="flex flex-col items-center justify-center min-h-screen">
      <div className="bg-white p-8 rounded-lg shadow-lg w-[90%] md:w-[60%]">
        <h2 className="text-2xl font-bold mb-4 text-center">
          Transaction History
        </h2>

        {transactions.length === 0 ? (
          <p className="text-gray-500 text-center">No transactions yet.</p>
        ) : (
          <table className="w-full border border-gray-300">
            <thead>
              <tr className="bg-gray-200 text-left">
                <th className="p-2 border">From</th>
                <th className="p-2 border">To</th>
                <th className="p-2 border">Amount</th>
                <th className="p-2 border">Date</th>
              </tr>
            </thead>
            <tbody>
              {transactions.map((t, index) => (
                <tr key={index} className="border-t">
                  <td className="p-2 border">{t.fromUser}</td>
                  <td className="p-2 border">{t.toUser}</td>

                  <td className="p-2 border text-green-600 font-semibold">
                    ₹{t.amount}
                  </td>
                  <td className="p-2 border">
                    {new Date(t.timestamp).toLocaleString()}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}
