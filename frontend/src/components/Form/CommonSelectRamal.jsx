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
    handleLoading,
    isDisabled,
    ...rest
  },
  ref
) => {
  const handleSelectChange = async (event) => {
    const entityId = event.target.value; // Obtém o valor selecionado no <select>
    // Faça a requisição GET para buscar a entidade com base no entityId
    try {
      if (entityId != 0) {
        handleLoading(true);
        const request = await api.get(
          `/funcionario/v1/setor/${entityId}?size=100`,
          {}
        ); // Substitua pela sua URL de API
        if (request.data.length != 0) {
          handleChange(request.data);
          handleLoading(false);
        }
      }
    } catch (error) {
      console.error("Erro ao buscar a entidade:");
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

export const CommonSelectRamal = forwardRef(Select);
