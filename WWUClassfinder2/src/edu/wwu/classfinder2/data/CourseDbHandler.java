package edu.wwu.classfinder2.data;

import edu.wwu.classfinder2.provider.CourseContract;
import edu.wwu.classfinder2.provider.InstructorContract;

import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CourseDbHandler {

    private Context mContext;
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "courses";

    private static final String CREATE_COURSE_TABLE =
        "CREATE TABLE " + CourseContract.TABLE + " ("
        + CourseContract._ID          + " INTEGER PRIMARY KEY, "
        + CourseContract.CRN          + " INTEGER, "
        + CourseContract.DEPARTMENT   + " TEXT, "
        + CourseContract.COURSENUMBER + " INTEGER, "
        + CourseContract.NAME         + " TEXT, "
        + CourseContract.INSTRUCTOR   + " INTEGER, "
        + CourseContract.SCHEDULE     + " TEXT, "
        + CourseContract.CAPACITY     + " INTEGER, "
        + CourseContract.ENROLLED     + " INTEGER, "
        + CourseContract.CREDITS      + " INTEGER, "
        + "FOREIGN KEY(" + CourseContract.INSTRUCTOR
                         + ") REFERENCES " + InstructorContract.TABLE;

    private static final String CREATE_INSTRUCTOR_TABLE =
        "CREATE TABLE " + InstructorContract.TABLE + " ("
        + InstructorContract._ID   + " INTEGER PRIMARY KEY, "
        + InstructorContract.FIRST_NAME + " TEXT, "
        + InstructorContract.LAST_NAME + " TEXT";

    public CourseDbHandler(Context context) {
        this.mContext = context;
    }

    public CourseDbHandler open() {
        mDbHelper = new DatabaseHelper(mContext);
        mDb = mDbHelper.getWritableDatabase();

        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public void insertCourse(Course course) {

    }

    public Cursor getAllCourses() {
        return null;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_INSTRUCTOR_TABLE);
            db.execSQL(CREATE_COURSE_TABLE);
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
