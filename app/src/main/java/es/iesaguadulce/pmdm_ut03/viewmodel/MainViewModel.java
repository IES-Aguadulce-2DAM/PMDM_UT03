package es.iesaguadulce.pmdm_ut03.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import es.iesaguadulce.pmdm_ut03.model.ToDo;
import es.iesaguadulce.pmdm_ut03.model.ToDoRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private ToDoRepository repository;
    private MutableLiveData<List<ToDo>> toDoList;

    public MainViewModel(Application application) {
        super(application);
        repository = new ToDoRepository(application.getBaseContext());
        toDoList = new MutableLiveData<>();
    }

    public MutableLiveData<List<ToDo>> obtener() {
        toDoList.setValue(repository.obtener());
        return toDoList;
    }

    public void insertar(ToDo toDo) {
        repository.insertar(toDo, new ToDoRepository.OnOperationCallback() {
            @Override
            public void onOperation() {
                toDoList.postValue(repository.obtener());
            }
        });
    }

    public void update(ToDo toDo) {
        Log.d("MainViewModel",
              "toDo: " + toDo.getTitle() + ". Aún no me han hecho el código, estoy esperando que el alumno lo escriba.");
        /*repository.update(toDo, new ToDoRepository.OnOperationCallback() {
            @Override
            public void onOperation() {
                toDoList.postValue(repository.obtener());
            }
        }); */
    }

    public void delete(ToDo toDo) {
        repository.delete(toDo, new ToDoRepository.OnOperationCallback() {
            @Override
            public void onOperation() {
                toDoList.postValue(repository.obtener());
            }
        });
    }

}
