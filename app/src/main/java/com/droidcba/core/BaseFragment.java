package com.droidcba.core;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;
import icepick.Icepick;

/**
 * Base Fragment.
 */
public abstract class BaseFragment extends Fragment {

    protected BaseActionsListener baseActions;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            baseActions = (BaseActionsListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement BaseActionsListener");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        injectDep();
        ButterKnife.inject(this, view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        baseActions = null;
    }

    /**
     * This method will be called from {@link #onCreate(Bundle)} to inject dependencies like dagger.
     */
    protected void injectDep() {

    }

    /*************************************
     * - Methods for MVP (TODO: Remove this later).
     */
    public void showLoading() {
        baseActions.showLoading();
    }

    public void hideLoading() {
        baseActions.hideLoading();
    }

    public void showToastLong(String msg) {
        baseActions.showToastLong(msg);
    }

    public void showToastShort(String msg) {
        baseActions.showToastShort(msg);
    }

    public String getResourceString(int stringResId) {
        return getString(stringResId);
    }

    /**
     * Base actions to be implemented by the BaseActivity to be available for every fragment.
     */
    public interface BaseActionsListener {
        void showLoading();

        void hideLoading();

        void showToastShort(String msg);

        void showToastLong(String msg);

        void hideKeyboard();

        void finishActivity();

        void changeFragment(BaseFragment f);

        void changeFragment(BaseFragment f, boolean cleanStack);
    }
}
