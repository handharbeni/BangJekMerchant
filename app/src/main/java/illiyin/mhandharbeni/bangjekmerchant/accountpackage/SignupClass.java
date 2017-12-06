package illiyin.mhandharbeni.bangjekmerchant.accountpackage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import illiyin.mhandharbeni.bangjekmerchant.R;
import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyRegisterModel;

/**
 * Created by root on 11/27/17.
 */

public class SignupClass extends AppCompatActivity {
    private AdapterModel adapterModel;

    private ImageView imgBack;
    private Spinner kategori;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetch_modules();
        fetch_components();
        fetch_click();
        fetch_adapter_kategori();
    }

    private void fetch_modules(){
        adapterModel = new AdapterModel(this);
    }

    private void fetch_components(){
        imgBack = (ImageView) findViewById(R.id.imgBack);
        kategori = (Spinner) findViewById(R.id.txtInputKategori);
    }
    private void fetch_click(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void prepare_body(){
//        private String name;
//        private String address;
//        private String password;
//        private String email;
//        private String phone;
//        private String photo;
//        private String latitude;
//        private String longitude;
//        private String idMerchantCategory;
//        private String maxUpload;
//        private String openAt;
//        private String closeAt;
//        private String point;
//        private String openStatus;
//        private String status;
//        private String deleted;
//        private String imei1;
//        private String imei2;
        BodyRegisterModel body = new BodyRegisterModel();
        body.setName("");
        body.setAddress("");
        body.setEmail("");
        body.setPassword("");
        body.setPhone("");
        body.setPhoto("");
        body.setLatitude("");
        body.setLongitude("");

    }

    private void fetch_adapter_kategori(){
        List<String> list = new ArrayList<String>();
        list.add("Restoran");
        list.add("Barbershop");
        list.add("Accesories");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategori.setAdapter(dataAdapter);
    }
}
