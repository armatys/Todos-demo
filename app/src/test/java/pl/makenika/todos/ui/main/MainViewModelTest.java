package pl.makenika.todos.ui.main;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;
import pl.makenika.todos.data.AppPrefs;
import pl.makenika.todos.data.UserRepository;

public class MainViewModelTest {
    @Mock
    private UserRepository userRepository;

    private MainViewModel tested;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tested = new MainViewModel(userRepository);
    }

    @Test
    public void authNeeded() {
        Mockito.when(userRepository.isUserLoggedIn()).thenReturn(Single.just(false));
        TestObserver<MainViewModel.InitialScreen> observer = tested.loadInitialScreen().test();
        observer.assertValue(MainViewModel.InitialScreen.AUTH);
        observer.assertComplete();
    }

    @Test
    public void loggedIn() {
        Mockito.when(userRepository.isUserLoggedIn()).thenReturn(Single.just(true));
        TestObserver<MainViewModel.InitialScreen> observer = tested.loadInitialScreen().test();
        observer.assertValue(MainViewModel.InitialScreen.DASHBOARD);
        observer.assertComplete();
    }
}
