import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { api } from "../api/walletApi";

export default function Register() {
  const [user, setUser] = useState({ username: "", password: "" });
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    await api.post("/users/register", user);
    alert("Registered successfully!");
    navigate("/");
  };

  return (
    <div className="flex items-center justify-center h-screen">
      <form className="bg-white p-8 rounded-lg shadow-md w-80">
        <h2 className="text-2xl font-bold mb-4 text-center">Register</h2>
        <input
          type="text"
          placeholder="Username"
          className="w-full border p-2 mb-3 rounded"
          onChange={(e) => setUser({ ...user, username: e.target.value })}
        />
        <input
          type="password"
          placeholder="Password"
          className="w-full border p-2 mb-3 rounded"
          onChange={(e) => setUser({ ...user, password: e.target.value })}
        />
        <button
          className="w-full bg-green-500 text-white p-2 rounded hover:bg-green-600"
          onClick={handleSubmit}
        >
          Register
        </button>
        <p className="mt-3 text-center">
          Already have an account?{" "}
          <Link to="/" className="text-blue-600">
            Login
          </Link>
        </p>
      </form>
    </div>
  );
}
