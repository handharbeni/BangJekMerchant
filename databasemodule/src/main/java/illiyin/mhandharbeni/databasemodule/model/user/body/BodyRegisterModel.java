package illiyin.mhandharbeni.databasemodule.model.user.body;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 11/24/17.
 */

public class BodyRegisterModel {

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("address")
    @Expose
    public String address;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("password")
    @Expose
    public String password;

    @SerializedName("phone")
    @Expose
    public String phone;

    @SerializedName("photo")
    @Expose
    public String photo;

    @SerializedName("latitude")
    @Expose
    public String latitude;

    @SerializedName("longitude")
    @Expose
    public String longitude;

    @SerializedName("id_merchant_category")
    @Expose
    public String idMerchantCategory;

    @SerializedName("max_upload")
    @Expose
    public String maxUpload;

    @SerializedName("open_at")
    @Expose
    public String openAt;

    @SerializedName("close_at")
    @Expose
    public String closeAt;

    @SerializedName("point")
    @Expose
    public String point;

    @SerializedName("open_status")
    @Expose
    public String openStatus;

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("deleted")
    @Expose
    public String deleted;

    @SerializedName("imei1")
    @Expose
    public String imei1;

    @SerializedName("imei2")
    @Expose
    public String imei2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIdMerchantCategory() {
        return idMerchantCategory;
    }

    public void setIdMerchantCategory(String idMerchantCategory) {
        this.idMerchantCategory = idMerchantCategory;
    }

    public String getMaxUpload() {
        return maxUpload;
    }

    public void setMaxUpload(String maxUpload) {
        this.maxUpload = maxUpload;
    }

    public String getOpenAt() {
        return openAt;
    }

    public void setOpenAt(String openAt) {
        this.openAt = openAt;
    }

    public String getCloseAt() {
        return closeAt;
    }

    public void setCloseAt(String closeAt) {
        this.closeAt = closeAt;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(String openStatus) {
        this.openStatus = openStatus;
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

    public String getImei1() {
        return imei1;
    }

    public void setImei1(String imei1) {
        this.imei1 = imei1;
    }

    public String getImei2() {
        return imei2;
    }

    public void setImei2(String imei2) {
        this.imei2 = imei2;
    }
}
