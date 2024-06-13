import {
  FormControl,
  FormLabel,
  FormErrorMessage,
  Select as ChakraSelect,
  useToast,
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
    handleLoading,
    isDisabled,
    ...rest
  },
  ref
) => {
  const handleSelectChange = async (event) => {
    handleLoading(true);
    handleChange(event.target.value);
    handleLoading(false);
  };

  return (
    <FormControl isInvalid={!!error}>
      {!!label && <FormLabel htmlFor={name}>{label}</FormLabel>}
      {handleChange ? (
        <ChakraSelect
          isDisabled={isDisabled}
          onChangeCapture={handleSelectChange} // Retorna o novo dado para component Pai.
          handleChange={handleChange}
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
      ) : (
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
      )}

      {!!error && (
        <FormErrorMessage>
          {!!error && <FormErrorMessage>{error.id.message}</FormErrorMessage>}
        </FormErrorMessage>
      )}
    </FormControl>
  );
};

export const CommonSelectChangeUtilsRelatorioManutencao = forwardRef(Select);
