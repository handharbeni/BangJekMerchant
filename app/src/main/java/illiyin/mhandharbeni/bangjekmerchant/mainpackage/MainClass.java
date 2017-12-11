package illiyin.mhandharbeni.bangjekmerchant.mainpackage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.h6ah4i.android.tablayouthelper.TabLayoutHelper;

import illiyin.mhandharbeni.bangjekmerchant.R;
import illiyin.mhandharbeni.bangjekmerchant.accountpackage.SigninClass;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.adapter.TabsPagerAdapter;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.FragmentMenu;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.FragmentProfile;
import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.model.CategoryMenuModel;
import illiyin.mhandharbeni.databasemodule.model.CategoryModel;
import illiyin.mhandharbeni.databasemodule.model.MenuMerchantModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.servicemodule.ServiceAdapter;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;

public class MainClass extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener, SessionListener {
    private static final String TAG = "MainClass";
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabLayoutHelper mTabLayoutHelper;

    private Session session;
    private AdapterModel adapterModel;

    private TextView txtNamaMerchant, txtAlamatMerchant, txtDeskripsiMerchant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetch_module();
        fetch_toolbar();
        init_view();
        fill_information_merchant();
    }
    public void init_view(){
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        txtNamaMerchant = (TextView) findViewById(R.id.txtNamaMerchant);
        txtAlamatMerchant = (TextView) findViewById(R.id.txtAlamatMerchant);
        txtDeskripsiMerchant = (TextView) findViewById(R.id.txtDeskripsiMerchant);

        viewPager.setAdapter(buildAdapter());
        mTabLayoutHelper = new TabLayoutHelper(tabLayout, viewPager);
        mTabLayoutHelper.setAutoAdjustTabModeEnabled(true);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorTabInActive), getResources().getColor(R.color.colorTabActive));
        tabLayout.addOnTabSelectedListener(this);
    }
    private PagerAdapter buildAdapter(){
        return new TabsPagerAdapter(getSupportFragmentManager());
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_class, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Add Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
        } else if (id == R.id.nav_help) {
        } else if (id == R.id.nav_logout){
            session.deleteSession();
            delete_all_realm();

            startActivity(new Intent(MainClass.this, SigninClass.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void delete_all_realm(){
        MenuMerchantModel mcm = new MenuMerchantModel();
        Crud crudMcm = new Crud(this, mcm);

        crudMcm.deleteAll(mcm.getClass());
        crudMcm.closeRealm();
    }
    private void fetch_toolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void fetch_module(){
        session = new Session(this, this);
        adapterModel = new AdapterModel(this);
        ServiceAdapter serviceAdapter = new ServiceAdapter(this);
        serviceAdapter.startService();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FragmentMenu.requestCode){
            changeTab(1);
        }else if (requestCode == FragmentProfile.requestCode){
            changeTab(0);
        }
    }
    private void changeTab(final int position){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tabLayout.getTabAt(position).select();
            }
        }, 100);
    }
    private void fill_information_merchant(){
        String nama = session.getCustomParams(Session.NAMA, "nothing");
        String alamat = session.getCustomParams(Session.ALAMAT, "nothing");
        String deskripsi = session.getCustomParams(Session.EMAIL, "nothing");
        txtNamaMerchant.setText(nama);
        txtAlamatMerchant.setText(alamat);
        txtDeskripsiMerchant.setText(deskripsi);
    }

    @Override
    public void sessionChange() {
        fill_information_merchant();
    }
}
