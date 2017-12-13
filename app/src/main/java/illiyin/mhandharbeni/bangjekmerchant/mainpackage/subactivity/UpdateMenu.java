package illiyin.mhandharbeni.bangjekmerchant.mainpackage.subactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import illiyin.mhandharbeni.bangjekmerchant.R;
import illiyin.mhandharbeni.databasemodule.AdapterModel;

/**
 * Created by faizalqurni on 12/6/17.
 */

public class UpdateMenu extends AppCompatActivity {
    private AdapterModel adapterModel;

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
    }

    private void fetch_components(){

    }
    private void fetch_modules(){
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    private void fetch_click(){

    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
