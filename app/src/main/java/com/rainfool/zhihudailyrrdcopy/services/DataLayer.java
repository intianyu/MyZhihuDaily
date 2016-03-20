package com.rainfool.zhihudailyrrdcopy.services;

import com.google.gson.Gson;
import com.rainfool.zhihudailyrrdcopy.model.News;
import com.rainfool.zhihudailyrrdcopy.model.TodayNews;
import com.rainfool.zhihudailyrrdcopy.protocol.ClientApi;
import com.rainfool.zhihudailyrrdcopy.protocol.ClientFactory;
import com.rainfool.zhihudailyrrdcopy.util.SpUtil;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by rainfool on 2016/3/16.
 */
public class DataLayer {



        private static ClientApi api;
    private Gson mGson;

    private static DataLayer instance;
    private DataLayer() {
        api = ClientFactory.getInstance().createClientApi();
        mGson = new Gson();
    }

    public static DataLayer getInstance() {
        if (instance == null) {
            instance = new DataLayer();
        }
        return instance;
    }

    /**
     * 以下是暴露给外部操作数据的接口
     */

    /**
     * 获取本地今日热文
     */
    public Observable<TodayNews> getLocalTodayNews(final String date) {
        return Observable.create(new Observable.OnSubscribe<TodayNews>() {
            @Override
            public void call(Subscriber<? super TodayNews> subscriber) {
                subscriber.onStart();
                String json = SpUtil.find(date);
                TodayNews todayNews = getGson().fromJson(json,TodayNews.class);
                subscriber.onNext(todayNews);
                subscriber.onCompleted();
            }
        });
    }

    /**
     * 从网络上获取今日热文
     * @return
     */
    public Observable<TodayNews> getTodayNews() {
        return api.getTodayNews();
    }

    /**
     * 从网络上获取新闻
     */
    public Observable<News> getNews(long newsId) {
        return api.getNews(newsId);
    }

    /**
     * 从本地获取新闻
     */
    public Observable<News> getLocalNews(final String id) {
        return Observable.create(new Observable.OnSubscribe<News>() {
            @Override
            public void call(Subscriber<? super News> subscriber) {
                try {
                    subscriber.onStart();
                    String json = SpUtil.find(id);
                    News news = getGson().fromJson(json, News.class);
                    subscriber.onNext(news);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
    /**
     * 缓存数据
     * @return
     */
    public void cacheTodayNews(final TodayNews todayNews) {
        SpUtil.saveOrUpdate(todayNews.getDate(), getGson().toJson(todayNews));
    }

    public void cacheNews(final News news) {
        SpUtil.saveOrUpdate(String.valueOf(news.getId()), getGson().toJson(news));
    }



    public Gson getGson() {
        return mGson;
    }

}
