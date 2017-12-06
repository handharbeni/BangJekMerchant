package illiyin.mhandharbeni.bangjekmerchant.splashpackage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
}
