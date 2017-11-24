package illiyin.mhandharbeni.databasemodule;

import android.content.Context;
import android.os.StrictMode;

import java.io.IOException;

import illiyin.mhandharbeni.databasemodule.generator.ServiceGenerator;
import illiyin.mhandharbeni.databasemodule.model.CategoryModel;
import illiyin.mhandharbeni.databasemodule.model.CategoryMenuModel;
import illiyin.mhandharbeni.databasemodule.model.MenuMerchantModel;
import illiyin.mhandharbeni.databasemodule.model.user.BodyRegisterModel;
import illiyin.mhandharbeni.databasemodule.utils.InterfaceMethod;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        Call<CategoryModel> call = interfaceMethod.getCategoryMerchant();
        call.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {

            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {

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

    public void do_register(BodyRegisterModel bodyRegisterModel) throws IOException {
        Call<String> call = interfaceMethod.register(bodyRegisterModel);
        String response = call.execute().body().toString();
        if (response.equalsIgnoreCase("300")){
            /*registrasi berhasil*/
        }else{
            /*registrasi gagal*/
        }
    }



    @Override
    public void sessionChange() {
    }
//    public String add_location_grup(String id_grup, String nama_lokasi, String latitude, String longitude, String prioritas, String type) throws JSONException {
//        String returns = "Gagal Menambah Lokasi";
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("key", session.getToken())
//                .addFormDataPart("id_grup", id_grup)
//                .addFormDataPart("nama_lokasi", nama_lokasi)
//                .addFormDataPart("latitude", latitude)
//                .addFormDataPart("longitude", longitude)
//                .addFormDataPart("prioritas", prioritas)
//                .addFormDataPart("type", type)
//                .build();
//        String response = null;
//        try{
//            response = callHttp.post(endpoint_sentlocationgroup, requestBody);
//        }catch(IllegalArgumentException e) {
//
//        }
////        String response = callHttp.post(endpoint_sentlocationgroup, requestBody);
//        JSONObject objectResponse = new JSONObject(response);
//        if (objectResponse.getInt("code")==300){
//            returns = "Lokasi Berhasil Ditambahkan";
//        }
//        return returns;
//    }
}
