package pl.makenika.todos.net.response;

import com.squareup.moshi.Json;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return createdAt.equals(todo.createdAt) &&
                createdBy.equals(todo.createdBy) &&
                id.equals(todo.id) &&
                items.equals(todo.items) &&
                title.equals(todo.title) &&
                updatedAt.equals(todo.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, createdBy, id, items, title, updatedAt);
    }
}
