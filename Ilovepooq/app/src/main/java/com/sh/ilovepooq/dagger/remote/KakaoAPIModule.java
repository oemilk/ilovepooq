package com.sh.ilovepooq.dagger.remote;

import com.sh.ilovepooq.BuildConfig;
import com.sh.ilovepooq.base.Constants;
import com.sh.ilovepooq.remote.KakaoAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class KakaoAPIModule {

    @Provides
    @Singleton
    OkHttpClient provideClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);

        return new OkHttpClient.Builder().addInterceptor(chain -> {
            Request request = chain.request();

            return chain.proceed(request.newBuilder()
                    .addHeader(Constants.API_AUTHORIZATION, Constants.API_KAKAO_KEY)
                    .build());
        })
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(String baseURL, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    KakaoAPI provideApiService() {
        return provideRetrofit(Constants.API_BASE_URL, provideClient()).create(KakaoAPI.class);
    }

}
