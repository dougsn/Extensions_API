import PropTypes from "prop-types";
import {
  FormControl,
  FormLabel,
  Input as ChakraInput,
  FormErrorMessage,
  InputGroup,
  InputRightElement,
  Button,
  Icon,
} from "@chakra-ui/react";
import { forwardRef, useState } from "react";
import { ViewOffIcon, ViewIcon } from '@chakra-ui/icons'

// Tivemos que trocar a function para const para passar a ref como parâmetro.
const InputPassword = ({ name, label, placeholder, error, ...rest }, ref) => {
  const [show, setShow] = useState(false)
  const handleClick = () => setShow(!show)

  return (
    <FormControl isInvalid={!!error}>
      {!!label && <FormLabel htmlFor={name}>{label}</FormLabel>}
      <InputGroup size="md">
        <ChakraInput
          pr="4.5rem"
          type={show ? "text" : "password"}
          variant="filled"
          placeholder={placeholder}
          _placeholder={{ opacity: 0.7, fontSize: 14, fontWeight: 400 }}
          id={name}
          name={name}
          ref={ref}
          {...rest}
        />
        <InputRightElement width="4.5rem">
          <Button h="1.75rem" size="lg" onClick={handleClick}>
            {show ? <Icon as={ViewOffIcon}/> : <Icon as={ViewIcon}/>}
          </Button>
        </InputRightElement>
      </InputGroup>

      {!!error && <FormErrorMessage>{error.message}</FormErrorMessage>}
    </FormControl>
  );
};

export const CommonInputPassword = forwardRef(InputPassword);

InputPassword.propTypes = {
  name: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  placeholder: PropTypes.string.isRequired,
  error: PropTypes.string.isRequired,
};
