package com.anjuke.library.uicomponent.chart.linechart;

import android.content.Context;
import android.util.AttributeSet;

public class CommunityLineChart extends LineChart {
    public CommunityLineChart(Context context) {
        super(context);
        init();
    }

    public CommunityLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        getChartStyle().setDrawBackgroundBelowLine(false);
    }

    public void setData(LineChartData data) {
        clearValues();
        addData(data);
    }
}
