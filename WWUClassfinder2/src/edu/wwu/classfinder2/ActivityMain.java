package edu.wwu.classfinder2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityMain extends Activity {

	private String[] mMenuTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mMenuTitles = getResources().getStringArray(R.array.drawer_menu_titles);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer_menu);

		// Set adapter for list view
		mDrawerList.setAdapter(new DrawerMenuAdapter(this));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	}

	@SuppressLint("Recycle") /* menu_icons (TypedArray) seems like it can be a (extremely minor) memory leak */
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
		case 2:
			fragment = new Fragment();
			break;
		case 3:
			fragment = new Fragment();
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
