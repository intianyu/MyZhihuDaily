package com.rainfool.zhihudailyrrdcopy.protocol;

import com.rainfool.zhihudailyrrdcopy.model.News;
import com.rainfool.zhihudailyrrdcopy.model.TodayNews;
import com.socks.library.KLog;

import junit.framework.TestCase;

import rx.Observable;
import rx.Observer;

/**
 * Created by rainfool on 2016/3/16.
 */
public class ClientFactoryTest extends TestCase {

    ClientFactory factory = ClientFactory.getInstance();
    ClientApi api = factory.createClientApi();

    public void testGetUser() {
        Observable<TodayNews> observable = api.getTodayNews();
        observable
        .subscribe(new Observer<TodayNews>() {
            @Override
            public void onCompleted() {
                KLog.i("complete");
            }

            @Override
            public void onError(Throwable e) {
                KLog.i("onerror");
            }

            @Override
            public void onNext(TodayNews todayNews) {
                KLog.i("onNext " + todayNews.toString());
            }
        });
    }
}