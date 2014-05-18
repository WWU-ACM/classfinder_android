package edu.wwu.classfinder2.data;

public class Instructor {

    private long mId;

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

}
