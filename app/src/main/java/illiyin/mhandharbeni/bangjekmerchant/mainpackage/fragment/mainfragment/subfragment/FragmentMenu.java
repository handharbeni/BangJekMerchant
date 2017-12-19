package illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.mainfragment.subfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import illiyin.mhandharbeni.bangjekmerchant.R;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment.adapter.AdapterMenu;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.listen.RvClick;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.subactivity.DetailMenu;
import illiyin.mhandharbeni.bangjekmerchant.mainpackage.subactivity.UpdateMenu;
import illiyin.mhandharbeni.databasemodule.model.MenuMerchantModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import io.realm.RealmResults;

/**
 * Created by root on 12/5/17.
 */

public class FragmentMenu extends Fragment implements RvClick{
    private MenuMerchantModel menuMerchantModel;
    private Crud crud;
    private AdapterMenu adapterMenu;
    private RealmRecyclerView rvMenu;

    public static Integer requestCode = 123;
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
        fetch_adapter();
    }

    private void fetch_components(){
        btnAddMenu = v.findViewById(R.id.btnAddMenu);
        rvMenu = v.findViewById(R.id.rvMenu);
    }
    private void fetch_modules(){
        menuMerchantModel = new MenuMerchantModel();
        crud = new Crud(getActivity().getApplicationContext(), menuMerchantModel);
    }
    private void fetch_click(){
        btnAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), DetailMenu.class);
                getActivity().startActivityForResult(i, requestCode);
            }
        });
    }
    private void fetch_adapter(){
        RealmResults resultMenu = crud.read();
        adapterMenu = new AdapterMenu(getActivity().getApplicationContext(), resultMenu, true, this);
        rvMenu.setAdapter(adapterMenu);
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onClick(Integer ID) {
        Bundle bundle = new Bundle();
        bundle.putString("idMenu", String.valueOf(ID));

        Intent i = new Intent(getActivity().getApplicationContext(), UpdateMenu.class);
        i.putExtras(bundle);

        getActivity().startActivityForResult(i, requestCode);
    }
}