package edu.wwu.classfinder2;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for
 * more information on how to write and extend Application tests.
 * <p/> To run this test, you can type: adb shell am instrument -w \
 * -e class edu.wwu.classfinder2.ActivityMainTest \
 * edu.wwu.classfinder2.tests/android.test.InstrumentationTestRunner
 */
public class ActivityMainTest
    extends ActivityInstrumentationTestCase2<ActivityMain> {

    public ActivityMainTest() {
        super("edu.wwu.classfinder2", ActivityMain.class);
    }

}
