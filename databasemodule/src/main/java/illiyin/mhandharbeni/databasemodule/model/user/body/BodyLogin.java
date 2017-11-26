package illiyin.mhandharbeni.databasemodule.model.user.body;

/**
 * Created by root on 11/26/17.
 */

public class BodyLogin {
    String email, password;

    public BodyLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public BodyLogin() {
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
}
