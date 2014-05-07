package edu.wwu.classfinder2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Bundle;

public class ActivityMain extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        TranscriptFragment transcriptFrag = new TranscriptFragment();
        ft.add(R.id.fragment_container, transcriptFrag);
        ft.commit();
    }

}
