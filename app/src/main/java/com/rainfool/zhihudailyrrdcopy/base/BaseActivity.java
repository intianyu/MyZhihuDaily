package com.rainfool.zhihudailyrrdcopy.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rainfool.zhihudailyrrdcopy.services.DataLayer;

import butterknife.ButterKnife;

/**
 * Created by rainfool on 2016/3/16.
 */
public abstract class BaseActivity extends AppCompatActivity{

    DataLayer dataLayer;

    public BaseActivity() {
        dataLayer = new DataLayer();
    }

    protected abstract void onAfterCreate(Bundle savedInstanceState);
    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        onAfterCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
