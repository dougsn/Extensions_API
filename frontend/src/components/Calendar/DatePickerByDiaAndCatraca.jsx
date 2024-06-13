import { useState } from "react";
import {
  Input,
  InputGroup,
  InputRightElement,
  Box,
  useColorMode,
  useToast,
} from "@chakra-ui/react";
import DateRangePicker from "@wojtekmaj/react-daterange-picker";
import "react-calendar/dist/Calendar.css";
import { Search2Icon } from "@chakra-ui/icons";
import { api } from "../../services/api";
import { getToken } from "../../utils/localstorage";

const DatePickerByDiaAndCatraca = ({
  handleChange,
  handleLoading,
  handleError,
  endpoint,
  idCatraca,
  page,
}) => {
  const currentDate = new Date();
  const formattedDate = `${currentDate
    .getDate()
    .toString()
    .padStart(2, "0")}/${(currentDate.getMonth() + 1)
    .toString()
    .padStart(2, "0")}/${currentDate.getFullYear()}`;

  const toast = useToast();

  const [value, onChange] = useState([formattedDate, formattedDate]);

  const [isOpen, setIsOpen] = useState(false);
  const [selectedDates, setSelectedDates] = useState([
    formattedDate,
    formattedDate,
  ]);
  const { colorMode } = useColorMode();

  const formatDate = (date) => {
    return date.toLocaleDateString("pt-BR");
  };

  const handleInputChange = async () => {
    try {
      handleLoading(true);
      if (value.length != 0) {
        const formattedDates = selectedDates.map((date) => formatDate(date));
        const request = await api.get(
          `/${endpoint}/v1/dias-e-catraca?inicio=${formattedDates[0].replace(
            "''",
            ""
          )}&fim=${formattedDates[1].replace(
            "''",
            ""
          )}&catracaId=${idCatraca}&page=${page}&size=${5}&direction=desc`,
          {
            headers: { Authorization: `Bearer ${getToken()}` },
          }
        );
        if (request.data.length != 0) {
          setTimeout(() => {
            handleChange(request.data, formattedDates);
            handleLoading(false);
          }, 500);
        } else {
          toast({
            title: `Não foram encontrados manutenções do dia ${formattedDates[0]} até
             ${formattedDates[1]} com uma catraca`,
            status: "error",
            position: "top-right",
            duration: 2000,
            isClosable: true,
          });
          setTimeout(() => {
            handleLoading(false);
            handleError(true);
          }, 500);
        }
      } else {
        setTimeout(() => {
          handleLoading(false);
          handleError(true);
        }, 500);
      }
    } catch (error) {
      handleLoading(false);
      return null;
    }
  };

  return (
    <>
      <InputGroup
        as={"form"}
        marginTop="20px"
        display="flex"
        alignItems={"stretch"}
        flexDir={"column"}
      >
        <Input
          cursor={"pointer"}
          value={
            value[0] == formattedDate
              ? `${value[0]} ~ ${value[1]}`
              : `${formatDate(value[0])} ~ ${formatDate(value[1])}`
          }
          readOnly
          onClick={() => setIsOpen(true)}
        />
        {isOpen && (
          <Box marginTop="10px" zIndex={1000}>
            <DateRangePicker
              onChange={(newValue) => {
                onChange(newValue);
                setSelectedDates(newValue);
              }}
              isOpen={isOpen}
              onCalendarClose={() => setIsOpen(false)}
              clearIcon={null}
            />
          </Box>
        )}
        <InputRightElement>
          <Box
            display="flex"
            gap={2}
            marginRight="20px"
            _hover={{
              bgColor: colorMode === "dark" ? "gray.700" : "gray.300",
            }}
            padding={2}
            borderRadius={"5"}
            transition={"all .5s ease"}
          >
            <Search2Icon onClick={handleInputChange} cursor="pointer" />
          </Box>
        </InputRightElement>
      </InputGroup>
    </>
  );
};

export default DatePickerByDiaAndCatraca;
