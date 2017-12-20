package illiyin.mhandharbeni.bangjekmerchant.mainpackage.subactivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import illiyin.mhandharbeni.bangjekmerchant.R;
import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.model.CategoryMenuModel;
import illiyin.mhandharbeni.databasemodule.model.MenuMerchantModel;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyCreateMenu;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyUpdateMenu;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import io.realm.RealmResults;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by faizalqurni on 12/6/17.
 */

public class DetailMenu extends AppCompatActivity {
    private static String IMAGE_CURRENT = "ImageCurrent";

    private AdapterModel adapterModel;
    private Session session;

    private Crud crudKategoriMenu;
    private CategoryMenuModel categoryMenuModel;

    private TextView txtNamaMenu, txtHargaMenu, txtDeskripsiMenu, txtDeleteMenu, txtDiscountMenu;
    private MaterialBetterSpinner txtKategori, txtDiscountVariantMenu;
    private CircleImageView images;

    private Button btnSaveMenu;
    private ArrayAdapter<String> adapter;

    private String imagePath;
    private String imageCurrent;
    private ArrayAdapter<String> adapterDiscountVariant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_form_menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetch_modules();
        fetch_components();
        fetch_click();

        fetch_data_kategori();
    }

    private void fetch_components(){
        images = (CircleImageView) findViewById(R.id.images);

        txtNamaMenu = (TextView) findViewById(R.id.txtNamaMenu);
        txtHargaMenu = (TextView) findViewById(R.id.txtHargaMenu);
        txtDeskripsiMenu = (TextView) findViewById(R.id.txtDeskripsiMenu);
        txtDiscountMenu = (TextView) findViewById(R.id.txtDiscountMenu);
        txtDiscountVariantMenu = (MaterialBetterSpinner) findViewById(R.id.txtDiscountVariantMenu);
        txtDeleteMenu = (TextView) findViewById(R.id.txtDeleteMenu);
        txtKategori = (MaterialBetterSpinner) findViewById(R.id.txtKategori);
        btnSaveMenu = (Button) findViewById(R.id.btnSaveMenu);
    }
    private void fetch_modules(){
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("TAMBAH MENU");

        adapterModel = new AdapterModel(this);
        session = new Session(this, new SessionListener() {
            @Override
            public void sessionChange() {

            }
        });

        categoryMenuModel = new CategoryMenuModel();
        crudKategoriMenu = new Crud(this, categoryMenuModel);
    }
    private void fetch_data_kategori(){
        ArrayList<String> list = new ArrayList();
        RealmResults resultKategori = crudKategoriMenu.read();
        if (resultKategori.size() > 0){
            for (int i=0;i<resultKategori.size();i++){
                CategoryMenuModel cmm = (CategoryMenuModel) resultKategori.get(i);
                list.add(cmm.getMerchantMenuCategory());
            }
        }
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);

        txtKategori.setAdapter(adapter);
        ArrayList<String> listDiscountVariant = new ArrayList<>();
        listDiscountVariant.add("%");
        listDiscountVariant.add("Cash");

        adapterDiscountVariant = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listDiscountVariant);
        txtDiscountVariantMenu.setAdapter(adapterDiscountVariant);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Do your actions here
        onBackPressed();
        return true;
    }
    private void showDialogSave(String message, final String captionSucces, final String captionFailed){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(captionSucces, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    do_save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton(captionFailed, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void do_save() throws IOException {

        BodyCreateMenu bodyCreateMenu = new BodyCreateMenu();
        bodyCreateMenu.setKey(session.getToken());
        bodyCreateMenu.setMerchant_menu(txtNamaMenu.getText().toString());
        bodyCreateMenu.setPrice(txtHargaMenu.getText().toString());
        bodyCreateMenu.setDescription(txtDeskripsiMenu.getText().toString());
        bodyCreateMenu.setId_merchant_menu_category(getIdCategoryMerchant(txtKategori.getText().toString()));
        bodyCreateMenu.setPhoto(session.getCustomParams(IMAGE_CURRENT, imageCurrent));
        bodyCreateMenu.setDiscount(txtDiscountMenu.getText().toString());
        bodyCreateMenu.setDiscount_variant(txtDiscountVariantMenu.getText().toString());
        bodyCreateMenu.setStatus("");

        String returns = adapterModel.createMenu(bodyCreateMenu, getString(R.string.caption_update_menu_success), getString(R.string.caption_update_menu_failed));
        if (returns.equalsIgnoreCase(getString(R.string.caption_update_menu_success))){
            showToast("Menu Berhasil Di Tambah");
            onBackPressed();
        }else{
            showToast("Menu Gagal Di Tambah");
        }
    }
    private void fetch_click(){
        btnSaveMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSave("Tambah Menu Baru?", "YA", "BATAL");
            }
        });
        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_PICK);

                final Intent chooserIntent = Intent.createChooser(galleryIntent, "Pilih Image");
                startActivityForResult(chooserIntent, 1010);
            }
        });
    }
    private String getIdCategoryMerchant(String label){
        RealmResults results = crudKategoriMenu.read("merchantMenuCategory", label);
        CategoryMenuModel cmm = (CategoryMenuModel) results.get(0);
        return cmm.getIdMerchantMenuCategory();
    }
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK && requestCode == 1010) {
            //TODO: action
            if (data == null) {
                //Display an error
                return;
            }
            Uri selectedImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);

                Glide.with(this).load(new File(imagePath))
                        .into(images);
                try {
                    do_upload();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void do_upload() throws IOException {
        File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("userfile", file.getName(), requestFile);
        MultipartBody.Part key = MultipartBody.Part.createFormData("key", session.getToken());
        String returns = adapterModel.uploadImage(body, key, getString(R.string.caption_upload_success), getString(R.string.caption_upload_failed));
        if (returns.equalsIgnoreCase(getString(R.string.caption_upload_success))){
            String locationFile = getString(illiyin.mhandharbeni.databasemodule.R.string.module_server)+"/uploads/"+file.getName();
            imageCurrent = locationFile;
            session.setCustomParams(IMAGE_CURRENT, locationFile);
            Glide.with(this).load(locationFile).into(images);
        }
    }
}
