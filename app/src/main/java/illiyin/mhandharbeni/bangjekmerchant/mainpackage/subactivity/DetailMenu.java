package illiyin.mhandharbeni.bangjekmerchant.mainpackage.subactivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.IOException;
import java.util.ArrayList;

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

/**
 * Created by faizalqurni on 12/6/17.
 */

public class DetailMenu extends AppCompatActivity {
    private AdapterModel adapterModel;
    private Session session;

    private Crud crudKategoriMenu;
    private CategoryMenuModel categoryMenuModel;

    private TextView txtNamaMenu, txtHargaMenu, txtDeskripsiMenu, txtDeleteMenu, txtDiscountMenu, txtDiscountVariantMenu;
    private MaterialBetterSpinner txtKategori;
    private Button btnSaveMenu;
    private ArrayAdapter<String> adapter;

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
        bodyCreateMenu.setId_merchant_menu_category(String.valueOf(adapter.getPosition(txtKategori.getText().toString())));
        bodyCreateMenu.setPhoto("");
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
    }
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
