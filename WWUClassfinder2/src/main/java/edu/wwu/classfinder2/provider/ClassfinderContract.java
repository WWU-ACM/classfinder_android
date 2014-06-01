package edu.wwu.classfinder2.provider;

import android.content.ContentResolver;
import android.content.Context;

import android.net.Uri;

import edu.wwu.classfinder2.R;

public final class ClassfinderContract {

    public static String AUTHORITY = "edu.wwu.classfinder2.provider";

    public static String BASE_TYPE = "/vnd.edu.wwu.classfinder2.";

    public static final Uri CONTENT_URI =
        Uri.parse("content://" + AUTHORITY);

    private static final String TERM_SUFFIX = "currentTerm";

    public static final Uri CURRENT_TERM_URI =
        Uri.withAppendedPath(CONTENT_URI,
                             TERM_SUFFIX);

    public static final String TERM_ITEM_CONTENT_TYPE =
        ContentResolver.CURSOR_ITEM_BASE_TYPE
        + BASE_TYPE
        + TERM_SUFFIX;

    public static final class CourseContract {

        private static final String SUFFIX = "courses";

        public static final Uri CONTENT_URI =
            Uri.withAppendedPath(ClassfinderContract.CONTENT_URI,
                                 SUFFIX);

        public static final String DIR_CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE
            + ClassfinderContract.BASE_TYPE
            + SUFFIX;

        public static final String ITEM_CONTENT_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE
            + ClassfinderContract.BASE_TYPE
            + SUFFIX;

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
        public static final String TERM         = "_term";

        public static final String[] PROJECTION_ALL =
        {_ID, CRN, DEPARTMENT, COURSENUMBER,
         NAME, INSTRUCTOR, SCHEDULE, CAPACITY,
         ENROLLED, CREDITS, TERM};

        public static final String SORT_ORDER_DEFAULT =
            DEPARTMENT + ", " + COURSENUMBER + " ASC";

    }

    public static final class InstructorContract {

        private static final String SUFFIX = "instructors";

        public static final Uri CONTENT_URI =
            Uri.withAppendedPath(ClassfinderContract.CONTENT_URI,
                                 SUFFIX);

        public static final String DIR_CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE
            + ClassfinderContract.BASE_TYPE
            + SUFFIX;

        public static final String ITEM_CONTENT_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE
            + ClassfinderContract.BASE_TYPE
            + SUFFIX;

        public static final String TABLE = "tblInstructors";

        // Content Provider keys
        public static final String _ID        = "_id";
        public static final String FIRST_NAME = "_first_name";
        public static final String LAST_NAME  = "_last_name";

        public static final String[] PROJECTION_ALL =
        {_ID, FIRST_NAME, LAST_NAME};

        public static final String SORT_ORDER_DEFAULT =
            LAST_NAME + ", " + FIRST_NAME + " ASC";

    }

}
