package pl.makenika.todos.net.response;

import com.squareup.moshi.Json;

import java.util.Date;
import java.util.List;

public class Todo {
    @Json(name = "created_at")
    public final Date createdAt;

    @Json(name = "created_by")
    public final String createdBy;

    public final String id;

    public final List<TodoItem> items;

    public final String title;

    @Json(name = "updated_at")
    public final Date updatedAt;

    public Todo(Date createdAt, String createdBy, String id, List<TodoItem> items, String title, Date updatedAt) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.id = id;
        this.items = items;
        this.title = title;
        this.updatedAt = updatedAt;
    }
}
