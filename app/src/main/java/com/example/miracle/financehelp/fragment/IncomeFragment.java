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
import com.example.miracle.financehelp.entity.IncomeType;
import com.example.miracle.financehelp.entity.IncomeTypeDao;
import com.example.miracle.financehelp.utils.SizeUtils;
import com.example.miracle.financehelp.utils.yyHelper;
import com.example.miracle.financehelp.widget.ConsumeTypeDialog;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IncomeFragment extends Fragment {
    @Bind(R.id.consumeType)
    TextView consumeType;
    @Bind(R.id.TotalImage)
    ImageView TotalImage;
    @Bind(R.id.dateImage)
    ImageView dateImage;
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
    private DatePickerDialog dialog;
    IncomeType incomeType = new IncomeType();
    private List<IncomeType> mLists = new ArrayList();
    private yyHelper yyHelper;
    private IncomeTypeDao dao;

    private void initTypeData() {
        consumeType.setText("工资");
        TotalImage.setBackgroundResource(R.drawable.gongzi_x48);
        Calendar calendar = Calendar.getInstance();

        final int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        dateTv.setText(year + "-" + (month + 1) + "-" + day);
        incomeType.setIncometypeName(consumeType.getText().toString());
        incomeType.setIncomeImage("drawable/gongzi_x48");
        mLists.add(new IncomeType("drawable/gongzi_x48", "工资"));
        mLists.add(new IncomeType("drawable/shenghuofei_x48", "生活费"));
        mLists.add(new IncomeType("drawable/hongbao_x48", "红包"));
        mLists.add(new IncomeType("drawable/lingqian_x48", "零钱"));
        mLists.add(new IncomeType("drawable/jianzhi_x48", "兼职"));
        mLists.add(new IncomeType("drawable/touzi_x48", "投资"));
        mLists.add(new IncomeType("drawable/jiangjin_x48", "奖金"));
        mLists.add(new IncomeType("drawable/baoxiao_x48", "报销"));
        mLists.add(new IncomeType("drawable/xianjin_x48", "现金"));
        mLists.add(new IncomeType("drawable/tuikuan_x48", "退款"));
        mLists.add(new IncomeType("drawable/zhifubao_x48", "支付宝"));
        mLists.add(new IncomeType("drawable/jieru_x48", "借入"));
        mLists.add(new IncomeType("drawable/weixin_x48", "微信"));
        mLists.add(new IncomeType("drawable/qita_x48", "其他"));
        mLists.addAll(dao.loadAll());
        mLists.add(new IncomeType("drawable/add_type_x48", "自定义"));
        for (int i = 0; i < mLists.size(); i++) {
            FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(SizeUtils.dp2px(getActivity(), 85), SizeUtils.dp2px(getActivity(), 85));
            Button button = new Button(getActivity());
            final int imageResource = getResources().getIdentifier((mLists.get(i)).getIncomeImage(), null, getContext().getPackageName());
            Drawable drawable = getResources().getDrawable(imageResource);
            button.setBackgroundColor(Color.TRANSPARENT);
            button.setText(mLists.get(i).getIncometypeName());
            button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13f);
            button.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            if (mLists.get(i).getIncometypeName().equals("自定义")) {
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
                                IncomeType incomeType = new IncomeType("drawable/zidingyi_x48", typeName);
                                dao.insert(incomeType);
                                addTypeData(incomeType);
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
                        consumeType.setText(mLists.get(finalI).getIncometypeName());
                        incomeType.setIncomeImage(mLists.get(finalI).getIncomeImage());
                        incomeType.setIncometypeName(mLists.get(finalI).getIncometypeName());
                    }
                });
            }
            flexboxLayout.addView(button, lp);
        }
    }

    private void insertIncome() {
        AccountDao Dao = App.getDaoSession().getAccountDao();
        Account account = new Account();
        account.setCategory(1);
        account.setRemark(remarkEt.getText().toString());
        account.setTime(getDate());
        if (!TextUtils.isEmpty(total.getText().toString())) {
            account.setTotal(Float.parseFloat(total.getText().toString()));
            if (!TextUtils.isEmpty(consumeType.getText().toString())) {
                account.setIncomeImage(incomeType.getIncomeImage());
                account.setIncometypeName(incomeType.getIncometypeName());
                Dao.insert(account);
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
                insertIncome();

            }
        });
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

    public void addTypeData(final IncomeType incomeType) {
        mLists.add(incomeType);
        Button button = new Button(getActivity());
        button.setBackgroundColor(Color.TRANSPARENT);
        final int i = getResources().getIdentifier(incomeType.getIncomeImage(), null, getContext().getPackageName());
        Drawable localDrawable = getResources().getDrawable(i);
        button.setText(incomeType.getIncometypeName());
        button.setTextSize(1, 13.0F);
        button.setCompoundDrawablesWithIntrinsicBounds(null, localDrawable, null, null);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                TotalImage.setBackgroundResource(i);
                consumeType.setText(incomeType.getIncometypeName());
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_income, parent, false);
        ButterKnife.bind(this, view);
        return view;
    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dao = App.getDaoSession().getIncomeTypeDao();
        initTypeData();
        setupInsertBtn();
        setupSpeechRecognizer();
    }


    @OnClick(R.id.dateLayout)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dateLayout:
                setupDatePicker();
                break;
        }
    }

}
