package edu.wwu.classfinder2;

import java.util.Calendar;

import android.accounts.Account;
import android.accounts.AccountManager;

import android.annotation.SuppressLint;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;

import android.content.SharedPreferences.Editor;

import android.content.res.Configuration;
import android.content.res.TypedArray;

import android.os.Bundle;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarActivity;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.InputMethodManager;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import edu.wwu.classfinder2.data.Course;

public class ActivityMain extends ActionBarActivity {

    // Constants

    public static final String TERM_PREF = "current_term";

    // The authority for the sync adapter's content provider
    public static final String AUTHORITY =
            "edu.wwu.classfinder2.provider";

    // An account type, in the form of a domain name
    // The account name
    public static final String ACCOUNT = "dummyaccount";

    // Instance fields
    private String mTitle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private Account mAccount;
    private ContentResolver mResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTermPreference();

        // Add the dummy account to the Sync service
        mAccount =
                CreateCourseSyncAccount(this.getApplicationContext(),
                        getResources()
                                .getString(R.string.account_type)
                );

        mResolver = getContentResolver();
        if (mResolver != null) {
            mResolver.setSyncAutomatically(mAccount, AUTHORITY, true);
        }

        mTitle = getTitle().toString();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer_menu);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.drawer_navigation,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.app_name);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(mTitle);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Set adapter for list view
        mDrawerList.setAdapter(new DrawerMenuAdapter(this));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener(this));

        // Reset title on configuration change
        if (savedInstanceState != null && savedInstanceState.getString("MTITLE") != null) {
            mTitle = savedInstanceState.getString("MTITLE");
            if (!savedInstanceState.getBoolean("DRAWER_OPEN")) {
                getSupportActionBar().setTitle(mTitle);
            }
        }

        // Open the drawer only on startup
        if (savedInstanceState == null) {
            mDrawerLayout.openDrawer(mDrawerList);
            getFragmentManager().beginTransaction().add(R.id.drawer_content, new FragmentBegin()).commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("MTITLE", mTitle);
        outState.putBoolean("DRAWER_OPEN", mDrawerLayout.isDrawerOpen(mDrawerList));
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title.toString();
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    public static Account CreateCourseSyncAccount(Context context, String accountType) {
        // Create the account type and default account
        Account newAccount = new Account(ACCOUNT,
                accountType);

        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report
         * an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            return newAccount;
        } else {
            /*
             * The account might exist already. Since its a dummy
             * account anyhow, let's just see if there are any
             * accounts for our account_type.
             */
            Account[] accounts =
                    accountManager.getAccountsByType(accountType);
            if (accounts.length != 0) {
                return accounts[0];
            } else {
                return null;
            }
        }

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
                convertView = inflater.inflate(R.layout.drawer_menu_item, parent, false);
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

        Activity activity;

        public DrawerItemClickListener(Activity activity) {
            this.activity = activity;
        }

        /* Swaps fragments in the main content view */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // Hide the keyboard
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

            Fragment fragment;

            switch (position) {
                case 0:
                    fragment = new FragmentTranscript();
                    setTitle(getResources().getStringArray(R.array.drawer_menu_titles)[0]);
                    break;
                case 1:
                    fragment = new FragmentSearch();
                    setTitle(getResources().getStringArray(R.array.drawer_menu_titles)[1]);
                    break;
                case 2:
                    fragment = new FragmentPlanner();
                    setTitle(getResources().getStringArray(R.array.drawer_menu_titles)[2]);
                    break;
                case 3:
                    fragment = new Fragment();
                    setTitle(getResources().getStringArray(R.array.drawer_menu_titles)[3]);
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


    private void initTermPreference() {
        Calendar today = Calendar.getInstance();

        String term =
            String.format("%d%d",
                          today.get(Calendar.YEAR),
                          getQuarterForMonth(today
                                             .get(Calendar.MONTH)));

        SharedPreferences prefs =
            this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = prefs.edit();

        prefEdit.putString(TERM_PREF, term);
        prefEdit.apply();
    }

    private int getQuarterForMonth(int month) {
        switch (month) {
            // Leading up to fall
            case Calendar.JUNE:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                return Course.FALL;

                // Leading up to winter
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
            case Calendar.JANUARY:
                return Course.WINTER;

                // Leading up to spring
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                return Course.SPRING;

                // Leading up to summer/fall
            case Calendar.APRIL:
            case Calendar.MAY:
                return Course.SUMMER;
            default:
                return -1;
        }
    }

}
