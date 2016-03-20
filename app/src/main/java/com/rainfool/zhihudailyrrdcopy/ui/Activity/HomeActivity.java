package com.rainfool.zhihudailyrrdcopy.ui.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rainfool.zhihudailyrrdcopy.R;
import com.rainfool.zhihudailyrrdcopy.base.BaseActivity;
import com.rainfool.zhihudailyrrdcopy.ui.Fragment.NewsListFragment;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onAfterCreate(Bundle savedInstanceState) {
        NewsListFragment fragment = new NewsListFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_container,fragment,NewsListFragment.TAG);
        ft.commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

}
