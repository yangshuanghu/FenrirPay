package com.fenrir.app.fenrirpay.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fenrir.app.fenrirpay.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by yume on 16-2-15.
 */
public class CustomDialogBuilder {
    private Context context;

    private AlertDialog.Builder builder;
    private DialogViewHolder viewHolder;

    private Action1<Dialog> onCancelBtnClick;
    private Action1<Dialog> onOkBtnClick;
    private Action1<String> onEditBack;

    public CustomDialogBuilder(Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);

        View contentView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_layout, null);
        viewHolder = new DialogViewHolder(contentView);
        builder.setView(contentView);
        initEmpty(viewHolder);
    }

    private static void initEmpty(DialogViewHolder viewHolder) {
        viewHolder.dialogTitleTv.setVisibility(View.VISIBLE);
        viewHolder.dialogContentTv.setVisibility(View.VISIBLE);
        viewHolder.dialogEditText.setVisibility(View.GONE);
        viewHolder.dialogCancelBtn.setVisibility(View.VISIBLE);
        viewHolder.dialogOkBtn.setVisibility(View.VISIBLE);
    }

    public CustomDialogBuilder setTitle(@StringRes int textRes) {
        return setTitle(context.getString(textRes));
    }

    public CustomDialogBuilder setTitle(String title) {
        viewHolder.dialogTitleTv.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(title))
            viewHolder.dialogTitleTv.setVisibility(View.GONE);
        else
            viewHolder.dialogTitleTv.setText(title);

        return this;
    }

    public CustomDialogBuilder setContent(@StringRes int textRes) {
        return setContent(context.getString(textRes));
    }

    public CustomDialogBuilder setContent(String content) {
        viewHolder.dialogContentTv.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(content))
            viewHolder.dialogContentTv.setVisibility(View.GONE);
        else
            viewHolder.dialogContentTv.setText(content);

        return this;
    }

    public CustomDialogBuilder hideEditText() {
        viewHolder.dialogEditText.setVisibility(View.GONE);
        return this;
    }

    public CustomDialogBuilder setEditText(@StringRes int hintTextId, String defaultString, Action1<String> onEditBack) {
        return setEditText(context.getString(hintTextId), defaultString, onEditBack);
    }

    public CustomDialogBuilder setEditText(String hintText, String defaultString, Action1<String> onEditBack) {
        viewHolder.dialogEditText.setVisibility(View.VISIBLE);
        viewHolder.dialogEditText.setHint(hintText);
        viewHolder.dialogEditText.setText(defaultString);
        this.onEditBack = onEditBack;

        return this;
    }

    public CustomDialogBuilder setCancelButton(@StringRes int textResId, Action1<Dialog> onCancelBtnClick) {
        return setCancelButton(context.getString(textResId), onCancelBtnClick);
    }

    public CustomDialogBuilder setCancelButton(String text, Action1<Dialog> onCancelBtnClick) {
        this.onCancelBtnClick = onCancelBtnClick;

        viewHolder.dialogCancelBtn.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(text))
            viewHolder.dialogCancelBtn.setVisibility(View.GONE);
        else
            viewHolder.dialogCancelBtn.setText(text);

        return this;
    }

    public CustomDialogBuilder setOkButton(@StringRes int textResId, Action1<Dialog> onOkBtnClick) {
        return setOkButton(context.getString(textResId), onOkBtnClick);
    }

    public CustomDialogBuilder setOkButton(String text, Action1<Dialog> onOkBtnClick) {
        this.onOkBtnClick = onOkBtnClick;

        viewHolder.dialogOkBtn.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(text))
            viewHolder.dialogOkBtn.setVisibility(View.GONE);
        else
            viewHolder.dialogOkBtn.setText(text);

        return this;
    }

    public void showDialog() {
        Dialog dialog = builder.create();

        viewHolder.dialogCancelBtn.setOnClickListener(v -> {
            if(onCancelBtnClick != null)
                onCancelBtnClick.call(dialog);
            dialog.dismiss();
        });

        final EditText editText = viewHolder.dialogEditText;
        viewHolder.dialogOkBtn.setOnClickListener(v -> {
            if(onOkBtnClick != null)
                onOkBtnClick.call(dialog);
            if(onEditBack != null)
                onEditBack.call(editText.getText().toString());
            dialog.dismiss();
        });

        dialog.show();

        context = null;
        builder = null;
        viewHolder = null;
    }

    public static class DialogViewHolder {
        @Bind(R.id.dialog_title_tv)
        TextView dialogTitleTv;
        @Bind(R.id.dialog_content_tv)
        TextView dialogContentTv;
        @Bind(R.id.dialog_cancel_btn)
        TextView dialogCancelBtn;
        @Bind(R.id.dialog_ok_btn)
        TextView dialogOkBtn;
        @Bind(R.id.dialog_edit_text)
        EditText dialogEditText;

        DialogViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
