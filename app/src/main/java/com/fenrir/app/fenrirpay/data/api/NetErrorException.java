package com.fenrir.app.fenrirpay.data.api;

import com.fenrir.app.fenrirpay.model.api.ErrorModel;

import java.io.IOException;

import lombok.Getter;

/**
 * Created by yume on 16/1/25.
 */
public class NetErrorException extends IOException {
    @Getter
    private ErrorModel errorModel;

    public NetErrorException(ErrorModel errorModel){
        super(errorModel.getErrorMessage());
        this.errorModel = errorModel;
    }
}
