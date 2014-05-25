package edu.wwu.classfinder2.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;

import android.database.Cursor;
import android.database.SQLException;

import android.net.Uri;

import edu.wwu.classfinder2.data.CourseDbHandler;
import edu.wwu.classfinder2.provider.ClassfinderContract.CourseContract;
import edu.wwu.classfinder2.provider.ClassfinderContract.InstructorContract;

public class CourseProvider extends ContentProvider {

    private static final int COURSE_LIST = 1;
    private static final int COURSE_ID = 2;
    private static final int INSTRUCTOR_LIST = 3;
    private static final int INSTRUCTOR_ID = 4;
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
            default:
                throw new IllegalArgumentException("Unsupported URI: "
                                                   + uri);
        }
    }

    @Override
    public Cursor query(Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {
        return null;
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
}
