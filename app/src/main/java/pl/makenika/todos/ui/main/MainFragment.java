package pl.makenika.todos.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import pl.makenika.todos.R;

@AndroidEntryPoint
public class MainFragment extends Fragment {
    private CompositeDisposable disposables = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        Disposable disposable = mainViewModel.loadInitialScreen().subscribe(this::showInitialScreen);
        disposables.add(disposable);
    }

    @Override
    public void onDestroyView() {
        disposables.dispose();
        super.onDestroyView();
    }

    private void showInitialScreen(MainViewModel.InitialScreen screen) {
        switch (screen) {
            case AUTH:
                navigateTo(MainFragmentDirections.actionMainFragmentToSignupFragment());
                break;
            case DASHBOARD:
                navigateTo(MainFragmentDirections.actionMainFragmentToDashboardFragment());
                break;
        }
    }

    private void navigateTo(NavDirections directions) {
        Navigation.findNavController(requireView()).navigate(directions);
    }
}
