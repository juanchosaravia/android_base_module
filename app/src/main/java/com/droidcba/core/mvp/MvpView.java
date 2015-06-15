package com.droidcba.core.mvp;

/**
 * MVP View Interface.
 */
public interface MvpView {
    void showLoading();

    void hideLoading();

    void showToastShort(String msg);

    void showToastLong(String msg);

    String getResourceString(int stringResId);
}
