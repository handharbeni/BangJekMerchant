package illiyin.mhandharbeni.bangjekmerchant.mainpackage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.FragmentMenu;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.FragmentProfile;

/**
 * Created by root on 12/5/17.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new FragmentProfile();
            case 1:
                return new FragmentMenu();
        }

        return new FragmentProfile();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "PROFIL";
        }
        else if (position == 1)
        {
            title = "MENU";
        }
        return title;
    }
}