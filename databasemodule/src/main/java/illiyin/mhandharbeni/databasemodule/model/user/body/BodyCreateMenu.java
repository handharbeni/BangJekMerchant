package illiyin.mhandharbeni.databasemodule.model.user.body;

/**
 * Created by root on 11/26/17.
 */

public class BodyCreateMenu {
    String key, merchant_menu, id_merchant_menu_category, photo, price, discount, discount_variant, description, status;

    public BodyCreateMenu(String key, String merchant_menu, String id_merchant_menu_category, String photo, String price, String discount, String discount_variant, String description, String status) {
        this.key = key;
        this.merchant_menu = merchant_menu;
        this.id_merchant_menu_category = id_merchant_menu_category;
        this.photo = photo;
        this.price = price;
        this.discount = discount;
        this.discount_variant = discount_variant;
        this.description = description;
        this.status = status;
    }

    public BodyCreateMenu() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMerchant_menu() {
        return merchant_menu;
    }

    public void setMerchant_menu(String merchant_menu) {
        this.merchant_menu = merchant_menu;
    }

    public String getId_merchant_menu_category() {
        return id_merchant_menu_category;
    }

    public void setId_merchant_menu_category(String id_merchant_menu_category) {
        this.id_merchant_menu_category = id_merchant_menu_category;
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

    public String getDiscount_variant() {
        return discount_variant;
    }

    public void setDiscount_variant(String discount_variant) {
        this.discount_variant = discount_variant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
