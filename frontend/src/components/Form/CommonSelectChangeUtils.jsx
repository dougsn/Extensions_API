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
    endpoint,
    subEndpoint,
    handleChange,
    handleLoading,
    isDisabled,
    ...rest
  },
  ref
) => {
  const toast = useToast();
  const handleSelectChange = async (event) => {
    const entityId = event.target.value;
    try {
      handleLoading(true);
      if (entityId != 0) {
        const request = await api.get(
          `/${endpoint}/v1/${subEndpoint}/${entityId}?size=100`,
          {
            headers: { Authorization: `Bearer ${getToken()}` },
          }
        );
        if (request.data._embedded.length != 0) {
          handleChange(request.data);
          handleLoading(false);
        }
      }
    } catch (error) {
      toast({
        title: `${endpoint} não foi encontrado(a) na base de dados.`,
        status: "error",
        position: "top-right",
        duration: 2000,
        isClosable: true,
      });
      const request = await api.get(`/${endpoint}/v1?page=${0}&size=${5}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      handleChange(request.data);
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

export const CommonSelectChangeUtils = forwardRef(Select);
