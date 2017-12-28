package illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.mainfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import illiyin.mhandharbeni.bangjekmerchant.R;

/**
 * Created by faizalqurni on 12/19/17.
 */

public class FragmentHelp extends Fragment {
    private View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_bantuan, container, false);
        return v;
    }
}
