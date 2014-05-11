package edu.wwu.classfinder2.data;

public class Course {

    private long mId;

    private int mCrn;

    private String mDepartment;

    private int mCourseNumber;

    private String mName;

    private String mInstructor;

    private Schedule mSchedule;

    private int mCapacity;

    private int mEnrolled;

    private int mCredits;

    public int getCredits() {
        return mCredits;
    }

    public void setCredits(int mCredits) {
        this.mCredits = mCredits;
    }

    public int getEnrolled() {
        return mEnrolled;
    }

    public void setEnrolled(int mEnrolled) {
        this.mEnrolled = mEnrolled;
    }

    public int getCapacity() {
        return mCapacity;
    }

    public void setCapacity(int mCapacity) {
        this.mCapacity = mCapacity;
    }

    public String getMName() {
        return mName;
    }

    public void setMName(String mName) {
        this.mName = mName;
    }

    public Schedule getSchedule() {
        return mSchedule;
    }

    public void setSchedule(Schedule mSchedule) {
        this.mSchedule = mSchedule;
    }

    public String getInstructor() {
        return mInstructor;
    }

    public void setInstructor(String mInstructor) {
        this.mInstructor = mInstructor;
    }

    public int getCourseNumber() {
        return mCourseNumber;
    }

    public void setCourseNumber(int mCourseNumber) {
        this.mCourseNumber = mCourseNumber;
    }

    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String mDepartment) {
        this.mDepartment = mDepartment;
    }

    public int getCrn() {
        return mCrn;
    }

    public void setCrn(int mCrn) {
        this.mCrn = mCrn;
    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

}
