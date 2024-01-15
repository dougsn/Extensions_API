import PropTypes from "prop-types";
import {
  FormControl,
  FormLabel,
  Input as ChakraInput,
  FormErrorMessage,
} from "@chakra-ui/react";
import { forwardRef } from "react";
import ReactInputMask from "react-input-mask";

// Tivemos que trocar a function para const para passar a ref como parâmetro.
const Input = (
  {
    name,
    label,
    placeholder,
    error,
    handleChange,
    ...rest
  },
  ref
) => {
  const handleSelectChange = async (event) => {
    const data = event.target.value; // Obtém o valor selecionado no <select>
    handleChange(data);
  };
  return (
    <FormControl isInvalid={!!error}>
      {!!label && <FormLabel htmlFor={name}>{label}</FormLabel>}
      <ChakraInput
        onChangeCapture={handleSelectChange} // Retorna o novo dado para component Pai.
        handleChange={handleChange}
        as={ReactInputMask}
        mask={"99/99/9999"}
        variant="filled"
        placeholder={placeholder}
        _placeholder={{ opacity: 0.7, fontSize: 14, fontWeight: 400 }}
        id={name}
        name={name}
        size="lg"
        ref={ref}
        {...rest}
      />

      {!!error && <FormErrorMessage>{error.message}</FormErrorMessage>}
    </FormControl>
  );
};

export const CommonInputData = forwardRef(Input);

Input.propTypes = {
  name: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  placeholder: PropTypes.string.isRequired,
  error: PropTypes.string.isRequired,
};
