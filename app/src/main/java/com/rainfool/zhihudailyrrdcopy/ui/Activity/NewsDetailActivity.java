package com.rainfool.zhihudailyrrdcopy.ui.Activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainfool.zhihudailyrrdcopy.R;
import com.rainfool.zhihudailyrrdcopy.base.BaseActivity;
import com.rainfool.zhihudailyrrdcopy.constant.BundleKey;
import com.rainfool.zhihudailyrrdcopy.model.News;
import com.rainfool.zhihudailyrrdcopy.model.TodayNews;
import com.rainfool.zhihudailyrrdcopy.ui.Fragment.NewsDetailFragment;

import butterknife.Bind;

public class NewsDetailActivity extends BaseActivity {

    @Override
    protected void onAfterCreate(Bundle savedInstanceState) {
        TodayNews.Story story = (TodayNews.Story) getIntent().getSerializableExtra(BundleKey.STORY);
        showNewsFragment(story);
    }

    private void showNewsFragment(TodayNews.Story story) {
        Fragment fragment = NewsDetailFragment.newInstance(story);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.rl_news_container, fragment, NewsDetailFragment.TAG);
        ft.commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
