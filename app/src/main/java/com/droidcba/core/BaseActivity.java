package com.droidcba.core;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import icepick.Icepick;

/**
 * Base Activity for all new Activities.
 */
public abstract class BaseActivity extends AppCompatActivity
        implements BaseFragment.BaseActionsListener {

    private AppCompatDialog loadingDialog;
    private AppCompatDialog closeAppDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDep();
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.base_activity);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    /**
     * This method will be called from {@link #onCreate(Bundle)} to inject dependencies like dagger.
     */
    protected void injectDep() {
    }

    @Override
    public void changeFragment(BaseFragment f) {
        changeFragment(f, false);
    }

    @Override
    public void changeFragment(BaseFragment f, boolean cleanStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (cleanStack) {
            clearBackStack();
        }
        ft.setCustomAnimations(
                R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_popup_enter, R.anim.abc_popup_exit);
        ft.replace(R.id.content_frame, f);
        ft.addToBackStack(null);
        ft.commit();
    }

    protected void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    /**
     * Prevent close the app when pressing back button.
     */
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            // If there are back-stack entries, leave the FragmentActivity
            // implementation take care of them.
            fragmentManager.popBackStack();

        } else {
            if (getPreventCloseActivity()) {
                // Otherwise, ask user if he wants to leave
                closeAppDialog = new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.exit_app_title))
                        .setMessage(getString(R.string.exit_app_message))
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                            }
                        }).create();
                closeAppDialog.show();
            } else {
                finish();
            }
        }
    }

    protected abstract boolean getPreventCloseActivity();

    @Override
    protected void onPause() {
        super.onPause();
        dismissDialog(closeAppDialog);
        dismissDialog(loadingDialog);
    }

    private void dismissDialog(AppCompatDialog dialog) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void showLoading() {
        if (loadingDialog != null) {
            loadingDialog.cancel();
        }
        AlertDialog.Builder builder =
                new AlertDialog.Builder(BaseActivity.this);
        builder.setCancelable(false);
        builder.setMessage(getString(R.string.loading_dialog_msg));
        loadingDialog = builder.create();
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (loadingDialog != null) {
            loadingDialog.cancel();
        }
    }

    @Override
    public void showToastShort(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToastLong(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
