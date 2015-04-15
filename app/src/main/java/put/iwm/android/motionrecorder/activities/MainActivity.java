package put.iwm.android.motionrecorder.activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.base.BaseActivity;
import put.iwm.android.motionrecorder.fragments.RouteMapFragment;
import put.iwm.android.motionrecorder.fragments.StartTrainingFragment;


public class MainActivity extends BaseActivity {

    private Map<String, Fragment> fragments;
    private List<String> fragmentsTitles;
    private String currentFragmentTitle;
    private ActionBar actionBar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ArrayAdapter<String> menuItems;

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        String targetFragmentTitle = fragmentsTitles.get(position);

        if(targetFragmentTitle != null) {
            currentFragmentTitle = targetFragmentTitle;
            Fragment targetFragment = fragments.get(targetFragmentTitle);
            replaceCurrentFragment(targetFragment);
        }

        drawerList.setItemChecked(position, true);
        setTitle(fragmentsTitles.get(position));
        drawerLayout.closeDrawer(drawerList);
    }

    private void replaceCurrentFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        redirectToAuthenticationActivityIfNeeded();

        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.drawer_list);

        setupFragments();
        setupDrawerList();
        setupDrawer();

        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void setupFragments() {

        fragmentsTitles = new ArrayList<String>() {{
            add("Trening");
            add("Trasa");
        }};

        fragments = new HashMap<String, Fragment>() {{
            put(fragmentsTitles.get(0), new StartTrainingFragment());
            put(fragmentsTitles.get(1), new RouteMapFragment());
        }};
    }

    private void setupDrawerList() {

        String[] drawerLlistItems = getResources().getStringArray(R.array.drawer_list_items);

        ArrayAdapter<String> drawerListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, drawerLlistItems);

        drawerList.setAdapter(drawerListAdapter);
    }

    private void setupDrawer() {

        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_list_open, R.string.drawer_list_open) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                actionBar.setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                actionBar.setTitle(currentFragmentTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
            return true;

        if(id == R.id.action_logout)
            logout();

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    private void logout() {

        sessionManager.logoutUser();
        redirectToAuthenticationActivity();
    }
}
