package com.rainfool.zhihudailyrrdcopy.ui.Fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rainfool.zhihudailyrrdcopy.R;
import com.rainfool.zhihudailyrrdcopy.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsListFragment extends BaseFragment {


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

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news_list;
    }

}
