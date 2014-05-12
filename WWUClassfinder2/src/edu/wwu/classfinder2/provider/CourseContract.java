package edu.wwu.classfinder2.provider;

import android.net.Uri;

public final class CourseContract {

    public static final Uri CONTENT_URI;

    static {
        Uri.Builder ub = new Uri.Builder();
        ub.scheme("content")
            .authority("edu.wwu.classfinder2.provider")
            .path("courses");
        CONTENT_URI = ub.build();
    }

    // Content Provider keys
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
