package edu.wwu.classfinder2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentPlannerPlan extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup contrainer, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_planner_plan, null);
		return view;
	}
}
