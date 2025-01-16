package nure.sytnyk.polina.lab4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMPORTANCE = "importance";
    public static final String COLUMN_DATE_TIME = "dateTime";
    public static final String COLUMN_IMAGE_URI = "imageUri";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NOTES + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_IMPORTANCE + " INTEGER, "
                + COLUMN_DATE_TIME + " TEXT, "
                + COLUMN_IMAGE_URI + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public long addNote(String title, String description, int importance, String dateTime, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_IMPORTANCE, importance);
        values.put(COLUMN_DATE_TIME, dateTime);
        values.put(COLUMN_IMAGE_URI, imageUri);

        long id = db.insert(TABLE_NOTES, null, values);
        db.close();
        return id;
    }

    public List<Note> getAllNotes() {
        List<Note> notesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTES, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int titleIndex = cursor.getColumnIndex(COLUMN_TITLE);
                int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
                int importanceIndex = cursor.getColumnIndex(COLUMN_IMPORTANCE);
                int dateTimeIndex = cursor.getColumnIndex(COLUMN_DATE_TIME);
                int imageUriIndex = cursor.getColumnIndex(COLUMN_IMAGE_URI);

                if (idIndex != -1 && titleIndex != -1 && descriptionIndex != -1 &&
                        importanceIndex != -1 && dateTimeIndex != -1 && imageUriIndex != -1) {

                    long id = cursor.getLong(idIndex);
                    String title = cursor.getString(titleIndex);
                    String description = cursor.getString(descriptionIndex);
                    int importance = cursor.getInt(importanceIndex);
                    String dateTime = cursor.getString(dateTimeIndex);
                    String imageUriString = cursor.getString(imageUriIndex);

                    Uri imageUri = null;
                    if (imageUriString != null && !imageUriString.isEmpty()) {
                        try {
                            imageUri = Uri.parse(imageUriString);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    Note note = new Note(id, title, description, importance, dateTime, imageUri);
                    notesList.add(note);
                }

            } while (cursor.moveToNext());

            cursor.close();
        }
        db.close();
        return notesList;
    }

    public int updateNote(long id, String title, String description, int importance, String dateTime, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_IMPORTANCE, importance);
        values.put(COLUMN_DATE_TIME, dateTime);
        values.put(COLUMN_IMAGE_URI, imageUri);

        int rowsAffected = db.update(TABLE_NOTES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }

    public void deleteNote(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteAllNotes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, null, null);
        db.close();
    }
}