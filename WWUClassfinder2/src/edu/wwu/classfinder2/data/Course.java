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

        values.put(CourseContract._ID, mId);
        values.put(CourseContract.CRN, mCrn);
        values.put(CourseContract.DEPARTMENT, mDepartment);
        values.put(CourseContract.COURSENUMBER, mCourseNumber);
        values.put(CourseContract.NAME, mName);
        mInstructor.asContentValues(values);
        values.put(CourseContract.SCHEDULE, mSchedule.asString());
        values.put(CourseContract.CAPACITY, mCapacity);
        values.put(CourseContract.ENROLLED, mEnrolled);
        values.put(CourseContract.CREDITS, mCredits);

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

}
