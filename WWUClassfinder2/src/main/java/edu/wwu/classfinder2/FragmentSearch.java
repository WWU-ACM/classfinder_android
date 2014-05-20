package edu.wwu.classfinder2;

import android.app.ListFragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;

public class FragmentSearch extends ListFragment{

    public FragmentSearch() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null)
            return null;

        View rootView = inflater.inflate(R.layout.fragment_search,
                                         container,
                                         false);

        String[] values = new String[] { "Message1",
                                         "Message2",
                                         "Message3" };
        ArrayAdapter<String> adapter =
            new ArrayAdapter<String>(getActivity(),
                                     android.R.layout.simple_list_item_1,
                                     values);
        setListAdapter(adapter);
        return rootView;
    }

}
