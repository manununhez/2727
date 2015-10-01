package com.cibersons.app2727.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bumptech.glide.util.Util;
import com.cibersons.app2727.App2727;
import com.cibersons.app2727.R;
import com.cibersons.app2727.beans.User.UserResponse;
import com.cibersons.app2727.comm.ApiImpl;
import com.cibersons.app2727.comm.CommReq;
import com.cibersons.app2727.utils.Utils;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Manuel on 8/19/2015.
 */
public class PerfilFragment extends RootFragment {

    private static PerfilFragment instance;

    private UserResponse userResponse;
    private EditText etNroDocumento;
    private EditText etNroCelular;
    private EditText etNombre;
    private EditText etApellido;

    private SharedPreferences sharedPref;


    public static PerfilFragment newInstance(String title) {
        if (instance == null) {
            instance = new PerfilFragment();
            Bundle args = new Bundle();
//            args.putString(ARG_FRAGMENT_TITLE, title);
            instance.setArguments(args);
        }
        return instance;
    }

    public PerfilFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);
        etNroDocumento = (EditText) rootView.findViewById(R.id.etNroDocumento);
        etNroCelular = (EditText) rootView.findViewById(R.id.etNroCelular);
        etNombre = (EditText) rootView.findViewById(R.id.etNombre);
        etApellido = (EditText) rootView.findViewById(R.id.etApellido);
        LinearLayout llRegistrar = (LinearLayout) rootView.findViewById(R.id.llRegistrar);
        sharedPref = getActivity().getSharedPreferences(getString(R.string.prefs_name), Context.MODE_PRIVATE);

        actualizarDatosPerfil();

        llRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = Utils.getProgressDialog(getActivity(), "Cargando...", "Aguarde un momento por favor!");
                progressDialog.show();
                ApiImpl postOkHttp = new ApiImpl();
                String putUserJson = postOkHttp.putUserObject(etNroCelular.getText().toString(), etNroDocumento.getText().toString(), etNombre.getText().toString() + " " + etApellido.getText().toString());
                App2727.Logger.i("Datos enviados:" + putUserJson);
                if (Utils.haveNetworkConnection(getActivity())) {
                    try {
                        postOkHttp.post(CommReq.BASE_URL, putUserJson, new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                App2727.Logger.e(e.getMessage());
                            }

                            @Override
                            public void onResponse(Response response) throws IOException {
                                progressDialog.dismiss();
                                Context context = getActivity().getApplicationContext();
                                String responseStr = response.body().string();
                                App2727.Logger.i("Mensaje recibido:" + responseStr);

                                try {
                                    JSONObject jsonObject = new JSONObject(responseStr);
                                    Gson gson = new Gson();

                                    userResponse = gson.fromJson(String.valueOf(jsonObject), UserResponse.class);

                                    if (userResponse.getStatus().equals("OK")) {
                                        saveSharedPreferencesData();
                                        showDialogOk(userResponse.getStatus(), "Usuario registrado correctamente!");
                                    } else {
                                        showDialogOk(userResponse.getStatus(), userResponse.getData().getNombreUsuario());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    App2727.Logger.e(e.getMessage());
                                }

                            }
                        });
                    } catch (IOException e) {
                        App2727.Logger.e(e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    progressDialog.dismiss();
                    showDialogOk(CommReq.ERROR_CONEXION_TITLE, CommReq.ERROR_CONEXION_BODY);
                }
            }
        });


        return rootView;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void actualizarDatosPerfil() {
        String ci = sharedPref.getString(getString(R.string.save_ci), getString(R.string.default_value));
        String celular = sharedPref.getString(getString(R.string.save_tel), getString(R.string.default_value));
        String nombre = sharedPref.getString(getString(R.string.save_nombre), getString(R.string.default_value));
        String apellido = sharedPref.getString(getString(R.string.save_apellido), getString(R.string.default_value));

        etNroDocumento.setText(ci.equals(getString(R.string.default_value)) ? "" : ci);
        etNroCelular.setText(celular.equals(getString(R.string.default_value)) ? "" : celular);
        etNombre.setText(nombre.equals(getString(R.string.default_value)) ? "" : nombre);
        etApellido.setText(apellido.equals(getString(R.string.default_value)) ? "" : apellido);
    }

    private void saveSharedPreferencesData() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.save_ci), etNroDocumento.getText().toString());
        editor.putString(getString(R.string.save_tel), etNroCelular.getText().toString());
        editor.putString(getString(R.string.save_nombre), etNombre.getText().toString());
        editor.putString(getString(R.string.save_apellido), etApellido.getText().toString());
        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        actualizarDatosPerfil();
    }
}
