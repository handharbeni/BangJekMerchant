package illiyin.mhandharbeni.databasemodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 11/24/17.
 */

public class CategoryModel extends RealmObject {

    @PrimaryKey
    @SerializedName("id_merchant_category")
    @Expose
    private String idMerchantCategory;
    @SerializedName("merchant_category")
    @Expose
    private String merchantCategory;
    @SerializedName("max_upload")
    @Expose
    private String maxUpload;
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

    public String getIdMerchantCategory() {
        return idMerchantCategory;
    }

    public void setIdMerchantCategory(String idMerchantCategory) {
        this.idMerchantCategory = idMerchantCategory;
    }

    public String getMerchantCategory() {
        return merchantCategory;
    }

    public void setMerchantCategory(String merchantCategory) {
        this.merchantCategory = merchantCategory;
    }

    public String getMaxUpload() {
        return maxUpload;
    }

    public void setMaxUpload(String maxUpload) {
        this.maxUpload = maxUpload;
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
