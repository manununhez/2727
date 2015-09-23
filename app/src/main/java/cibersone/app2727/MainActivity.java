package cibersone.app2727;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import cibersone.app2727.Adapter.TabAdapter;
import cibersone.app2727.Fragment.HistorialFragment;
import cibersone.app2727.Fragment.InstructivoFragment;
import cibersone.app2727.Fragment.MainFragment;
import cibersone.app2727.Fragment.PerfilFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.OnHeadlineSelectedListener {

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
}
