package illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.h6ah4i.android.tablayouthelper.TabLayoutHelper;

import de.hdodenhof.circleimageview.CircleImageView;
import illiyin.mhandharbeni.bangjekmerchant.R;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.adapter.TabsPagerAdapter;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.mainfragment.subfragment.FragmentMenu;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.mainfragment.subfragment.FragmentProfile;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.subactivity.DetailMenu;
import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;

/**
 * Created by faizalqurni on 12/19/17.
 */

public class  FragmentHome extends Fragment implements TabLayout.OnTabSelectedListener {
    private View v;

    private TabLayout tabLayout;

    private Session session;

    private static String TABACTIVE = "TABACTIVE";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_home, container, false);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        fetch_module();
        init_view();
        onResume();
    }
    private void fetch_module(){
        session = new Session(getActivity().getApplicationContext(), new SessionListener() {
            @Override
            public void sessionChange() {

            }
        });
    }
    public void init_view(){
        ViewPager viewPager = v.findViewById(R.id.pager);
        tabLayout = v.findViewById(R.id.tabLayout);

        viewPager.setAdapter(buildAdapter());
        TabLayoutHelper mTabLayoutHelper = new TabLayoutHelper(tabLayout, viewPager);
        mTabLayoutHelper.setAutoAdjustTabModeEnabled(true);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorTabInActive), getResources().getColor(R.color.colorTabActive));
        tabLayout.addOnTabSelectedListener(this);
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
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
//        onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FragmentMenu.requestCode){
            changeTab(1);
        }else if (requestCode == FragmentProfile.requestCode){
            changeTab(0);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        session.setCustomParams(TABACTIVE, tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    @Override
    public void onResume() {
        super.onResume();
//        viewPager.setCurrentItem(session.getCustomParams(TABACTIVE, 0), false);
//        viewPager.setCurrentItem(session.getCustomParams(TABACTIVE, 0));
        changeTab(session.getCustomParams(TABACTIVE, 0));
    }
    private void changeTab(final int position){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TabLayout.Tab tab = tabLayout.getTabAt(position);
                tab.select();
            }
        }, 100);
    }
    private void showSnackBar(String message){
//        new SnackBar(getApplicationContext()).view(this).message(message).build();
    }
}
