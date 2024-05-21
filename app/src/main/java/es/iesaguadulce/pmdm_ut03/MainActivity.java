package es.iesaguadulce.pmdm_ut03;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import es.iesaguadulce.pmdm_ut03.model.ToDo;
import es.iesaguadulce.pmdm_ut03.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ToDoAdapter.OnDeleteListener {

    private ToDoAdapter adapter;
    private RecyclerView rvToDoList;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ToDoAdapter(this, this);
        rvToDoList = findViewById(R.id.rv_todo_list);
        // Esto solo lo hacemos si no va a cambiar el tamaño de la lista
        rvToDoList.setHasFixedSize(true);
        rvToDoList.setLayoutManager(new LinearLayoutManager(this));
        rvToDoList.setAdapter(adapter);
        setUpItemTouchHelper();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.obtener().observe(this, new Observer<List<ToDo>>() {
            @Override
            public void onChanged(List<ToDo> toDos) {
                adapter.setToDoList(toDos);
            }
        });

        FloatingActionButton fabAddToDo = findViewById(R.id.fab_add_todo);
        fabAddToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDo toDo = new ToDo("Tarea " + (adapter.getItemCount() + 1),
                                     "Descripción de la tarea " + (adapter.getItemCount() + 1), false);
                viewModel.insertar(toDo);
            }
        });

    }

    private void setUpItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        ToDo toDo = adapter.getToDoList().get(position);
                        if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                            viewModel.delete(toDo);
                        }
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvToDoList);
    }

    @Override
    public void onUpdate(int position) {
        viewModel.update(adapter.getToDoList().get(position));
    }
}
