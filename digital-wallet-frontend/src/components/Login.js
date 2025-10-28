import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../api/walletApi";
import "./Login.css"; // 👈 Import CSS file

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post("/login", { username, password });
      if (response.status === 200) {
        localStorage.setItem("username", username);
        localStorage.setItem("password", password);
        alert("Login successful");
        navigate("/dashboard");
      }
    } catch (error) {
      alert("Invalid credentials");
      console.error("Login error:", error);
    }
  };

  return (
    <div className="login-container">
      <form className="login-box" onSubmit={handleLogin}>
        <h2>Digital Wallet Login</h2>

        <input
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          placeholder="Username"
          required
        />

        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="Password"
          required
        />

        <button type="submit">Login</button>
        <p className="tagline">Secure Access to Your Wallet 💳</p>
      </form>
    </div>
  );
};

export default Login;
