package illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.h6ah4i.android.tablayouthelper.TabLayoutHelper;

import de.hdodenhof.circleimageview.CircleImageView;
import illiyin.mhandharbeni.bangjekmerchant.R;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.adapter.TabsPagerAdapter;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.mainfragment.subfragment.FragmentMenu;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.mainfragment.subfragment.FragmentProfile;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.subactivity.DetailMenu;
import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.sessionlibrary.Session;

/**
 * Created by faizalqurni on 12/19/17.
 */

public class FragmentHome extends Fragment implements TabLayout.OnTabSelectedListener {
    private RelativeLayout rlHeader;
    private View v;
    private String imagePath;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabLayoutHelper mTabLayoutHelper;

    private Session session;
    private AdapterModel adapterModel;
    private NavigationView navigationView;

    private TextView txtNamaMerchant, txtAlamatMerchant, txtDeskripsiMerchant, emailMerchant;
    private CircleImageView image,imageHeader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_home, container, false);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        init_view();
    }
    public void init_view(){
        viewPager = v.findViewById(R.id.pager);
        tabLayout = v.findViewById(R.id.tabLayout);

        txtNamaMerchant = v.findViewById(R.id.txtNamaMerchant);
        txtAlamatMerchant = v.findViewById(R.id.txtAlamatMerchant);
        txtDeskripsiMerchant = v.findViewById(R.id.txtDeskripsiMerchant);

        viewPager.setAdapter(buildAdapter());
        mTabLayoutHelper = new TabLayoutHelper(tabLayout, viewPager);
        mTabLayoutHelper.setAutoAdjustTabModeEnabled(true);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorTabInActive), getResources().getColor(R.color.colorTabActive));
        tabLayout.addOnTabSelectedListener(this);
//        rlHeader = v.findViewById(R.id.rlHeader);
//        rlHeader.setVisibility(View.VISIBLE);
    }
    private PagerAdapter buildAdapter(){
        return new TabsPagerAdapter(getChildFragmentManager());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            int indexTabActive = tabLayout.getSelectedTabPosition();
            int requestCode = FragmentProfile.requestCode;
            if (indexTabActive == 1){
                requestCode = FragmentMenu.requestCode;
            }
            Intent i = new Intent(getActivity().getApplicationContext(), DetailMenu.class);
            startActivityForResult(i, requestCode);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
    private void showSnackBar(String message){
//        new SnackBar(getApplicationContext()).view(this).message(message).build();
    }
}
