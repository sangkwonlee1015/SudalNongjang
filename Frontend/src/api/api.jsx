import axios from "axios";

const HOST = "https://j7a606.p.ssafy.io/api/";
var token = window.localStorage.getItem("token");
if (token == null) {
  token = "";
}
axios.defaults.withCredentials = true;
const api = axios.create({
  baseURL: HOST,
  headers: { Authorization: `Bearer ${token}` },
});

export default api;
