import {
  FormControl,
  FormLabel,
  FormErrorMessage,
  Textarea as ChakraTextarea,
} from "@chakra-ui/react";
import { forwardRef } from "react";

const Textarea = (
  { name, label, placeholder, error, ...rest },
  ref
) => {
  return (
    <FormControl isInvalid={!!error}>
      {!!label && <FormLabel htmlFor={name}>{label}</FormLabel>}
      <ChakraTextarea
        placeholder={placeholder}
        _placeholder={{ textAlign: "center" }}
        id={name}
        name={name}
        ref={ref}
        {...rest}
      />

      {!!error && <FormErrorMessage>{error.message}</FormErrorMessage>}
    </FormControl>
  );
};

export const CommonTextarea = forwardRef(Textarea);