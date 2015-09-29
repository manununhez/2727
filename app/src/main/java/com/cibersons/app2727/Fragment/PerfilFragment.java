package com.cibersons.app2727.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cibersons.app2727.R;
import com.cibersons.app2727.beans.User.UserResponse;
import com.cibersons.app2727.comm.ApiImpl;
import com.cibersons.app2727.comm.CommReq;
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
public class PerfilFragment extends Fragment {

    private static PerfilFragment instance;

    private UserResponse userResponse;
    private EditText etNroDocumento;
    private EditText etNroCelular;
    private EditText etNombre;
    private EditText etApellido;

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

        llRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiImpl postOkHttp = new ApiImpl();
                String putUserJson = postOkHttp.putUserObject(etNroCelular.getText().toString(), etNroDocumento.getText().toString(), etNombre.getText().toString() + " " + etApellido.getText().toString());
                try {
                    postOkHttp.post(CommReq.BASE_URL, putUserJson, new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            Context context = getActivity().getApplicationContext();
                            SharedPreferences sharedPref = context.getSharedPreferences(
                                    getString(R.string.prefs_name), Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPref.edit();


                            String responseStr = response.body().string();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity().getApplicationContext(), "Usuario registrado!", Toast.LENGTH_LONG).show();
                                }
                            });
                            Log.i("DEBUG", "SUCESS!!");
                            Log.i("DEBUG", responseStr);
                            try {
                                JSONObject jsonObject = new JSONObject(responseStr);
                                Gson gson = new Gson();

                                userResponse = gson.fromJson(String.valueOf(jsonObject), UserResponse.class);
                                editor.putString(getString(R.string.save_ci), etNroDocumento.getText().toString() );
                                editor.putString(getString(R.string.save_tel), etNroCelular.getText().toString() );
                                editor.commit();

                                Log.d("DEBUG", userResponse.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
