package put.iwm.android.motionrecorder.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.adapters.NavigationDrawerAdapter;
import put.iwm.android.motionrecorder.base.BaseActivity;
import put.iwm.android.motionrecorder.exceptions.FragmentNotFoundException;
import put.iwm.android.motionrecorder.fragments.ActiveTrainingFragment;
import put.iwm.android.motionrecorder.fragments.RouteMapFragment;
import put.iwm.android.motionrecorder.fragments.TrainingListFragment;


public class MainActivity extends BaseActivity {

    private Map<String, Fragment> fragments;
    private List<String> fragmentsTitles;
    private String currentFragmentTitle;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private NavigationDrawerAdapter drawerListAdapter;

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        String targetFragmentTitle = fragmentsTitles.get(position);
        currentFragmentTitle = targetFragmentTitle;

        drawerListAdapter.setSelectedItemPosition(position);
        drawerLayout.closeDrawer(drawerList);
    }

    private void switchCurrentFragment(String targetFragmentTitle) throws FragmentNotFoundException {

        try {
            trySwitchCurrentFragment(targetFragmentTitle);
        } catch (FragmentNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    private void trySwitchCurrentFragment(String targetFragmentTitle) {

        if(!fragments.containsKey(targetFragmentTitle))
            throw new FragmentNotFoundException(String.format("Nie ma fragmentu o tytule '%s'", targetFragmentTitle));

        setCurrentFragmentTitle(targetFragmentTitle);
        setActionBarTitle(targetFragmentTitle);

        Fragment targetFragment = fragments.get(currentFragmentTitle);
        replaceCurrentFragment(targetFragment);
    }

    private void setCurrentFragmentTitle(String currentFragmentTitle) {

        this.currentFragmentTitle = currentFragmentTitle;
    }

    private void replaceCurrentFragment(Fragment targetFragment) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame, targetFragment);
        fragmentTransaction.commit();
    }

    private class ActionBarDrawerToggleImpl extends ActionBarDrawerToggle {

        private Runnable onDrawerClosedRunnable;
        private Handler onDrawerClosedHandler;

        public ActionBarDrawerToggleImpl(Activity activity, DrawerLayout drawerLayout, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
            onDrawerClosedHandler = new Handler();
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
        }

        @Override
        public void onDrawerClosed(View view) {

            onDrawerClosedRunnable = new Runnable() {
                @Override
                public void run() {
                    switchCurrentFragment(currentFragmentTitle);
                }
            };

            onDrawerClosedHandler.post(onDrawerClosedRunnable);

            super.onDrawerClosed(view);
            setActionBarTitle(currentFragmentTitle);
        }

        public Runnable getOnDrawerClosedRunnable() {
            return onDrawerClosedRunnable;
        }

        public void setOnDrawerClosedRunnable(Runnable onDrawerClosedRunnable) {
            this.onDrawerClosedRunnable = onDrawerClosedRunnable;
        }
    }

    private void setActionBarTitle(String title) {

        getActionBar().setTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        redirectToAuthenticationActivityIfNeeded();

        setContentView(R.layout.activity_main);

        setupViewReferences();
        setupFragments();
        setupDrawerList();
        setupDrawer();
        setupActionBar();

        switchCurrentFragment(fragmentsTitles.get(0));
    }

    private void restoreState(Bundle savedState) {

        if (savedState != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragments.put(currentFragmentTitle, fragmentManager.getFragment(savedState, currentFragmentTitle));
        }
    }

    private void setupViewReferences() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.drawer_list);
    }

    private void setupFragments() {

        fragmentsTitles = Arrays.asList( getResources().getStringArray(R.array.drawer_list_items) );

        fragments = new HashMap<String, Fragment>() {{
            put(fragmentsTitles.get(0), new ActiveTrainingFragment());
            put(fragmentsTitles.get(1), new RouteMapFragment());
            put(fragmentsTitles.get(2), new TrainingListFragment());
        }};
    }

    private void setupDrawerList() {

        drawerListAdapter = new NavigationDrawerAdapter(this, R.layout.drawer_list_item, getResources().getStringArray(R.array.drawer_list_items));

        drawerList.setAdapter(drawerListAdapter);
    }

    private void setupDrawer() {

        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        actionBarDrawerToggle = new ActionBarDrawerToggleImpl(this, drawerLayout, R.string.drawer_list_open, R.string.drawer_list_open);

        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    private void setupActionBar() {

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    /*@Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        Fragment currentFragment = fragments.get(currentFragmentTitle);
        getFragmentManager().putFragment(outState, currentFragmentTitle, currentFragment);
    }*/

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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings)
            return true;

        if(id == R.id.action_logout)
            logout();

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    private void logout() {

        sessionManager.logoutUser();
        redirectToAuthenticationActivity();
    }

}
