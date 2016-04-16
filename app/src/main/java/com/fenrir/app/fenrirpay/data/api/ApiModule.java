package com.fenrir.app.fenrirpay.data.api;

import android.text.TextUtils;

import com.fenrir.app.fenrirpay.util.Constants;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import indi.yume.tools.fragmentmanager.Tuple2;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by yume on 16-4-16.
 */
@Module
public class ApiModule {
    @Singleton
    @Provides
    public Retrofit provideAresRestAdapter(Gson gson){
        OkHttpClient okHttpClient = getApiOkHttpClient(gson);

        Retrofit.Builder builder = new Retrofit.Builder();
        dealRestAdapter(builder);
        return builder.addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    public Gson provideGson(){
        return new GsonBuilder()
                .registerTypeAdapter(List.class,
                        (JsonDeserializer<List>) (json, typeOfT, context) -> {
                            String j = json.toString();
                            if(TextUtils.isEmpty(j) || !json.isJsonArray())
                                return null;

                            JsonArray jArray = (JsonArray) json;
                            List<Object> list = new ArrayList<>();
                            for(JsonElement je : jArray) {
                                list.add(context.deserialize(je, ((ParameterizedType)(typeOfT)).getActualTypeArguments()[0]));
                            }
                            return list;
                        })
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    protected Retrofit.Builder dealRestAdapter(Retrofit.Builder builder) {
        return builder;
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient(){
        return okHttpClientSetting(new OkHttpClient.Builder())
                .build();
    }

    private OkHttpClient getApiOkHttpClient(Gson gson) {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                .authenticator((route, response) -> {
//                    String credential = Credentials.basic(
//                            authenticatorInfo.getData1(),
//                            authenticatorInfo.getData2());
//                    return response.request().newBuilder().header("Authorization", credential).build();
//                });

//        if (Constants.ALLOW_ALL_CERTIFICATES) {
//            builder.sslSocketFactory(getAllowAllSSLSocketFactory());
//            builder.hostnameVerifier((hostname, session) -> true);
//        }

        return okHttpClientSetting(new OkHttpClient.Builder())
                .addInterceptor(new NetErrorInterceptor(gson))
                .build();
    }

    protected OkHttpClient.Builder okHttpClientSetting(OkHttpClient.Builder builder){
        return builder;
    }

// example:
//
//    @Singleton
//    @Provides
//    public SearchUtil provideSearchService(Retrofit retrofit){
//        return new SearchUtil(retrofit.create(SearchService.class));
//    }
}
