package com.cibersons.app2727.comm;

import com.cibersons.app2727.App2727;
import com.cibersons.app2727.utils.Utils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by manunez on 24/09/2015.
 */
public class ApiImpl {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client;
    private static String user = "CnsgUser";
    private static String pass = "123456";

    public ApiImpl() throws Exception {
        App2727.Logger.i("ApiImpl() CREADO");
        client = new OkHttpClient();
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setWriteTimeout(10, TimeUnit.SECONDS);
        client.setReadTimeout(15, TimeUnit.SECONDS);
    }

    public Call post(String url, String json, Callback callback) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }


    public Call postWithParameters(String url, String ci, Callback callback) throws IOException {
        RequestBody formBody = new FormEncodingBuilder()
                .add("celular", "0982484860") //Manu
                .add("mensaje", ci)
//                .add("celular", "0981"+ Utils.md5(user)+"") //dario
//                .add("mensaje", ""+ Utils.md5(user)+"")
//                .add("celular", "0984"+ Utils.md5(user)+"") //Dani
//                .add("mensaje", "222222")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    /*Call run(String url, Callback callback) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }*/


    public static String getUserJson(String appID) {
        return "{\"accion\":\"getUser\",\"appId\":\"" + appID + "\"," +
                "\"userAutent\":\""+ Utils.md5(user)+"\",\"passAutent\":\""+ Utils.md5(pass)+"\"}";
    }

    public static String getTransaction(String tipoConsulta, String appID) {
        return "{\"accion\":\"getTransaccion\",\"appId\":\"" + appID + "\"," +
                "\"tipoConsulta\": \"" + tipoConsulta + "\",\"userAutent\":\""+ Utils.md5(user)+"\",\"passAutent\":\""+ Utils.md5(pass)+"\"}";
    }

    public static String putUserObject(String nroDocumento, String nombreApellido, String appID) {
        return "{\"accion\":\"putUser\",\"appId\":\"" + appID + "\"," +
                "\"cedula\":\"" + nroDocumento + "\",\"nombre\":\"" + nombreApellido + "\"," +
                "\"userAutent\":\""+ Utils.md5(user)+"\",\"passAutent\":\""+ Utils.md5(pass)+"\"}";
    }


    public static String putViewTrans(String numeroTransaccion, String appID) {
        return "{\"accion\":\"putViewTrans\"," +
                "\"appId\":\"" + appID + "\"," +
                "\"nroTransaccion\":\"" + numeroTransaccion + "\",\"userAutent\":\""+ Utils.md5(user)+"\",\"passAutent\":\""+ Utils.md5(pass)+"\"}";
    }

    public static String getAutentication(String appID){
        return "{\"accion\":\"getAutenticacion\",\"appId\":\""+appID+"\",\"userAutent\":\""+Utils.md5(user)+"\",\"passAutent\":\""+ Utils.md5(pass)+"\"}";
    }


}
