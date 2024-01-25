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
    endpoint,
    handleChange,
    handleLoading,
    isDisabled,
    ...rest
  },
  ref
) => {
  const handleSelectChange = async (event) => {
    const entityId = event.target.value;     
    try {
      if (entityId != 0) {
        handleLoading(true);
        const request = await api.get(
          `/${endpoint}/v1/setor/${entityId}?size=100`, {
            headers: { Authorization: `Bearer ${getToken()}` },
          }
        ); 
        if (request.data.length != 0) {
          handleChange(request.data);
          handleLoading(false);
        }
      }
    } catch (error) {
      handleLoading(false);
    }
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

export const CommonSelectChange = forwardRef(Select);
