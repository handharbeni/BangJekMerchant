package illiyin.mhandharbeni.databasemodule.model.user.body;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 11/26/17.
 */

public class BodyUpdateMerchant {
    @SerializedName("key")
    @Expose
    public String key;

    @SerializedName("field")
    @Expose
    public String field;

    @SerializedName("value")
    @Expose
    public String value;

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
