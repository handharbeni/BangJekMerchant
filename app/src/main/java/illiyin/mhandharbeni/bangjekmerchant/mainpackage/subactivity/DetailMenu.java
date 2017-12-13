package illiyin.mhandharbeni.bangjekmerchant.mainpackage.subactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import illiyin.mhandharbeni.bangjekmerchant.R;
import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.model.CategoryMenuModel;
import illiyin.mhandharbeni.realmlibrary.Crud;

/**
 * Created by faizalqurni on 12/6/17.
 */

public class DetailMenu extends AppCompatActivity {
    private AdapterModel adapterModel;

    private Crud crudKategoriMenu;
    private CategoryMenuModel categoryMenuModel;


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
    }

    private void fetch_components(){

    }
    private void fetch_modules(){
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        categoryMenuModel = new CategoryMenuModel();
        crudKategoriMenu = new Crud(this, categoryMenuModel);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Do your actions here
        onBackPressed();
        return true;
    }

    private void fetch_click(){

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
