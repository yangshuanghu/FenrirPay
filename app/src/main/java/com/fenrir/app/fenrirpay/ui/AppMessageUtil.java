package com.fenrir.app.fenrirpay.ui;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fenrir.app.fenrirpay.R;
import com.fenrir.app.fenrirpay.data.api.NetErrorException;
import com.fenrir.app.fenrirpay.model.api.ErrorModel;
import com.fenrir.app.fenrirpay.util.StringUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * Created by yume on 15/9/16.
 */
public class AppMessageUtil {
    private Context context;
    private MaterialDialog progressDialog;
    private String errorTitle = "エラーが発生しました";

    public AppMessageUtil(Context context){
        this.context = context;
    }

    public void dealResultError(Response response){
        String result = null;

        result = StringUtil.inputStream2String(response.body().byteStream());

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            Logger.e(e, null);
            return;
        }
        try {
            String title = jsonObject.getString("title");
            String msg = jsonObject.getString("message");
            showDialog(title, msg);
        } catch (JSONException e) {
            Logger.e(e, null);
        }
    }

    public void dealResultError(ErrorModel errorModel){
        showDialog(errorTitle, errorModel.getErrorMessage());
    }

    public void dealResultError(ErrorModel.ErrorEntity errorEntity){
        showDialog(errorEntity.getStatus(), errorEntity.getMessage());
    }

    public void showDialog(String msg){
        showDialog(errorTitle, msg);
    }

    public void showDialog(String title, String msg){
        showDialog(title, msg, null);
    }

    public void showDialog(String title, String msg, Action0 doOnDismiss){
        Observable.just("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s ->
                                showCustomDialog(title, msg, doOnDismiss).show(),
//                        new AlertDialog.Builder(context)
//                                .setTitle(title)
//                                .setMessage(msg)
//                                .setPositiveButton("OK", null)
//                                .create()
//                                .show(),
                        t -> Logger.e(t, null));
    }

    public void showMessage(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public <T> Observable.Transformer<T, T> showNetProgressDialog(String title){
        return obs -> obs
                .doOnSubscribe(() -> showDialog(title))
                .doOnError(t -> this.hideProgressDialog())
                .doOnCompleted(this::hideProgressDialog);
    }

    public void showProgressDialog(@NonNull String title){
        if(progressDialog != null)
            return;
        Observable.just("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s -> {
                            progressDialog = new MaterialDialog
                                    .Builder(context)
                                    .content(title)
                                    .contentColor(ContextCompat.getColor(context, R.color.color_gr1))
                                    .cancelable(false)
                                    .progress(true, 0)
                                    .progressIndeterminateStyle(false)
                                    .show();
                        },
                        t -> Logger.e(t, null)
                );
    }

    public void hideProgressDialog(){
        Observable.just("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                            if(progressDialog != null) {
                                progressDialog.dismiss();
                                progressDialog = null;
                            }
                        },
                        t -> Logger.e(t, null)
                );
    }

    public void dealThrowable(Throwable t){
        Logger.e(t, null);
        if(t instanceof NetErrorException)
            dealResultError(((NetErrorException)t).getErrorModel());
        else
            showDialog(errorTitle, t.getMessage());
    }

    public Dialog showCustomDialog(String title, Action0 doOnDismiss){
        return this.showCustomDialog(title, null, true, doOnDismiss);
    }

    public Dialog showCustomDialog(String title, String message, Action0 doOnDismiss){
        return this.showCustomDialog(title, message, true, doOnDismiss);
    }

    private Dialog showCustomDialog(String title, String message, boolean showOkButton, Action0 doOnDismiss){
        View view = LayoutInflater.from(context).inflate(R.layout.app_message_custom_dialog, null);
        CustomDialogViewHolder viewHolder = new CustomDialogViewHolder();
        ButterKnife.bind(viewHolder, view);

        viewHolder.setContent(title, message, showOkButton);

        Dialog customDialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();

        if(showOkButton)
            viewHolder.okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialog.dismiss();
                    if(doOnDismiss != null)
                        doOnDismiss.call();
                }
            });

        return customDialog;
    }

    public static class CustomDialogViewHolder{
        @Bind(R.id.dialog_title_tv)
        TextView dialogTitleTv;
        @Bind(R.id.dialog_content_tv)
        TextView dialogContentTv;
        @Bind(R.id.ok_button)
        Button okButton;

        public void setContent(String title, String message, boolean showOkButton){
            setTitle(title);
            setMessage(message);

            if(showOkButton)
                okButton.setVisibility(View.VISIBLE);
            else
                okButton.setVisibility(View.GONE);
        }

        private void setTitle(String title){
            if(TextUtils.isEmpty(title)){
                dialogTitleTv.setVisibility(View.GONE);
            } else {
                dialogTitleTv.setText(title);
            }
        }

        private void setMessage(String message){
            if(TextUtils.isEmpty(message)){
                dialogContentTv.setVisibility(View.GONE);
            } else {
                dialogContentTv.setText(message);
            }
        }
    }
}
