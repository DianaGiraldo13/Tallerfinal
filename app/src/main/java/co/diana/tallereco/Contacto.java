package co.diana.tallereco;

import android.content.Context;

public class Contacto {

    private String id,idusuario,nombre,telefono;

    public Contacto(String id,String idusuario,String nombre,String telefono){

        this.id=id;
        this.idusuario =idusuario;
        this.nombre=nombre;
        this.telefono=telefono;

    }

    public Contacto(){



    }

    public String getIdusuario() {

        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
