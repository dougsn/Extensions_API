package com.extensions.domain.dto.antena;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

public class AntenaDTO extends RepresentationModel<AntenaDTO> implements Serializable {
    private String id;
    @Schema(type = "string", example = "John Doe ...")
    @NotEmpty(message = "O campo [ip] é obrigatório.")
    @Length(max = 60, message = "O campo [ip] não pode ser maior que 60 caracteres")
    private String ip;

    @Schema(type = "string", example = "John Doe ...")
    @NotEmpty(message = "O campo [localizacao] é obrigatório.")
    @Length(max = 60, message = "O campo [localizacao] não pode ser maior que 60 caracteres")
    private String localizacao;
    @Schema(type = "string", example = "John Doe ...")
    @NotEmpty(message = "O campo [ssid] é obrigatório.")
    @Length(max = 60, message = "O campo [ssid] não pode ser maior que 60 caracteres")
    private String ssid;
    @Schema(type = "string", example = "John Doe ...")
    @NotEmpty(message = "O campo [senha] é obrigatório.")
    @Length(max = 60, message = "O campo [senha] não pode ser maior que 60 caracteres")
    private String senha;

    @Schema(type = "string", example = "08db05ec-7c84-45d1-8e37-170f21a32138")
    @NotEmpty(message = "O campo [id_local] é obrigatório.")
    @JsonProperty("id_local")
    private String idLocal;
    @Schema(type = "string", example = "Maruí")
    @JsonProperty("nome_local")
    private String nomeLocal;
    @Schema(type = "string", example = "08db05ec-7c84-45d1-8e37-170f21a32138")
    @NotEmpty(message = "O campo [id_modelo] é obrigatório.")
    @JsonProperty("id_modelo")
    private String idModelo;
    @Schema(type = "string", example = "Power Bean")
    @JsonProperty("nome_modelo")
    private String nomeModelo;
    @Schema(type = "string", example = "08db05ec-7c84-45d1-8e37-170f21a32138")
    @NotEmpty(message = "O campo [id_tipo_antena] é obrigatório.")
    @JsonProperty("id_tipo_antena")
    private String idTipoAntena;
    @Schema(type = "string", example = "AP")
    @JsonProperty("nome_tipo_antena")
    private String nomeTipoAntena;

    public AntenaDTO() {
    }

    public AntenaDTO(String id, String ip, String localizacao, String ssid, String senha, String idLocal, String nomeLocal, String idModelo, String nomeModelo, String idTipoAntena, String nomeTipoAntena) {
        this.id = id;
        this.ip = ip;
        this.localizacao = localizacao;
        this.ssid = ssid;
        this.senha = senha;
        this.idLocal = idLocal;
        this.nomeLocal = nomeLocal;
        this.idModelo = idModelo;
        this.nomeModelo = nomeModelo;
        this.idTipoAntena = idTipoAntena;
        this.nomeTipoAntena = nomeTipoAntena;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }

    public String getNomeLocal() {
        return nomeLocal;
    }

    public void setNomeLocal(String nomeLocal) {
        this.nomeLocal = nomeLocal;
    }

    public String getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(String idModelo) {
        this.idModelo = idModelo;
    }

    public String getNomeModelo() {
        return nomeModelo;
    }

    public void setNomeModelo(String nomeModelo) {
        this.nomeModelo = nomeModelo;
    }

    public String getIdTipoAntena() {
        return idTipoAntena;
    }

    public void setIdTipoAntena(String idTipoAntena) {
        this.idTipoAntena = idTipoAntena;
    }

    public String getNomeTipoAntena() {
        return nomeTipoAntena;
    }

    public void setNomeTipoAntena(String nomeTipoAntena) {
        this.nomeTipoAntena = nomeTipoAntena;
    }
}
