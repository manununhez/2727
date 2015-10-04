package com.cibersons.app2727.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

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
public class MainFragment extends RootFragment {

    private static MainFragment instance;
    //    private EditText phoneNo;
//    private EditText messageBody;
    private LinearLayout send;
    private LinearLayout llTextoBienvenida;
    //    private Button btnOK;
//    private CardView cardView;
    private EditText etCI;

    private static int PRESSED = 1;
    OnHeadlineSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onSelectedInstructivo(int pressed);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception

        Activity activity = null;

        if (context instanceof Activity) {
            activity = (Activity) context;
        }
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public static MainFragment newInstance(String title) {
        if (instance == null) {
            instance = new MainFragment();
            Bundle args = new Bundle();
//            args.putString(ARG_FRAGMENT_TITLE, title);
            instance.setArguments(args);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        send = (LinearLayout) rootView.findViewById(R.id.btnSendSMS);
        llTextoBienvenida = (LinearLayout) rootView.findViewById(R.id.llTextoBienvenida);
        llTextoBienvenida.getBackground().setAlpha(80);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.prefs_name), Context.MODE_PRIVATE);
                String ci = sharedPref.getString(getString(R.string.save_ci), getString(R.string.default_value));

                if (ci.equals(getString(R.string.default_value))) {
                    //enviar SMS
                    final Dialog dialog = Utils.customAlertDialogCI(getActivity());
                    final LinearLayout btnAceptar = (LinearLayout) dialog.findViewById(R.id.btn_aceptar);
                    etCI = (EditText) dialog.findViewById(R.id.etCI);
                    btnAceptar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            registrarUsuario();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {

                    //enviar SMS
//                    sendMessage(); -----CONTROLAR QUE EXISTA EL NUMERO DE CELULAR, sino EXISTE, enviar a la pagina PERFIL
                    //Si no esta suscrito, mostrar instructivo
                    final Dialog dialog = Utils.customAlertDialogInstructivo(getActivity());
                    final LinearLayout dialogButton = (LinearLayout) dialog.findViewById(R.id.btn_dialog);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // if button is clicked, close the custom dialog
                            dialog.dismiss();
                            mCallback.onSelectedInstructivo(PRESSED);
                        }
                    });
                    dialog.show();
                }


            }
        });

        return rootView;
    }


    private void registrarUsuario() {
        SharedPreferences sharedPreferences = Utils.getSharedPreferences(getActivity());
        String numeroDocumento = etCI.getText().toString();
        String nombre = sharedPreferences.getString(getString(R.string.save_nombre), getString(R.string.default_value));
        String apellido = sharedPreferences.getString(getString(R.string.save_apellido), getString(R.string.default_value));
        String nombreApellido = nombre + " " + apellido;
        String numeroCelular = sharedPreferences.getString(getString(R.string.save_tel), getString(R.string.default_value));

        if (numeroDocumento.equals(getString(R.string.default_value))) {
            showDialogOk("Campo requerido!", "Favor ingresar el número de documento.");
        } else {
            final ProgressDialog progressDialog = Utils.getProgressDialog(getActivity(), "Cargando...", "Aguarde un momento por favor!");
            progressDialog.show();
//            ApiImpl postOkHttp = null;
//            try {
//                postOkHttp = new ApiImpl();
//            } catch (Exception e) {
//                progressDialog.dismiss();
//                e.printStackTrace();
//                showDialogOk("Error!", "XXXXXXXX");
//                App2727.Logger.e(e.getMessage());
//            }
            String putUserJson = ApiImpl.putUserObject(numeroCelular, numeroDocumento, nombreApellido);
            App2727.Logger.i("Datos enviados:" + putUserJson);
            if (Utils.haveNetworkConnection(getActivity())) {
                try {
                    new ApiImpl().post(CommReq.BASE_URL, putUserJson, new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            progressDialog.dismiss();
                            showDialogOk("Error!", e.getMessage());
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

                                UserResponse userResponse = gson.fromJson(String.valueOf(jsonObject), UserResponse.class);

                                if (userResponse.getStatus().equals("OK")) {
                                    saveSharedPreferencesData();
                                    showDialogOk(userResponse.getStatus(), "Usuario registrado correctamente!");
                                } else {
                                    showDialogOk(userResponse.getStatus(), userResponse.getData().getNombreUsuario());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                                showDialogOk("Error!", e.getMessage());
                                App2727.Logger.e(e.getMessage());
                            }

                        }
                    });
                } catch (Exception e) {
                    progressDialog.dismiss();
                    App2727.Logger.e(e.getMessage());
//                    showDialogOk("Error!", e.getMessage());
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            } else {
                progressDialog.dismiss();
                App2727.Logger.e(CommReq.ERROR_CONEXION_BODY);
                showDialogOk(CommReq.ERROR_CONEXION_TITLE, CommReq.ERROR_CONEXION_BODY);
            }
        }
    }

    private void saveSharedPreferencesData() {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(getActivity().getString(R.string.prefs_name), Context.MODE_PRIVATE).edit();
        editor.putString(getActivity().getString(R.string.save_ci), etCI.getText().toString());
        editor.commit();
    }

    private void sendMessage() {
        String number = "0982484860";
        String sms = "prueba";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, sms, null, null);
            Toast.makeText(getActivity().getApplicationContext(), "Mensaje Enviado!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Envio de SMS fallido, intente de nuevo!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


}
