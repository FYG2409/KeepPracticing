package com.freakdeveloper.kep.model;

public class Enciclopedia {
    private String ID;
    private String Materia;
    private String Tema;
    private String Descripcion;
    private String Ejemplo;

    public Enciclopedia() {

    }

    public Enciclopedia(String ID, String materia, String tema, String descripcion, String ejemplo) {
        this.ID = ID;
        Materia = materia;
        Tema = tema;
        Descripcion = descripcion;
        Ejemplo = ejemplo;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMateria() {
        return Materia;
    }

    public void setMateria(String materia) {
        Materia = materia;
    }

    public String getTema() {
        return Tema;
    }

    public void setTema(String tema) {
        Tema = tema;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getEjemplo() {
        return Ejemplo;
    }

    public void setEjemplo(String ejemplo) {
        Ejemplo = ejemplo;
    }
}

