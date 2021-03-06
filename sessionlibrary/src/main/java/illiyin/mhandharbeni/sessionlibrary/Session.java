package illiyin.mhandharbeni.sessionlibrary;

import android.content.Context;

import com.pddstudio.preferences.encrypted.EncryptedPreferences;

/**
 * Created by root on 17/07/17.
 */

public class Session implements EncryptedPreferences.OnSharedPreferenceChangeListener{
    private SessionListener sessionListener;
    private String defaultKey = "aJ5QElpvadHaiz7mcPNPVQx0P3Xxx0P3Xx";
    public static String NAMA = "NAMA",
            ALAMAT = "ALAMAT",
            NOTELP = "NOTELP",
            EMAIL = "EMAIL",
            KEY = "TOKEN",
            STATUS = "STATUS",
            CONNECTION = "CONNECTION",
            IMAGE = "IMAGE",
            SSHA = "SHA",
            DESKRIPSI = "DESKRIPSI",
            JAM_BUKA = "JAM_BUKA",
            JAM_TUTUP = "JAM_TUTUP",
            LATITUDE = "LATITUDE",
            LONGITUDE = "LONGITUDE";

    public String STATELOGIN = "STATELOGIN";/*true belum login, false Sudah Login*/

    Context context;
    EncryptedPreferences encryptedPreferences;
    public Session(Context context, SessionListener sessionListener) {
        this.context = context;
        encryptedPreferences = new EncryptedPreferences.Builder(context)
                .withEncryptionPassword(this.context.getString(R.string.password)).build();
        encryptedPreferences.registerOnSharedPreferenceChangeListener(this);
        this.sessionListener = sessionListener;
    }
    public String encryptedString(String value){
        return encryptedPreferences.getUtils().encryptStringValue(value);
    }
    public String decryptString(String value){
        return encryptedPreferences.getUtils().decryptStringValue(value);
    }
    public String getStateLogin(){
        return encryptedPreferences.getString(STATELOGIN, "false");
    }
    public void setStateLogin(String state){
        encryptedPreferences.edit().putString(STATELOGIN, state).apply();
    }
    public String getToken(){
        return encryptedPreferences.getUtils().decryptStringValue(encryptedPreferences.getString(KEY, defaultKey));
    }
    public void setCustomParams(String key, String value){
        encryptedPreferences.edit()
                .putString(key, value)
                .apply();
        sessionListener.sessionChange();
    }
    public void setCustomParams(String key, Integer value){
        encryptedPreferences.edit()
                .putInt(key, value)
                .apply();
        sessionListener.sessionChange();
    }
    public void setCustomParams(String key, Long value){
        encryptedPreferences.edit()
                .putLong(key, value)
                .apply();
        sessionListener.sessionChange();
    }
    public void setCustomParams(String key, Boolean value){
        encryptedPreferences.edit()
                .putBoolean(key, value)
                .apply();
        sessionListener.sessionChange();
    }
    public void setCustomParams(String key, Float value){
        encryptedPreferences.edit()
                .putFloat(key, value)
                .apply();
        sessionListener.sessionChange();
    }
    public String getCustomParams(String key, String method){
        return encryptedPreferences.getString(key, method);
    }
    public Integer getCustomParams(String key, Integer method){
        return encryptedPreferences.getInt(key, method);
    }
    public Long getCustomParams(String key, Long method){
        return encryptedPreferences.getLong(key, method);
    }
    public Boolean getCustomParams(String key, Boolean method){
        return encryptedPreferences.getBoolean(key, method);
    }
    public Float getCustomParams(String key, Float method){
        return encryptedPreferences.getFloat(key, method);
    }

    public void setSession(String nama, String alamat, String notelp, String email, String key, String status, String image, String deskripsi, String jam_buka, String jam_tutup){
        encryptedPreferences.edit()
                .putString(NAMA, nama)
                .putString(ALAMAT, alamat)
                .putString(NOTELP, notelp)
                .putString(EMAIL, email)
                .putString(KEY, encryptedPreferences.getUtils().encryptStringValue(key))
                .putString(STATUS, status)
                .putString(IMAGE, image)
                .putString(DESKRIPSI, deskripsi)
                .putString(JAM_BUKA, jam_buka)
                .putString(JAM_TUTUP, jam_tutup)
                .apply();
    }
    public Boolean checkSession(){
        if (encryptedPreferences.getString(KEY, defaultKey) != null){
            if (!encryptedPreferences.getUtils().decryptStringValue(encryptedPreferences.getString(KEY, defaultKey)).equalsIgnoreCase("NOTHING")){
                return true;
            }
            return false;
        }
        return false;
    }
    public void deleteSession(){
        encryptedPreferences.forceDeleteExistingPreferences();
    }

    public void setConnectionState(String state){
        encryptedPreferences.edit().putString(CONNECTION, state).apply();
    }
    public String getConnectionState(){
        return encryptedPreferences.getString(CONNECTION, "0");
    }

    public void destroyListener(){
        encryptedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(EncryptedPreferences encryptedPreferences, String key) {
        sessionListener.sessionChange();
    }
}
