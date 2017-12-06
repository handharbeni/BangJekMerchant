package illiyin.mhandharbeni.databasemodule;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import illiyin.mhandharbeni.databasemodule.generator.ServiceGenerator;
import illiyin.mhandharbeni.databasemodule.model.CategoryModel;
import illiyin.mhandharbeni.databasemodule.model.CategoryMenuModel;
import illiyin.mhandharbeni.databasemodule.model.MenuMerchantModel;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyCreateMenu;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyDeleteMenu;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyLogin;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyRegisterModel;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyUpdateMenu;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyUpdateMerchant;
import illiyin.mhandharbeni.databasemodule.model.user.response.ResponseGeneral;
import illiyin.mhandharbeni.databasemodule.model.user.response.ResponseLoginModel;
import illiyin.mhandharbeni.databasemodule.utils.InterfaceMethod;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import io.realm.RealmResults;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 17/07/17.
 */

public class AdapterModel implements SessionListener{
    private InterfaceMethod interfaceMethod;
    private Context context;
    private Session session;

    public AdapterModel(Context context) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        interfaceMethod = ServiceGenerator.createService(InterfaceMethod.class);

        this.context = context;
        session = new Session(context, this);
    }
    public void syncCategoryMenu(){
        Call<CategoryMenuModel> call = interfaceMethod.getCategoryMenu();
        call.enqueue(new Callback<CategoryMenuModel>() {
            @Override
            public void onResponse(Call<CategoryMenuModel> call, Response<CategoryMenuModel> response) {

            }

            @Override
            public void onFailure(Call<CategoryMenuModel> call, Throwable t) {

            }
        });
    }

    public void syncCategoryMerchant(){
        Call<ArrayList<CategoryModel>> call = interfaceMethod.getCategoryMerchant();
        call.enqueue(new Callback<ArrayList<CategoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryModel>> call, Response<ArrayList<CategoryModel>> response) {
                if (response.body().size() > 0){
                    for (int i=0;i<response.body().size();i++){
                        CategoryModel cm = response.body().get(i);
                        CategoryModel newCm = new CategoryModel();
                        Crud crud = new Crud(context, newCm);
                        RealmResults results = crud.read("idMerchantCategory", cm.getIdMerchantCategory());
                        if (results.size() > 0){
                            /*check sha*/
                            newCm = (CategoryModel) results.get(0);
                            assert newCm != null;
                            if (!cm.getSha().equalsIgnoreCase(newCm.getSha())){
                                /*update kategori*/
                                Log.d(TAG, "onResponse: Update Kategori "+cm.getMerchantCategory());
                                crud.openObject();
                                newCm.setMerchantCategory(cm.getMerchantCategory());
                                newCm.setDateAdd(cm.getDateAdd());
                                newCm.setDeleted(cm.getDeleted());
                                newCm.setMaxUpload(cm.getMaxUpload());
                                newCm.setSha(cm.getSha());
                                newCm.setStatus(cm.getStatus());
                                crud.update(newCm);
                                crud.commitObject();
                            }
                        }else{
                            /*insert*/
                            Log.d(TAG, "onResponse: Insert New Kategori "+cm.getMerchantCategory());
                            crud.create(cm);
                        }
                        crud.closeRealm();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryModel>> call, Throwable t) {

            }
        });
    }

    public void syncMenu(){
        if (session.getToken().equalsIgnoreCase("nothing")){
            Call<MenuMerchantModel> call = interfaceMethod.getMenu(session.getToken());
            call.enqueue(new Callback<MenuMerchantModel>() {
                @Override
                public void onResponse(Call<MenuMerchantModel> call, Response<MenuMerchantModel> response) {

                }

                @Override
                public void onFailure(Call<MenuMerchantModel> call, Throwable t) {

                }
            });
        }
    }

    public void doRegister(BodyRegisterModel bodyRegisterModel) throws IOException {
        Call<String> call = interfaceMethod.register(bodyRegisterModel);
        String response = call.execute().body().toString();
        if (response.equalsIgnoreCase("300")){
            /*registrasi berhasil*/
        }else{
            /*registrasi gagal*/
        }
    }

    public void createMenu(BodyCreateMenu bodyCreateMenu){
        Call<ResponseGeneral> call = interfaceMethod.createMenu(bodyCreateMenu);
        call.enqueue(new Callback<ResponseGeneral>() {
            @Override
            public void onResponse(Call<ResponseGeneral> call, Response<ResponseGeneral> response) {

            }

            @Override
            public void onFailure(Call<ResponseGeneral> call, Throwable t) {

            }
        });
    }

    public void updateMenu(BodyUpdateMenu bodyUpdateMenu){
        Call<ResponseGeneral> call = interfaceMethod.updateMenu(bodyUpdateMenu);
        call.enqueue(new Callback<ResponseGeneral>() {
            @Override
            public void onResponse(Call<ResponseGeneral> call, Response<ResponseGeneral> response) {

            }

            @Override
            public void onFailure(Call<ResponseGeneral> call, Throwable t) {

            }
        });
    }
    public void deleteMenu(BodyDeleteMenu bodyDeleteMenu){
        Call<ResponseGeneral> call = interfaceMethod.deleteMenu(bodyDeleteMenu);
        call.enqueue(new Callback<ResponseGeneral>() {
            @Override
            public void onResponse(Call<ResponseGeneral> call, Response<ResponseGeneral> response) {

            }

            @Override
            public void onFailure(Call<ResponseGeneral> call, Throwable t) {

            }
        });
    }
    public void doLogin(BodyLogin bodyLogin){
        Call<ArrayList<ResponseLoginModel>> call = interfaceMethod.login(bodyLogin);
        call.enqueue(new Callback<ArrayList<ResponseLoginModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseLoginModel>> call, Response<ArrayList<ResponseLoginModel>> response) {
                if (response.body() != null){
                    Log.d(TAG, "onResponse: "+response.body().size());
                    for (int i = 0;i<response.body().size();i++){
                        ResponseLoginModel responseLoginModel = response.body().get(i);
                        Log.d(TAG, "onResponse: "+i);
                        Log.d(TAG, "onResponse: "+responseLoginModel.getKey());
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseLoginModel>> call, Throwable t) {

            }
        });
    }

    public void uploadImage(MultipartBody.Part userfile, RequestBody key){
        Call<ResponseGeneral> call = interfaceMethod.uploadImage(userfile, key);
        call.enqueue(new Callback<ResponseGeneral>() {
            @Override
            public void onResponse(Call<ResponseGeneral> call, Response<ResponseGeneral> response) {
//                response.body().getStatus();
            }

            @Override
            public void onFailure(Call<ResponseGeneral> call, Throwable t) {

            }
        });
    }

    public void updateMerchant(BodyUpdateMerchant bodyUpdateMerchant){
        Call<ResponseGeneral> call = interfaceMethod.updateMerchant(bodyUpdateMerchant);
        call.enqueue(new Callback<ResponseGeneral>() {
            @Override
            public void onResponse(Call<ResponseGeneral> call, Response<ResponseGeneral> response) {

            }

            @Override
            public void onFailure(Call<ResponseGeneral> call, Throwable t) {

            }
        });
    }
    @Override
    public void sessionChange() {
    }
}
