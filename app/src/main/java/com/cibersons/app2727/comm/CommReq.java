package com.cibersons.app2727.comm;

/**
 * Created by manunez on 24/09/2015.
 */
public class CommReq {
//    public static String BASE_URL = "http://192.168.1.157/wsBepsa/wsBepsaInfo.php";
    public static String BASE_URL = "http://45.33.12.78/cbsApp/cbsAppMain.php";

    //USER
    public static final String GET_USER = "getUser";
    public static final String PUT_USER = "putUser";

    //TRANSACCION
    public static final String GET_TRANSACCION = "getTransaccion";

    //TIPO DE CONSULTA TRANSACCION
    public static final String TIPO_CONSULTA_HISTORIAL = "historial";
    public static final String TIPO_CONSULTA_ULTIMO_PENDIENTE = "unico";

}
