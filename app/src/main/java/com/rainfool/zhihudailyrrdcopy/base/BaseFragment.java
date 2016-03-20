package com.rainfool.zhihudailyrrdcopy.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rainfool.zhihudailyrrdcopy.services.DataLayer;

import butterknife.ButterKnife;

/**
 * Created by rainfool on 2016/3/17.
 */
public abstract class BaseFragment extends Fragment{

    public static final String TAG = BaseFragment.class.getSimpleName();

    protected View mRootView;

    DataLayer mDataLayer;

    public BaseFragment() {
        mDataLayer = DataLayer.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(),container,false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        ButterKnife.bind(this, view);
        afterCreate(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ButterKnife.unbind(this);
    }

    public DataLayer getDataLayer() {
        return mDataLayer;
    }

    protected abstract void afterCreate(Bundle savedInstanceState);

    public abstract int getLayoutId();
}
