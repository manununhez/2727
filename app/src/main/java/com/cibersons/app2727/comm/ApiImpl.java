package com.cibersons.app2727.comm;

import android.content.Context;
import android.util.Log;

import com.cibersons.app2727.beans.User.UserResponse;



/**
 * Created by manunez on 24/09/2015.
 */
public class ApiImpl{// implements Api {

   /* private Context context;
    private Api api;
    private RestAdapter retrofit;

    public ApiImpl(Context context) {
        this.context = context;
        retrofit = new RestAdapter.Builder()
                .setEndpoint(CommReq.BASE_URL)
                .build();

        api = retrofit.create(Api.class);
    }

    @Override
    public void postUser(final Callback<UserResponse> cb) {
        Log.i("INFO", "Entrando a la postUser");
        api.postUser(new Callback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {
                Log.d("DEBUG", userResponse.getData().toString());
                cb.success(userResponse, response);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Debug", error.getMessage());
                cb.failure(error);
            }
        });
    }

    @Override
    public void getUser(String accion, String appId, String celular, String cedula, String operadora, String userAutent, String passAutent, final Callback<UserResponse> cb) {
        Log.i("INFO", "Entrando a la getUser");
        api.getUser(accion, appId, celular, cedula, operadora, userAutent, passAutent, new Callback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {
                Log.d("DEBUG", userResponse.getData().toString());
                cb.success(userResponse, response);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Debug", error.getMessage());
                cb.failure(error);
            }
        });
    }*/
}
