import { Button, Icon, useColorMode, useToast } from "@chakra-ui/react";
import { RiFileExcel2Fill } from "react-icons/ri";
import { api } from "../../services/api";
import { getToken } from "../../utils/localstorage";
import * as XLSX from "xlsx";

export const ExcelButton = ({ endpoint, downloadName, sheetName }) => {
  const { colorMode } = useColorMode();
  const toast = useToast();

  const handleExport = async () => {
    try {
      // Busca os dados do endpoint
      const response = await api.get(`${endpoint}`, {
        headers: { Authorization: `Bearer ${getToken()}` },
      });
      const data = response.data;

      // Converte os dados para a planilha
      const worksheet = XLSX.utils.json_to_sheet(data);

      // Define a largura das colunas
      const maxLength = (data) => {
        return data.reduce((acc, curr) => {
          const keys = Object.keys(curr);
          keys.forEach((key, index) => {
            const value = curr[key] ? curr[key].toString().length : 10; // Largura mínima de 10
            acc[index] = acc[index] > value ? acc[index] : value;
          });
          return acc;
        }, []);
      };

      const wscols = maxLength(data).map((w) => ({ wch: w }));
      worksheet["!cols"] = wscols;

      // Cria o arquivo Excel e o salva
      const workbook = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(workbook, worksheet, sheetName);
      XLSX.writeFile(workbook, `${downloadName}.xlsx`);
    } catch (error) {
      toast({
        title: error.response.data.errorMessage,
        status: "error",
        position: "top-right",
        duration: 2000,
        isClosable: true,
      });
      console.error("Erro ao exportar os dados", error);
    }
  };

  return (
    <Button
      size="sm"
      fontSize="sm"
      bgColor={colorMode === "dark" ? "green.800" : "green.500"}
      color={colorMode === "dark" ? "" : "white"}
      _hover={{
        bgColor: colorMode === "dark" ? "green.900" : "green.600",
      }}
      onClick={handleExport}
    >
      <Icon as={RiFileExcel2Fill} fontSize="20" />
    </Button>
  );
};
