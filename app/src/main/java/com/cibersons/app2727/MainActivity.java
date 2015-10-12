package com.cibersons.app2727;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cibersons.app2727.adapter.TabAdapter;
import com.cibersons.app2727.beans.User.UserAutenticationResponse;
import com.cibersons.app2727.comm.CommReq;
import com.cibersons.app2727.comm.ApiImpl;
import com.cibersons.app2727.fragment.HistorialFragment;
import com.cibersons.app2727.fragment.InstructivoFragment;
import com.cibersons.app2727.fragment.MainFragment;
import com.cibersons.app2727.fragment.PerfilFragment;
import com.cibersons.app2727.googleCloudNotification.QuickstartPreferences;
import com.cibersons.app2727.googleCloudNotification.RegistrationIntentService;
import com.cibersons.app2727.utils.Utils;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements MainFragment.OnHeadlineSelectedListener {

    private ViewPager pager;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private int[] imageResId = {
            R.mipmap.ic_home_new,
            R.mipmap.ic_saldo_new,
            R.mipmap.ic_instructivo_new,
            R.mipmap.ic_perfil_new
    };

    private boolean mSilentMode;
    private int exit = 0;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private UserAutenticationResponse userAutenticationResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Manejo del cloud notification
        cloudManagement();
        // Restore preferences
        sharedPreferencesSettingsControl();

        //Autenticacion
        controlDeAutenticacion();
        exit = 0;
        pager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new TabAdapter(getResources(), getSupportFragmentManager());
        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        fab = (FloatingActionButton) findViewById(R.id.fab);

        pager.setAdapter(adapter);

        if (pager != null) {
            setupViewPager(pager);
        }

        //Recibiendo mensaje de la notificacion
        manageNotification();

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        App2727.Logger.i("Main " + position);
                        break;
                    case 1:
                        App2727.Logger.i("Historial " + position);
                        String ci = Utils.getSharedPreferences(MainActivity.this).getString(getString(R.string.save_ci), getString(R.string.default_value));
                        String userAutentication = Utils.getSharedPreferences(MainActivity.this).getString(getString(R.string.user_autentication), getString(R.string.default_value));
                        if (!ci.equals(getString(R.string.default_value)) && userAutentication.equals(CommReq.STATUS_OK))
                            HistorialFragment.newInstance("Historial").onGetHistorial();
                        break;
                    case 2:
                        App2727.Logger.i("Instructivo " + position);
                        break;
                    case 3:
                        App2727.Logger.i("Perfil " + position);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setupWithViewPager(pager);
        tabLayout.getBackground().setAlpha(140);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
        }


    }

    private void manageNotification() {
        App2727.Logger.i("manageNotification");
        if (getIntent().hasExtra("FromNotification")) {
            if (getIntent().getBooleanExtra("FromNotification", false))
                pager.setCurrentItem(1);
        }
    }

    private void controlDeAutenticacion() {


        final SharedPreferences sharedPreferences = Utils.getSharedPreferences(this);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        String appID = sharedPreferences.getString(getString(R.string.token), getString(R.string.default_value));
        String userAutentication = sharedPreferences.getString(getString(R.string.user_autentication), getString(R.string.default_value));
        String getAutenticationString = ApiImpl.getAutentication(appID);

        App2727.Logger.i("Autenticacion de usuario = " + userAutentication);
        App2727.Logger.i("Mensaje enviado =" + getAutenticationString);

        try {
            new ApiImpl().post(CommReq.BASE_URL, getAutenticationString, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    App2727.Logger.e(e.getMessage());
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String responseStr = response.body().string();

                    App2727.Logger.i(responseStr);
                    Gson gson = new Gson();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(responseStr);
                        userAutenticationResponse = gson.fromJson(String.valueOf(jsonObject), UserAutenticationResponse.class);
                        App2727.Logger.i("Mensaje recibido = " + userAutenticationResponse);
                        if (userAutenticationResponse.getStatus().equals(CommReq.STATUS_OK)) {
                            editor.putString(getString(R.string.user_autentication), CommReq.STATUS_OK);
                        } else if (userAutenticationResponse.getStatus().equals(CommReq.STATUS_ERROR)) {
                            editor.putString(getString(R.string.user_autentication), CommReq.STATUS_ERROR);
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Utils.customAlertDialogWithOkFinish(MainActivity.this, "Error de autenticación!", "Por favor actualice su app.").show();

                                }
                            });
                        }
                        editor.commit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void cloudManagement() {
        final ProgressDialog progressDialog = Utils.getProgressDialog(this, "Registrando", "Espere un momento por favor...");
        progressDialog.show();
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                progressDialog.dismiss();
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    App2727.Logger.e(getString(R.string.gcm_send_message));
                } else {
                    App2727.Logger.e(getString(R.string.token_error_message));
                }
            }
        };

        if (Utils.checkPlayServices(this)) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));

        manageNotification();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter.clearListFragments();
        adapter.addFragment(MainFragment.newInstance("Main"), "");
        adapter.addFragment(HistorialFragment.newInstance("Historial"), "");
        adapter.addFragment(InstructivoFragment.newInstance("Instructivo"), "");
        adapter.addFragment(PerfilFragment.newInstance("Perfil"), "");
        viewPager.setAdapter(adapter);
    }

    private void sharedPreferencesSettingsControl() {
        SharedPreferences sharedPref = Utils.getSharedPreferences(this);
        mSilentMode = sharedPref.getBoolean("silentMode", false);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {

        final Dialog dialog = Utils.customAlertDialogWithOptions(this, "Saliendo de la aplicacion", "Esta seguro que desea salir?");
        final Button btnAceptar = (Button) dialog.findViewById(R.id.btn_aceptar);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit = 1;
                finish();
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });

        if (exit == 1) {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSelectedInstructivo(int pressed) {
        if (pressed == 1) {
            pager.setCurrentItem(2);

        }
    }


}
