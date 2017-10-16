package com.example.miracle.financehelp.utils;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.Calendar;

/**
 * Created by Miracle on 2017/10/16 0016.
 */

public class DayAxisValueFormatter implements IAxisValueFormatter {
    private static final String TAG = DayAxisValueFormatter.class.getSimpleName();

    public DayAxisValueFormatter() {
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, (int) -value);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        return year + "-" + month;
    }
}
