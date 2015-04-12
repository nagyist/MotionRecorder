package put.iwm.android.motionrecorder.activities;

import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.baseactivities.BaseActivity;
import put.iwm.android.motionrecorder.fragments.RouteMapFragment;
import put.iwm.android.motionrecorder.fragments.StartTrainingFragment;


public class MainActivity extends BaseActivity {

    private Fragment startTrainingFragment;
    private Fragment routeMapFragment;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        redirectToAuthenticationActivityIfNeeded();

        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        setupFragments();
    }

    private void setupFragments() {

        startTrainingFragment = new StartTrainingFragment();
        routeMapFragment = new RouteMapFragment();

        addFragmentToTab(startTrainingFragment, "Trening");
        addFragmentToTab(routeMapFragment, "Trasa");
    }

    private void addFragmentToTab(Fragment fragment, String tabTitle) {

        actionBar.addTab(actionBar.newTab().setText(tabTitle)
                .setTabListener(new TabListener(fragment)));
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
        if (id == R.id.actionSettings)
            return true;

        if(id == R.id.actionLogout)
            logout();

        return super.onOptionsItemSelected(item);
    }

    private void logout() {

        sessionManager.logoutUser();
        redirectToAuthenticationActivity();
    }



    public static class TabListener implements ActionBar.TabListener {

        private final Fragment fragment;

        public TabListener(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            if (null != fragment)
                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            if (null != fragment)
                fragmentTransaction.remove(fragment);
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            if (null != fragment)
                fragmentTransaction.remove(fragment);
        }
    }
}
