package illiyin.mhandharbeni.bangjekmerchant.mainpackage.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import illiyin.mhandharbeni.bangjekmerchant.R;
import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyUpdateMerchant;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.utilslibrary.SnackBar;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 12/5/17.
 */

public class FragmentProfile extends Fragment implements View.OnFocusChangeListener {
    public static Integer requestCode = 122;
    View v;

    private Session session;
    private AdapterModel adapterModel;

    private TextView txtNamaUsaha, txtAlamat, txtEmail, txtNoTelp, txtDeskripsi, txtJamBuka, txtJamTutup;
    private Button btnRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_fragment_profile, container, false);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        fetch_module();
        fetch_component();
        fetch_click();
        fetch_info_merchant();
        fetch_onchange();
    }

    private void fetch_module(){
        session = new Session(getActivity().getApplicationContext(), new SessionListener() {
            @Override
            public void sessionChange() {

            }
        });
        adapterModel = new AdapterModel(getActivity().getApplicationContext());
    }
    private void fetch_component(){
        txtNamaUsaha = v.findViewById(R.id.txtNamaUsaha);
        txtAlamat = v.findViewById(R.id.txtAlamat);
        txtEmail = v.findViewById(R.id.txtEmail);
        txtNoTelp = v.findViewById(R.id.txtNoTelp);
        txtDeskripsi = v.findViewById(R.id.txtDeskripsi);
        txtJamBuka = v.findViewById(R.id.txtJamBuka);
        txtJamTutup = v.findViewById(R.id.txtJamTutup);

        btnRegister = v.findViewById(R.id.btnRegister);
        btnRegister.setText(getString(R.string.placeholder_simpan_profile));
    }
    private void fetch_click(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void fetch_info_merchant(){
        if (!session.getToken().equalsIgnoreCase("nohting")){
            txtNamaUsaha.setText(session.getCustomParams(Session.NAMA, "Not Available"));
            txtAlamat.setText(session.getCustomParams(Session.ALAMAT, "Not Available"));
            txtEmail.setText(session.getCustomParams(Session.EMAIL, "Not Available"));
            txtNoTelp.setText(session.getCustomParams(Session.NOTELP, "Not Available"));
//            txtDeskripsi.setText(session.getCustomParams(Session.NAMA, "Not Available"));
//            txtJamBuka.setText(session.getCustomParams(Session.NAMA, "Not Available"));
//            txtJamTutup.setText(session.getCustomParams(Session.NAMA, "Not Available"));

        }
    }
    private void fetch_onchange(){
        txtNamaUsaha.setOnFocusChangeListener(this);
        txtAlamat.setOnFocusChangeListener(this);
        txtEmail.setOnFocusChangeListener(this);
        txtNoTelp.setOnFocusChangeListener(this);
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
        new SnackBar(getActivity().getApplicationContext()).view(v).message(message).build();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (!b){
            if (view.getId() == R.id.txtNamaUsaha){
//                do_save();
            }else if (view.getId() == R.id.txtAlamat){
                Log.d(TAG, "onFocusChange: ALAMAT UNFOCUSED");
            }else if (view.getId() == R.id.txtEmail){
                Log.d(TAG, "onFocusChange: EMAIL UNFOCUSED");
            }else if (view.getId() == R.id.txtNoTelp){
                Log.d(TAG, "onFocusChange: NOTELP UNFOCUSED");
            }
        }
    }
}
