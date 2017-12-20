package illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.mainfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import illiyin.mhandharbeni.bangjekmerchant.R;

/**
 * Created by faizalqurni on 12/19/17.
 */

public class FragmentHelp extends Fragment {
    private View v;
    private RelativeLayout rlHeader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_bantuan, container, false);
//        rlHeader = v.findViewById(R.id.rlHeader);
//        rlHeader.setVisibility(View.GONE);

        return v;
    }
}
