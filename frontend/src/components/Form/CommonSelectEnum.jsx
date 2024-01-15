import {
  FormControl,
  FormLabel,
  FormErrorMessage,
  Select as ChakraSelect,
} from "@chakra-ui/react";
import { forwardRef } from "react";

const SelectEnum = (
  { name, label, placeholder, error, type, ...rest },
  ref
) => {
  return (
    <FormControl isInvalid={!!error}>
      {!!label && <FormLabel htmlFor={name}>{label}</FormLabel>}

      <ChakraSelect
        cursor={"pointer"}
        size="lg"
        variant="filled"
        placeholder={placeholder}
        id={name}
        name={name}
        ref={ref}
        {...rest}
      >
        {type.map((typeMap) => (
          <option key={typeMap.value} value={typeMap.value}>
            {typeMap.label}
          </option>
        ))}
      </ChakraSelect>

      {!!error && (
        <FormErrorMessage>
          {!!error && <FormErrorMessage>{error.message}</FormErrorMessage>}
        </FormErrorMessage>
      )}
    </FormControl>
  );
};

export const CommonSelectEnum = forwardRef(SelectEnum);
