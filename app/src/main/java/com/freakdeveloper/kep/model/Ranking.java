package com.freakdeveloper.kep.model;

public class Ranking {
    private int Imagen;
    private String NickName;
    private int Posicion;
    private int NumUsu;

    public Ranking(int imagen, String nickName, int posicion, int numUsu) {
        Imagen=imagen;
        NickName = nickName;
        Posicion = posicion;
        NumUsu = numUsu;
    }
    public Ranking( String nickName, int posicion, int numUsu) {
        NickName = nickName;
        Posicion = posicion;
        NumUsu = numUsu;
    }

    public Ranking() {
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public int getPosicion() {
        return Posicion;
    }

    public void setPosicion(int posicion) {
        Posicion = posicion;
    }

    public int getNumUsu() {
        return NumUsu;
    }

    public void setNumUsu(int numUsu) {
        NumUsu = numUsu;
    }
    public int getImagen() {
        return Imagen;
    }

    public void setImagen(int imagen) {
        Imagen = imagen;
    }
}

