import axios from "axios";

export const api = axios.create({
   baseURL: "http://localhost:8080/api" // IP do local
   // baseURL: "http://192.168.0.111:8080" // IP do servidor, trocar quando for realizar o build. (Ubuntu)
   // baseURL: "http://189.1.132.18:8080" // IP do servidor com liberação no firewall, trocar quando for realizar o build. (Ubuntu)
})