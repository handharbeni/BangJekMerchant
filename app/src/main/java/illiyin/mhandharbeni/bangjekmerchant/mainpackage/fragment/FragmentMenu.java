package illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import illiyin.mhandharbeni.bangjekmerchant.R;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.subactivity.DetailMenu;

/**
 * Created by root on 12/5/17.
 */

public class FragmentMenu extends Fragment {
    View v;
    private Button btnAddMenu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_fragment_menu, container, false);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        fetch_modules();
        fetch_components();
        fetch_click();
    }

    private void fetch_components(){
        btnAddMenu = v.findViewById(R.id.btnAddMenu);
    }
    private void fetch_modules(){

    }
    private void fetch_click(){
        btnAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), DetailMenu.class);
                getActivity().startActivityForResult(i, 122);
            }
        });
    }
}