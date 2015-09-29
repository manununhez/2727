package com.cibersons.app2727;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.cibersons.app2727.adapter.TabAdapter;
import com.cibersons.app2727.comm.CommReq;
import com.cibersons.app2727.comm.ApiImpl;
import com.cibersons.app2727.fragment.HistorialFragment;
import com.cibersons.app2727.fragment.InstructivoFragment;
import com.cibersons.app2727.fragment.MainFragment;
import com.cibersons.app2727.fragment.PerfilFragment;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class MainActivity extends AppCompatActivity  implements MainFragment.OnHeadlineSelectedListener{ //, HistorialFragment.OnHistorialListener {

    private ViewPager pager;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    //    private FloatingActionButton fab;
    private int[] imageResId = {
            R.mipmap.ic_home_new,
            R.mipmap.ic_saldo_new,
            R.mipmap.ic_instructivo_new,
            R.mipmap.ic_perfil_new
    };

    private boolean mSilentMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Restore preferences
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.prefs_name), 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.save_ci), getString(R.string.default_value));
        editor.putString(getString(R.string.save_tel), getString(R.string.default_value) );
        editor.commit();
         mSilentMode = sharedPref.getBoolean("silentMode", false);


        pager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new TabAdapter(getResources(), getSupportFragmentManager());
        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        fab = (FloatingActionButton) findViewById(R.id.fab);

        pager.setAdapter(adapter);

        if (pager != null) {
            setupViewPager(pager);
        }

        tabLayout.setupWithViewPager(pager);
        tabLayout.getBackground().setAlpha(140);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
        }

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

    private void setupViewPager(ViewPager viewPager) {
        adapter.clearListFragments();
        adapter.addFragment(MainFragment.newInstance("Main"), "");
        adapter.addFragment(HistorialFragment.newInstance("Historial"), "");
        adapter.addFragment(InstructivoFragment.newInstance("Instructivo"), "");
        adapter.addFragment(PerfilFragment.newInstance("Perfil"), "");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onStop(){
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(getString(R.string.prefs_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("silentMode", mSilentMode);
        editor.putString(getString(R.string.save_ci), "4192547");

        // Commit the edits!
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.prefs_name),Context.MODE_PRIVATE);
            String ci = sharedPref.getString(getString(R.string.save_ci), "");
            String celular = sharedPref.getString(getString(R.string.save_tel),"");
            Log.i("INFOCI",ci);
            Log.i("INFOCELULAr",celular);
            pager.setCurrentItem(2);
        }
    }


}
