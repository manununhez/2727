package com.cibersons.app2727.beans.Transaccion;

import java.util.List;

/**
 * Created by manunez on 29/09/2015.
 */
public class TransaccionData {
    List<Transaccion> transaccion;

    public List<Transaccion> getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(List<Transaccion> transaccion) {
        this.transaccion = transaccion;
    }

    @Override
    public String toString() {
        return "TransaccionData{" +
                "transaccion=" + transaccion +
                '}';
    }
}
