package illiyin.mhandharbeni.bangjekmerchant.splashpackage;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import illiyin.mhandharbeni.bangjekmerchant.R;
import illiyin.mhandharbeni.bangjekmerchant.accountpackage.SigninClass;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.MainClass;
import illiyin.mhandharbeni.servicemodule.ServiceAdapter;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;

/**
 * Created by root on 11/27/17.
 */

public class SplashScreen extends AppCompatActivity implements SessionListener{
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestPermission();
        run_service();
        session = new Session(this, this);
        String state = session.getToken();
        if (state.equalsIgnoreCase("nothing")){
            runHandler(SigninClass.class);
        }else{
            runHandler(MainClass.class);
        }
    }

    private void runHandler(final Class destinationClass){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, destinationClass));
                finish();
            }
        },2000);
    }

    private void run_service(){
        ServiceAdapter serviceAdapter = new ServiceAdapter(this);
        serviceAdapter.startService();
    }

    @Override
    public void sessionChange() {

    }
    private void requestPermission(){
        String[] permissions = new String[11];
        permissions[0] = Manifest.permission.CAMERA;
        permissions[1] = Manifest.permission.INTERNET;
        permissions[2] = Manifest.permission.WAKE_LOCK;
        permissions[3] = Manifest.permission.LOCATION_HARDWARE;
        permissions[4] = Manifest.permission.ACCESS_COARSE_LOCATION;
        permissions[5] = Manifest.permission.ACCESS_FINE_LOCATION;
        permissions[6] = Manifest.permission.READ_PHONE_STATE;
        permissions[7] = Manifest.permission.ACCESS_NETWORK_STATE;
        permissions[8] = Manifest.permission.ACCESS_WIFI_STATE;
        permissions[9] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        permissions[10] = Manifest.permission.READ_EXTERNAL_STORAGE;
        ActivityCompat.requestPermissions(
                this,
                permissions,
                5
        );
    }
}
