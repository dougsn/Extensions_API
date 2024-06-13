import PropTypes from "prop-types";
import {
  FormControl,
  FormLabel,
  Input as ChakraInput,
  FormErrorMessage,
  useToast,
} from "@chakra-ui/react";
import { forwardRef, useState } from "react";
import { api } from "../../services/api";
import { getToken } from "../../utils/localstorage";

// Tivemos que trocar a function para const para passar a ref como parâmetro.
const Input = (
  {
    name,
    label,
    placeholder,
    error,
    as,
    mask,
    valueDefault,
    entity,
    handleChange,
    handleLoading,
    readOnly,
    endpoint,
    ...rest
  },
  ref
) => {
  const toast = useToast();
  

  const handleInputChange = async (event) => {
    const data = event.target.value.trim();
    try {
      handleLoading(true);
      if (data) {
        const request = await api.get(
          `/${endpoint}/v1/defeito/${data}`,
          {
            headers: { Authorization: `Bearer ${getToken()}` },
          }
        );
        if (request.data.length != 0) {
          setTimeout(() => {
            handleChange(request.data);
            handleLoading(false);
          }, 1000);
        } else {
          toast({
            title: `${data} não foi encontrado na base de dados.`,
            status: "error",
            position: "top-right",
            duration: 2000,
            isClosable: true,
          });
          const request = await api.get(`/${endpoint}/v1?page=${0}&size=${5}&direction=desc`, {
            headers: { Authorization: `Bearer ${getToken()}` },
          });
          setTimeout(() => {
            handleChange(request.data);
            handleLoading(false);
          }, 1000);
        }
      } else {
        const request = await api.get(`/${endpoint}/v1?page=${0}&size=${5}&direction=desc`, {
          headers: { Authorization: `Bearer ${getToken()}` },
        });
        setTimeout(() => {
          handleChange(request.data);
          handleLoading(false);
        }, 1000);
      }
    } catch (error) {
      handleLoading(false);
      return null;
    }
  };

  return (
    <FormControl isInvalid={!!error}>
      {!!label && <FormLabel htmlFor={name}>{label}</FormLabel>}
      {handleChange ? (
        <ChakraInput
          onKeyUp={handleInputChange}
          handleChange={handleChange}
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
      ) : (
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
      )}

      {!!error && <FormErrorMessage>{error.message}</FormErrorMessage>}
    </FormControl>
  );
};

export const CommonInputChangeDefeitoManutencao = forwardRef(Input);

Input.propTypes = {
  name: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  placeholder: PropTypes.string.isRequired,
  error: PropTypes.string.isRequired,
};
