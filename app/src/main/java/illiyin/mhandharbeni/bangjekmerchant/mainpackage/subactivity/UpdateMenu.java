package illiyin.mhandharbeni.bangjekmerchant.mainpackage.subactivity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import illiyin.mhandharbeni.bangjekmerchant.R;
import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.model.CategoryMenuModel;
import illiyin.mhandharbeni.databasemodule.model.MenuMerchantModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import io.realm.RealmResults;

/**
 * Created by faizalqurni on 12/6/17.
 */

public class UpdateMenu extends AppCompatActivity {
    private AdapterModel adapterModel;

    private Crud crud;
    private MenuMerchantModel menuMerchantModel;

    private Crud crudKategoriMenu;
    private CategoryMenuModel categoryMenuModel;

    private TextView txtNamaMenu, txtHargaMenu, txtDeskripsiMenu, txtDeleteMenu;
    private Spinner txtKategori;
    private Button btnSaveMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_menu);
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
        txtDeleteMenu = (TextView) findViewById(R.id.txtDeleteMenu);
        txtKategori = (Spinner) findViewById(R.id.txtKategori);
        btnSaveMenu = (Button) findViewById(R.id.btnSaveMenu);
    }
    private void fetch_modules(){
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,list);

        txtKategori.setAdapter(adapter);
    }
    private void fetch_click(){

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Do your actions here
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }


}
