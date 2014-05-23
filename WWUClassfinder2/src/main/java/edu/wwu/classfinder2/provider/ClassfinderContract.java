package edu.wwu.classfinder2.provider;

import android.content.Context;

import android.net.Uri;

import edu.wwu.classfinder2.R;

public final class ClassfinderContract {

    public static String AUTHORITY = "edu.wwu.classfinder2.provider";

    public static final Uri CONTENT_URI =
        Uri.parse("content://" + AUTHORITY);

    public static final class CourseContract {

        public static final Uri CONTENT_URI =
            Uri.withAppendedPath(ClassfinderContract.CONTENT_URI,
                                 "courses");

        public static final String TABLE = "tblCourses";

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

}
