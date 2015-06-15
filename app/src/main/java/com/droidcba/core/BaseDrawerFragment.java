package com.droidcba.core;

import android.app.Activity;
import android.support.v7.widget.Toolbar;

/**
 * Base Fragment that use drawer.
 */
public abstract class BaseDrawerFragment extends BaseFragment {

    protected BaseActionsWithDrawerListener baseActions;

    public BaseDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            baseActions = (BaseActionsWithDrawerListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement BaseActionsWithDrawerListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        baseActions = null;
    }

    public interface BaseActionsWithDrawerListener extends BaseFragment.BaseActionsListener {
        Toolbar getToolbar();
    }
}
