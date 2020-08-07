package pl.makenika.todos.ui.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.PersistableBundle;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import pl.makenika.todos.R;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private CompositeDisposable allDisposable = new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        Disposable disposable = mainViewModel.loadInitialScreen().subscribe(initialScreen -> {
            // TODO
        });
        allDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        allDisposable.dispose();
        super.onDestroy();
    }
}
