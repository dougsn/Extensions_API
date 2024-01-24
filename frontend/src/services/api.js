import axios from "axios";

export const api = axios.create({
   baseURL: "http://localhost:8080/api" // IP do local
   // baseURL: "http://192.168.0.177:8080/api" // IP do servidor, trocar quando for realizar o build. (Ubuntu)
})