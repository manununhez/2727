package com.cibersons.app2727.comm;

/**
 * Created by manunez on 24/09/2015.
 */
public class CommReq {
    public static String BASE_URL = "http://192.168.1.157/wsBepsa/wsBepsaInfo.php";
    public static String BASE_URL_MO = "http://192.168.1.157/wsBepsa/emularMO.php";
//    public static String BASE_URL = "http://45.33.12.78/cbsApp/cbsAppMain.php";

    //    MENSAJES DE ERROR
    public static String ERROR_CONEXION_TITLE = "Error de conexión!";
    public static String ERROR_CONEXION_BODY = "Verifique su conexión de red para poder realizar esta acción.";
    //USER
    public static final String GET_USER = "getUser";
    public static final String PUT_USER = "putUser";

    //TRANSACCION
    public static final String GET_TRANSACCION = "getTransaccion";

    //TIPO DE CONSULTA TRANSACCION
    public static final String TIPO_CONSULTA_HISTORIAL = "historial";
    public static final String TIPO_CONSULTA_ULTIMO_PENDIENTE = "unico";

    //    CODIGO DE ERROR ENVIO DE MENSAJE
    public static String RESPUESTA_CORRECTA = "00";
    public static String NO_EXISTE_CUENTA = "78";
    public static String ENVIO_DESDE_OTRO_CELULAR = "81";
    public static String NO_SE_ENCUENTRA_SUSCRIPTO = "82";
    public static String CIERRE_BANCARIO = "99";
    public static String TARJETA_VENCIDA = "54";
}
