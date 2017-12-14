package illiyin.mhandharbeni.bangjekmerchant.mainpackage.subactivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

/**
 * Created by faizalqurni on 12/6/17.
 */

public class UpdateMenu extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    private static final String TAG = "UPDATE MENU";
    private AdapterModel adapterModel;
    private Session session;

    private Crud crud;
    private MenuMerchantModel menuMerchantModel;

    private Crud crudKategoriMenu;
    private CategoryMenuModel categoryMenuModel;

    private TextView txtNamaMenu, txtHargaMenu, txtDeskripsiMenu, txtDeleteMenu, txtDiscountMenu, txtDiscountVariantMenu;
    private MaterialBetterSpinner txtKategori;
    private CircleImageView images;
    private Button btnSaveMenu;

    private String id;

    private ArrayAdapter<String> adapter;
    private String imagePath;
    private String imageCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetch_extras();
        fetch_modules();
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
        txtDiscountVariantMenu = (TextView) findViewById(R.id.txtDiscountVariantMenu);
        txtDeleteMenu = (TextView) findViewById(R.id.txtDeleteMenu);
        txtKategori = (MaterialBetterSpinner) findViewById(R.id.txtKategori);
        btnSaveMenu = (Button) findViewById(R.id.btnSaveMenu);
    }
    private void fetch_modules(){
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        adapterModel = new AdapterModel(this);
        session = new Session(this, new SessionListener() {
            @Override
            public void sessionChange() {

            }
        });

        menuMerchantModel = new MenuMerchantModel();
        crud = new Crud(this, menuMerchantModel);

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
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        txtKategori.setAdapter(adapter);
    }
    private void fetch_data_menu(){
        RealmResults resultMenu = crud.read("idMerchantMenu", id);
        if (resultMenu.size() > 0){
            MenuMerchantModel mm = (MenuMerchantModel) resultMenu.get(0);
            Glide.with(this).load(mm.getPhoto()).into(images);
            imageCurrent = mm.getPhoto();
            txtNamaMenu.setText(mm.getMerchantMenu());
            txtHargaMenu.setText(mm.getPrice());
            txtDeskripsiMenu.setText(mm.getDescription());
            txtKategori.setText(mm.getIdMerchantMenuCategory());
            txtDiscountMenu.setText(mm.getDiscount());
            txtDiscountVariantMenu.setText(mm.getDiscountVariant());

            RealmResults result = crudKategoriMenu.read("idMerchantMenuCategory", mm.getIdMerchantMenuCategory());
            CategoryMenuModel kategori = (CategoryMenuModel) result.get(0);


            select_kategori(kategori.getMerchantMenuCategory());
        }
    }
    private void select_kategori(final String label){
        new Runnable() {
            @Override
            public void run() {
                int position = adapter.getPosition(label);
                txtKategori.setSelection(position);
                txtKategori.setText(label);
            }
        };
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
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                final Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_PICK);

                // Chooser of file system options.
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
    private void showDialogDelete(String message, final String captionSucces, final String captionFailed){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(captionSucces, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    do_delete();
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
        BodyUpdateMenu bodyUpdateMenu = new BodyUpdateMenu();
        bodyUpdateMenu.setId_merchant_menu(id);
        bodyUpdateMenu.setKey(session.getToken());
        bodyUpdateMenu.setMerchant_menu(txtNamaMenu.getText().toString());
        bodyUpdateMenu.setPrice(txtHargaMenu.getText().toString());
        bodyUpdateMenu.setDescription(txtDeskripsiMenu.getText().toString());
        bodyUpdateMenu.setId_merchant_menu_category(String.valueOf(adapter.getPosition(txtKategori.getText().toString())));
        bodyUpdateMenu.setPhoto(session.getCustomParams("ImageCurrent", "https://images.google.com/images/branding/googleg/1x/googleg_standard_color_128dp.png"));
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

    private void do_delete() throws IOException {
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
//                try {
//                    do_upload();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
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
            session.setCustomParams("ImageCurrent", imageCurrent);
            Glide.with(this).load(imageCurrent).into(images);
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
