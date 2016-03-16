package com.rainfool.zhihudailyrrdcopy.protocol;

import com.rainfool.zhihudailyrrdcopy.model.News;
import com.rainfool.zhihudailyrrdcopy.model.StartImage;
import com.rainfool.zhihudailyrrdcopy.model.TodayNews;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by rainfool on 2016/3/15.
 */
public interface ClientApi {

    String FIELD_NEWS_ID = "newsId";

    /**
     * 获取启动页面URL
     */
    String URL_GET_START_IMAGE = "start-image/1080*1776";
    /**
     * 获取最新日报新闻列表
     */
    String URL_GET_LATEST_NEWS = "news/latest";
    /**
     * 获取新闻
     */
    String URL_GET_NEWS = "news/{newsId}";


    /**
     * 获取今日新闻
     * @return TodayNews
     */
    @GET(URL_GET_LATEST_NEWS)
    Observable<TodayNews> getTodayNews();

    /**
     * 获取启动图片
     * @return
     */
    @GET(URL_GET_START_IMAGE)
    Observable<StartImage> getStartImage();

    /**
     * 获取新闻
     * @param newsId
     * @return
     */
    @GET(URL_GET_NEWS)
    Observable<News> getNews(@Path(FIELD_NEWS_ID) long newsId);

}
