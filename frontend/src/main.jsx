import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import "./styles/global.css";
import { ChakraProvider, ColorModeScript } from '@chakra-ui/react'
import { theme } from './styles/theme.ts'

const rootElement = document.getElementById('root')
ReactDOM.createRoot(rootElement).render(
  <React.StrictMode>
    <ChakraProvider theme={theme}>
    <ColorModeScript
        options={{
          useSystemColorMode: true,
          initialColorMode: "light",
        }}
      />
      <App />
    </ChakraProvider>
  </React.StrictMode>,
)

