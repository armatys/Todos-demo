package pl.makenika.todos.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import pl.makenika.todos.R;
import pl.makenika.todos.net.response.Todo;
import pl.makenika.todos.ui.data.Resource;

@AndroidEntryPoint
public class DashboardFragment extends Fragment {
    private TodoAdapter adapter;
    private CompositeDisposable disposables = new CompositeDisposable();
    private DashboardViewModel viewModel;

    private ViewGroup progressContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressContainer = view.findViewById(R.id.progress_container);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new TodoAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        Disposable disposable = viewModel.getTodoListResource()
                .subscribe(this::renderResource, throwable -> {
                    Snackbar.make(view, R.string.generic_error, Snackbar.LENGTH_LONG).show();
                });
        disposables.add(disposable);

        viewModel.loadTodoList();
    }

    @Override
    public void onDestroyView() {
        disposables.dispose();
        super.onDestroyView();
    }

    private void renderResource(Resource<List<Todo>> resource) {
        if (resource instanceof Resource.Idle) {
            progressContainer.setVisibility(View.GONE);
        } else if (resource instanceof Resource.Loading) {
            progressContainer.setVisibility(View.VISIBLE);
        } else if (resource instanceof Resource.Loaded) {
            progressContainer.setVisibility(View.GONE);
            List<Todo> todos = ((Resource.Loaded<List<Todo>>) resource).value;
            adapter.setTodos(todos);
        } else if (resource instanceof Resource.Error) {
            progressContainer.setVisibility(View.GONE);
        }
    }
}
