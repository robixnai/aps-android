package br.com.aps.aps.views;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;

import br.com.aps.aps.R;
import br.com.aps.aps.storages.internal.async.MyLocationTask;
import br.com.aps.aps.views.fragment.ChatFragment;
import br.com.aps.aps.views.fragment.HomeFragment;
import br.com.aps.aps.views.fragment.InformationFragment;
import br.com.aps.aps.views.fragment.MapsFragment;
import br.com.aps.aps.views.fragment.SettingsFragment;
import br.com.aps.aps.utils.AppUtil;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private FloatingActionButton mFabMultipleActions, mFabMyLocation, mFabLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        bindElements();
        bindFab();

        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void bindElements() {
        mToolbar = AppUtil.get(findViewById(R.id.toolbar));
        mDrawer = AppUtil.get(findViewById(R.id.drawer_layout));
        mNavigationView = AppUtil.get(findViewById(R.id.nav_view));
    }

    private void bindFab() {
        mFabMultipleActions = AppUtil.get(findViewById(R.id.fab_expand_menu_button));

        mFabMyLocation = AppUtil.get(findViewById(R.id.fab_my_location));
        mFabMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyLocationTask().execute();
            }
        });

        mFabLocation = AppUtil.get(findViewById(R.id.fab_location));
        mFabLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Enviar outra localização", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (item.getItemId()) {
            case R.id.nav_home :
                fragmentTransaction.replace(R.id.container_fragment, HomeFragment.newInstance()).commitAllowingStateLoss();
                break;
            case R.id.nav_maps :
                fragmentTransaction.replace(R.id.container_fragment, MapsFragment.newInstance()).commitAllowingStateLoss();
                break;
            case R.id.nav_chat :
                fragmentTransaction.replace(R.id.container_fragment, ChatFragment.newInstance()).commitAllowingStateLoss();
                break;
            case R.id.nav_settings :
                fragmentTransaction.replace(R.id.container_fragment, SettingsFragment.newInstance()).commitAllowingStateLoss();
                break;
            case R.id.nav_information :
                fragmentTransaction.replace(R.id.container_fragment, InformationFragment.newInstance()).commitAllowingStateLoss();
                break;
            case R.id.nav_exit :
                finish();
                break;
        }

        DrawerLayout drawer = AppUtil.get(findViewById(R.id.drawer_layout));
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
