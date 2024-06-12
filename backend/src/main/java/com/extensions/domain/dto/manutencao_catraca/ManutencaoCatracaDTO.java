package com.extensions.domain.dto.manutencao_catraca;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDate;

public class ManutencaoCatracaDTO extends RepresentationModel<ManutencaoCatracaDTO> implements Serializable {
    private String id;
    @Schema(type = "string", example = "01/01/2111")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull(message = "O campo [dia] é obrigatório.")
    private LocalDate dia;
    @Schema(type = "string", example = "Offline")
    @NotEmpty(message = "O campo [defeito] é obrigatório.")
    private String defeito;
    @Schema(type = "string", example = "Sem rede")
    @NotEmpty(message = "O campo [observacao] é obrigatório.")
    private String observacao;
    @Schema(type = "string", example = "08db05ec-7c84-45d1-8e37-170f21a32138")
    @NotEmpty(message = "O campo [id_catraca] é obrigatório.")
    @JsonProperty("id_catraca")
    private String idCatraca;
    @Schema(type = "string", example = "Catraca 01")
    @JsonProperty("nome_catraca")
    private String nomeCatraca;

    public ManutencaoCatracaDTO() {
    }

    public ManutencaoCatracaDTO(String id, LocalDate dia, String defeito, String observacao, String idCatraca, String nomeCatraca) {
        this.id = id;
        this.dia = dia;
        this.defeito = defeito;
        this.observacao = observacao;
        this.idCatraca = idCatraca;
        this.nomeCatraca = nomeCatraca;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public String getDefeito() {
        return defeito;
    }

    public void setDefeito(String defeito) {
        this.defeito = defeito;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getIdCatraca() {
        return idCatraca;
    }

    public void setIdCatraca(String idCatraca) {
        this.idCatraca = idCatraca;
    }

    public String getNomeCatraca() {
        return nomeCatraca;
    }

    public void setNomeCatraca(String nomeCatraca) {
        this.nomeCatraca = nomeCatraca;
    }
}
