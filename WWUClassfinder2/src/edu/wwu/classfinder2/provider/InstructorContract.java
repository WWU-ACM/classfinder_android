package edu.wwu.classfinder2.provider;

import android.net.Uri;

public class InstructorContract {

    public static final Uri CONTENT_URI;

    static {
        Uri.Builder ub = new Uri.Builder();
        ub.scheme("content")
            .authority("edu.wwu.classfinder2.provider")
            .path("instructors");
        CONTENT_URI = ub.build();
    }

    public static final String TABLE = "tblInstructors";

    // Content Provider keys
    public static final String _ID        = "_id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME  = "last_name";

}
