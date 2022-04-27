package com.example.library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE = "library.db";
    public static final int VER = 1;

    private static final String TABLENAME = "books";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "book_title";
    private static final String COLUMN_AUTHOR = "book_author";
    private static final String COLUMN_DESCRIPTION = "book_description";
    private static final String COLUMN_CATEGORY = "book_category";
    private static final String COLUMN_PUBLISHER = "book_publisher";


    DB(@Nullable Context context) {
        super(context, DATABASE, null, VER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TABLENAME +"(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_PUBLISHER + " TEXT);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "DROP TABLE IF EXISTS "+ TABLENAME;
        db.execSQL(query);
        onCreate(db);
    }

    public void addBook(String title,String author,String description,String category,String publisher){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_PUBLISHER, publisher);
        long result = db.insert(TABLENAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_LONG).show();
        }


    }

    Cursor readallData(){
        String query = "SELECT * FROM "+ TABLENAME+"";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
           cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String title,String author,String category,String publisher, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_PUBLISHER, publisher);


        long result = db.update(TABLENAME,cv,"_id=?", new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, "Updated Successfully", Toast.LENGTH_LONG).show();
        }
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLENAME,"_id=?", new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_LONG).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLENAME);
    }
}
