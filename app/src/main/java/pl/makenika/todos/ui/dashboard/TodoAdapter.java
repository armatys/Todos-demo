package pl.makenika.todos.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import pl.makenika.todos.R;
import pl.makenika.todos.net.response.Todo;

public class TodoAdapter extends ListAdapter<Todo, TodoAdapter.TodoViewHolder> {
    public TodoAdapter() {
        super(diffUtilCallback);
    }

    private final static DiffUtil.ItemCallback<Todo> diffUtilCallback = new DiffUtil.ItemCallback<Todo>() {
        @Override
        public boolean areItemsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_view, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        final Todo todo = getItem(position);
        holder.titleView.setText(todo.title);
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        public final TextView titleView;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title_view);
        }
    }
}
