package com.freakdeveloper.kep.model;

public class Buzon {
    private String noSolicitud;
    private String duda;
    private String tipo;
    private String estado;
    private String respuesta;
    private String IdUsuario;
    private String IdPAsignada;

    public Buzon(String noSolicitud, String duda, String tipo, String estado, String respuesta, String idUsuario, String idPAsignada) {
        this.noSolicitud = noSolicitud;
        this.duda = duda;
        this.tipo = tipo;
        this.estado = estado;
        this.respuesta = respuesta;
        IdUsuario = idUsuario;
        IdPAsignada = idPAsignada;
    }

    public Buzon(String duda, String estado, String idUsuario) {
        this.duda = duda;
        this.estado = estado;
        IdUsuario = idUsuario;
    }

    public Buzon(String duda, String idUsuario) {
        this.duda = duda;
        IdUsuario = idUsuario;
    }

    public Buzon() {
    }

    public String getNoSolicitud() {
        return noSolicitud;
    }

    public void setNoSolicitud(String noSolicitud) {
        this.noSolicitud = noSolicitud;
    }

    public String getDuda() {
        return duda;
    }

    public void setDuda(String duda) {
        this.duda = duda;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getIdPAsignada() {
        return IdPAsignada;
    }

    public void setIdPAsignada(String idPAsignada) {
        IdPAsignada = idPAsignada;
    }
}
