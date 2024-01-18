import PropTypes from "prop-types";
import {
  FormControl,
  FormLabel,
  Input as ChakraInput,
  FormErrorMessage,
} from "@chakra-ui/react";
import { forwardRef } from "react";
import { api } from "../../services/api";

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
    ...rest
  },
  ref
) => {
  const handleSelectChange = async (event) => {
    const data = event.target.value.trim();
    try {
      handleLoading(true);
      if (data) {
        const request = await api.get(
          `/funcionario/v1/funcionario?nome=${data}`
        );
        if (request.data.length !== 0) {
          setTimeout(() => {
            handleChange(request.data);
            handleLoading(false);
          }, 1000);
        }
      } else {
        const request = await api.get(`/funcionario/v1?page=${0}&size=${5}`);
        setTimeout(() => {
          handleChange(request.data._embedded.funcionarioDTOList);
          handleLoading(false);
        }, 1000);
      }
    } catch (error) {}
  };

  return (
    <FormControl isInvalid={!!error}>
      {!!label && <FormLabel htmlFor={name}>{label}</FormLabel>}
      {handleChange ? (
        <ChakraInput
          onKeyUp={handleSelectChange}
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

export const CommonInputRamal = forwardRef(Input);

Input.propTypes = {
  name: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  placeholder: PropTypes.string.isRequired,
  error: PropTypes.string.isRequired,
};
