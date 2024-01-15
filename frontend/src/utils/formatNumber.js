export function formatNumber(value) {
    // Remove o primeiro ponto e substitui a vírgula por ponto para obter o formato correto (2000,69)
    const formattedValue = value.replace('.', '').replace(',', '.');

    return formattedValue;
}
