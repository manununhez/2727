package cibersone.app2727;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;



public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash_screen);
        SplashWaiter launcher = new SplashWaiter();
        launcher.start();
    }
    private class SplashWaiter extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(3 * 1000);
            } catch (Exception e) {
                //Log.e(app.development.lrm.smartbar.MeridianoApp.TAG, "ocurrio un error");
            }
            Intent intent = null;

            //  prefEntidad = null; prefSucursal=null; prefTerminal=null;

            // if (prefEntidad == null && prefSucursal == null && prefTerminal == null) {

            //if (prefEntidad == null && prefSucursal == null && prefTerminal == null) {

            //intent = new Intent(SplashScreen.this, PagoServicios.class);
            intent = new Intent(SplashScreen.this, MainActivity.class);


            // } else {
            //intent = new Intent(SplashScreen.this, PagoServicios.class)
            // if (prefEntidad == null && prefSucursal == null && prefTerminal == null) {

            //intent = new Intent(SplashScreen.this, PagoServicios.class);
            //  } else {
            //   intent = new Intent(SplashScreen.this, PagosServicios.class);


            SplashScreen.this.startActivity(intent);
            SplashScreen.this.finish();
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
}
