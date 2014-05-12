package edu.wwu.classfinder2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentStart extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup contrainer, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_begin, null);
	}
}