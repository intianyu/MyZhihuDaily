package com.rainfool.zhihudailyrrdcopy.protocol;

/**
 * Created by rainfool on 2016/3/15.
 */
public class ClientApi {

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

}
