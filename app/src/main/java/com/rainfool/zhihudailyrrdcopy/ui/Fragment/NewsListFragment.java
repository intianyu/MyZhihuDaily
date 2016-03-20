package com.rainfool.zhihudailyrrdcopy.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rainfool.zhihudailyrrdcopy.R;
import com.rainfool.zhihudailyrrdcopy.base.BaseFragment;
import com.rainfool.zhihudailyrrdcopy.base.DividerItemDecoration;
import com.rainfool.zhihudailyrrdcopy.constant.BundleKey;
import com.rainfool.zhihudailyrrdcopy.model.TodayNews;
import com.rainfool.zhihudailyrrdcopy.ui.Activity.NewsDetailActivity;
import com.rainfool.zhihudailyrrdcopy.ui.Adapter.NewsListAdapter;
import com.socks.library.KLog;

import org.joda.time.DateTime;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class NewsListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {


    //RecyclerView 用到的列数
    public static final int SPAN_COUNT = 1;//列数

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_load_empty)
    TextView mTvLoadEmpty;
    @Bind(R.id.tv_load_error)
    TextView mTvLoadError;
    @Bind(R.id.rcv_news_list)
    RecyclerView mRcvNewsList;
    @Bind(R.id.srl_news_list)
    SwipeRefreshLayout mSrlNewsList;

    NewsListAdapter mNewsListAdapter;

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        init();
        loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news_list;
    }

    private void init() {
        mToolbar.setTitle(getString(R.string.today_news));
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);

        mSrlNewsList.setOnRefreshListener(this);
        mSrlNewsList.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager
                (SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        mRcvNewsList.setLayoutManager(manager);

        mNewsListAdapter = new NewsListAdapter(getActivity(), new ArrayList<TodayNews.Story>(),
                new ArrayList<TodayNews.Story>(), this);
        mRcvNewsList.setAdapter(mNewsListAdapter);

        mRcvNewsList.addItemDecoration(new DividerItemDecoration
                (getActivity(), DividerItemDecoration.VERTICAL_LIST));
    }

    private void loadData() {
        String nowDateStr = DateTime.now().minus(1).toString("yyyyMMdd");
        //获取到本地缓存的今日热文
        Observable<TodayNews> cache = getDataLayer().getLocalTodayNews(nowDateStr);

        //获取网络上的今日热文
        Observable<TodayNews> network = getDataLayer().getTodayNews();

        //缓存
        network = network.doOnNext(new Action1<TodayNews>() {
            @Override
            public void call(TodayNews todayNews) {
                getDataLayer().cacheTodayNews(todayNews);
            }
        });

        //先获取缓存里面的数据
        Observable<TodayNews> source = Observable
                .concat(cache, network)
                .first(new Func1<TodayNews, Boolean>() {
                       @Override
                       public Boolean call(TodayNews todayNews) {
                           return todayNews != null;
                       }
                   }
                );


        source  .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TodayNews>() {
                    @Override
                    public void call(TodayNews todayNews) {
                        hideProgress();
                        KLog.i(todayNews.toString());
                        if (todayNews.getStories() == null) {
                            mTvLoadEmpty.setVisibility(View.VISIBLE);
                        } else {
                            mNewsListAdapter.setStories(todayNews.getStories(), todayNews.getTopStories());
                            mNewsListAdapter.notifyDataSetChanged();
                            mTvLoadEmpty.setVisibility(View.GONE);
                        }
                        mTvLoadError.setVisibility(View.GONE);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        //停止显示刷新动画
                        hideProgress();
                        Toast.makeText(getActivity(), getString(R.string.load_fail), Toast.LENGTH_SHORT).show();
                        mTvLoadEmpty.setVisibility(View.GONE);
                        mTvLoadError.setVisibility(View.VISIBLE);
                    }
                });
    }


    /**
     * SwipeRefreshLayout的onrefresh接口
     */
    @Override
    public void onRefresh() {
        loadData();
    }


    /**
     * RecyclerView里item的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        final int position = mRcvNewsList.getChildAdapterPosition(v);
        if (RecyclerView.NO_POSITION != position) {
            TodayNews.Story story = mNewsListAdapter.getItemData(position);
            Intent intent = new Intent(getActivity(),NewsDetailActivity.class);
            intent.putExtra(BundleKey.STORY,story);
            getActivity().startActivity(intent);
        }
    }


    public void showProgress() {
        mSrlNewsList.post(new Runnable() {
            @Override
            public void run() {
                //刷新动画
                mSrlNewsList.setRefreshing(true);
            }
        });
    }

    public void hideProgress() {
        mSrlNewsList.post(new Runnable() {
            @Override
            public void run() {
                mSrlNewsList.setRefreshing(false);
            }
        });
    }
}
