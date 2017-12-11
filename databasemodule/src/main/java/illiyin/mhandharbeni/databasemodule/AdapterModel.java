package illiyin.mhandharbeni.databasemodule;

import android.content.Context;
import android.os.StrictMode;

import java.io.IOException;
import java.util.ArrayList;

import illiyin.mhandharbeni.databasemodule.generator.ServiceGenerator;
import illiyin.mhandharbeni.databasemodule.model.CategoryMenuModel;
import illiyin.mhandharbeni.databasemodule.model.CategoryModel;
import illiyin.mhandharbeni.databasemodule.model.MenuMerchantModel;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyCreateMenu;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyDeleteMenu;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyLogin;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyRegisterModel;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyUpdateMenu;
import illiyin.mhandharbeni.databasemodule.model.user.body.BodyUpdateMerchant;
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

/**
 * Created by root on 17/07/17.
 */

public class AdapterModel implements SessionListener{
    private InterfaceMethod interfaceMethod;
    private Context context;
    private Session session;

    public AdapterModel(Context context) {
        requestPermission();
        ServiceGenerator.changeApiBAseUrl("http://192.168.3.7/bangjekApi/");
        interfaceMethod = ServiceGenerator.createService(InterfaceMethod.class);

        this.context = context;
        session = new Session(context, this);
    }
    public void requestPermission(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    public void syncCategoryMenu(){
        Call<ArrayList<CategoryMenuModel>> call = interfaceMethod.getCategoryMenu();
        call.enqueue(new Callback<ArrayList<CategoryMenuModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryMenuModel>> call, Response<ArrayList<CategoryMenuModel>> response) {
                if (response.body().size() > 0){
                    for (int i=0;i<response.body().size();i++){
                        CategoryMenuModel kategoriServer = response.body().get(i);
                        CategoryMenuModel kategoriLokal = new CategoryMenuModel();
                        Crud crudLokal = new Crud(context, kategoriLokal);
                        RealmResults resultLokal = crudLokal.read("idMerchantMenuCategory", kategoriServer.getIdMerchantMenuCategory());
                        if (resultLokal.size() > 0){
                            CategoryMenuModel updateCategory = (CategoryMenuModel) resultLokal.get(0);
                            assert updateCategory != null;
                            if (!updateCategory.getSha().equalsIgnoreCase(kategoriServer.getSha())){
                                crudLokal.openObject();
                                updateCategory.setMerchantMenuCategory(kategoriServer.getMerchantMenuCategory());
                                updateCategory.setStatus(kategoriServer.getStatus());
                                updateCategory.setDeleted(kategoriServer.getDeleted());
                                updateCategory.setSha(kategoriServer.getSha());
                                crudLokal.update(updateCategory);
                                crudLokal.commitObject();
                            }
                            /*update with check sha*/
                        }else{
                            crudLokal.create(kategoriServer);
                        }
                        crudLokal.closeRealm();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryMenuModel>> call, Throwable t) {

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

    public void syncInfo(){
        if (!session.getToken().equalsIgnoreCase("nothing")){
            Call<ArrayList<ResponseLoginModel>> call = interfaceMethod.fetch_info(session.getToken());
            call.enqueue(new Callback<ArrayList<ResponseLoginModel>>() {
                @Override
                public void onResponse(Call<ArrayList<ResponseLoginModel>> call, Response<ArrayList<ResponseLoginModel>> response) {
                    if (response.body()!=null){
                        for (int i = 0;i<response.body().size();i++) {
                            ResponseLoginModel responseLoginModel = response.body().get(i);
                            String sha = responseLoginModel.getSha();
                            if (!session.getCustomParams(Session.SHA, "nothing").equalsIgnoreCase(sha)){
                                String nama = responseLoginModel.getName();
                                String alamat = responseLoginModel.getAddress();
                                String notelp = responseLoginModel.getPhone();
                                String email = responseLoginModel.getEmail();
                                String token = responseLoginModel.getKey();
                                String status = responseLoginModel.getStatus();
                                String image = responseLoginModel.getPhone();

                                session.setCustomParams(Session.SHA, sha);

                                session.setSession(nama, alamat, notelp, email, token, status, image);
                            }

                        }

                    }

                }

                @Override
                public void onFailure(Call<ArrayList<ResponseLoginModel>> call, Throwable t) {

                }
            });
        }
    }

    public void syncMenu(){
        if (session.getToken().equalsIgnoreCase("nothing")){
            Call<ArrayList<MenuMerchantModel>> call = interfaceMethod.getMenu(session.getToken());
            call.enqueue(new Callback<ArrayList<MenuMerchantModel>>() {
                @Override
                public void onResponse(Call<ArrayList<MenuMerchantModel>> call, Response<ArrayList<MenuMerchantModel>> response) {
                    if (response.body().size() > 0){
                        for (int i=0;i<response.body().size();i++){
                            MenuMerchantModel menuServer = response.body().get(i);
                            MenuMerchantModel menuLokal = new MenuMerchantModel();
                            Crud crudLokal = new Crud(context, menuLokal);
                            RealmResults resultsLokal = crudLokal.read("id_merchant_menu", menuServer.getIdMerchantMenu());
                            if (resultsLokal.size() > 0){
                                /*check sha then update*/
                                MenuMerchantModel updateMerchant = (MenuMerchantModel) resultsLokal.get(0);
                                assert updateMerchant != null;
                                if (!updateMerchant.getSha().equalsIgnoreCase(menuServer.getSha())){
                                    crudLokal.openObject();
                                    updateMerchant.setIdMerchant(menuServer.getIdMerchant());
                                    updateMerchant.setMerchantMenu(menuServer.getMerchantMenu());
                                    updateMerchant.setIdMerchantMenuCategory(menuServer.getIdMerchantMenuCategory());
                                    updateMerchant.setPhoto(menuServer.getPhoto());
                                    updateMerchant.setPrice(menuServer.getPrice());
                                    updateMerchant.setDiscount(menuServer.getDiscount());
                                    updateMerchant.setDiscountVariant(menuServer.getDiscountVariant());
                                    updateMerchant.setDescription(menuServer.getDescription());
                                    updateMerchant.setStatus(menuServer.getStatus());
                                    updateMerchant.setDeleted(menuServer.getDeleted());
                                    updateMerchant.setSha(menuServer.getSha());
                                    crudLokal.update(updateMerchant);
                                    crudLokal.commitObject();
                                }
                            }else{
                                /*insert new*/
                                crudLokal.create(menuServer);
                            }
                            crudLokal.closeRealm();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<MenuMerchantModel>> call, Throwable t) {

                }
            });
        }
    }

    public String doRegister(BodyRegisterModel bodyRegisterModel, String captionSuccess, String captionFailed) throws IOException {
        String returns = captionFailed;
        Call<String> call = interfaceMethod.register(bodyRegisterModel);
        String response = call.execute().body();
        assert response != null;
        if (response.equalsIgnoreCase("300")){
            /*registrasi berhasil*/
            returns = captionSuccess;
        }else{
            /*registrasi gagal*/
            returns = captionFailed;
        }
        return returns;
    }

    public String createMenu(BodyCreateMenu bodyCreateMenu, String captionSuccess, String captionFailed) throws IOException {
        String returns = captionFailed;
        Call<String> call = interfaceMethod.createMenu(bodyCreateMenu);
        String response = call.execute().body();
        assert response != null;
        if (response.equalsIgnoreCase("300")){
            /*registrasi berhasil*/
            returns = captionSuccess;
        }else{
            /*registrasi gagal*/
            returns = captionFailed;
        }
        return returns;
    }

    public String updateMenu(BodyUpdateMenu bodyUpdateMenu, String captionSuccess, String captionFailed) throws IOException {
        String returns = captionFailed;
        Call<String> call = interfaceMethod.updateMenu(bodyUpdateMenu);
        String response = call.execute().body();
        assert response != null;
        if (response.equalsIgnoreCase("300")){
            /*update berhasil*/
            returns = captionSuccess;
        }else{
            /*update gagal*/
            returns = captionFailed;
        }
        return returns;
    }
    public String deleteMenu(BodyDeleteMenu bodyDeleteMenu, String captionSuccess, String captionFailed) throws IOException {
        String returns = captionFailed;
        Call<String> call = interfaceMethod.deleteMenu(bodyDeleteMenu);
        String response = call.execute().body();
        assert response != null;
        if (response.equalsIgnoreCase("300")){
            /*delete berhasil*/
            returns = captionSuccess;
        }else{
            /*delete gagal*/
            returns = captionFailed;
        }
        return returns;
    }
    public String doLogin(BodyLogin bodyLogin, final String captionSuccess, final String captionFailed){
        final String[] returns = {captionFailed};
        Call<ArrayList<ResponseLoginModel>> call = interfaceMethod.login(bodyLogin);
        call.enqueue(new Callback<ArrayList<ResponseLoginModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseLoginModel>> call, Response<ArrayList<ResponseLoginModel>> response) {
                if (response.body() != null){
                    for (int i = 0;i<response.body().size();i++){
                        ResponseLoginModel responseLoginModel = response.body().get(i);
                        /*
                        * nama
                        * alamat
                        * notelp
                        * email
                        * token
                        * status
                        * image
                        * */

                        String nama = responseLoginModel.getName();
                        String alamat = responseLoginModel.getAddress();
                        String notelp = responseLoginModel.getPhone();
                        String email = responseLoginModel.getEmail();
                        String token = responseLoginModel.getKey();
                        String status = responseLoginModel.getStatus();
                        String image = responseLoginModel.getPhone();
                        String sha = responseLoginModel.getSha();

                        session.setCustomParams(Session.SHA, sha);

                        session.setSession(nama, alamat, notelp, email, token, status, image);
                        returns[0] = captionSuccess;
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseLoginModel>> call, Throwable t) {
                returns[0] = captionFailed;
            }
        });
        return returns[0];
    }

    public String uploadImage(MultipartBody.Part userfile, RequestBody key, String captionSuccess, String captionFailed) throws IOException {
        String returns = captionFailed;
        Call<String> call = interfaceMethod.uploadImage(userfile, key);
        String response = call.execute().body();
        assert response != null;
        if (response.equalsIgnoreCase("300")){
            /*upload berhasil*/
            returns = captionSuccess;
        }else{
            /*upload gagal*/
            returns = captionFailed;
        }
        return returns;
    }

    public String updateMerchant(BodyUpdateMerchant bodyUpdateMerchant, String captionSuccess, String captionFailed) throws IOException {
        String returns = captionFailed;
        Call<String> call = interfaceMethod.updateMerchant(bodyUpdateMerchant);
        String response = call.execute().body();
        assert response != null;
        if (response.equalsIgnoreCase("300")){
            /*update berhasil*/
            returns = captionSuccess;
        }else{
            /*update gagal*/
            returns = captionFailed;
        }
        return returns;
    }
    @Override
    public void sessionChange() {
    }
}
