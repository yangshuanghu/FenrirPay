package com.fenrir.app.fenrirpay.data.api;

import com.fenrir.app.fenrirpay.model.api.ErrorModel;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by yume on 16/1/25.
 *
 * Used for check service error.
 *
 * Example:
 * {"error":{"message":"リクエストされたオブジェクトが見つかりません。","status":"404","type":"1"}}
 *
 * @author xuemao.tang
 *
 */
public class NetErrorInterceptor implements Interceptor {
    private Gson gson;

    public NetErrorInterceptor(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if(chain == null)
            return null;

        Response response = chain.proceed(chain.request());

        ResponseBody responseBody = response.body();
        Long contentLength = responseBody.contentLength();

        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();

        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }

        if (contentLength != 0L) {
            ErrorModel errorModel = null;
            try {
                String data = buffer.clone().readString(charset);
                Logger.json(data);

                errorModel = gson.fromJson(data, ErrorModel.class);
            } catch(Exception e){
                Logger.e(e, "Gson convert error");
            }
            if(errorModel != null && errorModel.getError() != null){
                throw new NetErrorException(errorModel);
            }
        }

        return response;
    }
}
