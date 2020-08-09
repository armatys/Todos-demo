package pl.makenika.todos.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.makenika.todos.R;
import pl.makenika.todos.net.response.Todo;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<Todo> todoList = new ArrayList<>();

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_view, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        final Todo todo = todoList.get(position);
        holder.titleView.setText(todo.title);
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void setTodos(List<Todo> todos) {
        todoList = todos;
        notifyDataSetChanged();
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        public final TextView titleView;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title_view);
        }
    }
}
