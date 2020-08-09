package pl.makenika.todos.ui.dashboard;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.schedulers.TestScheduler;
import io.reactivex.rxjava3.subscribers.TestSubscriber;
import pl.makenika.todos.net.TodoService;
import pl.makenika.todos.net.response.Todo;
import pl.makenika.todos.ui.data.Resource;
import retrofit2.HttpException;

import static org.junit.Assert.*;

public class DashboardViewModelTest {
    @Mock
    private TodoService todoService;

    private TestScheduler scheduler;
    private DashboardViewModel tested;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        scheduler = new TestScheduler();
        tested = new DashboardViewModel(scheduler, scheduler, todoService);
    }

    @Test
    public void loadTodos() {
        Mockito.when(todoService.getTodos()).thenReturn(Single.just(Collections.singletonList(sampleTodo)));
        TestObserver<Resource<List<Todo>>> observer = tested.getTodoListResource().test();
        tested.loadTodoList();
        scheduler.triggerActions();
        observer.assertValues(
                new Resource.Idle<>(),
                new Resource.Loading<>(),
                new Resource.Loaded<>(Collections.singletonList(sampleTodo))
        );
    }

    @Test
    public void loadingError() {
        final Throwable throwable = new Throwable("test error");
        Mockito.when(todoService.getTodos()).thenReturn(Single.error(throwable));
        TestObserver<Resource<List<Todo>>> observer = tested.getTodoListResource().test();
        tested.loadTodoList();
        scheduler.triggerActions();
        observer.assertValues(
                new Resource.Idle<>(),
                new Resource.Loading<>(),
                new Resource.Error<>(throwable)
        );
    }

    private Todo sampleTodo = new Todo(new Date(), "a", "1", Collections.emptyList(), "title", new Date());
}