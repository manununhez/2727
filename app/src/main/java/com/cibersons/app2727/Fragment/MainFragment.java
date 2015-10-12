package com.cibersons.app2727.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cibersons.app2727.App2727;
import com.cibersons.app2727.R;
import com.cibersons.app2727.beans.Transaccion.TransaccionResponse;
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
    private LinearLayout send;
    private LinearLayout llTextoBienvenida;
    private EditText etCI;
    private static int PRESSED = 1;
    OnHeadlineSelectedListener mCallback;
    private TransaccionResponse transaccionResponse;

    private SharedPreferences sharedPreferences;
    private Button btnSMS;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        send = (LinearLayout) rootView.findViewById(R.id.btnSendSMS);
        llTextoBienvenida = (LinearLayout) rootView.findViewById(R.id.llTextoBienvenida);
        llTextoBienvenida.getBackground().setAlpha(80);

        sharedPreferences = Utils.getSharedPreferences(getActivity());
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ci = sharedPreferences.getString(getString(R.string.save_ci), getString(R.string.default_value));

                if (ci.equals(getString(R.string.default_value))) {
                    final Dialog dialog = Utils.customAlertDialogCI(getActivity());
                    final Button btnAceptar = (Button) dialog.findViewById(R.id.btn_aceptar);
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
//                    sendMessagePrueba();
                    sendMessage(); //-----CONTROLAR QUE EXISTA EL NUMERO DE CELULAR, sino EXISTE, enviar a la pagina PERFIL


                }


            }
        });

        return rootView;
    }


    private void registrarUsuario() {
        String numeroDocumento = etCI.getText().toString();
        String nombreApellido = sharedPreferences.getString(getString(R.string.save_nombre), getString(R.string.default_value));
        String appID = sharedPreferences.getString(getString(R.string.token), getString(R.string.default_value));

        if (numeroDocumento.equals(getString(R.string.default_value))) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Utils.customAlertDialogWithOk(getActivity(), "Campo requerido!", "Favor ingresar el número de documento.").show();

                }
            });

//            showDialogOk("Campo requerido!", "Favor ingresar el número de documento.");
        } else if (!numeroDocumento.equals(getString(R.string.default_value)) && numeroDocumento.length() < 6) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Utils.customAlertDialogWithOk(getActivity(), "Longitud de cèdula!", "Favor ingresar el número de documento con mayor a 6 dígitos.").show();

                }
            });
        } else {
            final ProgressDialog progressDialog = Utils.getProgressDialog(getActivity(), "Cargando...", "Aguarde un momento por favor!");
            progressDialog.show();

            String putUserJson = ApiImpl.putUserObject(numeroDocumento, nombreApellido, appID);
            App2727.Logger.i("Datos enviados:" + putUserJson);
            if (Utils.haveNetworkConnection(getActivity())) {
                try {
                    new ApiImpl().post(CommReq.BASE_URL, putUserJson, new Callback() {
                        @Override
                        public void onFailure(Request request, final IOException e) {
                            progressDialog.dismiss();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Utils.customAlertDialogWithOk(getActivity(), "Error!", e.getMessage()).show();

                                }
                            });

//                            showDialogOk("Error!", e.getMessage());
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

                                final UserResponse userResponse = gson.fromJson(String.valueOf(jsonObject), UserResponse.class);

                                if (userResponse.getStatus().equals(CommReq.STATUS_OK)) {
                                    saveSharedPreferencesData();
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(), "Usuario registrado correctamente!", Toast.LENGTH_LONG);
//                                            Utils.customAlertDialogWithOk(getActivity(), userResponse.getStatus(), "Usuario registrado correctamente!").show();

                                        }
                                    });

//                                    showDialogOk(userResponse.getStatus(), "Usuario registrado correctamente!");
                                    sendMessagePrueba();
                                } else {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Utils.customAlertDialogWithOk(getActivity(), userResponse.getStatus(), userResponse.getData().getNombreUsuario()).show();

                                        }
                                    });

//                                    showDialogOk(userResponse.getStatus(), userResponse.getData().getNombreUsuario());
                                }
                            } catch (final JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Utils.customAlertDialogWithOk(getActivity(), "Error!", e.getMessage()).show();

                                    }
                                });

//                                showDialogOk("Error!", e.getMessage());
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.customAlertDialogWithOk(getActivity(), CommReq.ERROR_CONEXION_TITLE, CommReq.ERROR_CONEXION_BODY).show();

                    }
                });
//                showDialogOk(CommReq.ERROR_CONEXION_TITLE, CommReq.ERROR_CONEXION_BODY);
            }
        }
    }

    private void saveSharedPreferencesData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getActivity().getString(R.string.save_ci), etCI.getText().toString());
        editor.apply();
    }

    private void sendMessage() {
        final String ci = sharedPreferences.getString(getString(R.string.save_ci), getString(R.string.default_value));

        String number = "2727";
        String sms = ci+" app";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, sms, null, null);
            Toast.makeText(getActivity().getApplicationContext(), "Mensaje Enviado!",
                    Toast.LENGTH_LONG).show();
            getMessageResponse();
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Envio de SMS fallido, intente de nuevo!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void getMessageResponse(){
        final String appID = sharedPreferences.getString(getString(R.string.token), getString(R.string.default_value));
        String json = ApiImpl.getTransaction("unico", appID);
        App2727.Logger.i("Mensaje enviado" + json);
        try {
            new ApiImpl().post(CommReq.BASE_URL, json, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    App2727.Logger.e(e.getMessage());
                }

                @Override
                public void onResponse(Response response) throws IOException {
//                                progressDialog.dismiss();
                    String responseStr = response.body().string();
                    App2727.Logger.i("Respuesta = " + responseStr);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(responseStr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Gson gson = new Gson();

                    transaccionResponse = gson.fromJson(String.valueOf(jsonObject), TransaccionResponse.class);


                    if (transaccionResponse.getStatus().equals(CommReq.STATUS_OK)) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (transaccionResponse.getData().getTransaccion().get(0).getCodRespuesta().equals(CommReq.NO_SE_ENCUENTRA_SUSCRIPTO)) {
                                    irAInstructivo();
                                } else {
                                    String putViewTransString = ApiImpl.putViewTrans(transaccionResponse.getData().getTransaccion().get(0).getIdTransaccion(), appID);
                                    App2727.Logger.i("Mensaje enviado = " + putViewTransString);
                                    //Aviso para no mostrar el push notification
                                    try {
                                        new ApiImpl().post(CommReq.BASE_URL, putViewTransString, new Callback() {
                                            @Override
                                            public void onFailure(Request request, IOException e) {
                                                App2727.Logger.e(e.getMessage());

                                            }

                                            @Override
                                            public void onResponse(Response response) throws IOException {
                                                String responseStr = response.body().string();
                                                App2727.Logger.i("Respuesta = " + responseStr);
                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    Utils.customAlertDialogWithOk(getActivity(), transaccionResponse.getStatus(), transaccionResponse.getData().getTransaccion().get(0).getMensaje()).show();

                                }

                            }
                        });
                    } else if (transaccionResponse.getStatus().equals(CommReq.STATUS_ERROR)) {
                        Utils.customAlertDialogWithOk(getActivity(), transaccionResponse.getStatus(), transaccionResponse.getData().getTransaccion().get(0).getMensaje()).show();

                    }

//                                showDialogOk("OK!", transaccionResponse.getData().getTransaccion().get(0).getMensaje());

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessagePrueba() {
        final String appID = sharedPreferences.getString(getString(R.string.token), getString(R.string.default_value));
        final String ci = sharedPreferences.getString(getString(R.string.save_ci), getString(R.string.default_value));

//        final ProgressDialog progressDialog = Utils.getProgressDialog(getContext(), "Cargando...", "Aguarde un momento por favor!");
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                progressDialog.show();

//            }
//        });
        try {
            App2727.Logger.i("MO ENVIADO");
            new ApiImpl().postWithParameters(CommReq.BASE_URL_MO, ci, new Callback() {
                @Override
                public void onFailure(Request request, final IOException e) {
//                    progressDialog.dismiss();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utils.customAlertDialogWithOk(getActivity(), "Error!", e.getMessage()).show();

                        }
                    });
//                    showDialogOk("Error!", e.getMessage());
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String responseStr = response.body().string();
//                    progressDialog.dismiss();
//                    showDialogOk("OK!", responseStr);
                    String json = ApiImpl.getTransaction("unico", appID);
                    App2727.Logger.i("Respuesta = " + responseStr);
                    App2727.Logger.i("Mensaje enviado" + json);
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            final ProgressDialog progressDialog = Utils.getProgressDialog(getActivity(), "Cargando...", "Aguarde un momento por favor!");
//                            progressDialog.show();
//                        }
//                    });

                    try {
                        new ApiImpl().post(CommReq.BASE_URL, json, new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                App2727.Logger.e(e.getMessage());
                            }

                            @Override
                            public void onResponse(Response response) throws IOException {
//                                progressDialog.dismiss();
                                String responseStr = response.body().string();
                                App2727.Logger.i("Respuesta = " + responseStr);
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(responseStr);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Gson gson = new Gson();

                                transaccionResponse = gson.fromJson(String.valueOf(jsonObject), TransaccionResponse.class);


                                if (transaccionResponse.getStatus().equals(CommReq.STATUS_OK)) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (transaccionResponse.getData().getTransaccion().get(0).getCodRespuesta().equals(CommReq.NO_SE_ENCUENTRA_SUSCRIPTO)) {
                                                irAInstructivo();
                                            } else {
                                                String putViewTransString = ApiImpl.putViewTrans(transaccionResponse.getData().getTransaccion().get(0).getIdTransaccion(), appID);
                                                App2727.Logger.i("Mensaje enviado = " + putViewTransString);
                                                try {
                                                    new ApiImpl().post(CommReq.BASE_URL, putViewTransString, new Callback() {
                                                        @Override
                                                        public void onFailure(Request request, IOException e) {
                                                            App2727.Logger.e(e.getMessage());

                                                        }

                                                        @Override
                                                        public void onResponse(Response response) throws IOException {
                                                            String responseStr = response.body().string();
                                                            App2727.Logger.i("Respuesta = " + responseStr);
                                                        }
                                                    });
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                Utils.customAlertDialogWithOk(getActivity(), transaccionResponse.getStatus(), transaccionResponse.getData().getTransaccion().get(0).getMensaje()).show();

                                            }

                                        }
                                    });
                                } else if (transaccionResponse.getStatus().equals(CommReq.STATUS_ERROR)) {
                                    Utils.customAlertDialogWithOk(getActivity(), transaccionResponse.getStatus(), transaccionResponse.getData().getTransaccion().get(0).getMensaje()).show();

                                }

//                                showDialogOk("OK!", transaccionResponse.getData().getTransaccion().get(0).getMensaje());

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //Cuando no esta registrado
                    //irAInstructivo();
                }
            });
        } catch (final Exception e) {
//            progressDialog.dismiss();
            e.printStackTrace();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Utils.customAlertDialogWithOk(getActivity(), "Error!", e.getMessage()).show();

                }
            });
//            showDialogOk("Error!", e.getMessage());
        }
    }

    private void irAInstructivo() {
        final Dialog dialog = Utils.customAlertDialogInstructivo(getActivity());
        final Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
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
