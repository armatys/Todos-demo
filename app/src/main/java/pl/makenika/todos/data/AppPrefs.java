package pl.makenika.todos.data;

import android.content.SharedPreferences;

import javax.inject.Inject;

import pl.makenika.todos.di.qualifier.EncryptedPrefs;

public class AppPrefs {
    private SharedPreferences prefs;

    @Inject
    public AppPrefs(@EncryptedPrefs SharedPreferences prefs) {
        this.prefs = prefs;
    }
}
