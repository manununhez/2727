package com.cibersons.app2727;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.cibersons.app2727.adapter.TabAdapter;
import com.cibersons.app2727.beans.User.UserResponse;
import com.cibersons.app2727.comm.ApiImpl;
import com.cibersons.app2727.comm.CommReq;
import com.cibersons.app2727.fragment.HistorialFragment;
import com.cibersons.app2727.fragment.InstructivoFragment;
import com.cibersons.app2727.fragment.MainFragment;
import com.cibersons.app2727.fragment.PerfilFragment;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.logging.Logger;




public class MainActivity extends AppCompatActivity implements MainFragment.OnHeadlineSelectedListener, HistorialFragment.OnHistorialListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            pager.setCurrentItem(2);
        }
    }

    @Override
    public void onGetHistorial() {
        Log.i("2727","onGETHISTORIAL");
        String accion = CommReq.GET_USER;
        String appId = "";
        String celular = "0984000000";
        String cedula = "44444444";
        String operadora = "pytgo";
        String userAutent = "CnsgUser";
        String passAutent = "123456";

        PostExample example = new PostExample();
        String json = example.bowlingJson("Jesse", "Jake");
        try {
            example.post("http://www.roundsapp.com/post", json, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String responseStr = response.body().string();
                    Log.d("DEBUG", responseStr);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* new ApiImpl(MainActivity.this).getUser(accion, appId, celular, cedula, operadora, userAutent, passAutent, new Callback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {
                Log.d("DEBUG", userResponse.getData().toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Debug", error.getMessage());
            }
        });*/
    }
}
