package es.iesaguadulce.pmdm_ut03;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import es.iesaguadulce.pmdm_ut03.model.ToDo;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    private final Context context;
    private List<ToDo> todoList;
    private OnDeleteListener listener;

    public interface OnDeleteListener {
        void onUpdate(int position);
    }

    public ToDoAdapter(Context context, OnDeleteListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_todo, viewGroup, false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        holder.tvTitle.setText(todoList.get(position).getTitle());
        holder.tvDescription.setText(todoList.get(position).getDescription());
        holder.cbStatus.setChecked(todoList.get(position).isDone());
    }

    public List<ToDo> getToDoList() {
        return todoList;
    }

    public void setToDoList(List<ToDo> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (todoList != null) {
            return todoList.size();
        }

        return 0;
    }

    public class ToDoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView tvTitle;
        protected TextView tvDescription;
        protected CheckBox cbStatus;

        public ToDoViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.todo_title);
            tvDescription = itemView.findViewById(R.id.todo_description);
            cbStatus = itemView.findViewById(R.id.todo_status);
        }

        @Override
        public void onClick(View v) {
            listener.onUpdate(getAdapterPosition());
        }
    }
}
