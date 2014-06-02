package edu.wwu.classfinder2.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Calendar;

import edu.wwu.classfinder2.data.Course;
import edu.wwu.classfinder2.data.CourseDbHandler;
import edu.wwu.classfinder2.provider.ClassfinderContract.CourseContract;
import edu.wwu.classfinder2.provider.ClassfinderContract.InstructorContract;

public class CourseProvider extends ContentProvider {

    private static final int COURSE_LIST = 1;
    private static final int COURSE_ID = 2;
    private static final int INSTRUCTOR_LIST = 3;
    private static final int INSTRUCTOR_ID = 4;
    private static final int CURRENT_TERM = 5;

    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(ClassfinderContract.AUTHORITY,
                           "courses",
                           COURSE_LIST);
        URI_MATCHER.addURI(ClassfinderContract.AUTHORITY,
                           "courses/#",
                           COURSE_ID);
        URI_MATCHER.addURI(ClassfinderContract.AUTHORITY,
                           "instructors",
                           INSTRUCTOR_LIST);
        URI_MATCHER.addURI(ClassfinderContract.AUTHORITY,
                           "instructors/#",
                           INSTRUCTOR_ID);
        URI_MATCHER.addURI(ClassfinderContract.AUTHORITY,
                           "currentTerm/#",
                           CURRENT_TERM);
    }

    private CourseDbHandler dbHandler;

    @Override
    public boolean onCreate() {
        this.dbHandler = new CourseDbHandler(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case COURSE_LIST :
                return CourseContract.DIR_CONTENT_TYPE;
            case COURSE_ID :
                return CourseContract.ITEM_CONTENT_TYPE;
            case INSTRUCTOR_LIST :
                return InstructorContract.DIR_CONTENT_TYPE;
            case INSTRUCTOR_ID :
                return InstructorContract.ITEM_CONTENT_TYPE;
            case CURRENT_TERM :
                return ClassfinderContract.TERM_ITEM_CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI: "
                                                   + uri);
        }
    }

    /**
     * Selection for multiple courses should always include a year and
     * quarter. If none is specified, the "current" year and quarter
     * will be used.  With the meaning of current still
     * to-be-determined.
     */
    @Override
    public Cursor query(Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        switch (URI_MATCHER.match(uri)) {
            case COURSE_ID :
                builder.appendWhere(CourseContract._ID + " = ");
                builder.appendWhereEscapeString(uri.getLastPathSegment());
                builder.setTables(CourseContract.TABLE);
                break;

            case COURSE_LIST :
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = CourseContract.SORT_ORDER_DEFAULT;
                }
                ensureTerm(builder, selection);
                builder.setTables(CourseContract.TABLE);
                break;

            case INSTRUCTOR_ID :
                builder.appendWhere(InstructorContract._ID + " = ");
                builder.appendWhereEscapeString(uri.getLastPathSegment());
            case INSTRUCTOR_LIST :
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = InstructorContract.SORT_ORDER_DEFAULT;
                }
                builder.setTables(InstructorContract.TABLE);
                break;

            case CURRENT_TERM :

                break;

            default:
                throw new IllegalArgumentException("Unsupported URI: "
                                                   + uri);
        }

        SQLiteDatabase db = dbHandler.getDatabase();
        return builder.query(db, projection,
                selection, selectionArgs,
                null, null, sortOrder);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriMatch = URI_MATCHER.match(uri);

        if (uriMatch != COURSE_LIST) {
            throw new IllegalArgumentException(
                "Unsupported URI for insertion: " + uri);
        }

        dbHandler.open();
        long id = dbHandler.insertCourse(values);
        return getUriForId(id, uri);
    }

    @Override
    public int delete(Uri uri, String selection,
                      String[] selectionArgs) {
        return 0;
    }

    public int update(Uri uri,
                      ContentValues values,
                      String selection,
                      String[] selectionArgs) {
        return 0;
    }

    private Uri getUriForId(long id, Uri uri) {
        if (id > 0) {
            Uri itemUri = ContentUris.withAppendedId(uri, id);
            getContext().
                getContentResolver().
                notifyChange(itemUri, null);
            return itemUri;
        }

        // something went wrong:
        throw new SQLException(
            "Problem while inserting into uri: " + uri);
    }


    private void ensureTerm(SQLiteQueryBuilder builder,
                            String selection) {
        if (selection != null) {
            if (!selection.contains(CourseContract.TERM)) {
                throw new
                        IllegalArgumentException("Must include "
                        + CourseContract.TERM
                        + " in the selection clause.");
            }
        }
    }

}
