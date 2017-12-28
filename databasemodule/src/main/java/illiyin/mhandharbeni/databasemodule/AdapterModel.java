package illiyin.mhandharbeni.databasemodule;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 17/07/17.
 */

public class AdapterModel implements SessionListener{
    private InterfaceMethod interfaceMethod;
    private Context context;
    private Session session;

    public AdapterModel(Context context) {
        requestPermission();
        ServiceGenerator.changeApiBAseUrl(context.getString(R.string.module_server));
        interfaceMethod = ServiceGenerator.createService(InterfaceMethod.class);

        this.context = context;
        session = new Session(context, this);
    }
    public void requestPermission(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    public void syncCategoryMenuByRx(){
        interfaceMethod.getCategoryMenuByRx()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<CategoryMenuModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<CategoryMenuModel> categoryMenuModels) {
                        if (categoryMenuModels.size() > 0){
                            for (int i=0;i<categoryMenuModels.size();i++){
                                CategoryMenuModel kategoriServer = categoryMenuModels.get(i);
                                CategoryMenuModel kategoriLokal = new CategoryMenuModel();
                                Crud crudLokal = new Crud(context, kategoriLokal);
                                RealmResults resultLokal = crudLokal.read("idMerchantMenuCategory", kategoriServer.getIdMerchantMenuCategory());
                                if (resultLokal.size() > 0){
                                    CategoryMenuModel updateCategory = (CategoryMenuModel) resultLokal.get(0);
                                    assert updateCategory != null;
                                    if (!updateCategory.getSha().equalsIgnoreCase(kategoriServer.getSha())){
                                        if (kategoriServer.getDeleted().equalsIgnoreCase("Y")){
                                            crudLokal.delete("idMerchantMenuCategory", kategoriServer.getIdMerchantMenuCategory());
                                        }else{
                                            crudLokal.openObject();
                                            updateCategory.setMerchantMenuCategory(kategoriServer.getMerchantMenuCategory());
                                            updateCategory.setStatus(kategoriServer.getStatus());
                                            updateCategory.setDeleted(kategoriServer.getDeleted());
                                            updateCategory.setSha(kategoriServer.getSha());
                                            crudLokal.update(updateCategory);
                                            crudLokal.commitObject();
                                        }
                                    }
                            /*update with check sha*/
                                }else{
                                    if (kategoriServer.getDeleted().equalsIgnoreCase("N")){
                                        crudLokal.create(kategoriServer);
                                    }
                                }
                                crudLokal.closeRealm();
                            }
                        }

                    }
                });
    }
    public void syncCategoryMerchantByRx(){
        interfaceMethod.getCategoryMerchantByRx()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<CategoryModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<CategoryModel> categoryModels) {
                        if (categoryModels.size() > 0){
                            for (int i=0;i<categoryModels.size();i++){
                                CategoryModel cm = categoryModels.get(i);
                                CategoryModel newCm = new CategoryModel();
                                Crud crud = new Crud(context, newCm);
                                RealmResults results = crud.read("idMerchantCategory", cm.getIdMerchantCategory());
                                if (results.size() > 0){
                            /*check sha*/
                                    newCm = (CategoryModel) results.get(0);
                                    assert newCm != null;
                                    if (!cm.getSha().equalsIgnoreCase(newCm.getSha())){
                                        if (cm.getDeleted().equalsIgnoreCase("Y")){
                                            crud.delete("idMerchantCategory", cm.getIdMerchantCategory());
                                        }else{
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
                                    }
                                }else{
                                    if (cm.getDeleted().equalsIgnoreCase("N")){
                                /*insert*/
                                        crud.create(cm);
                                    }
                                }
                                crud.closeRealm();
                            }
                        }

                    }
                });
    }
    public void syncInfoByRx(){
        interfaceMethod.fetch_info_rx(session.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<ResponseLoginModel>>() {
                    @Override
                    public void onNext(ArrayList<ResponseLoginModel> value) {
                        if (value.size()> 0){
                            for (int i = 0;i<value.size();i++) {
                                ResponseLoginModel responseLoginModel = value.get(i);
                                assert responseLoginModel != null;
                                String sha = responseLoginModel.getSha();
                                assert sha != null;
                                if (!session.getCustomParams(Session.SSHA, "nothing").equalsIgnoreCase(sha)){
                                    String nama = responseLoginModel.getName();
                                    String alamat = responseLoginModel.getAddress();
                                    String notelp = responseLoginModel.getPhone();
                                    String email = responseLoginModel.getEmail();
                                    String token = responseLoginModel.getKey();
                                    String status = responseLoginModel.getOpenStatus();
                                    String image = responseLoginModel.getPhoto();
                                    String deskripsi = responseLoginModel.getDescription();
                                    String jamBuka = responseLoginModel.getOpenAt();
                                    String jamTutup = responseLoginModel.getCloseAt();

                                    session.setCustomParams(Session.SSHA, sha);

                                    session.setSession(nama, alamat, notelp, email, token, status, image, deskripsi, jamBuka, jamTutup);
                                }

                            }

                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    public void syncMenu(){
        if (!session.getToken().equalsIgnoreCase("nothing")){
            Call<ArrayList<MenuMerchantModel>> call = interfaceMethod.getMenu(session.getToken());
            call.enqueue(new Callback<ArrayList<MenuMerchantModel>>() {
                @Override
                public void onResponse(Call<ArrayList<MenuMerchantModel>> call, Response<ArrayList<MenuMerchantModel>> response) {
                    if (response.body().size() > 0){
                        for (int i=0;i<response.body().size();i++){
                            MenuMerchantModel menuServer = response.body().get(i);
                            MenuMerchantModel menuLokal = new MenuMerchantModel();
                            Crud crudLokal = new Crud(context, menuLokal);
                            RealmResults resultsLokal = crudLokal.read("idMerchantMenu", menuServer.getIdMerchantMenu());
                            if (resultsLokal.size() > 0){
                                /*check sha then update*/
                                MenuMerchantModel updateMerchant = (MenuMerchantModel) resultsLokal.get(0);
                                assert updateMerchant != null;
                                if (!updateMerchant.getSha().equalsIgnoreCase(menuServer.getSha())){
                                    if (menuServer.getDeleted().equalsIgnoreCase("Y")){
                                        /*delete data from realm*/
                                        crudLokal.delete("idMerchantMenu", menuServer.getIdMerchantMenu());
                                    }else{
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
                                }
                            }else{
                                /*insert new*/
                                if (menuServer.getDeleted().equalsIgnoreCase("N")){
                                    crudLokal.create(menuServer);
                                }
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
    public void syncMenuByRx(){
        if (!session.getToken().equalsIgnoreCase("nothing")) {
            interfaceMethod.getMenuByRx(session.getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.newThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(new Observer<ArrayList<MenuMerchantModel>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(ArrayList<MenuMerchantModel> menuMerchantModels) {
                            if (menuMerchantModels.size() > 0) {
                                for (int i = 0; i < menuMerchantModels.size(); i++) {
                                    MenuMerchantModel menuServer = menuMerchantModels.get(i);
                                    MenuMerchantModel menuLokal = new MenuMerchantModel();
                                    Crud crudLokal = new Crud(context, menuLokal);
                                    RealmResults resultsLokal = crudLokal.read("idMerchantMenu", menuServer.getIdMerchantMenu());
                                    if (resultsLokal.size() > 0) {
                                /*check sha then update*/
                                        MenuMerchantModel updateMerchant = (MenuMerchantModel) resultsLokal.get(0);
                                        assert updateMerchant != null;
                                        if (!updateMerchant.getSha().equalsIgnoreCase(menuServer.getSha())) {
                                            if (menuServer.getDeleted().equalsIgnoreCase("Y")) {
                                        /*delete data from realm*/
                                                crudLokal.delete("idMerchantMenu", menuServer.getIdMerchantMenu());
                                            } else {
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
                                        }
                                    } else {
                                /*insert new*/
                                        if (menuServer.getDeleted().equalsIgnoreCase("N")) {
                                            crudLokal.create(menuServer);
                                        }
                                    }
                                    crudLokal.closeRealm();
                                }
                            }
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
        call.cancel();
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
        call.cancel();
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
        call.cancel();
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
        call.cancel();
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
                        String status = responseLoginModel.getOpenStatus();
                        String image = responseLoginModel.getPhoto();
                        String sha = responseLoginModel.getSha();
                        String deskripsi = responseLoginModel.getDescription();
                        String jamBuka = responseLoginModel.getOpenAt();
                        String jamTutup = responseLoginModel.getCloseAt();

                        session.setCustomParams(Session.SSHA, sha);

                        session.setSession(nama, alamat, notelp, email, token, status, image, deskripsi, jamBuka, jamTutup);
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

    public String uploadImage(MultipartBody.Part userfile, MultipartBody.Part key, String captionSuccess, String captionFailed) throws IOException {
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
        call.cancel();
        return returns;
    }
    public String uploadRegisterImage(MultipartBody.Part userfile, String captionSuccess, String captionFailed) throws IOException {
        String returns = captionFailed;
        Call<String> call = interfaceMethod.uploadRegisterImage(userfile);
        String response = call.execute().body();
        assert response != null;
        if (response.equalsIgnoreCase("300")){
            /*upload berhasil*/
            returns = captionSuccess;
        }else{
            /*upload gagal*/
            returns = captionFailed;
        }
        call.cancel();
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
        call.cancel();
        return returns;
    }
    @Override
    public void sessionChange() {
    }
}
