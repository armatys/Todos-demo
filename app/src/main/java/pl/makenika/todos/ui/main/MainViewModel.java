package pl.makenika.todos.ui.main;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.core.Single;

class MainViewModel extends ViewModel {
    @ViewModelInject
    MainViewModel() {

    }

    Single<InitialScreen> loadInitialScreen() {
        return Single.just(InitialScreen.AUTH);
    }

    enum InitialScreen {
        AUTH, DASHBOARD
    }
}
