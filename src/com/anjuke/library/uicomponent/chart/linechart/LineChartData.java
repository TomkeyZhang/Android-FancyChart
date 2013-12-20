
package com.anjuke.library.uicomponent.chart.linechart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.anjuke.library.uicomponent.chart.AxisValue;
import com.anjuke.library.uicomponent.chart.Point;

import android.graphics.Color;

public class LineChartData {

    private static final String TAG = LineChartData.class.getName();

    public static final String LINE_COLOR_BLUE = "#3b86c4";
    public static final String LINE_COLOR_RED = "#cf6e6e";
    public static final String LINE_COLOR_GREEN = "#7bab73";

    private List<AxisValue> yValues;
    private List<AxisValue> xValues;
    private List<Point> points;

    private int lineColor;
    private int belowLineColor;

    public LineChartData(String lineColor) {
        this.yValues = new ArrayList<AxisValue>();
        this.xValues = new ArrayList<AxisValue>();
        this.points = new ArrayList<Point>();

        this.lineColor = Color.parseColor(lineColor);
        // 30% opacity
        this.belowLineColor = Color.parseColor("#33" + lineColor.replace("#", ""));
    }

    public int getLineColor() {
        return lineColor;
    }

    public int getBelowLineColor() {
        return belowLineColor;
    }

    public void addYValue(double value, String title) {
        yValues.add(new AxisValue(value, title));
        Collections.sort(yValues);
    }

    public void addXValue(double value, String title) {
        xValues.add(new AxisValue(value, title));
        Collections.sort(xValues);
    }

    public List<AxisValue> getXValues() {
        return xValues;
    }

    public List<AxisValue> getYValues() {
        return yValues;
    }

    public LineChartData addPoint(int x, int y, String title, String subtitle) {
        Point point = new Point(x, y);
        point.setTitle(title);
        point.setSubtitle(subtitle);
        points.add(point);

        return this;
    }

    public LineChartData addPoint(int x, int y) {
        addPoint(x, y, null, null);

        return this;
    }

    public void automaticallyAddXValues() {
        double maxX = 0;
        for (Point point : points) {
            if (Double.compare(point.getX(), maxX) > 0) {
                maxX = point.getX();
            }
        }

        double step = maxX / 10.0;

        for (double i = 0; Double.compare(i, maxX) < 0; i += step) {
            String label = Integer.toString((int) i);
            if (Double.compare(i, 0) < 0) {
                label = String.format("%.3f", i);
            }

            xValues.add(new AxisValue(i, label));
        }
    }

    public void automaticallyAddYValues() {
        double maxY = 0;
        double minY = Double.MAX_VALUE;
        for (Point point : points) {
            if (Double.compare(point.getY(), maxY) > 0) {
                maxY = point.getY();
            }
            if (Double.compare(point.getY(), minY) < 0) {
                minY = point.getY();
            }
        }
        double startY = minY * 0.9;
        double endY = maxY * 1.2;
        double step = (endY - startY) / 10.0;

        for (double i = startY; Double.compare(i, endY) < 0; i += step) {
            String label = Integer.toString((int) i);
            if (Double.compare(i, 0) < 0) {
                label = String.format("%.3f", i);
            }

            yValues.add(new AxisValue(i, label));
        }
    }

    public double getMinX() {
        if (xValues.size() == 0) {
            return 0;
        }

        return xValues.get(0).value;
    }

    public double getMaxX() {
        if (xValues.size() == 0) {
            return 0;
        }

        return xValues.get(xValues.size() - 1).value;
    }

    public double getMinY() {
        if (yValues.size() == 0) {
            return 0;
        }

        return yValues.get(0).value;
    }

    public double getMaxY() {
        if (yValues.size() == 0) {
            return 0;
        }

        return yValues.get(yValues.size() - 1).value;
    }

    public void clearValues() {
        xValues.clear();
        yValues.clear();
        points.clear();
    }

    public List<Point> getPoints() {
        return points;
    }

}
