package edu.wwu.classfinder2.data;

import java.util.Map;
import java.util.TreeMap;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.wwu.classfinder2.provider.ClassfinderContract.CourseContract;
import edu.wwu.classfinder2.provider.ClassfinderContract.InstructorContract;

public class CourseDbHandler {

    private Context mContext;
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "courses";

    private static final String CREATE_GLOBAL_TABLE =
        "CREATE TABLE " + GLOBALS.TABLE + " ("
        + GLOBALS._ID   + " INTEGER PRIMARY KEY,"
        + GLOBALS.KEY   + " TEXT UNIQUE,"
        + GLOBALS.VALUE + " TEXT)";

    private static final String CREATE_COURSE_TABLE =
        "CREATE TABLE " + CourseContract.TABLE + " ("
        + CourseContract._ID          + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + CourseContract.CRN          + " INTEGER, "
        + CourseContract.DEPARTMENT   + " TEXT, "
        + CourseContract.COURSENUMBER + " INTEGER, "
        + CourseContract.NAME         + " TEXT, "
        + CourseContract.INSTRUCTOR   + " INTEGER, "
        + CourseContract.SCHEDULE     + " TEXT, "
        + CourseContract.CAPACITY     + " INTEGER, "
        + CourseContract.ENROLLED     + " INTEGER, "
        + CourseContract.CREDITS      + " INTEGER, "
        + CourseContract.TERM         + " TEXT, "
        + "FOREIGN KEY(" + CourseContract.INSTRUCTOR
                         + ") REFERENCES " + InstructorContract.TABLE
        + ")";

    private static final String CREATE_INSTRUCTOR_TABLE =
        "CREATE TABLE " + InstructorContract.TABLE + " ("
        + InstructorContract._ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + InstructorContract.FIRST_NAME + " TEXT, "
        + InstructorContract.LAST_NAME + " TEXT )";

    public CourseDbHandler(Context context) {
        this.mContext = context;
    }

    public CourseDbHandler open() {
        if (mDbHelper == null) {
            mDbHelper = new DatabaseHelper(mContext);
        }
        if (mDb == null) {
            mDb = mDbHelper.getWritableDatabase();
        }

        return this;
    }

    public SQLiteDatabase getDatabase() {
        open();
        return mDbHelper.getReadableDatabase();
    }

    public void close() {
        if (mDbHelper == null) {
            mDbHelper.close();
        }
    }

    public Cursor getCurrentTerm() {
        return queryForGlobal(GLOBALS.CURRENT_TERM);
    }

    public long insertCourse(Course course) {

        // Call this before asContentValues.
        // ensureInstructorExists modifies the Instructor object
        // inside the course to make sure it corresponds to one from
        // the database.
        ensureInstructorExists(course.getInstructor());

        ContentValues values = course.asContentValues();
        return insertCourse(values);
    }

    public long insertCourse(ContentValues values) {
        return mDb.insert(CourseContract.TABLE, null, values);
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
                      "1",
                      null); // No cancellation signal

        if (results.getCount() == 1) {
            results.moveToFirst();
            return Instructor.fromCursor(results);
        } else {
            return new Instructor();
        }
    }

    private Cursor queryForGlobal(String key) {
        return mDb.query(true, // get one result
                         GLOBALS.TABLE, GLOBALS.PROJECTION,
                         GLOBALS.WHERE_KEY,
                         new String[] {key},
                         null,
                         null,
                         null,
                         "LIMIT 1");
    }

    private long insertGlobal(String key, String value) {
        ContentValues values = new ContentValues();
        values.put(GLOBALS.KEY, key);
        values.put(GLOBALS.VALUE, value);
        return mDb.insert(GLOBALS.TABLE, null, values);
    }

    private int updateGlobal(String key, String value) {
        ContentValues values = new ContentValues();
        values.put(GLOBALS.VALUE, value);
        return mDb.update(GLOBALS.TABLE, values,
                          GLOBALS.WHERE_KEY, new String[] {key});
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_INSTRUCTOR_TABLE);
            db.execSQL(CREATE_COURSE_TABLE);
            db.execSQL(CREATE_GLOBAL_TABLE);
            initGlobals(db);
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

        public void initGlobals(SQLiteDatabase db) {
            for (Map.Entry<String, String> entry:
                     GLOBALS.DEFAULTS.entrySet()) {
                ContentValues values = new ContentValues();
                values.put(GLOBALS.KEY, entry.getKey());
                values.put(GLOBALS.VALUE, entry.getValue());
                db.insert(GLOBALS.TABLE, null, values);
            }
        }

    }

    private static class GLOBALS {

        private static final String TABLE = "globals";

        public static final String _ID = "_id";
        public static final String KEY = "_key";
        public static final String VALUE = "_value";

        public static final String WHERE_KEY = KEY + " = ?";

        public static final String[] PROJECTION = {VALUE};

        public static final String CURRENT_TERM = "_currentTerm";

        public static final Map<String, String> DEFAULTS;

        static {
            DEFAULTS = new TreeMap<String, String>();
            DEFAULTS.put(CURRENT_TERM, "201440");
        }


    }
}
