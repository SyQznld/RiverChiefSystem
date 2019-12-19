package com.appler.riverchiefsystem.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.appler.riverchiefsystem.global.Global;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment implements BaseView {

    private View view;
    private Activity activity;
    private Unbinder unbinder;
    private BaseMvpPresenter baseMvpPresenter;

    private MaterialDialog materialDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(setFragmentView(), container, false);
        activity = getActivity();
        baseMvpPresenter = new BaseMvpPresenter();
        return view;
    }

    public View getView() {
        return view;
    }


    protected abstract int setFragmentView();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initFragmentView();
        doFragmentBusiness();
    }

    protected abstract void doFragmentBusiness();

    protected abstract void initFragmentView();


    @Override
    public void setState(int state) {
        switch (state) {
            case Global.LOADING_DOING:
                materialDialog = new MaterialDialog.Builder(activity)
                        .content("正在加载")
                        .progress(true, 0)
                        .progressIndeterminateStyle(false)
                        .show();
                break;
            case Global.LOADING_SUCCESS:
                if (materialDialog != null) {
                    materialDialog.setContent("加载成功");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            materialDialog.dismiss();
                        }
                    }, 500);
                }
                break;
            case Global.LOADING_FAIL:
                if (materialDialog != null) {
                    materialDialog.setContent("加载失败");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            materialDialog.dismiss();
                        }
                    }, 1000);
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        baseMvpPresenter.detachView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}




