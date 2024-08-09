package com.extensions.domain.dto.terminal;

import java.io.Serializable;

public class TerminalDTOExport implements Serializable {
    private String setor;
    private String usuario;
    private String modelo;

    public TerminalDTOExport() {
    }

    public TerminalDTOExport(String setor, String usuario, String modelo) {
        this.setor = setor;
        this.usuario = usuario;
        this.modelo = modelo;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
