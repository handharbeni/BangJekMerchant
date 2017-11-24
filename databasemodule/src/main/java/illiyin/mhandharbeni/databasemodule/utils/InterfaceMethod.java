package illiyin.mhandharbeni.databasemodule.utils;

import illiyin.mhandharbeni.databasemodule.model.CategoryModel;
import illiyin.mhandharbeni.databasemodule.model.CategoryMenuModel;
import illiyin.mhandharbeni.databasemodule.model.MenuMerchantModel;
import illiyin.mhandharbeni.databasemodule.model.user.BodyRegisterModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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


}
