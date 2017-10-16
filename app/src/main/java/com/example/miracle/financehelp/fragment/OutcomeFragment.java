package com.example.miracle.financehelp.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.miracle.financehelp.App;
import com.example.miracle.financehelp.R;
import com.example.miracle.financehelp.entity.Account;
import com.example.miracle.financehelp.entity.AccountDao;
import com.example.miracle.financehelp.entity.OutcomeType;
import com.example.miracle.financehelp.entity.OutcomeTypeDao;
import com.example.miracle.financehelp.utils.SizeUtils;
import com.example.miracle.financehelp.utils.yyHelper;
import com.example.miracle.financehelp.widget.ConsumeTypeDialog;
import com.example.miracle.financehelp.widget.PaymentDialog;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OutcomeFragment extends Fragment {
    @Bind(R.id.consumeType)
    TextView consumeType;
    @Bind(R.id.paymentTv)
    TextView paymentTv;
    @Bind(R.id.paymentLayout)
    LinearLayout paymentLayout;
    @Bind(R.id.TotalImage)
    ImageView TotalImage;
    @Bind(R.id.dateTv)
    TextView dateTv;
    @Bind(R.id.dateLayout)
    LinearLayout dateLayout;
    @Bind(R.id.remarkEt)
    EditText remarkEt;
    @Bind(R.id.yyBtn)
    Button yyBtn;
    @Bind(R.id.flexboxLayout)
    FlexboxLayout flexboxLayout;
    @Bind(R.id.total)
    EditText total;
    @Bind(R.id.view2)
    View view2;
    private DatePickerDialog dialog;
    OutcomeType outcomeType = new OutcomeType();
    private List<OutcomeType> mLists = new ArrayList();
    private yyHelper yyHelper;
    private OutcomeTypeDao dao;
    private int flag;
    private long id;
    private AccountDao accountDao;
    private Account account;

    public static OutcomeFragment newInstance(int flag, long id) {

        Bundle args = new Bundle();
        args.putInt("flag", flag);
        args.putLong("AccountId", id);
        OutcomeFragment fragment = new OutcomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flag = getArguments().getInt("flag", 0);
        id = getArguments().getLong("AccountId", -1);
        Log.d("111", "onCreate: flag" + flag);
        accountDao = App.getDaoSession().getAccountDao();
        Log.d("111", "onCreate: " + flag + "-" + id);
    }

    private void initTypeData() {

        if (id == -1) {
            consumeType.setText("一般");
            TotalImage.setBackgroundResource(R.drawable.putong_x48);
            Calendar calendar = Calendar.getInstance();
            final int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            dateTv.setText(year + "-" + (month + 1) + "-" + day);
            outcomeType.setOutcomeTypeName(consumeType.getText().toString());
            outcomeType.setOutcomeImage("drawable/putong_x48");
        }
        mLists.add(new OutcomeType("drawable/putong_x48", "一般"));
        mLists.add(new OutcomeType("drawable/canyin_x48", "餐饮"));
        mLists.add(new OutcomeType("drawable/jiaotong_x48", "交通"));
        mLists.add(new OutcomeType("drawable/jiushui_x48", "酒水"));
        mLists.add(new OutcomeType("drawable/lingshi_x48", "零食"));
        mLists.add(new OutcomeType("drawable/shucai_x48", "蔬菜"));
        mLists.add(new OutcomeType("drawable/shenghuo_x48", "生活用品"));
        mLists.add(new OutcomeType("drawable/shuiguo_x48", "水果"));
        mLists.add(new OutcomeType("drawable/dianying_x48", "电影"));
        mLists.add(new OutcomeType("drawable/yule_x48", "娱乐"));
        mLists.add(new OutcomeType("drawable/lvyou_x48", "旅游"));
        mLists.add(new OutcomeType("drawable/wanggou_x48", "网购"));
        mLists.add(new OutcomeType("drawable/huafei_x48", "话费充值"));
        mLists.add(new OutcomeType("drawable/hongbao_x48", "红包"));
        mLists.add(new OutcomeType("drawable/shuji_x48", "书籍"));
        mLists.add(new OutcomeType("drawable/jiaju_x48", "居家"));
        mLists.add(new OutcomeType("drawable/yifu_x48", "衣服鞋包"));
        mLists.add(new OutcomeType("drawable/jiechu_x48", "借出"));
        mLists.add(new OutcomeType("drawable/hufu_x48", "护肤彩妆"));
        mLists.add(new OutcomeType("drawable/jianshen_x48", "美容健身"));
        mLists.add(new OutcomeType("drawable/fangzu_x48", "房租房贷"));
        mLists.add(new OutcomeType("drawable/taobao_x48", "淘宝"));
        mLists.add(new OutcomeType("drawable/liwu_x48", "人情礼物"));
        mLists.add(new OutcomeType("drawable/yaopin_x48", "药品"));
        mLists.add(new OutcomeType("drawable/shuma_x48", "数码"));
        mLists.add(new OutcomeType("drawable/yisheng_x48", "医生医院"));
        mLists.addAll(dao.loadAll());
        mLists.add(new OutcomeType("drawable/add_type_x48", "自定义"));
        for (int i = 0; i < mLists.size(); i++) {
            FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(SizeUtils.dp2px(getActivity(), 85), SizeUtils.dp2px(getActivity(), 85));
            Button button = new Button(getActivity());
            final int imageResource = getResources().getIdentifier((mLists.get(i)).getOutcomeImage(), null, getContext().getPackageName());
            Drawable drawable = getResources().getDrawable(imageResource);
            button.setText(mLists.get(i).getOutcomeTypeName());
            button.setBackgroundColor(Color.TRANSPARENT);
            button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13f);
            button.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            if (mLists.get(i).getOutcomeTypeName().equals("自定义")) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final ConsumeTypeDialog dialog = new ConsumeTypeDialog(getActivity());
                        dialog.setTitle("自定义类型");
                        dialog.setOnClickListener(new ConsumeTypeDialog.OnClickListener() {
                            @Override
                            public void onCancelBtnClick() {
                                dialog.dismiss();
                            }

                            @Override
                            public void onPositiveBtnClick(String typeName) {
                                OutcomeType OutcomeType = new OutcomeType("drawable/zidingyi_x48", typeName);
                                dao.insert(OutcomeType);
                                addTypeData(OutcomeType);
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                });
            } else {
                final int finalI = i;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TotalImage.setBackgroundResource(imageResource);
                        consumeType.setText(mLists.get(finalI).getOutcomeTypeName());
                        outcomeType.setOutcomeImage(mLists.get(finalI).getOutcomeImage());
                        outcomeType.setOutcomeTypeName(mLists.get(finalI).getOutcomeTypeName());
                    }
                });
            }
            flexboxLayout.addView(button, lp);

        }

    }

    private void insertOutcome() {
        Account account = new Account();
        account.setCategory(2);
        account.setRemark(" " + remarkEt.getText().toString());
        account.setTime(getDate());
        account.setPaymentType(paymentTv.getText().toString());
        if (!TextUtils.isEmpty(total.getText().toString())) {
            account.setTotal(Float.parseFloat(total.getText().toString()));
            if (!TextUtils.isEmpty(consumeType.getText().toString())) {
                account.setOutcomeImage(outcomeType.getOutcomeImage());
                account.setOutcomeTypeName(outcomeType.getOutcomeTypeName());
                accountDao.insert(account);
                getActivity().finish();
            }
        } else {
            Snackbar.make(getView(), "还没输入金额", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void setupDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dateTv.setText(year + "-" + (month + 1) + "-" + day);
            }
        }, year, month, day);
        dialog.show();

    }

    private void setupInsertBtn() {
        getActivity().findViewById(R.id.insertAccountBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (id == -1) {
                    insertOutcome();
                } else {
                    updateOutcome();
                }

            }
        });
    }

    /**
     * 更新支出
     */
    private void updateOutcome() {
        Account account = new Account();
        account.setId(id);
        account.setCategory(2);
        account.setRemark(remarkEt.getText().toString());
        account.setTime(getDate());
        account.setPaymentType(paymentTv.getText().toString());
        if (!TextUtils.isEmpty(total.getText().toString())) {
            account.setTotal(Float.parseFloat(total.getText().toString()));
            if (!TextUtils.isEmpty(consumeType.getText().toString())) {
                account.setOutcomeImage(outcomeType.getOutcomeImage());
                account.setOutcomeTypeName(outcomeType.getOutcomeTypeName());
                accountDao.update(account);
                getActivity().finish();
            }
        } else {
            Snackbar.make(getView(), "还没输入金额", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void setupSpeechRecognizer() {
//        Log.d("111", String.valueOf("setupSpeechRecognizer: engine_type"));
        yyHelper = yyHelper.getInstance(getActivity());
        yyBtn.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View paramAnonymousView, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        yyHelper.startListening(new yyHelper.onResultListener() {
                            public void onResult(String paramAnonymous2String) {
                                remarkEt.setText(paramAnonymous2String);
                            }
                        });
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        yyHelper.stopListening();
                        break;
                }
                return false;
            }
        });
    }

    public void addTypeData(final OutcomeType outcomeType) {
        mLists.add(outcomeType);
        Button button = new Button(getActivity());
        button.setBackgroundColor(Color.TRANSPARENT);
        final int i = getResources().getIdentifier(outcomeType.getOutcomeImage(), null, getContext().getPackageName());
        Drawable localDrawable = getResources().getDrawable(i);
        button.setText(outcomeType.getOutcomeTypeName());
        button.setTextSize(1, 13.0F);
        button.setCompoundDrawablesWithIntrinsicBounds(null, localDrawable, null, null);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                TotalImage.setBackgroundResource(i);
                consumeType.setText(outcomeType.getOutcomeTypeName());
            }
        });
        flexboxLayout.addView(button);
    }

    public Date getDate() {
        Date date = new Date();
        String str = dateTv.getText().toString();
        int year = Integer.parseInt(str.substring(0, str.indexOf("-")));
        int month = Integer.parseInt(str.substring(str.indexOf("-") + 1, str.lastIndexOf("-")));
        int day = Integer.parseInt(str.substring(str.lastIndexOf("-") + 1, str.length()));
        Log.d("111", "getDate: " + year + "-" + month + "-" + day);
        date.setYear(year);
        date.setMonth(month - 1);
        date.setDate(day);
        return date;
    }

    @Nullable
    public View onCreateView(LayoutInflater paramLayoutInflater, @Nullable ViewGroup parent, @Nullable Bundle paramBundle) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_outcome, parent, false);
        ButterKnife.bind(this, view);
        return view;
    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dao = App.getDaoSession().getOutcomeTypeDao();
        if (id != -1) {
            account = accountDao.queryBuilder().where(AccountDao.Properties.Id.eq(id)).unique();
            consumeType.setText(account.getOutcomeTypeName());
            total.setText(account.getTotal() + "");
            int imageResource = getResources().getIdentifier(account.getOutcomeImage(), null, App.getContext().getPackageName());
            TotalImage.setBackgroundResource(imageResource);
            paymentTv.setText(account.getPaymentType());
            dateTv.setText(account.getTime().getYear() + "-" + (account.getTime().getMonth() + 1) + "-" + account.getTime().getDate());
            remarkEt.setText(account.getRemark());
            outcomeType.setOutcomeTypeName(account.getOutcomeTypeName());
            outcomeType.setOutcomeImage(account.getOutcomeImage());
        }
        initTypeData();
        setupInsertBtn();
        setupSpeechRecognizer();
    }


    @OnClick({R.id.dateLayout, R.id.paymentLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dateLayout:
                setupDatePicker();
                break;
            case R.id.paymentLayout:
                setupPaymentWay();
                break;
        }
    }

    private void setupPaymentWay() {
        paymentLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View paramAnonymousView) {
                Log.d("111", "onClick: ");
                final PaymentDialog dialog = new PaymentDialog(OutcomeFragment.this.getActivity());
                dialog.setTitle("选择支付方式");
                dialog.setOnSelectedListener(new PaymentDialog.OnSelectedListener() {
                    public void onCancelBtnClick() {
                        dialog.dismiss();
                    }

                    public void onPositiveBtnClick(String payment) {
                        Log.d("111", "onPositiveBtnClick: 选择" + payment);
                        paymentTv.setText(payment);
                        dialog.dismiss();
                    }

                    public void onSelected(String payment) {
                        Log.d("111", "onSelected: " + payment);
                    }
                });
                dialog.show();
            }
        });
    }
}
