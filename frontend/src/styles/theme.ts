import { extendTheme } from '@chakra-ui/react'

export const theme = extendTheme({
    fonts: {
        body: "Poppins",
        heading: "Poppins"
    },
    config: {
        initialColorMode: 'system',
        useSystemColorMode: true,
    }
})