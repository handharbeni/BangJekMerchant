package illiyin.mhandharbeni.bangjekmerchant.mainpackage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import illiyin.mhandharbeni.bangjekmerchant.R;
import illiyin.mhandharbeni.bangjekmerchant.accountpackage.SigninClass;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.mainfragment.FragmentHelp;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.mainfragment.FragmentHome;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.mainfragment.subfragment.FragmentMenu;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.mainfragment.subfragment.FragmentProfile;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.subactivity.DetailMenu;
import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.model.MenuMerchantModel;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyUpdateMerchant;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.servicemodule.ServiceAdapter;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainClass extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener, SessionListener {
    private String imagePath;

    private Session session;
    private AdapterModel adapterModel;
    private NavigationView navigationView;

    private TextView txtNamaMerchant, txtAlamatMerchant, txtDeskripsiMerchant, emailMerchant;
    private CircleImageView image,imageHeader;
    private ProgressDialog progressDialog;
    private ToggleButton toggle;

    private static String CURRENT_FRAGMENT = "CURRENT_FRAGMENT";
    private static String FRAGMENT_HOME = "HOME";
    private static String FRAGMENT_HELP = "HELP";

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
        fill_information_header();
        requestPermission();
    }
    public void init_view(){
        toggle = (ToggleButton) findViewById(R.id.toggleStatus);
        txtNamaMerchant = (TextView) findViewById(R.id.txtNamaMerchant);
        txtAlamatMerchant = (TextView) findViewById(R.id.txtAlamatMerchant);
        txtDeskripsiMerchant = (TextView) findViewById(R.id.txtDeskripsiMerchant);

        image = (CircleImageView) findViewById(R.id.images);
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
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    do_save("open_status", "Buka");
                } else {
                    // The toggle is disabled
                    do_save("open_status", "Tutup");
                }
            }
        });
        changeFragment(new FragmentHome(), FRAGMENT_HOME);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Fragment fragment = new FragmentHome();
        if (session.getCustomParams(CURRENT_FRAGMENT, FRAGMENT_HOME).equalsIgnoreCase(FRAGMENT_HELP)){
            fragment = new FragmentHelp();
        }
        changeFragment(fragment, session.getCustomParams(CURRENT_FRAGMENT, FRAGMENT_HOME));
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
            Intent i = new Intent(getApplicationContext(), DetailMenu.class);
            startActivityForResult(i, FragmentMenu.requestCode);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            collapseActionBar(true);

            changeFragment(new FragmentHome(), FRAGMENT_HOME);
        } else if (id == R.id.nav_help) {
            collapseActionBar(false);

            changeFragment(new FragmentHelp(), FRAGMENT_HELP);
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
    private void collapseActionBar(Boolean expanded){
        AppBarLayout appBarLayout =  (AppBarLayout) findViewById(R.id.appbar_layout);
        appBarLayout.setExpanded(expanded);
    }
    private void changeFragment(Fragment fragment, String title){
        session.setCustomParams(CURRENT_FRAGMENT, title);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrameLayout, fragment);
        ft.commit();
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

        navigationView = (NavigationView) findViewById(R.id.nav_view);
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
    private void showProgress(){
        progressDialog = ProgressDialog.show(this, "UPLOAD IMAGE", "UPLOADING", true);
    }
    private void hideProgress(){
        progressDialog.dismiss();
    }
    private void dialogChangeImage(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    do_upload();
                } catch (IOException e) {
                    e.printStackTrace();
                    showSnackBar("Upload Image Gagal");
                }

            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                hideProgress();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == 1010) {
            showProgress();
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
                dialogChangeImage("Ganti Image Profile?");
            }
        }
    }
    private void fill_information_merchant(){
        String nama = session.getCustomParams(Session.NAMA, "nothing");
        String alamat = session.getCustomParams(Session.ALAMAT, "nothing");
        String deskripsi = session.getCustomParams(Session.EMAIL, "nothing");
        String photo = session.getCustomParams(Session.IMAGE, "nothing");
        String status = session.getCustomParams(Session.STATUS, "Tutup");
        txtNamaMerchant.setText(nama);
        txtAlamatMerchant.setText(alamat);
        txtDeskripsiMerchant.setText(deskripsi);
        Boolean checked = false;
        if (status.equalsIgnoreCase("Buka")){
            checked = true;
        }
        toggle.setChecked(checked);
        try {
            Glide.with(MainClass.this).load(photo).thumbnail(0.1f).into(image);
        }catch (IllegalStateException | IllegalArgumentException e){

        }

    }
    private void fill_information_header(){
        View headerView = navigationView.getHeaderView(0);
        emailMerchant = headerView.findViewById(R.id.emailMerchant);
        imageHeader = headerView.findViewById(R.id.images);
        String email = session.getCustomParams(Session.EMAIL, "nothing");
        String photo = session.getCustomParams(Session.IMAGE, "nothing");
        emailMerchant.setText(email);
        try {
            Glide.with(MainClass.this).load(photo).thumbnail(0.1f).into(imageHeader);
        }catch (IllegalStateException | IllegalArgumentException e){

        }
    }

    @Override
    public void sessionChange() {
        fill_information_merchant();
        fill_information_header();
    }
    private void requestPermission(){
        String[] permissions = new String[11];
        permissions[0] = Manifest.permission.CAMERA;
        permissions[1] = Manifest.permission.INTERNET;
        permissions[2] = Manifest.permission.WAKE_LOCK;
        permissions[3] = Manifest.permission.LOCATION_HARDWARE;
        permissions[4] = Manifest.permission.ACCESS_COARSE_LOCATION;
        permissions[5] = Manifest.permission.ACCESS_FINE_LOCATION;
        permissions[6] = Manifest.permission.READ_PHONE_STATE;
        permissions[7] = Manifest.permission.ACCESS_NETWORK_STATE;
        permissions[8] = Manifest.permission.ACCESS_WIFI_STATE;
        permissions[9] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        permissions[10] = Manifest.permission.READ_EXTERNAL_STORAGE;
        ActivityCompat.requestPermissions(
                this,
                permissions,
                5
        );
    }
    private void do_save(String field, String value){
        try {
            BodyUpdateMerchant bodyRegisterModel = new BodyUpdateMerchant();
            bodyRegisterModel.setField(field);
            bodyRegisterModel.setKey(session.getToken());
            bodyRegisterModel.setValue(value);
            String returns = adapterModel.updateMerchant(bodyRegisterModel, getString(R.string.caption_update_profile_success), getString(R.string.caption_update_profile_failed));
            if (returns.equalsIgnoreCase(getString(R.string.caption_update_profile_success))){
                showSnackBar(getString(R.string.caption_update_profile_success));
            }else{
                showSnackBar(getString(R.string.caption_update_profile_failed));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void showSnackBar(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void do_upload() throws IOException {
        File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("userfile", file.getName(), requestFile);
        MultipartBody.Part key = MultipartBody.Part.createFormData("key", session.getToken());
        String returns = adapterModel.uploadImage(body, key, getString(R.string.caption_upload_success), getString(R.string.caption_upload_failed));
        if (returns.equalsIgnoreCase(getString(R.string.caption_upload_success))){
            String locationFile = getString(illiyin.mhandharbeni.databasemodule.R.string.module_server)+"/uploads/"+file.getName();
            Glide.with(this).load(locationFile).into(image);
            do_save("photo", locationFile);
        }else{
            showSnackBar("Upload Image Gagal");
        }
        hideProgress();
    }
}
