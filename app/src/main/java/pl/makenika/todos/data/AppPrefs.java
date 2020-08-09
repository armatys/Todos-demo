package pl.makenika.todos.data;

import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import javax.inject.Inject;

import pl.makenika.todos.di.qualifier.EncryptedPrefs;

public class AppPrefs {
    private SharedPreferences prefs;

    @Inject
    public AppPrefs(@EncryptedPrefs SharedPreferences prefs) {
        this.prefs = prefs;
    }

    @Nullable
    public String getJwt() {
        return prefs.getString(KEY_JWT, null);
    }

    public void setJwt(@Nullable String jwt) {
        prefs.edit().putString(KEY_JWT, jwt).apply();
    }

    private static final String KEY_JWT = "jwt";
}
