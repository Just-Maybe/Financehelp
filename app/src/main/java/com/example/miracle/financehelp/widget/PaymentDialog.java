package com.example.miracle.financehelp.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.miracle.financehelp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentDialog
        extends Dialog {
    public static final String WEIXIN = "微信";
    public static final String XIANJIN = "现金";
    public static final String YINHANGKA = "银行卡";
    public static final String ZHIFUBAO = "支付宝";
    public OnSelectedListener listener;
    @Bind(R.id.weixin)
    RadioButton weixin;
    @Bind(R.id.zhifubao)
    RadioButton zhifubao;
    @Bind(R.id.xianjin)
    RadioButton xianjin;
    @Bind(R.id.yinhangka)
    RadioButton yinhangka;
    @Bind(R.id.selectedBtn)
    Button selectedBtn;
    @Bind(R.id.cancelBtn)
    Button cancelBtn;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    private String payment;

    public PaymentDialog(@NonNull Context paramContext) {
        super(paramContext);
    }

    private void setupRadioGroup() {
//        this.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            public void onCheckedChanged(RadioGroup paramAnonymousRadioGroup, @IdRes int paramAnonymousInt) {
//                switch (paramAnonymousInt) {
//                    default:
//                        return;
//                    case 2131624151:
//                        PaymentDialog.access$002(PaymentDialog.this, "微信");
//                        PaymentDialog.this.listener.onSelected(PaymentDialog.this.payment);
//                        return;
//                    case 2131624154:
//                        PaymentDialog.access$002(PaymentDialog.this, "银行卡");
//                        PaymentDialog.this.listener.onSelected(PaymentDialog.this.payment);
//                        return;
//                    case 2131624152:
//                        PaymentDialog.access$002(PaymentDialog.this, "支付宝");
//                        PaymentDialog.this.listener.onSelected(PaymentDialog.this.payment);
//                        return;
//                }
//                PaymentDialog.access$002(PaymentDialog.this, "现金");
//                PaymentDialog.this.listener.onSelected(PaymentDialog.this.payment);
//            }
//        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.weixin:
                        payment = WEIXIN;
                        weixin.setChecked(true);
                        listener.onSelected(payment);
                        break;
                    case R.id.yinhangka:
                        payment = YINHANGKA;
                        yinhangka.setChecked(true);
                        listener.onSelected(payment);
                        break;
                    case R.id.zhifubao:
                        payment = ZHIFUBAO;
                        zhifubao.setChecked(true);
                        listener.onSelected(payment);
                        break;
                    case R.id.xianjin:
                        payment = XIANJIN;
                        xianjin.setChecked(true);
                        listener.onSelected(payment);
                        break;
                }
            }
        });
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.dialog_payment_selector);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        ButterKnife.bind(this, this);
        setupRadioGroup();
    }


    @OnClick({R.id.selectedBtn, R.id.cancelBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.selectedBtn:
                listener.onPositiveBtnClick(payment);
                break;
            case R.id.cancelBtn:
                listener.onCancelBtnClick();
                break;
        }
    }

    public void setOnSelectedListener(OnSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnSelectedListener {
        void onCancelBtnClick();

        void onPositiveBtnClick(String payment);

        void onSelected(String payment);
    }
}
