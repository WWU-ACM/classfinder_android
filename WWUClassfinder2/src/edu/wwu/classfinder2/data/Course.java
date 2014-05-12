package edu.wwu.classfinder2.data;

import edu.wwu.classfinder2.provider.CourseContract;

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

        values.put(_ID, mId);
        values.put(CRN, mCrn);
        values.put(DEPARTMENT, mDepartment);
        values.put(COURSENUMBER, mCourseNumber);
        values.put(NAME, mName);
        values.put(INSTRUCTOR, mInstructor);
        values.put(SCHEDULE, mSchedule.asString());
        values.put(CAPACITY, mCapacity);
        values.put(ENROLLED, mEnrolled);
        values.put(CREDITS, mCredits);
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

    public static final String CONTENT_URI  =
        "content://classfinder/courses";

    // Database keys for this class
    public static final String _ID          = "_id";
    public static final String CRN          = "_crn";
    public static final String DEPARTMENT   = "_department";
    public static final String COURSENUMBER = "_coursenumber";
    public static final String NAME         = "_name";
    public static final String INSTRUCTOR   = "_instructor";
    public static final String SCHEDULE     = "_schedule";
    public static final String CAPACITY     = "_capacity";
    public static final String ENROLLED     = "_enrolled";
    public static final String CREDITS      = "_credits";
}
