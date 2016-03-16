package com.rainfool.zhihudailyrrdcopy.protocol;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by rainfool on 2016/3/15.
 */
public class ClientFactory {

    private static final String API_VERSION = "4";
    private static final String BASE_URL = "http://news-at.zhihu.com/api/4/";

    private static ClientApi clientApi;
    private static Retrofit retrofit;
    private static ClientFactory factory;

    private ClientFactory() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static ClientFactory getInstance() {
        if (factory == null) {
            factory = new ClientFactory();
        }
        return factory;
    }

    public static ClientApi createClientApi() {
        clientApi = retrofit.create(ClientApi.class);
        return clientApi;
    }
}
