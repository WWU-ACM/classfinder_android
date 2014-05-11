package edu.wwu.classfinder2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityMain extends Activity {

	private String mTitle;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = getTitle().toString();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer_menu);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_navigation_drawer,
				R.string.drawer_open, R.string.drawer_close) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(R.string.app_name);
			}
			@Override
			public void onDrawerClosed(View drawerView){
				super.onDrawerClosed(drawerView);
				getActionBar().setTitle(mTitle);
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// Set adapter for list view
		mDrawerList.setAdapter(new DrawerMenuAdapter(this));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// Open the drawer on startup
		if (savedInstanceState == null || !savedInstanceState.getBoolean("ConfigChange")) {
			mDrawerLayout.openDrawer(mDrawerList);
			getFragmentManager().beginTransaction().add(R.id.drawer_content, new FragmentStart()).commit();
		}

		// Reset title on configuration change
		if (savedInstanceState != null && savedInstanceState.getString("ActionBarTitle") != null) {
			setTitle(savedInstanceState.getString("ActionBarTitle"));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putBoolean("ConfigChange", true);
		outState.putString("ActionBarTitle", getActionBar().getTitle().toString());
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title.toString();
        getActionBar().setTitle(mTitle);
    }

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@SuppressLint("Recycle")
	/*
	 * menu_icons (TypedArray) seems like it can be a (extremely minor) memory
	 * leak
	 */
	private class DrawerMenuAdapter extends BaseAdapter {

		private Context context;
		private String[] menu_titles;
		private TypedArray menu_icons;

		public DrawerMenuAdapter(Context context) {
			this.context = context;
			this.menu_titles = getResources().getStringArray(R.array.drawer_menu_titles);
			this.menu_icons = getResources().obtainTypedArray(R.array.drawer_menu_icons);
		}

		@Override
		public int getCount() {
			return menu_titles.length;
		}

		@Override
		public Object getItem(int position) {
			return menu_titles[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Reuse convertView if we can
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.drawer_menu_item, null);
			}
			// Set Image and Text
			TextView tv = (TextView) convertView.findViewById(R.id.drawer_menu_item_title);
			ImageView iv = (ImageView) convertView.findViewById(R.id.drawer_menu_item_icon);

			tv.setText(menu_titles[position]);
			iv.setImageDrawable(menu_icons.getDrawable(position));

			return convertView;
		}
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		/* Swaps fragments in the main content view */
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Fragment fragment;

			switch (position) {
			case 0:
				fragment = new FragmentTranscript();
				setTitle("Transcript");
				break;
			case 1:
				fragment = new FragmentSearch();
				setTitle("Course Search");
				break;
			case 2:
				fragment = new FragmentPlanner();
				setTitle("Course Planner");
				break;
			case 3:
				fragment = new Fragment();
				setTitle("Calendar");
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

	// private class DrawerItemClickListener implements
	// ListView.OnItemClickListener {
	//
	// private final String[] TAG = { "FRAG_TRANSCRIPT", "FRAG_SEARCH",
	// "FRAG_PLANNER", "FRAG_CALENDAR" };
	//
	// public DrawerItemClickListener() {
	//
	// FragmentManager fm = getFragmentManager();
	//
	// for (int i = 0; i < TAG.length; i++) {
	// if (fm.findFragmentByTag(TAG[i]) == null) {
	// if (TAG[i] == TAG[0]) {
	// fm.beginTransaction().add(R.id.drawer_content, new FragmentTranscript(),
	// TAG[i]).commit();
	// } else if (TAG[i] == TAG[1]) {
	// fm.beginTransaction().add(R.id.drawer_content, new FragmentSearch(),
	// TAG[i]).commit();
	// } else if (TAG[i] == TAG[2]) {
	// fm.beginTransaction().add(R.id.drawer_content, new FragmentPlanner(),
	// TAG[i]).commit();
	// } else if (TAG[i] == TAG[3]) {
	// fm.beginTransaction().add(R.id.drawer_content, new Fragment(),
	// TAG[i]).commit();
	// }
	// fm.beginTransaction().hide(findFragment(TAG[i])).commit();
	// }
	// }
	// }
	//
	// // Swaps fragments in the main content view
	// @Override
	// public void onItemClick(AdapterView<?> parent, View view, int position,
	// long id) {
	//
	// FragmentManager fm = getFragmentManager();
	// for (int i = 0; i < TAG.length; i++) {
	// fm.beginTransaction().hide(findFragment(TAG[i])).commit();
	// }
	// fm.beginTransaction().show(findFragment(TAG[position])).commit();
	//
	// mDrawerList.setItemChecked(position, true);
	// mDrawerLayout.closeDrawer(mDrawerList);
	// }
	//
	// private Fragment findFragment(String tag) {
	// FragmentManager fm = getFragmentManager();
	// return fm.findFragmentByTag(tag);
	// }
	// }
}
