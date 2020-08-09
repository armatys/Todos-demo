package pl.makenika.todos.net;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import pl.makenika.todos.net.response.Todo;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TodoService {
    @GET("/todos")
    Single<List<Todo>> getTodos();

    @POST("/todos")
    @FormUrlEncoded
    Single<Todo> createTodo(@Field("title") String title);
}
