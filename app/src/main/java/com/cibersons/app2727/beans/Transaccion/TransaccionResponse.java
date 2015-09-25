package com.cibersons.app2727.beans.Transaccion;

import java.util.List;

/**
 * Created by manunez on 25/09/2015.
 */
public class TransaccionResponse {
    private String status;
    private String cantidad;
    private List<Transaccion> data;

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

    public List<Transaccion> getData() {
        return data;
    }

    public void setData(List<Transaccion> data) {
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
