package com.example.miracle.financehelp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miracle.financehelp.App;
import com.example.miracle.financehelp.R;
import com.example.miracle.financehelp.entity.Account;
import com.example.miracle.financehelp.entity.AccountDao;
import com.example.miracle.financehelp.utils.ChartUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Miracle on 2017/9/30 0030.
 */

public class TableFragment extends Fragment {
    @Bind(R.id.OutPieChartView)
    PieChart mOutChart;
    @Bind(R.id.InPieChartView)
    PieChart mInChart;
    @Bind(R.id.OutBarChart)
    BarChart OutBarChart;
    @Bind(R.id.InBarChart)
    BarChart InBarChart;
    private AccountDao dao;
    private int month;
    private Date date1;
    private Date date2;
    private static final String TAG = TableFragment.class.getSimpleName();
    private Calendar calendar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        date1 = new Date(year, month, 0);
        date2 = new Date(year, month, 31);
    }

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
        initOutPieChartView();
        initInPieChartView();
        initOutLineChartView();
        initInLineChartView();
    }

    /**
     * 显示当前月份的支出比例
     */
    private void initOutPieChartView() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        List<Account> list = dao.queryBuilder().where(AccountDao.Properties.Category.eq(2),
                AccountDao.Properties.Time.between(date1, date2)).list();
        float total = 0.0f;
        for (int i = 0; i < list.size(); i++) {
            entries.add(new PieEntry(list.get(i).getTotal(), list.get(i).getOutcomeTypeName()));
            total += list.get(i).getTotal();
        }
        ChartUtils.showPieChartView(mOutChart, String.valueOf(month + 1), entries, ChartUtils.OUTTYPE, total);
    }

    /**
     * 显示当前月份的收入比例
     */
    private void initInPieChartView() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        List<Account> list = dao.queryBuilder().where(AccountDao.Properties.Category.eq(1),
                AccountDao.Properties.Time.between(date1, date2)).list();
        float total = 0.0f;
        for (int i = 0; i < list.size(); i++) {
            entries.add(new PieEntry(list.get(i).getTotal(), list.get(i).getIncometypeName()));
            total += list.get(i).getTotal();
        }
        ChartUtils.showPieChartView(mInChart, String.valueOf(month + 1), entries, ChartUtils.INTYPE, total);
    }


    /**
     * 半年内支出情况
     */
    private void initOutLineChartView() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Calendar calendar = Calendar.getInstance();
            float total = 0.0f;
            calendar.add(Calendar.MONTH, -i);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            Date date1 = new Date(year, month, 1);
            Date date2 = new Date(year, month, 31);
            List<Account> mList = dao.queryBuilder().where(AccountDao.Properties.Time.between(date1, date2),
                    AccountDao.Properties.Category.eq(2)).list();
            for (int j = 0; j < mList.size(); j++) {
                total += mList.get(j).getTotal();
            }
            entries.add(new BarEntry(i, total));
        }
        ChartUtils.showLineChartView(OutBarChart, entries, ChartUtils.OUTTYPE);
    }

    /**
     * 半年内收入情况
     */
    private void initInLineChartView() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Calendar calendar = Calendar.getInstance();
            float total = 0.0f;
            calendar.add(Calendar.MONTH, -i);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            Date date1 = new Date(year, month, 1);
            Date date2 = new Date(year, month, 31);
            List<Account> mList = dao.queryBuilder().where(AccountDao.Properties.Time.between(date1, date2),
                    AccountDao.Properties.Category.eq(1)).list();
            for (int j = 0; j < mList.size(); j++) {
                total += mList.get(j).getTotal();
            }
            entries.add(new BarEntry(i, total));
        }
        ChartUtils.showLineChartView(InBarChart, entries, ChartUtils.INTYPE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
