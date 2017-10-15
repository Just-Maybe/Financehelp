package com.example.miracle.financehelp.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miracle.financehelp.App;
import com.example.miracle.financehelp.R;
import com.example.miracle.financehelp.entity.Account;
import com.example.miracle.financehelp.entity.AccountDao;
import com.example.miracle.financehelp.utils.ChartUtils;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Miracle on 2017/9/30 0030.
 */

public class TableFragment extends Fragment {
    @Bind(R.id.pieChartView)
    PieChart mChart;
    private AccountDao dao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_chart, container, false);
        ButterKnife.bind(this, view);
        dao = App.getDaoSession().getAccountDao();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initPieChartView();

    }

    private void initPieChartView() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        List<Account> list = dao.queryBuilder().where(AccountDao.Properties.Category.eq(2)).list();
        for (int i = 0; i < list.size(); i++) {
            entries.add(new PieEntry(list.get(i).getTotal(), list.get(i).getOutcomeTypeName()));
        }
        ChartUtils.showPieChartView(mChart, "支出图", entries);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
