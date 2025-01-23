package nure.sytnyk.polina.lab4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "notes.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "notes";
    private Uri imageUri;
    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, importance INTEGER, dateTime TEXT, imageUri TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addNote(String title, String desc, int imp, String date, String imgUri) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", desc);
        values.put("importance", imp);
        values.put("dateTime", date);
        values.put("imageUri", imgUri);
        return db.insert(TABLE_NAME, null, values);
    }

    public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            notes.add(new Note(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5) != null ? Uri.parse(cursor.getString(5)) : null
            ));
        }
        cursor.close();
        return notes;
    }

    public int updateNote(long id, String title, String desc, int imp, String date, String imgUri) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", desc);
        values.put("importance", imp);
        values.put("dateTime", date);
        values.put("imageUri", imgUri);
        return db.update(TABLE_NAME, values, "id = ?", new String[]{String.valueOf(id)});
    }

    public void deleteNote(long id) {
        getWritableDatabase().delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
    }


}

