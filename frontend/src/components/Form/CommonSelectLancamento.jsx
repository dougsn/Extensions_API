import {
  FormControl,
  FormLabel,
  FormErrorMessage,
  Select as ChakraSelect,
} from "@chakra-ui/react";
import { forwardRef } from "react";
import { api } from "../../services/api";
import { getToken } from "../../utils/localstorage";

const Select = (
  {
    name,
    label,
    placeholder,
    error,
    entity,
    handleChange,
    isDisabled,
    ...rest
  },
  ref
) => {

  return (
    <FormControl isInvalid={!!error}>
      {!!label && <FormLabel htmlFor={name}>{label}</FormLabel>}
      {
        <ChakraSelect
          defaultValue={entity.id}
          isReadOnly={true}
          cursor={"pointer"}
          size="lg"
          variant="filled"
          placeholder={entity.name}
          id={name}
          name={name}
          ref={ref}
          {...rest}
        >
          <option key={entity.id} value={entity.id}>
            {entity.name}
          </option>
        </ChakraSelect>
      }

      {!!error && (
        <FormErrorMessage>
          {!!error && <FormErrorMessage>{error.id.message}</FormErrorMessage>}
        </FormErrorMessage>
      )}
    </FormControl>
  );
};

export const CommonSelectLancamento = forwardRef(Select);
