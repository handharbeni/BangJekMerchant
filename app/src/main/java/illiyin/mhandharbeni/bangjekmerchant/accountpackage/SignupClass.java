package illiyin.mhandharbeni.bangjekmerchant.accountpackage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import illiyin.mhandharbeni.bangjekmerchant.R;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.mainfragment.subfragment.FragmentProfile;
import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.model.CategoryModel;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyRegisterModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.utilslibrary.TimePicker;
import io.realm.RealmResults;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;

/**
 * Created by root on 11/27/17.
 */

public class SignupClass extends AppCompatActivity {
    public static int PLACE_PICKER_REQUEST = 1;
    private static String IMAGE_CURRENT = "ImageCurrent";
    private String ImageCurrent = "https://pbs.twimg.com/profile_images/658332899335258112/6RSo0UwJ_400x400.jpg";
    private String imagePath;

    private AdapterModel adapterModel;

    private CategoryModel categoryModel;
    private Crud crud;

    private ImageView imgBack;
    private MaterialBetterSpinner kategori;

    private ArrayAdapter<String> adapter;

    private EditText txtNamaUsaha, txtAlamat, txtEmail, txtPassword, txtNoTelp, txtDeskripsi;
    private TextView txtJamBuka, txtJamTutup, txtPlace;
    private Button btnRegister, btnPlace;
    private CircleImageView image;
    private Session session;
    private static String latSignup = "LATITUDESIGNUP", longSignup = "LONGITUDESIGNUP";
    private static String defLat = "-8.0075251", defLong = "112.62202";

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
        fetch_data_kategori();
    }

    private void fetch_modules() {
        session = new Session(this, new SessionListener() {
            @Override
            public void sessionChange() {

            }
        });
        adapterModel = new AdapterModel(this);
        categoryModel = new CategoryModel();
        crud = new Crud(this, categoryModel);
    }

    private void fetch_components() {
        imgBack = (ImageView) findViewById(R.id.imgBack);
        txtNamaUsaha = (EditText) findViewById(R.id.txtNamaUsaha);
        txtAlamat = (EditText) findViewById(R.id.txtAlamat);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtNoTelp = (EditText) findViewById(R.id.txtNoTelp);
        txtDeskripsi = (EditText) findViewById(R.id.txtDeskripsi);
        txtJamBuka = (TextView) findViewById(R.id.txtJamBuka);
        txtJamTutup = (TextView) findViewById(R.id.txtJamTutup);
        txtPlace = (TextView) findViewById(R.id.txtPlace);
        image = (CircleImageView) findViewById(R.id.images);
        kategori = (MaterialBetterSpinner) findViewById(R.id.txtInputKategori);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnPlace = (Button) findViewById(R.id.btnPlace);
    }

    private void fetch_click() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    do_register();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_PICK);

                final Intent chooserIntent = Intent.createChooser(galleryIntent, "Pilih Image");
                startActivityForResult(chooserIntent, 1010);

            }
        });
        txtJamBuka.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showTimePickerDialog(txtJamBuka);
                }
            }
        });
        txtJamBuka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(txtJamBuka);
            }
        });
        txtJamTutup.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showTimePickerDialog(txtJamTutup);
                }
            }
        });
        txtJamTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(txtJamTutup);
            }
        });
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    startActivityForResult(builder.build(SignupClass.this), PLACE_PICKER_REQUEST);
                } catch (Exception e) {
                    Timber.e(e);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                Double latitude = place.getLatLng().latitude;
                Double longitude = place.getLatLng().longitude;
                session.setCustomParams(latSignup, String.valueOf(latitude));
                session.setCustomParams(longSignup, String.valueOf(longitude));
                txtPlace.setText(place.getAddress());
            }
        }else if (resultCode == Activity.RESULT_OK && requestCode == 1010) {
            //TODO: action
            if (data == null) {
                //Display an error
                return;
            }
            Uri selectedImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            assert selectedImageUri != null;
            @SuppressLint("Recycle") Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);
                try {
                    do_upload();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Upload Image Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void do_upload() throws Exception {
        File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("userfile", file.getName(), requestFile);
        String returns = adapterModel.uploadRegisterImage(body, getString(R.string.caption_upload_success), getString(R.string.caption_upload_failed));
        if (returns.equalsIgnoreCase(getString(R.string.caption_upload_success))) {
            String locationFile = getString(illiyin.mhandharbeni.databasemodule.R.string.module_server) + "/uploads/" + file.getName();
            ImageCurrent = locationFile;
            session.setCustomParams(IMAGE_CURRENT, locationFile);
            Glide.with(this).load(locationFile).into(image);
        } else {
            Toast.makeText(this, "Upload Image Gagal", Toast.LENGTH_SHORT).show();
        }
    }

    private BodyRegisterModel prepare_body() {
        BodyRegisterModel body = new BodyRegisterModel();
        body.setName(txtNamaUsaha.getText().toString());//
        body.setAddress(txtAlamat.getText().toString());
        body.setEmail(txtEmail.getText().toString());//
        body.setPassword(txtPassword.getText().toString());//
        body.setPhone(txtNoTelp.getText().toString());
        body.setPhoto(session.getCustomParams(IMAGE_CURRENT, ImageCurrent));
        body.setIdMerchantCategory(getIdCategoryMerchant(kategori.getText().toString()));//
        body.setMaxUpload("2");
        body.setOpenAt(txtJamBuka.getText().toString());
        body.setCloseAt(txtJamTutup.getText().toString());
        body.setDescription(txtDeskripsi.getText().toString());
        body.setPoint("0");
        body.setOpenStatus("OPEN");
        body.setStatus("123");
        body.setLatitude(session.getCustomParams(latSignup, defLat));
        body.setLongitude(session.getCustomParams(longSignup, defLong));
        body.setImei1(deviceId());
        body.setImei2("123");

        return body;
    }

    private String deviceId() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            assert telephonyManager != null;
            return telephonyManager.getDeviceId();
        }
        return "null";
    }

    private String getIdCategoryMerchant(String label){
        RealmResults results = crud.read("merchantCategory", label);
        CategoryModel cmm = (CategoryModel) results.get(0);
        assert cmm != null;
        return cmm.getIdMerchantCategory();
    }
    private void do_register() throws Exception {
        String returns = adapterModel.doRegister(prepare_body(), getString(R.string.caption_register_success), getString(R.string.caption_register_failed));
        if (returns.equalsIgnoreCase(getString(R.string.caption_register_success))){
            showToast(returns);
            finish();
        }else{
            showToast(returns);
        }
    }
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void fetch_data_kategori(){
        ArrayList<String> list = new ArrayList();

        RealmResults resultKategori = crud.read();
        if (resultKategori.size() > 0){
            for (int i=0;i<resultKategori.size();i++){
                CategoryModel cmm = (CategoryModel) resultKategori.get(i);
                assert cmm != null;
                list.add(cmm.getMerchantCategory());
            }
        }
        adapter = new ArrayAdapter<>(this,
                R.layout.item_spinner_with_padding, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_with_padding);

        kategori.setAdapter(adapter);
    }
    public void showTimePickerDialog(TextView v) {
        DialogFragment newFragment = new TimePicker(v);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
}
