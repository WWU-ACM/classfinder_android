package edu.wwu.classfinder2.data;

import edu.wwu.classfinder2.provider.ClassfinderContract.CourseContract;
import edu.wwu.classfinder2.provider.InstructorContract;

import android.content.ContentValues;
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

        // Call this before asContentValues.
        // ensureInstructorExists modifies the Instructor object
        // inside the course to make sure it corresponds to one from
        // the database.
        ensureInstructorExists(course.getInstructor());

        ContentValues values = course.asContentValues();
        mDb.insert(CourseContract.TABLE, null, values);
    }

    public Cursor getAllCourses() {
        return null;
    }

    private boolean insertInstructor(Instructor instructor) {
        ContentValues values = instructor.asContentValues();

        long id = mDb.insert(InstructorContract.TABLE, null, values);
        instructor.setId(id);
        return id != -1;
    }

    private boolean ensureInstructorExists(Instructor instructor) {

        if (instructor.getId() != -1) {
            Instructor fromDb = getInstructorById(instructor.getId());
            return fromDb.equals(instructor);
        } else {
            return insertInstructor(instructor);
        }
    }

    private Instructor getInstructorById(long id) {
        Cursor results =
            mDb.query(true, InstructorContract.TABLE,
                      new String[] {InstructorContract._ID,
                                    InstructorContract.FIRST_NAME,
                                    InstructorContract.LAST_NAME},
                      InstructorContract._ID + " = ?",
                      new String[] {Long.toString(id)},
                      null, // No group by clause
                      null, // Same, but mandatory because no grouping
                      null, // Default sort order
                      "LIMIT 1",
                      null); // No cancellation signal

        if (results.getCount() == 1) {
            results.moveToFirst();
            return Instructor.fromCursor(results);
        } else {
            return new Instructor();
        }
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
