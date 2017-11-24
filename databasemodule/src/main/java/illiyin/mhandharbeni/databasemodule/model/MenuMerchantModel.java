package illiyin.mhandharbeni.databasemodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 11/24/17.
 */

public class MenuMerchantModel extends RealmObject {
    @PrimaryKey
    @SerializedName("id_merchant_menu")
    @Expose
    private String idMerchantMenu;

    @SerializedName("id_merchant")
    @Expose
    private String idMerchant;

    @SerializedName("merchant_menu")
    @Expose
    private String merchantMenu;

    @SerializedName("id_merchant_menu_category")
    @Expose
    private String idMerchantMenuCategory;

    @SerializedName("photo")
    @Expose
    private String photo;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("discount")
    @Expose
    private String discount;

    @SerializedName("discount_variant")
    @Expose
    private String discountVariant;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("date_add")
    @Expose
    private String dateAdd;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("deleted")
    @Expose
    private String deleted;

    @SerializedName("sha")
    @Expose
    private String sha;

    public MenuMerchantModel() {
    }

    public String getIdMerchantMenu() {
        return idMerchantMenu;
    }

    public void setIdMerchantMenu(String idMerchantMenu) {
        this.idMerchantMenu = idMerchantMenu;
    }

    public String getIdMerchant() {
        return idMerchant;
    }

    public void setIdMerchant(String idMerchant) {
        this.idMerchant = idMerchant;
    }

    public String getMerchantMenu() {
        return merchantMenu;
    }

    public void setMerchantMenu(String merchantMenu) {
        this.merchantMenu = merchantMenu;
    }

    public String getIdMerchantMenuCategory() {
        return idMerchantMenuCategory;
    }

    public void setIdMerchantMenuCategory(String idMerchantMenuCategory) {
        this.idMerchantMenuCategory = idMerchantMenuCategory;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountVariant() {
        return discountVariant;
    }

    public void setDiscountVariant(String discountVariant) {
        this.discountVariant = discountVariant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }
}
