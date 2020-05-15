package in.gov.mhrd.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    private static final String USAGE ="usage" ;
    private final String ID="userid" ;
    private final String NAME = "name";
    private final String MOBILE = "mobile";
    private final String LOGIN = "login";

    private SharedPreferences app_prefs;

    PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences("publichelper",
                Context.MODE_PRIVATE);
    }

    void putIsLogin(Boolean login) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(LOGIN, login);
        edit.apply();
    }
    boolean getIsLogin() {
        return app_prefs.getBoolean(LOGIN, false);
    }

    void putName(String name) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(NAME, name);
        edit.apply();
    }
    public String getName() {
        return app_prefs.getString(NAME, "");
    }

    void putMobile(String mobile) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(MOBILE, mobile);
        edit.apply();
    }
    public String getMobile() {
        return app_prefs.getString(MOBILE, "");
    }
    void putID(String id) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(ID, id);
        edit.apply();
    }
    public String getID() {        return app_prefs.getString(ID, "");    }

    void putUsage(Boolean usage) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(USAGE, usage);
        edit.commit();
    }
    boolean getIsUsage() {
        putUsage(true);
        return app_prefs.getBoolean(USAGE,false);    }
}
