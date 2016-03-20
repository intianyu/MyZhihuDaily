package com.rainfool.zhihudailyrrdcopy.ui.Fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainfool.zhihudailyrrdcopy.R;
import com.rainfool.zhihudailyrrdcopy.base.BaseFragment;
import com.rainfool.zhihudailyrrdcopy.constant.BundleKey;
import com.rainfool.zhihudailyrrdcopy.model.News;
import com.rainfool.zhihudailyrrdcopy.model.TodayNews;
import com.rainfool.zhihudailyrrdcopy.util.HtmlUtil;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class NewsDetailFragment extends BaseFragment {

    @Bind(R.id.wv_news)
    WebView mWvNews;
    @Bind(R.id.cpb_loading)
    ContentLoadingProgressBar mCpbLoading;
    @Bind(R.id.iv_header)
    ImageView mImageView;
    @Bind(R.id.tv_source)
    TextView mTvSource;
    @Bind(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.nested_view)
    NestedScrollView mNestedScrollView;
    @Bind(R.id.tv_load_empty)
    TextView mTvLoadEmpty;
    @Bind(R.id.tv_load_error)
    TextView mTvLoadError;
    private TodayNews.Story mStory;
    private News mNews;

    public static android.support.v4.app.Fragment newInstance(TodayNews.Story story) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.STORY, story);
        android.support.v4.app.Fragment Fragment = new NewsDetailFragment();
        Fragment.setArguments(bundle);
        return Fragment;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        mStory = (TodayNews.Story) getArguments().getSerializable(BundleKey.STORY);
        init();
        loadData();
    }

    private void init() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setHasOptionsMenu(true);
        mNestedScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mNestedScrollView.setElevation(0);
        mWvNews.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mWvNews.setElevation(0);

        mWvNews.getSettings().setLoadsImagesAutomatically(true);

        mWvNews.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        mWvNews.getSettings().setDomStorageEnabled(true);

        mCollapsingToolbarLayout.setTitle("知乎日报");

    }

    private void loadData() {

        Observable<News> networks = getDataLayer().getNews(mStory.getId());

        Observable<News> cache = getDataLayer().getLocalNews(String.valueOf(mStory.getId()));

        //输出数据前缓存到本地
        networks = networks.doOnNext(new Action1<News>() {
            @Override
            public void call(News news) {
                getDataLayer().cacheNews(news);
            }
        });

        Observable<News> source = Observable.concat(cache,networks).first(new Func1<News, Boolean>() {
            @Override
            public Boolean call(News news) {
                //如果本地数据存在的话
                return news != null;
            }
        });

        source .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        //解除订阅关系后停隐藏刷新progress
                        hideLoading();
                        if (null == mNews) {
                            mTvLoadEmpty.setVisibility(View.GONE);
                            mTvLoadError.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        //隐藏progress
                        hideLoading();
                        mNews = news;
                        if (mNews == null) {
                            mTvLoadEmpty.setVisibility(View.VISIBLE);
                        } else {
                            Picasso.with(getActivity())
                                    .load(news.getImage())
                                    .into(mImageView);
                            mTvSource.setText(news.getImageSource());

                            String htmlData = HtmlUtil.createHtmlData(news);
                            mWvNews.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
                            mTvLoadEmpty.setVisibility(View.GONE);
                        }
                        mTvLoadError.setVisibility(View.GONE);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        //隐藏progress
                        hideLoading();
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news_detail;
    }

    /**
     * 显示Loading
     */
    private void showLoading() {
        mCpbLoading.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏Loading
     */
    private void hideLoading() {
        mCpbLoading.setVisibility(View.GONE);
    }
}
