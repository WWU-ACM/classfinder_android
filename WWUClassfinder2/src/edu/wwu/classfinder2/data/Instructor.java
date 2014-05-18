package edu.wwu.classfinder2.data;

import edu.wwu.classfinder2.provider.InstructorContract;

import android.content.ContentValues;

import android.database.Cursor;

public class Instructor {

    private long mId = -1;

    private String mFirstName;

    private String mLastName;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstname) {
        this.mFirstName = firstname;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastname) {
        this.mLastName = lastname;
    }

    public ContentValues asContentValues() {
        return asContentValues(new ContentValues());
    }

    public ContentValues asContentValues(ContentValues values) {
        values.put(InstructorContract._ID, mId);
        values.put(InstructorContract.FIRST_NAME, mFirstName);
        values.put(InstructorContract.LAST_NAME, mLastName);
        return values;
    }

    public static Instructor fromCursor(Cursor cursor) {
        Instructor instructor = new Instructor();

        int col = cursor.getColumnIndex(InstructorContract._ID);
        instructor.setId(cursor.getLong(col));

        col = cursor.getColumnIndex(InstructorContract.FIRST_NAME);
        instructor.setFirstName(cursor.getString(col));

        col = cursor.getColumnIndex(InstructorContract.LAST_NAME);
        instructor.setLastName(cursor.getString(col));
        return instructor;
    }
}
