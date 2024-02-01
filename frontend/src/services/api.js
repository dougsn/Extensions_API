import axios from "axios";

export const api = axios.create({
   // baseURL: "http://localhost:8080/api" // IP do local
   baseURL: "https://extension-api.up.railway.app/api" // IP do servidor, trocar quando for realizar o build. (Ubuntu)
})