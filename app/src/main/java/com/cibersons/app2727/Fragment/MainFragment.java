package com.cibersons.app2727.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cibersons.app2727.R;
import com.cibersons.app2727.utils.Utils;

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
                String celular = sharedPref.getString(getString(R.string.save_tel), getString(R.string.default_value));
                Log.i("INFOCI", ci);
                Log.i("INFOCELULAr", celular);
                if (ci.equals(getString(R.string.default_value))) {
                    //enviar SMS
                    sendMessage();
                    Utils.customAlertDialogCI(getActivity());
                } else {

                    //enviar SMS
                    sendMessage();
                    //Si no esta suscrito, mostrar instructivo
                    final Dialog dialog = Utils.customAlertDialogInstructivo(getActivity());
                    final LinearLayout dialogButton = (LinearLayout) dialog.findViewById(R.id.btn_dialog);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // if button is clicked, close the custom dialog
                            mCallback.onSelectedInstructivo(PRESSED);
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }




            }
        });

        return rootView;
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
