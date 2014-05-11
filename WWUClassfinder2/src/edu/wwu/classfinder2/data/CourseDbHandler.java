package edu.wwu.classfinder2.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class CourseDbHandler {

    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "courses";




    private static class DatabaseHelper extends SQLiteOpenHelper {

        Context context;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion,
                              int newVersion) {
        }

        @Override
        public void onDowngrade(SQLiteDatabase db,
                                int oldVersion,
                                int newVersion) {
        }

    }
}
