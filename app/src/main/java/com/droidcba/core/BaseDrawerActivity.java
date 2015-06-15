package com.droidcba.core;

import android.animation.ValueAnimator;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;

/**
 * Base Activity with Drawer.
 */
public abstract class BaseDrawerActivity extends BaseActivity
        implements BaseDrawerFragment.BaseActionsWithDrawerListener,
        AdapterView.OnItemClickListener, FragmentManager.OnBackStackChangedListener {

    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_drawer_activity);

        // Set Toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                menuItem.setChecked(true);
                onMenuItemClick(menuItem);
                return true;
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
                invalidateOptionsMenu();
                syncState();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showProperMenuIcon();
        mDrawerToggle.syncState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    public void onBackStackChanged() {
        showProperMenuIcon();
    }

    private void showProperMenuIcon() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() <= 1) {
            // show hamburger
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            showBurger();
        } else {
            // show arrow to go back
            showArrow();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportFragmentManager().removeOnBackStackChangedListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean goBack = getSupportFragmentManager().getBackStackEntryCount() > 1;

        switch (item.getItemId()) {
            case android.R.id.home: {
                if (goBack) {
                    onBackPressed();
                } else {
                    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mDrawerLayout.closeDrawers();
                    } else {
                        mDrawerLayout.openDrawer(GravityCompat.START);
                    }
                }
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    public abstract void onMenuItemClick(MenuItem menuItem);


    private void showArrow() {
        switchBurger(0, 1);
    }

    private void showBurger() {
        switchBurger(1, 0);
    }

    private void switchBurger(final int start, final int end) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ValueAnimator anim = ValueAnimator.ofFloat(start, end);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float slideOffset = (Float) valueAnimator.getAnimatedValue();
                        mDrawerToggle.onDrawerSlide(mDrawerLayout, slideOffset);
                    }
                });
                anim.setInterpolator(new DecelerateInterpolator());
                // You can change this duration to more closely match that of the default animation.
                anim.setDuration(300);
                anim.start();
            }
        });
    }
}
