package es.iesaguadulce.pmdm_ut03.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoDBHelper extends SQLiteOpenHelper {

    public ToDoDBHelper(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE ToDo (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, done INTEGER CHECK(done IN (0, 1)))");
        db.execSQL("INSERT INTO ToDo (title, description, done) VALUES ('Tarea 1', 'Descripción de la tarea 1', 0)");
        db.execSQL("INSERT INTO ToDo (title, description, done) VALUES ('Tarea 2', 'Descripción de la tarea 2', 1)");
        db.execSQL("INSERT INTO ToDo (title, description, done) VALUES ('Tarea 3', 'Descripción de la tarea 3', 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ToDo");
        db.execSQL(
                "CREATE TABLE ToDo (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, done INTEGER CHECK(done IN (0, 1)))");
    }
}
