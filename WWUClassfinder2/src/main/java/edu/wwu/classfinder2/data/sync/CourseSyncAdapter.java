package edu.wwu.classfinder2.data.sync;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.accounts.Account;

import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;

import android.os.Bundle;

import android.util.JsonReader;

public class CourseSyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TEST_URL = "cfinder.mcyamaha.com/test.php";

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
            URL url = new URL(TEST_URL);

            HttpURLConnection urlConnection =
                (HttpURLConnection) url.openConnection();
            try {
                JsonReader reader =
                    new JsonReader(
                        new InputStreamReader(urlConnection
                                              .getInputStream()));

            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {

        } catch (IOException e) {

        }

    }
}
