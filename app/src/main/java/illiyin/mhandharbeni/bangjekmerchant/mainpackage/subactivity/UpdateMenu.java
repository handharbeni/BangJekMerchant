package illiyin.mhandharbeni.bangjekmerchant.mainpackage.subactivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
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
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import illiyin.mhandharbeni.bangjekmerchant.R;
import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.model.CategoryMenuModel;
import illiyin.mhandharbeni.databasemodule.model.MenuMerchantModel;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyDeleteMenu;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyUpdateMenu;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import io.realm.RealmResults;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;

/**
 * Created by faizalqurni on 12/6/17.
 */

public class UpdateMenu extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    private static final String TAG = "UPDATE MENU";
    private static String FROM_RESULT = "FromResult";
    private static String IMAGE_CURRENT = "ImageCurrent";
    private AdapterModel adapterModel;
    private Session session;

    private Crud crud;
    private MenuMerchantModel menuMerchantModel;

    private Crud crudKategoriMenu;
    private CategoryMenuModel categoryMenuModel;

    private TextView txtNamaMenu, txtHargaMenu, txtDeskripsiMenu, txtDeleteMenu, txtDiscountMenu;
    private MaterialBetterSpinner txtKategori, txtDiscountVariantMenu;
    private CircleImageView images;
    private Button btnSaveMenu;

    private String id;

    private ArrayAdapter<String> adapter;
    private String imagePath;
    private String imageCurrent;

    private ArrayAdapter<String> adapterDiscountVariant;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetch_modules();
        setContentView(R.layout.layout_detail_menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetch_extras();
        fetch_components();
        fetch_click();

        fetch_data_kategori();
        fetch_data_menu();
    }

    private void fetch_extras(){
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("idMenu");
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
        getSupportActionBar().setTitle("DETAIL MENU");

        adapterModel = new AdapterModel(this);
        adapterModel.requestPermission();
        session = new Session(this, new SessionListener() {
            @Override
            public void sessionChange() {

            }
        });

        menuMerchantModel = new MenuMerchantModel();
        crud = new Crud(this, menuMerchantModel);

        categoryMenuModel = new CategoryMenuModel();
        crudKategoriMenu = new Crud(this, categoryMenuModel);

        session.setCustomParams(FROM_RESULT, "FALSE");
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
                R.layout.item_spinner_with_padding, list);
        adapter.setDropDownViewResource(R.layout.item_spinner_with_padding);

        txtKategori.setAdapter(adapter);
        ArrayList<String> listDiscountVariant = new ArrayList<>();
        listDiscountVariant.add("%");
        listDiscountVariant.add("Cash");

        adapterDiscountVariant = new ArrayAdapter<String>(this,
                R.layout.item_spinner_with_padding, listDiscountVariant);
        adapterDiscountVariant.setDropDownViewResource(R.layout.item_spinner_with_padding);
        txtDiscountVariantMenu.setAdapter(adapterDiscountVariant);
    }
    private void fetch_data_menu(){
        RealmResults resultMenu = crud.read("idMerchantMenu", id);
        if (resultMenu.size() > 0){
            MenuMerchantModel mm = (MenuMerchantModel) resultMenu.get(0);
            if (session.getCustomParams(FROM_RESULT, "FALSE").equalsIgnoreCase("FALSE")){
                Glide.with(this).load(mm.getPhoto()).into(images);
            }
            imageCurrent = mm.getPhoto();
            txtNamaMenu.setText(mm.getMerchantMenu());
            txtHargaMenu.setText(mm.getPrice());
            txtDeskripsiMenu.setText(mm.getDescription());
            txtDiscountMenu.setText(mm.getDiscount());
            txtDiscountVariantMenu.setText(mm.getDiscountVariant());

            RealmResults result = crudKategoriMenu.read("idMerchantMenuCategory", mm.getIdMerchantMenuCategory());
            CategoryMenuModel kategori = (CategoryMenuModel) result.get(0);


            select_kategori(kategori.getMerchantMenuCategory());
        }
    }
    private void select_kategori(final String label){
        for(int i=0; i < adapter.getCount(); i++) {
            if(label.trim().equals(adapter.getItem(i))){
                txtKategori.setText(label.trim());
                txtKategori.setSelection(i);
                break;
            }
        }
    }
    private void fetch_click(){
        btnSaveMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSave("Ubah data Menu?", "YA", "BATAL");
            }
        });
        txtDeleteMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDelete("Hapus Menu?", "YA", "BATAL");
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
    private void showDialogSave(String message, final String captionSucces, final String captionFailed){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(captionSucces, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    do_save();
                } catch (Exception e) {
                    Timber.e(e);
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
    private void showDialogDelete(String message, final String captionSucces, final String captionFailed){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(captionSucces, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    do_delete();
                } catch (Exception e) {
                    Timber.e(e);
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

    private void do_save() throws Exception {
        BodyUpdateMenu bodyUpdateMenu = new BodyUpdateMenu();
        bodyUpdateMenu.setId_merchant_menu(id);
        bodyUpdateMenu.setKey(session.getToken());
        bodyUpdateMenu.setMerchant_menu(txtNamaMenu.getText().toString());
        bodyUpdateMenu.setPrice(txtHargaMenu.getText().toString());
        bodyUpdateMenu.setDescription(txtDeskripsiMenu.getText().toString());

        bodyUpdateMenu.setId_merchant_menu_category(getIdCategoryMerchant(txtKategori.getText().toString()));
        bodyUpdateMenu.setPhoto(session.getCustomParams(IMAGE_CURRENT, imageCurrent));
        bodyUpdateMenu.setDiscount(txtDiscountMenu.getText().toString());
        bodyUpdateMenu.setDiscount_variant(txtDiscountVariantMenu.getText().toString());
        bodyUpdateMenu.setStatus("");

        String returns = adapterModel.updateMenu(bodyUpdateMenu, getString(R.string.caption_update_menu_success), getString(R.string.caption_update_menu_failed));
        if (returns.equalsIgnoreCase(getString(R.string.caption_update_menu_success))){
            showToast("Menu Berhasil Di Ubah");
            onBackPressed();
        }else{
            showToast("Menu Gagal Di Ubah");
        }
    }

    private String getIdCategoryMerchant(String label){
        RealmResults results = crudKategoriMenu.read("merchantMenuCategory", label);
        CategoryMenuModel cmm = (CategoryMenuModel) results.get(0);
        return cmm.getIdMerchantMenuCategory();
    }

    private void do_delete() throws Exception {
        BodyDeleteMenu bodyDeleteMenu = new BodyDeleteMenu();
        bodyDeleteMenu.setKey(session.getToken());
        bodyDeleteMenu.setId_merchant_menu(id);

        String returns = adapterModel.deleteMenu(bodyDeleteMenu, getString(R.string.caption_update_menu_success), getString(R.string.caption_update_menu_failed));
        if (returns.equalsIgnoreCase(getString(R.string.caption_update_menu_success))){
            showToast("Menu Berhasil Dihapus");
            onBackPressed();
        }else{
            showToast("Menu Gagal Dihapus");
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        showProgress();

        if (resultCode == Activity.RESULT_OK && requestCode == 1010) {

            //TODO: action
            if (data == null) {
                //Display an error
                hideProgress();
                return;
            }
            Uri selectedImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            assert selectedImageUri != null;
            @SuppressLint("Recycle")
            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();

                session.setCustomParams(FROM_RESULT, "TRUE");

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);

                try {
                    do_upload();
                } catch (Exception e) {
                    Timber.e(e);
                    Toast.makeText(this, "Upload Image Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        }

        hideProgress();
    }

    private void showProgress(){
        progressDialog = ProgressDialog.show(this, "UPLOAD IMAGE", "UPLOADING", true);
    }
    private void hideProgress(){
        progressDialog.dismiss();
    }

    private void do_upload() throws Exception {
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
        }else{
            Toast.makeText(this, "Upload Image Gagal", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        finish();
    }


}
