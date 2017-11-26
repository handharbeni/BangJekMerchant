package illiyin.mhandharbeni.bangjekmerchant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyLogin;
import illiyin.mhandharbeni.servicemodule.ServiceAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ServiceAdapter serviceAdapter = new ServiceAdapter(this);
        serviceAdapter.startService();

        setContentView(R.layout.activity_main);

        BodyLogin bodyLogin = new BodyLogin();
        bodyLogin.setEmail("waralaba@gmail.com");
        bodyLogin.setPassword("12345");

        AdapterModel adapterModel = new AdapterModel(this);
        adapterModel.doLogin(bodyLogin);
    }
}
