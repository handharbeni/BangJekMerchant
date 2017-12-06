package illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import illiyin.mhandharbeni.bangjekmerchant.R;

/**
 * Created by root on 12/5/17.
 */

public class FragmentProfile extends Fragment {
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_fragment_profile, container, false);
        return v;
    }
}
