package illiyin.mhandharbeni.bangjekmerchant.accountpackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import illiyin.mhandharbeni.bangjekmerchant.R;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.MainClass;
import illiyin.mhandharbeni.databasemodule.AdapterModel;

/**
 * Created by root on 11/27/17.
 */

public class SigninClass extends AppCompatActivity {
    private AdapterModel adapterModel;

    private TextView klikDaftar;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetch_modules();
        fetch_components();
        fetch_click();
    }

    private void fetch_modules(){
        adapterModel = new AdapterModel(this);
    }
    private void fetch_components(){
        klikDaftar = (TextView) findViewById(R.id.klikDaftar);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }
    private void fetch_click(){
        klikDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SigninClass.this, SignupClass.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SigninClass.this, MainClass.class);
                startActivity(i);
            }
        });
    }
}
