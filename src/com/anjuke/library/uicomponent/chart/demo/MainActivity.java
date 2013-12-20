package com.anjuke.library.uicomponent.chart.demo;

import android.app.Activity;
import android.os.Bundle;

import com.anjuke.library.uicomponent.chart.linechart.CommunityLineChart;
import com.anjuke.library.uicomponent.chart.linechart.LineChartData;
import com.anjuke.library.uicomponent.chart.linechart.VerticalLegend;
import com.svenkapudija.fancychart.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

        VerticalLegend legend = (VerticalLegend) findViewById(R.id.chart_vertical);
        // chart.setOnPointClickListener(new FancyChartPointListener() {
        //
        // @Override
        // public void onClick(Point point) {
        // Toast.makeText(MainActivity.this, "I clicked point " + point,
        // Toast.LENGTH_LONG).show();
        // }
        // });

        CommunityLineChart chart = (CommunityLineChart) findViewById(R.id.chart);
        LineChartData data = new LineChartData(LineChartData.LINE_COLOR_BLUE);
        int[] yValues = new int[] {
                20360, 21350, 25350, 26320, 27000, 26000, 26700, 27100, 25300, 26300, 27100,
                26800
        };
        for (int i = 0; i <= 11; i++) {
            data.addPoint(i, yValues[i]);
            data.addXValue(i, (i + 1) + "月");
        }
        chart.setData(data);
		
        // ChartData data2 = new ChartData(ChartData.LINE_COLOR_RED);
        // int[] yValues2 = new int[]{0, 5, 9, 23, 15, 35, 45, 50, 41, 45, 32,
        // 24};
        // for(int i = 8; i <= 19; i++) {
        // data2.addPoint(i, yValues2[i-8]);
        // data2.addXValue(i, i + ":00");
        // }
        // data2.addXValue(20, "20");
        // data2.addYValue(30, "30");
        // chart.addData(data2);
        legend.setChart(chart);
        legend.invalidate();
	}

}