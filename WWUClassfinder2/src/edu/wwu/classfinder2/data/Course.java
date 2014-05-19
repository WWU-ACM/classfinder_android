package edu.wwu.classfinder2.data;

import edu.wwu.classfinder2.provider.CourseContract;

import android.content.ContentValues;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Course {

    private long mId = -1;

    private int mCrn;

    private String mDepartment;

    private int mCourseNumber;

    private String mName;

    private Instructor mInstructor;

    private Schedule mSchedule;

    private int mCapacity;

    private int mEnrolled;

    private int mCredits;

    public Course() {

    }

    public ContentValues asContentValues() {
        return asContentValues(new ContentValues());
    }

    public ContentValues asContentValues(ContentValues values) {
        values.put(_ID, mId);
        values.put(CRN, mCrn);
        values.put(DEPARTMENT, mDepartment);
        values.put(COURSENUMBER, mCourseNumber);
        values.put(NAME, mName);
        mInstructor.asContentValues(values);
        values.put(SCHEDULE, mSchedule.asString());
        values.put(CAPACITY, mCapacity);
        values.put(ENROLLED, mEnrolled);
        values.put(CREDITS, mCredits);
        return values;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public int getCrn() {
        return mCrn;
    }

    public void setCrn(int crn) {
        this.mCrn = crn;
    }

    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String department) {
        this.mDepartment = department;
    }

    public int getCourseNumber() {
        return mCourseNumber;
    }

    public void setCourseNumber(int coursenumber) {
        this.mCourseNumber = coursenumber;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public Instructor getInstructor() {
        return mInstructor;
    }

    public void setMInstructor(Instructor instructor) {
        this.mInstructor = instructor;
    }

    public Schedule getSchedule() {
        return mSchedule;
    }

    public void setSchedule(Schedule schedule) {
        this.mSchedule = schedule;
    }

    public int getCapacity() {
        return mCapacity;
    }

    public void setCapacity(int capacity) {
        this.mCapacity = capacity;
    }

    public int getEnrolled() {
        return mEnrolled;
    }

    public void setEnrolled(int enrolled) {
        this.mEnrolled = enrolled;
    }

    public int getCredits() {
        return mCredits;
    }

    public void setCredits(int credits) {
        this.mCredits = credits;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj == this || !(obj instanceof Course)) {
            return false;
        }

        Course oCourse = (Course) obj;
        return new EqualsBuilder()
            .append(mId, oCourse.mId)
            .append(mCrn, oCourse.mCrn)
            .append(mDepartment, oCourse.mDepartment)
            .append(mCourseNumber, oCourse.mCourseNumber)
            .append(mName, oCourse.mName)
            .append(mInstructor, oCourse.mInstructor)
            .append(mSchedule, oCourse.mSchedule)
            .append(mCapacity, oCourse.mCapacity)
            .append(mEnrolled, oCourse.mEnrolled)
            .append(mCredits, oCourse.mCredits)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
            .append(mId)
            .append(mCrn)
            .append(mDepartment)
            .append(mCourseNumber)
            .append(mName)
            .append(mInstructor)
            .append(mSchedule)
            .append(mCapacity)
            .append(mEnrolled)
            .append(mCredits)
            .toHashCode();
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
