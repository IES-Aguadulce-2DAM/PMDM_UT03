package es.iesaguadulce.pmdm_ut03.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ToDoRepository {

    private ToDoDBHelper dbHelper;
    private Executor executor;

    public interface OnOperationCallback {
        void onOperation();
    }

    public ToDoRepository(Context context) {
        dbHelper = new ToDoDBHelper(context, "ToDoDB", null, 1);
        executor = Executors.newSingleThreadExecutor();
    }

    public List<ToDo> obtener() {
        List<ToDo> toDos = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (!db.isOpen()) {
            return null;
        }

        Cursor cur = db.rawQuery("SELECT * FROM ToDO", null);
        if (cur.moveToFirst()) {
            do {
                String title = cur.getString(1);
                String description = cur.getString(2);
                boolean done = cur.getInt(3) == 1;
                toDos.add(new ToDo(title, description, done));
            } while (cur.moveToNext());
        }

        db.close();
        return toDos;
    }

    public void insertar(ToDo toDo, OnOperationCallback callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                if (!db.isOpen()) {
                    return;
                }

                short done = 0;
                if (toDo.isDone()) {
                    done = 1;
                }

                db.execSQL(
                        "INSERT INTO ToDo (title, description, done) VALUES ('" + toDo.getTitle() + "', '" + toDo.getDescription() + "', " + done + ")");
                db.close();
                callback.onOperation();
            }
        });
    }

    public void delete(ToDo toDo, OnOperationCallback callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                if (!db.isOpen()) {
                    return;
                }

                db.execSQL("DELETE FROM ToDo WHERE title = '" + toDo.getTitle() + "'");
                db.close();
                callback.onOperation();
            }
        });
    }

}
