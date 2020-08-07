package pl.makenika.todos.ui.main;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.core.Single;
import pl.makenika.todos.data.AppPrefs;

public class MainViewModel extends ViewModel {
    private final AppPrefs appPrefs;

    @ViewModelInject
    public MainViewModel(AppPrefs appPrefs) {
        this.appPrefs = appPrefs;
    }

    Single<InitialScreen> loadInitialScreen() {
        return Single.just(InitialScreen.AUTH);
    }

    enum InitialScreen {
        AUTH, DASHBOARD
    }
}
