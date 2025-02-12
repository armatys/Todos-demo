package pl.makenika.todos.ui.dashboard;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.CompletableSubject;
import pl.makenika.todos.data.UserRepository;
import pl.makenika.todos.di.qualifier.IoScheduler;
import pl.makenika.todos.di.qualifier.MainScheduler;
import pl.makenika.todos.net.TodoService;
import pl.makenika.todos.net.response.Todo;
import pl.makenika.todos.ui.data.Resource;

public class DashboardViewModel extends ViewModel {
    private CompositeDisposable disposables = new CompositeDisposable();

    private BehaviorSubject<Resource<List<Todo>>> todoListResource = BehaviorSubject.createDefault(new Resource.Idle<>());
    private CompletableSubject logoutSubject = CompletableSubject.create();

    private final Scheduler ioScheduler;
    private final Scheduler mainScheduler;
    private final TodoService todoService;
    private final UserRepository userRepository;

    @ViewModelInject
    public DashboardViewModel(@IoScheduler Scheduler ioScheduler, @MainScheduler Scheduler mainScheduler, TodoService todoService, UserRepository userRepository) {
        this.ioScheduler = ioScheduler;
        this.mainScheduler = mainScheduler;
        this.todoService = todoService;
        this.userRepository = userRepository;
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }

    public void loadTodoList() {
        todoListResource.onNext(new Resource.Loading<>());
        Disposable disposable = todoService.getTodos()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(todoList -> {
                    todoListResource.onNext(new Resource.Loaded<>(todoList));
                }, throwable -> {
                    todoListResource.onNext(new Resource.Error<>(throwable));
                });
        disposables.add(disposable);
    }

    public Completable getLogoutSignal() {
        return logoutSubject;
    }

    public Observable<Resource<List<Todo>>> getTodoListResource() {
        return todoListResource;
    }

    public void addNewTodo(String todoTitle) {
        if (todoTitle.isEmpty()) {
            return;
        }
        todoListResource.onNext(new Resource.Loading<>());
        Disposable disposable = todoService.createTodo(todoTitle)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(todo -> loadTodoList(), throwable -> {
                    todoListResource.onNext(new Resource.Error<>(throwable));
                });
        disposables.add(disposable);
    }

    public void logout() {
        Disposable disposable = userRepository
                .logout()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(() -> {
                    logoutSubject.onComplete();
                    logoutSubject.onComplete();
                });
        disposables.add(disposable);
    }
}
