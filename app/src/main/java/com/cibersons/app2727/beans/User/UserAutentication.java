package com.cibersons.app2727.beans.User;

/**
 * Created by manunez on 12/10/2015.
 */
public class UserAutentication {
    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "UserAutentication{" +
                "mensaje='" + mensaje + '\'' +
                '}';
    }
}
