package com.cibersons.app2727.beans.User;

/**
 * Created by manunez on 12/10/2015.
 */
public class UserAutenticationResponse {
    private String status;
    private String cantidad;
    private UserAutentication data;

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

    public UserAutentication getData() {
        return data;
    }

    public void setData(UserAutentication data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserAutenticationResponse{" +
                "status='" + status + '\'' +
                ", cantidad='" + cantidad + '\'' +
                ", data=" + data +
                '}';
    }
}
