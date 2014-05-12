package edu.wwu.classfinder2.data;

import android.content.ContentValues;

public class Course {

    private long mId = -1;

    private int mCrn;

    private String mDepartment;

    private int mCourseNumber;

    private String mName;

    private String mInstructor;

    private Schedule mSchedule;

    private int mCapacity;

    private int mEnrolled;

    private int mCredits;

    public Course() {

    }

    public ContentValues asContentValues() {
        ContentValues values = new ContentValues();

        values.put(ID_KEY, mId);
        values.put(CRN_KEY, mCrn);
        values.put(DEPARTMENT_KEY, mDepartment);
        values.put(COURSENUMBER_KEY, mCourseNumber);
        values.put(NAME_KEY, mName);
        values.put(INSTRUCTOR_KEY, mInstructor);
        values.put(SCHEDULE_KEY, mSchedule);
        values.put(CAPACITY_KEY, mCapacity);
        values.put(ENROLLED_KEY, mEnrolled);
        values.put(CREDITS_KEY, mCredits);
        return values;
    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public int getCrn() {
        return mCrn;
    }

    public void setCrn(int mCrn) {
        this.mCrn = mCrn;
    }

    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String mDepartment) {
        this.mDepartment = mDepartment;
    }

    public int getCourseNumber() {
        return mCourseNumber;
    }

    public void setCourseNumber(int mCourseNumber) {
        this.mCourseNumber = mCourseNumber;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getInstructor() {
        return mInstructor;
    }

    public void setInstructor(String mInstructor) {
        this.mInstructor = mInstructor;
    }

    public Schedule getSchedule() {
        return mSchedule;
    }

    public void setSchedule(Schedule mSchedule) {
        this.mSchedule = mSchedule;
    }


    public int getCapacity() {
        return mCapacity;
    }

    public void setCapacity(int mCapacity) {
        this.mCapacity = mCapacity;
    }

    public int getEnrolled() {
        return mEnrolled;
    }

    public void setEnrolled(int mEnrolled) {
        this.mEnrolled = mEnrolled;
    }

    public int getCredits() {
        return mCredits;
    }

    public void setCredits(int mCredits) {
        this.mCredits = mCredits;
    }

    // Database keys for this class
    public static final String ID_KEY           = "_id";
    public static final String CRN_KEY          = "_crn";
    public static final String DEPARTMENT_KEY   = "_department";
    public static final String COURSENUMBER_KEY = "_coursenumber";
    public static final String NAME_KEY         = "_name";
    public static final String INSTRUCTOR_KEY   = "_instructor";
    public static final String SCHEDULE_KEY     = "_schedule";
    public static final String CAPACITY_KEY     = "_capacity";
    public static final String ENROLLED_KEY     = "_enrolled";
    public static final String CREDITS_KEY      = "_credits";
}
