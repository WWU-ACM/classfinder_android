package edu.wwu.classfinder2.provider;

import android.content.ContentProvider;
import android.content.ContentValues;

import android.database.Cursor;

import android.net.Uri;

import edu.wwu.classfinder2.data.CourseDbHandler;

public class CourseProvider extends ContentProvider {

    private CourseDbHandler dbHandler;

    @Override
    public boolean onCreate() {
        this.dbHandler = new CourseDbHandler(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        return new String();
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
        return null;
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
}
