package com.droidcba.core.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.droidcba.core.BaseDrawerFragment;

/**
 * Base MVP Presenter with Drawer.
 */
public abstract class MvpBaseDrawerFragment<P extends MvpPresenter> extends BaseDrawerFragment implements MvpView {

    /**
     * The presenter for this view. Will be instantiated with {@link #createPresenter()}
     */
    protected P presenter;

    /**
     * Creates a new presenter instance, if needed. Will reuse the previous presenter instance if
     * {@link #setRetainInstance(boolean)} is set to true. This method will be called after from
     * {@link #onViewCreated(View, Bundle)}
     */
    protected abstract P createPresenter();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Create the presenter if needed
        if (presenter == null) {
            presenter = createPresenter();

            if (presenter == null) {
                throw new NullPointerException("Presenter is null.");
            }
        }
        presenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView(getRetainInstance());
    }
}