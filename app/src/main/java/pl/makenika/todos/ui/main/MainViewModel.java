package pl.makenika.todos.ui.main;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.core.Single;
import pl.makenika.todos.data.UserRepository;

public class MainViewModel extends ViewModel {
    private final UserRepository userRepository;

    @ViewModelInject
    public MainViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    Single<InitialScreen> loadInitialScreen() {
        return userRepository
                .isUserLoggedIn()
                .map(loggedIn -> {
                    if (loggedIn) {
                        return InitialScreen.DASHBOARD;
                    } else {
                        return InitialScreen.AUTH;
                    }
                });
    }

    enum InitialScreen {
        AUTH, DASHBOARD
    }
}
