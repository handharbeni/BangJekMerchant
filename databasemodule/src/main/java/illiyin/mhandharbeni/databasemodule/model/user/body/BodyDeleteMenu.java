package illiyin.mhandharbeni.databasemodule.model.user.body;

/**
 * Created by root on 11/26/17.
 */

public class BodyDeleteMenu {
    String id_merchant_menu, key;

    public BodyDeleteMenu(String id_merchant_menu, String key) {
        this.id_merchant_menu = id_merchant_menu;
        this.key = key;
    }

    public BodyDeleteMenu() {
    }

    public String getId_merchant_menu() {
        return id_merchant_menu;
    }

    public void setId_merchant_menu(String id_merchant_menu) {
        this.id_merchant_menu = id_merchant_menu;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
