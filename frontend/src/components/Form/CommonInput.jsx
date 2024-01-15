import PropTypes from "prop-types";
import {
  FormControl,
  FormLabel,
  Input as ChakraInput,
  FormErrorMessage,
} from "@chakra-ui/react";
import { forwardRef } from "react";

// Tivemos que trocar a function para const para passar a ref como parâmetro.
const Input = (
  { name, label, placeholder, error, as, mask, valueDefault, readOnly, ...rest },
  ref
) => {
  return (
    <FormControl isInvalid={!!error}>
      {!!label && <FormLabel htmlFor={name}>{label}</FormLabel>}
      <ChakraInput
        readOnly={readOnly}
        defaultValue={valueDefault}
        as={as}
        mask={mask}
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

export const CommonInput = forwardRef(Input);

Input.propTypes = {
  name: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  placeholder: PropTypes.string.isRequired,
  error: PropTypes.string.isRequired,
};
