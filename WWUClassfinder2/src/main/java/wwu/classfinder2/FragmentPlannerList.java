package edu.wwu.classfinder2;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/* This Fragment is not a actual ListView, but a hack that achieves the same result */
public class FragmentPlannerList extends Fragment {

    // Implemented by FragmentPlanner to listen for this fragment's callbacks
    public interface OnItemSelectedListener {
        public void onItemSelected(int id);
    }

    private ViewGroup mContainerView;
    private View view;

    public FragmentPlannerList() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contrainer, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_planner_list, null);
        mContainerView = (ViewGroup) view.findViewById(R.id.planner_container);
        addItem("");
        addItem("");
        addItem("");
        addItem("");
        return view;
    }

    public void addItem(String title) {

        view.findViewById(R.id.planner_empty).setVisibility(View.INVISIBLE);

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewGroup newView = (ViewGroup) inflater.inflate(R.layout.fragment_planner_list_item, mContainerView, false);

        TextView tv = (TextView) newView.findViewById(R.id.planner_item_text);
        ImageButton ib = (ImageButton) newView.findViewById(R.id.planner_item_delete);

        tv.setText("HELLO WORLD!");
        ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContainerView.removeView(newView);
                    if(mContainerView.getChildCount() == 0){
                        view.findViewById(R.id.planner_empty).setVisibility(View.VISIBLE);
                    }
                }
            });

        mContainerView.addView(newView, 0);
    }

}
