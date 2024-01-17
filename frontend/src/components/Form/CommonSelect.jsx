import {
  FormControl,
  FormLabel,
  FormErrorMessage,
  Select as ChakraSelect,
} from "@chakra-ui/react";
import { forwardRef } from "react";

const Select = (
  { name, label, placeholder, error, entity, isDisabled, ...rest },
  ref
) => {
  return (
    <FormControl isInvalid={!!error}>
      {!!label && <FormLabel htmlFor={name}>{label}</FormLabel>}
      
        <ChakraSelect
          isDisabled={isDisabled}
          cursor={"pointer"}
          size="lg"
          variant="filled"
          placeholder={placeholder}
          id={name}
          name={name}
          ref={ref}
          {...rest}
        >
          {entity.map((entityMap) => (
            <option key={entityMap.id} value={entityMap.id}>
              {entityMap.nome}
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

export const CommonSelect = forwardRef(Select);
