package edu.wwu.classfinder2;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivityMain extends Activity {

	private String[] mMenuTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mMenuTitles = getResources().getStringArray(R.array.drawer_menu);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer_menu);

		// Set adapter for list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_menu_item, mMenuTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			setDrawerContent(position);
		}
	}

	/* Swaps fragments in the main content view */
	private void setDrawerContent(int position) {

		Fragment fragment;

		switch (position) {
		case 0:
			fragment = new TranscriptFragment();
			break;
		case 1:
			fragment = new MainSearchFragment();
			break;
		default:
			fragment = null;
			break;
		}

		FragmentManager fm = getFragmentManager();
		fm.beginTransaction().replace(R.id.drawer_content, fragment).commit();

		mDrawerList.setItemChecked(position, true);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

}
