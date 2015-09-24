package com.cibersons.app2727.beans.User;

/**
 * Created by manunez on 24/09/2015.
 */
public class UserResponse {

    private String status;
    private String cantidad;
    private User data;

    public UserResponse() {
    }

    public UserResponse(String status, String cantidad, User data) {
        this.status = status;
        this.cantidad = cantidad;
        this.data = data;
    }

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

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "status='" + status + '\'' +
                ", cantidad='" + cantidad + '\'' +
                ", data=" + data +
                '}';
    }
}
