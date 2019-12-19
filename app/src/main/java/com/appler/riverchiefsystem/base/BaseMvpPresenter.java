package com.appler.riverchiefsystem.base;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by appler on 2018/12/18.
 */

public class BaseMvpPresenter<V> implements MvpPresent<V> {

    protected CompositeSubscription compositeSubscription = new CompositeSubscription();
    private V mMvpView;

    @Override
    public void attachView(V view) {
        this.mMvpView = view;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        mMvpView = null;
        compositeSubscription.clear();
        compositeSubscription = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
