package com.cibersons.app2727.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cibersons.app2727.R;

/**
 * Created by Manuel on 8/19/2015.
 */
public class MainFragment extends Fragment {

    private static MainFragment instance;
//    private EditText phoneNo;
//    private EditText messageBody;
    private LinearLayout send;
    private LinearLayout llTextoBienvenida;
//    private Button btnOK;
//    private CardView cardView;

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

        if (context instanceof Activity){
            activity=(Activity) context;
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
//        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        send = (LinearLayout) rootView.findViewById(R.id.btnSendSMS);
        llTextoBienvenida = (LinearLayout) rootView.findViewById(R.id.llTextoBienvenida);
//        phoneNo = (EditText) rootView.findViewById(R.id.mobileNumber);
//        messageBody = (EditText) rootView.findViewById(R.id.smsBody);
//        btnOK = (Button) rootView.findViewById(R.id.btnOk);
//        cardView = (CardView) rootView.findViewById(R.id.card_view_bienvenida);

//        btnOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cardView.setVisibility(View.GONE);
//            }
//        });
//        send = (Button) findViewById(R.id.send);
        llTextoBienvenida.getBackground().setAlpha(80);

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.prefs_name),Context.MODE_PRIVATE);
                String ci = sharedPref.getString(getString(R.string.save_ci),  getString(R.string.default_value));
                String celular = sharedPref.getString(getString(R.string.save_tel),  getString(R.string.default_value));
                Log.i("INFOCI",ci);
                Log.i("INFOCELULAr",celular);
                if(ci.equals(getString(R.string.default_value))){
                    //enviar SMS
                    dialog.setContentView(R.layout.custom_dialog_ingreso_ci);
                    final EditText etCI = (EditText) dialog.findViewById(R.id.etCI);
                    final LinearLayout btnAceptar = (LinearLayout) dialog.findViewById(R.id.btn_aceptar);
                    final LinearLayout btnCancelar = (LinearLayout) dialog.findViewById(R.id.btn_cancelar);
                    btnAceptar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(getString(R.string.save_ci), etCI.getText().toString());
                            editor.commit();
                            dialog.dismiss();
                        }
                    });

                    btnCancelar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                }else{
                    dialog.setContentView(R.layout.custom_dialog);
                    final LinearLayout dialogButton = (LinearLayout) dialog.findViewById(R.id.btn_dialog);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // if button is clicked, close the custom dialog
                            mCallback.onSelectedInstructivo(PRESSED);
                            dialog.dismiss();
                        }
                    });
                }

                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();

//                String number = "0982484860";
//                String sms = "prueba";
//
//                try {
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage(number, null, sms, null, null);
//                    Toast.makeText(getActivity().getApplicationContext(), "Mensaje Enviado!",
//                            Toast.LENGTH_LONG).show();
//                } catch (Exception e) {
//                    Toast.makeText(getActivity().getApplicationContext(),
//                            "Envio de SMS fallido, intente de nuevo!",
//                            Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }
            }
        });

        return rootView;
    }


}
