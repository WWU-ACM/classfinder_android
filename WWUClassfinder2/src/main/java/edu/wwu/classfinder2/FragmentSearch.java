package edu.wwu.classfinder2;

import android.animation.LayoutTransition;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import edu.wwu.classfinder2.data.Course;
import edu.wwu.classfinder2.data.CourseDbHandler;
import edu.wwu.classfinder2.data.Instructor;
import edu.wwu.classfinder2.provider.ClassfinderContract;

public class FragmentSearch extends Fragment {

    private String currentQuery = "";
    private SearchLayout resultProfessors;
    private SearchLayout resultSubjects;
    private SearchLayout resultCrn;
    private SearchLayout resultGur;

    public FragmentSearch() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().deleteDatabase("courses");

        CourseDbHandler cdh = new CourseDbHandler(getActivity());
        cdh.open();

        Instructor instructor = new Instructor();
        instructor.setId(1);
        instructor.setFirstName("First Name");
        instructor.setLastName("Last Name");

        for (int i = 0; i < 100; i++){
            Course c = new Course();
            c.setId(i);
            c.setName("BLABLABLA " + Integer.toString(i));
            c.setCourseNumber(i);
            c.setCrn(1000 + i);
            c.setInstructor(instructor);
            cdh.insertCourse(c);
        }

        SQLiteDatabase db = cdh.getDatabase();

        Cursor c = db.rawQuery("SELECT * from " + ClassfinderContract.CourseContract.TABLE, null);
        while (c.moveToNext()) {
            int numCols = c.getColumnCount();
            String result = "";
            for (int i = 0; i < c.getColumnCount(); i++){
                result += "'" + c.getColumnName(i) + "'" + ": '" + c.getString(i) + "' " ;
            }
            Log.d("HI c", result);
        }

        ContentResolver resolver = getActivity().getContentResolver();
        Cursor d = resolver.query(ClassfinderContract.CourseContract.CONTENT_URI, null, null, null, null);
        Log.d("HI d", Integer.toString(d.getCount()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        LinearLayout searchContainer = (LinearLayout) view.findViewById(R.id.search_container);

        resultProfessors = new SearchLayout(getActivity(), "Professors");
        resultSubjects = new SearchLayout(getActivity(), "Subjects");
        resultCrn = new SearchLayout(getActivity(), "CRNs");
        resultGur = new SearchLayout(getActivity(), "GURs");

        searchContainer.addView(resultSubjects);
        searchContainer.addView(resultProfessors);
        searchContainer.addView(resultCrn);
        searchContainer.addView(resultGur);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                autoSearch(newText);
                return false;
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* For search as you type */
    private void autoSearch(String newText) {
        /* performs a search if more than 3 letters has been typed since the last known query */
        if (Math.abs(currentQuery.length() - newText.length()) > 2) {
            currentQuery = newText;
            search(currentQuery);
        }
        /* resets the query if we get empty string */
        if (newText.equals("")) {
            currentQuery = "";
        }
    }

    /* Performs the search */
    private void search(String query) {

        currentQuery = query;

    }

    private class SearchLayout extends LinearLayout {

        private String title;
        private Context context;

        public SearchLayout(Context context, String title) {
            super(context);
            this.context = context;
            this.title = title;
            init();
        }

        private void init() {
            this.setOrientation(LinearLayout.VERTICAL);
            this.setDividerDrawable(getResources().getDrawable(android.R.drawable.divider_horizontal_bright));
            this.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            LayoutTransition lt = new LayoutTransition();
            lt.disableTransitionType(LayoutTransition.DISAPPEARING);
            lt.disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
            this.setLayoutTransition(lt);

            LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0);

            TextView titleText = new TextView(context);

            int paddingPx = 10;
            float density = context.getResources().getDisplayMetrics().density;
            int paddingDp = (int) (paddingPx * density);
            titleText.setPadding(0, paddingDp, 0, paddingDp);
            titleText.setLayoutParams(params);
            titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            titleText.setText(title);
            titleText.setMaxLines(1);
            this.addView(titleText);

            addItem("HELLO WORLD! 01 wwww wwww wwww wwwww wwwwwwww wwwwwwwww wwwwwww wwww wwww");
            addItem("HELLO WORLD! 02 wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
            addItem("HELLO WORLD! 03 wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
            addItem("HELLO WORLD! 04 wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
            addItem("HELLO WORLD! 05 wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        }

        private void addItem(String text) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final LinearLayout newView = (LinearLayout) inflater.inflate(R.layout.fragment_search_item, this, false);

            final TextView tv = (TextView) newView.findViewById(R.id.search_item_text);
            tv.setText(text);

            final SearchLayout hi = this;

            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("HI", "CLICKED");
                }
            });

            this.addView(newView);
        }

    }
}
