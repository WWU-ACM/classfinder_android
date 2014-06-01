package edu.wwu.classfinder2.data.sync;


import android.accounts.Account;
import android.app.NotificationManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

import edu.wwu.classfinder2.R;

public class CourseSyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String URL = "http://cfinder.mcyamaha.com/test.php";
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
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(URL));
            StatusLine statusLine = response.getStatusLine();
            NotificationCompat.Builder builder =
                new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.logo_classfinder)
                .setContentTitle("Course Sync Adapter");

            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                builder.setContentText("Got the JSON stub");
            } else{
                builder.setContentText("Something bad happened... "
                                       + "No JSON for us.");
            }
            NotificationManager nm =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(1, builder.build());

            response.getEntity().getContent().close();

        } catch (IOException e) {
            // FIXME: actually do stuff?
        } finally {


        }
    }

}
