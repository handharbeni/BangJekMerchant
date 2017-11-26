package illiyin.mhandharbeni.databasemodule.utils;

import java.util.ArrayList;
import java.util.List;

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
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by root on 11/24/17.
 */

public interface InterfaceMethod {

    @GET("merchant/listcategory")
    Call<CategoryModel> getCategoryMerchant();

    @GET("merchant/listcategorymenu")
    Call<CategoryMenuModel> getCategoryMenu();

    @FormUrlEncoded
    @POST("merchant/read_menu")
    Call<MenuMerchantModel> getMenu(@Field("key") String key);

    @POST("merchant/register")
    Call<String> register(@Body BodyRegisterModel registerModel);

    @POST("merchant/login")
    Call<ArrayList<ResponseLoginModel>> login(@Body BodyLogin bodyLogin);

    @POST("merchant/create_menu")
    Call<ResponseGeneral> createMenu(@Body BodyCreateMenu bodyCreateMenu);

    @POST("merchant/update_menu")
    Call<ResponseGeneral> updateMenu(@Body BodyUpdateMenu bodyUpdateMenu);

    @POST("merchant/delete_menu")
    Call<ResponseGeneral> deleteMenu(@Body BodyDeleteMenu bodyDeleteMenu);

    @Multipart
    @POST("merchant/upload")
    Call<ResponseGeneral> uploadImage(@Part("userfile") MultipartBody.Part userfile, @Part("name") RequestBody key);

    @POST("merchant/update_merchant")
    Call<ResponseGeneral> updateMerchant(@Body BodyUpdateMerchant bodyUpdateMerchant);
}
