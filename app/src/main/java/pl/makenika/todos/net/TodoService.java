package pl.makenika.todos.net;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import pl.makenika.todos.net.response.Todo;
import retrofit2.http.GET;

public interface TodoService {
    @GET("/todos")
    Single<List<Todo>> getTodos();
}
