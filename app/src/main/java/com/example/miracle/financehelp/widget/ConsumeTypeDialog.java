package com.example.miracle.financehelp.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.miracle.financehelp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConsumeTypeDialog extends Dialog {
    OnClickListener listener;
    @Bind(R.id.consumeEt)
    EditText consumeEt;
    @Bind(R.id.selectedBtn)
    Button selectedBtn;
    @Bind(R.id.cancelBtn)
    Button cancelBtn;

    public ConsumeTypeDialog(@NonNull Context paramContext) {
        super(paramContext);
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.dialog_consume_layout);
        ButterKnife.bind(this, this);
    }


    @OnClick({R.id.selectedBtn, R.id.cancelBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.selectedBtn:
                listener.onPositiveBtnClick(consumeEt.getText().toString());
                break;
            case R.id.cancelBtn:
                listener.onCancelBtnClick();
                break;
        }
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {
        void onCancelBtnClick();

        void onPositiveBtnClick(String typeName);
    }
}
