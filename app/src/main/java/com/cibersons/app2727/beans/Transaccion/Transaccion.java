package com.cibersons.app2727.beans.Transaccion;

/**
 * Created by manunez on 25/09/2015.
 */
public class Transaccion {
    private String idTransaccion;
    private String mensaje;

    public Transaccion(String idTransaccion, String mensaje) {
        this.idTransaccion = idTransaccion;
        this.mensaje = mensaje;
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "idTransaccion='" + idTransaccion + '\'' +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }

}
