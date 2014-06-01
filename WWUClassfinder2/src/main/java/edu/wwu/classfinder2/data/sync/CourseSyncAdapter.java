package edu.wwu.classfinder2.data.sync;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.accounts.Account;

import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;

import android.os.Bundle;

import android.util.JsonReader;

import edu.wwu.classfinder2.data.Course;

public class CourseSyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String SUBJECT_URL =
        "cfinder.mcyamaha.com/subjects.php";

    private static final String TEST_URL =
        "cfinder.mcyamaha.com/test.php";

    // Global variables
    // Define a variable to contain a content resolver instance
    Context mContext;
    ContentResolver mContentResolver;

    /**
     * Set up the sync adapter
     */
    public CourseSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContext = context;
        mContentResolver = context.getContentResolver();
    }

    /**
     * Set up the sync adapter. This form of the constructor maintains
     * compatibility with Android 3.0 and later platform versions
     */
    public CourseSyncAdapter(
                             Context context,
                             boolean autoInitialize,
                             boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContext = context;
        mContentResolver = context.getContentResolver();

    }

    @Override
    public void onPerformSync(
                              Account account,
                              Bundle extras,
                              String authority,
                              ContentProviderClient provider,
                              SyncResult syncResult) {
        try {
            URL url = new URL(SUBJECT_URL);

            HttpURLConnection urlConnection =
                (HttpURLConnection) url.openConnection();
            try {
                JsonReader reader =
                    new JsonReader(
                        new InputStreamReader(urlConnection
                                              .getInputStream()));

                List<String> subjects = readSubjectList(reader);
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {

        } catch (IOException e) {

        }

    }

    private List<String> readSubjectList(JsonReader reader)
        throws IOException {

        List<String> subjects = new ArrayList<String>();

        reader.beginArray();
        while (reader.hasNext()) {
            subjects.add(readSubject(reader));
        }
        reader.endArray();

        return subjects;
    }

    private String readSubject(JsonReader reader)
        throws IOException {

        String subject = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("abbreviation")) {
                subject = reader.nextString();
            } else {
                reader.nextString();
            }
        }
        reader.endObject();
        return subject;
    }

    private List<Course> readCourseList(JsonReader reader)
        throws IOException {

        List<Course> courses = new ArrayList<Course>();

        reader.beginArray();
        while (reader.hasNext()) {
            courses.add(readCourse(reader));
        }
        reader.endArray();

        return courses;
    }

    private Course readCourse(JsonReader reader) throws IOException {
        Course course = new Course();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("crn")) {
                course.setCrn(reader.nextInt());
            } else if (name.equals("course")) {
                String[] subjectAndNumber =
                    reader.nextString().split(" ");
                course.setDepartment(subjectAndNumber[0]);
                course.setCourseNumber(Integer
                                       .parseInt(subjectAndNumber[1]));

            } else if (name.equals("name")) {
                course.setName(reader.nextString());

            } else if (name.equals("proff")) {
                // reader.nextString();
                // course.setInstructor(null);

            } else if (name.startsWith("schedule")) {
                //course.setSchedule(reader.nextString());

            } else if (name.equals("cap")) {
                course.setCapacity(reader.nextInt());

            } else if (name.equals("enrl")) {
                course.setEnrolled(reader.nextInt());

            } else if (name.equals("credits")) {
                course.setCredits(reader.nextInt());

            } else {
                reader.nextString();
            }
        }
        reader.endObject();

        return course;
    }

}
