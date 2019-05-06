package com.freakdeveloper.kep.model;

public class Ranking {

    private String NickName;
    private int Posicion;
    private float NumUsu;

    public Ranking( String nickName, int posicion, float numUsu) {
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

    public float getNumUsu() {
        return NumUsu;
    }

    public void setNumUsu(float numUsu) {
        NumUsu = numUsu;
    }

}