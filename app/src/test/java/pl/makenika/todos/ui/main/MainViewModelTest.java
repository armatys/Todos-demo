package pl.makenika.todos.ui.main;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.rxjava3.observers.TestObserver;

public class MainViewModelTest {
    private MainViewModel tested;

    @Before
    public void setUp() {
        tested = new MainViewModel();
    }

    @Test
    public void authNeeded() {
        TestObserver<MainViewModel.InitialScreen> observer = tested.loadInitialScreen().test();
        observer.assertValue(MainViewModel.InitialScreen.AUTH);
        observer.assertComplete();
    }
}