package illiyin.mhandharbeni.bangjekmerchant.accountpackage;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import illiyin.mhandharbeni.bangjekmerchant.R;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.MainClass;
import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyLogin;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.utilslibrary.SnackBar;

/**
 * Created by root on 11/27/17.
 */

public class SigninClass extends AppCompatActivity implements SessionListener {
    private AdapterModel adapterModel;
    private Session session;
    private TextView klikDaftar;
    private Button btnLogin;

    private EditText txtUsername, txtPassword;

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
        requestPermission();
    }

    private void fetch_modules(){
        adapterModel = new AdapterModel(this);
        session = new Session(this, this);
    }
    private void fetch_components(){
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
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
                BodyLogin bl = new BodyLogin();
                bl.setEmail(txtUsername.getText().toString());
                bl.setPassword(txtPassword.getText().toString());
                String login = adapterModel.doLogin(bl, getString(R.string.caption_login_success), getString(R.string.caption_login_failed));
                if (login.equalsIgnoreCase(getResources().getString(R.string.caption_login_success))){
//                    showSnackBar(login);
                    sessionChange();
                }else{
                    showSnackBar(login);
                }
//                if (getString(R.string.caption_login_success).equalsIgnoreCase(login)){
//                    sessionChange();
//                }else{
//                    showSnackBar(login);
//                }
            }
        });
    }

    @Override
    public void sessionChange() {
        if (!session.getToken().equalsIgnoreCase("nothing")){
            startActivity(new Intent(SigninClass.this, MainClass.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }
    }
    private void showSnackBar(String message){
        new SnackBar(this).view(findViewById(R.id.bottomlinear)).message(message).build().show();
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
