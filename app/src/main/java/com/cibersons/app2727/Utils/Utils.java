package com.cibersons.app2727.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cibersons.app2727.App2727;
import com.cibersons.app2727.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by manunez on 01/10/2015.
 */
public class Utils {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static ProgressDialog getProgressDialog(Context context, String title, String text) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setTitle(title);
        pd.setMessage(text);
        pd.setCancelable(false);
        return pd;
    }

    public static void customAlertDialogWithOk(Activity activity, String title, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_gral);


        LinearLayout dialogButton = (LinearLayout) dialog.findViewById(R.id.btn_dialog);
        TextView tvMensajeTitle = (TextView) dialog.findViewById(R.id.tvMensajeTitle);
        TextView tvMensajeBody = (TextView) dialog.findViewById(R.id.tvMensajeBody);
        tvMensajeBody.setText(msg);
        tvMensajeTitle.setText(title);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    public static Dialog customAlertDialogWithOptions(Activity activity, String title, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_gral_options);

        TextView tvMensajeTitle = (TextView) dialog.findViewById(R.id.tvMensajeTitle);
        TextView tvMensajeBody = (TextView) dialog.findViewById(R.id.tvMensajeBody);

        final LinearLayout btnCancelar = (LinearLayout) dialog.findViewById(R.id.btn_cancelar);
        tvMensajeBody.setText(msg);
        tvMensajeTitle.setText(title);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        return dialog;
    }

    public static Dialog customAlertDialogCI(final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_ingreso_ci);

//        final EditText etCI = (EditText) dialog.findViewById(R.id.etCI);
//        final LinearLayout btnAceptar = (LinearLayout) dialog.findViewById(R.id.btn_aceptar);
        final LinearLayout btnCancelar = (LinearLayout) dialog.findViewById(R.id.btn_cancelar);
//        btnAceptar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences.Editor editor = activity.getSharedPreferences(activity.getString(R.string.prefs_name), Context.MODE_PRIVATE).edit();
//                editor.putString(activity.getString(R.string.save_ci), etCI.getText().toString());
//                editor.commit();
//                dialog.dismiss();
//            }
//        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        return dialog;
    }

    public static Dialog customAlertDialogInstructivo(final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        return dialog;
    }

    public static SharedPreferences getSharedPreferences(Activity activity){
       SharedPreferences sharedPreferences =  activity.getSharedPreferences(activity.getString(R.string.prefs_name), Context.MODE_PRIVATE);
        return sharedPreferences;

    }

    /**
     * Retorna verdadero si hay una conexión
     *
     * @param context Activity
     * @return boolean
     */
    public static boolean haveNetworkConnection(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    public static boolean checkPlayServices(Activity activity) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(activity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                App2727.Logger.i("This device is not supported.");
                activity.finish();
            }
            return false;
        }
        return true;
    }
}
