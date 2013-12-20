package com.anjuke.library.uicomponent.chart.linechart;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class VerticalLegend extends View {
    public VerticalLegend(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private LineChart chart;


    public void setChart(LineChart chart) {
        this.chart = chart;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (chart != null) {
            chart.drawHorizontalLinesAndLegend(canvas, true, false);
        }
    }
}
