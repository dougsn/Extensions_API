import {
  FormControl,
  FormLabel,
  FormErrorMessage,
  Textarea as ChakraTextarea,
} from "@chakra-ui/react";
import { forwardRef } from "react";

const Textarea = (
  { name, label, placeholder, error, height, isReadOnly, cursor, ...rest },
  ref
) => {
  return (
    <FormControl isInvalid={!!error}>
      {!!label && <FormLabel htmlFor={name}>{label}</FormLabel>}
      <ChakraTextarea
        isReadOnly={isReadOnly}
        cursor={cursor}
        height={height}
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
