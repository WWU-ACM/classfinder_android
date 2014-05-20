package edu.wwu.classfinder2;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class FragmentPlanner extends Fragment implements FragmentPlannerList.OnItemSelectedListener {

    ViewPager mViewPager;
    ActionBar mActionBar;

    public FragmentPlanner() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentManager fm = getChildFragmentManager();
        View view = inflater.inflate(R.layout.fragment_planner, container, false);

        // Don't do anything if we're being restored from a previous state
        if (savedInstanceState != null) {
            return view;
        }

        // We we are using the single pane view, add our list fragment
        if (view.findViewById(R.id.fragment_planner_single) != null) {
            FragmentPlannerList fpl = new FragmentPlannerList();
            fpl.setTargetFragment(this, 0);
            fm.beginTransaction().add(R.id.fragment_planner_single, fpl, "PlannerList").commit();
        }
        // Otherwise we are in dual-view, add both fragments to the list
        else {
            FragmentPlannerList fpl = new FragmentPlannerList();
            FragmentPlannerPlan fpp = new FragmentPlannerPlan();
            fpl.setTargetFragment(this, 0);
            fm.beginTransaction().add(R.id.fragment_planner_list, fpl, "PlannerList").commit();
            fm.beginTransaction().add(R.id.fragment_planner_plan, fpp).commit();
        }

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // i can haz menu?
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_planner_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                FragmentPlannerList fpl = (FragmentPlannerList) getChildFragmentManager().findFragmentByTag("PlannerList");
                fpl.addItem("");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(int id) {

    }
}
