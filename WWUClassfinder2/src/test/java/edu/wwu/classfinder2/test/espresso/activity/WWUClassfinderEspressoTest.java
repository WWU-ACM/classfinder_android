package edu.wwu.classfinder2.test.espresso.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import edu.wwu.classfinder2.R;
import edu.wwu.classfinder2.ActivityMain;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;

@LargeTest
public class WWUClassfinderEspressoTest extends ActivityInstrumentationTestCase2<ActivityMain> {

    @SuppressWarnings("deprecation")
    public WWUClassfinderEspressoTest() {
       // This constructor was deprecated - but we want to support lower API levels.
       super("edu.wwu.classfinder2", ActivityMain.class);
     }
    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Espresso will not launch our activity for us, we must launch it via getActivity().
        getActivity();
    }

}
