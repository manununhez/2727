package com.cibersons.app2727.comm;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by manunez on 24/09/2015.
 */
public class ApiImpl {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

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

    /*Call run(String url, Callback callback) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }*/

    public String bowlingJson(String player1, String player2) {
        return "{'winCondition':'HIGH_SCORE',"
                + "'name':'Bowling',"
                + "'round':4,"
                + "'lastSaved':1367702411696,"
                + "'dateStarted':1367702378785,"
                + "'players':["
                + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
                + "]}";
    }

    public String getUserJson() {
        return "{\"accion\":\"getUser\",\"appId\":\"\"," +
                "\"celular\":\"0984000000\",\"cedula\":\"44444444\"," +
                "\"operadora\":\"pytgo\",\"userAutent\":\"CnsgUser\",\"passAutent\":\"123456\"}";
    }

    public String getTransaction(String tipoConsulta){
        return "{\"accion\":\"getTransaccion\",\"appId\":\"\"," +
                "\"celular\":\"0984000000\",\"cedula\":\"44444444\"," +
                "\"tipoConsulta\": \""+tipoConsulta+"\",\"userAutent\":\"CnsgUser\",\"passAutent\":\"123456\"}";
    }

}
