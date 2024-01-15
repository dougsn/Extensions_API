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
    value,
    label,
    error,
    locales,
    format,
    currency,
    unit,
    unitDisplay,
    maximumFractionDigits,
    readOnly,
    disabled,
    ...rest
  },
  ref
) => {

  return (
    <FormControl isInvalid={!!error}>
      {!!label && <FormLabel htmlFor={name}>{label}</FormLabel>}
      <ChakraInput
        value={value}
        isDisabled={disabled}
        readOnly={readOnly}
        minimumFractionDigits={3}
        maximumFractionDigits={maximumFractionDigits}
        unit={unit}
        unitDisplay={unitDisplay}
        as={InputNumberFormat}
        locales={locales}
        format={format}
        currency={currency}
        variant="filled"
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

export const NumberCommonInputValorTotal = forwardRef(Input);

Input.propTypes = {
  name: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  placeholder: PropTypes.string.isRequired,
  error: PropTypes.string.isRequired,
};
