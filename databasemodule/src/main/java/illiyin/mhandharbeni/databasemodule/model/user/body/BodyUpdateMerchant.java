package illiyin.mhandharbeni.databasemodule.model.user.body;

/**
 * Created by root on 11/26/17.
 */

public class BodyUpdateMerchant {
    String key, field, value;

    public BodyUpdateMerchant(String key, String field, String value) {
        this.key = key;
        this.field = field;
        this.value = value;
    }

    public BodyUpdateMerchant() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
