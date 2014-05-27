package edu.wwu.classfinder2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

public class FragmentSearch extends Fragment {

    private String currentQuery = "";

    public FragmentSearch() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
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
        /* performs a search if more than 3 letters has been typed since last known query */
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

        View view = getView();
        TextView et = (TextView) view.findViewById(R.id.search_text);
        et.setText(query);
    }
}
