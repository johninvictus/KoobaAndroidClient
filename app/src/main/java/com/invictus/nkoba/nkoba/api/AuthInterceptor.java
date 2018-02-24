package com.invictus.nkoba.nkoba.api;

import com.invictus.nkoba.nkoba.utils.AppConstants;

import okhttp3.Interceptor;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by invictus on 2/24/18.
 */
public class AuthInterceptor implements Interceptor {

    private static final String LOG_TAG = AuthInterceptor.class.getSimpleName();


    public AuthInterceptor() {
        // inject dagger here
    }

    @Override
    public Response intercept(Chain chain) throws IOException {


        Request request = chain.request();
        HttpUrl httpUrl = request.url()
                .newBuilder()
                .addQueryParameter(AppConstants.TOKEN_KEY_ENTRY, "")
                .build();

        request = request.newBuilder().url(httpUrl).build();
        return chain.proceed(request);
    }
}
