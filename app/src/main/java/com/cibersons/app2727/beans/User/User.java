package com.cibersons.app2727.beans.User;

/**
 * Created by manunez on 24/09/2015.
 */
public class User {
    private String nomUsuario;

    public String getNombreUsuario() {
        return nomUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nomUsuario = nombreUsuario;
    }

    @Override
    public String toString() {
        return "User{" +
                "nomUsuario='" + nomUsuario + '\'' +
                '}';
    }
}
