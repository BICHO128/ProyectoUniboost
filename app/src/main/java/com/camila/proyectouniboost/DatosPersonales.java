package com.camila.proyectouniboost;

public class DatosPersonales {
    private String Nombre;
    private String CorreoElectronico;
    private String Celular;

    public DatosPersonales(String nombre, String correoElectronico, String celular) {
        Nombre = nombre;
        CorreoElectronico= correoElectronico;
        Celular= celular;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCorreoElectronico() {
        return CorreoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        CorreoElectronico = correoElectronico;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }
}
