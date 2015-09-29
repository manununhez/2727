package com.cibersons.app2727.beans.Transaccion;

import java.util.List;

/**
 * Created by manunez on 25/09/2015.
 */
public class TransaccionResponse {
    private String status;
    private String cantidad;
    private TransaccionData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public TransaccionData getData() {
        return data;
    }

    public void setData(TransaccionData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TransaccionResponse{" +
                "status='" + status + '\'' +
                ", cantidad='" + cantidad + '\'' +
                ", data=" + data +
                '}';
    }
}
