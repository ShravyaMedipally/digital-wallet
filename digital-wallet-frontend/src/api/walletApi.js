import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:8088/api/wallet",
});
api.interceptors.request.use((config) => {
  const username = localStorage.getItem("username");
  const password = localStorage.getItem("password");

  if (username && password) {
    config.auth = { username, password };
  }

  return config;
});
