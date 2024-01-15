import PropTypes from "prop-types";
import {
  FormControl,
  FormLabel,
  Input as ChakraInput,
  FormErrorMessage,
} from "@chakra-ui/react";
import { forwardRef } from "react";
import { InputNumberFormat } from "@react-input/number-format";

// Tivemos que trocar a function para const para passar a ref como parâmetro.
const Input = (
  {
    name,
    label,
    placeholder,
    error,
    locales,
    format,
    currency,
    unit,
    unitDisplay,
    maximumFractionDigits,
    readOnly,
    defaultValue,
    disabled,
    handleChange,
    ...rest
  },
  ref
) => {
  const handleInputChange = async (event) => {
    const data = event.target.value; // Obtém o valor selecionado no <select>
    handleChange(data);
  };

  return (
    <FormControl isInvalid={!!error}>
      {!!label && <FormLabel htmlFor={name}>{label}</FormLabel>}
      <ChakraInput
        onChangeCapture={handleInputChange}
        handleChange={handleChange}
        minimumFractionDigits={3}
        maximumFractionDigits={maximumFractionDigits}
        unit={unit}
        unitDisplay={unitDisplay}
        as={InputNumberFormat}
        locales={locales}
        format={format}
        currency={currency}
        variant="filled"
        _placeholder={{ opacity: 0.7, fontSize: 14, fontWeight: 400 }}
        id={name}
        name={name}
        size="lg"
        ref={ref}
        {...rest}
      />

      {!!error && <FormErrorMessage>{error.message}</FormErrorMessage>}
    </FormControl>
  );
};

export const NumberCommonInputAfericao = forwardRef(Input);

Input.propTypes = {
  name: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  placeholder: PropTypes.string.isRequired,
  error: PropTypes.string.isRequired,
};
