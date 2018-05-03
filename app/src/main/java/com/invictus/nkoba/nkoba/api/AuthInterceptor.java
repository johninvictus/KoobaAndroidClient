package com.invictus.nkoba.nkoba.api;

import com.invictus.nkoba.nkoba.utils.AppConstants;

import okhttp3.Interceptor;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by invictus on 2/24/18.
 */
public class AuthInterceptor implements Interceptor {

    private static final String LOG_TAG = AuthInterceptor.class.getSimpleName();

    private String token;

    public AuthInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        String value = "Bearer: " + token;

        Request request = chain.request()
                .newBuilder()
                .addHeader("Authorization", value)
                .build();
        Timber.e(value);
        return chain.proceed(request);
    }
}
