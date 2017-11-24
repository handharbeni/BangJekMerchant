package illiyin.mhandharbeni.databasemodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 11/24/17.
 */

public class CategoryMenuModel extends RealmObject{
    @PrimaryKey
    @SerializedName("id_merchant_menu_category")
    @Expose
    private String idMerchantMenuCategory;
    @SerializedName("merchant_menu_category")
    @Expose
    private String merchantMenuCategory;
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

    public CategoryMenuModel() {
    }

    public String getIdMerchantMenuCategory() {
        return idMerchantMenuCategory;
    }

    public void setIdMerchantMenuCategory(String idMerchantMenuCategory) {
        this.idMerchantMenuCategory = idMerchantMenuCategory;
    }

    public String getMerchantMenuCategory() {
        return merchantMenuCategory;
    }

    public void setMerchantMenuCategory(String merchantMenuCategory) {
        this.merchantMenuCategory = merchantMenuCategory;
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
