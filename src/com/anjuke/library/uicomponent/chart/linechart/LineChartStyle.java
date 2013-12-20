
package com.anjuke.library.uicomponent.chart.linechart;

import android.graphics.Color;

public class LineChartStyle {
    /**
     * 是否在线条下面绘制颜色
     */
    private boolean drawBackgroundBelowLine;
    /**
     * 结点颜色
     */
    private int pointColor;
    /**
     * 网格横线颜色
     */
    private int horizontalGridColor;
    /**
     * 网格竖线颜色
     */
    private int verticalGridColor;
    /**
     * 横轴标题文本颜色
     */
    private int xAxisLegendColor;
    /**
     * 纵轴标题文本颜色
     */
    private int yAxisLegendColor;
    /**
     * 结点半径
     */
    private int pointRadius;
    /**
     * 结点画笔的宽度
     */
    private int pointStrokeWidth;
    /**
     * 横轴和纵轴标题的文本大小
     */
    private int legendTextSize;
    /**
     * 结点提示文本的大小
     */
    private int tipTextSize;
    /**
     * 结点提示文本的颜色
     */
    private int tipTextColor;

    /**
     * 节点连接线的宽度
     */
    private int dataLineWidth;
    /**
     * 网格线宽度
     */
    private int gridLineWidth;
    /**
     * 选中点的画笔的宽度
     */
    private int selectedBoxStrokeWidth;
    /**
     * 图表左边距
     */
    private int chartPaddingLeft;
    /**
     * 图表右边距
     */
    private int chartPaddingRight;
    /**
     * 图表上边距
     */
    private int chartPaddingTop;
    /**
     * 图表下边距
     */
    private int chartPaddingBottom;

    /**
     * 是否画背景竖线
     */
    private boolean drawVerticalLines;
    /**
     * 是否画背景横线
     */
    private boolean drawHorizontalLines;
    /**
     * 是否画横坐标文本
     */
    private boolean drawVerticalLegend;
    /**
     * 是否画纵坐标文本
     */
    private boolean drawHorizontalLegend;
    /**
     * 是否在点的上方显示描述的值
     */
    private boolean drawTip;

    public LineChartStyle() {
        pointColor = Color.parseColor("#efefeb");

        horizontalGridColor = Color.parseColor("#ddddda");
        verticalGridColor = horizontalGridColor;

        xAxisLegendColor = Color.parseColor("#372f2b");
        yAxisLegendColor = xAxisLegendColor;

        tipTextColor = Color.parseColor("#372f2b");

        pointColor = Color.parseColor("#ffffff");

        pointRadius = 9;
        pointStrokeWidth = 3;

        legendTextSize = 14;
        tipTextSize = 14;

        dataLineWidth = 5;
        gridLineWidth = 1;
        selectedBoxStrokeWidth = 3;

        chartPaddingLeft = 20;
        chartPaddingRight = 40;
        chartPaddingTop = 20;
        chartPaddingBottom = 20;

        drawBackgroundBelowLine = true;

        drawVerticalLines = false;
        drawHorizontalLines = false;
        drawVerticalLegend = true;
        drawHorizontalLegend = false;
        drawTip = true;
    }

    public boolean isDrawBackgroundBelowLine() {
        return drawBackgroundBelowLine;
    }

    public int getPointColor() {
        return pointColor;
    }

    public int getHorizontalGridColor() {
        return horizontalGridColor;
    }

    public int getVerticalGridColor() {
        return verticalGridColor;
    }

    public int getxAxisLegendColor() {
        return xAxisLegendColor;
    }

    public int getyAxisLegendColor() {
        return yAxisLegendColor;
    }

    public int getPointRadius() {
        return pointRadius;
    }

    public int getPointStrokeWidth() {
        return pointStrokeWidth;
    }

    public int getLegendTextSize() {
        return legendTextSize;
    }

    public int getTipTextSize() {
        return tipTextSize;
    }

    public int getTipTextColor() {
        return tipTextColor;
    }

    public int getDataLineWidth() {
        return dataLineWidth;
    }

    public int getGridLineWidth() {
        return gridLineWidth;
    }

    public int getSelectedBoxStrokeWidth() {
        return selectedBoxStrokeWidth;
    }

    public int getChartPaddingLeft() {
        return chartPaddingLeft;
    }

    public int getChartPaddingRight() {
        return chartPaddingRight;
    }

    public int getChartPaddingTop() {
        return chartPaddingTop;
    }

    public int getChartPaddingBottom() {
        return chartPaddingBottom;
    }

    public boolean isDrawVerticalLines() {
        return drawVerticalLines;
    }

    public boolean isDrawHorizontalLines() {
        return drawHorizontalLines;
    }

    public boolean isDrawVerticalLegend() {
        return drawVerticalLegend;
    }

    public boolean isDrawHorizontalLegend() {
        return drawHorizontalLegend;
    }

    public boolean isDrawTip() {
        return drawTip;
    }

    public void setDrawBackgroundBelowLine(boolean drawBackgroundBelowLine) {
        this.drawBackgroundBelowLine = drawBackgroundBelowLine;
    }

    public void setPointColor(int pointColor) {
        this.pointColor = pointColor;
    }

    public void setHorizontalGridColor(int horizontalGridColor) {
        this.horizontalGridColor = horizontalGridColor;
    }

    public void setVerticalGridColor(int verticalGridColor) {
        this.verticalGridColor = verticalGridColor;
    }

    public void setxAxisLegendColor(int xAxisLegendColor) {
        this.xAxisLegendColor = xAxisLegendColor;
    }

    public void setyAxisLegendColor(int yAxisLegendColor) {
        this.yAxisLegendColor = yAxisLegendColor;
    }

    public void setPointRadius(int pointRadius) {
        this.pointRadius = pointRadius;
    }

    public void setPointStrokeWidth(int pointStrokeWidth) {
        this.pointStrokeWidth = pointStrokeWidth;
    }

    public void setLegendTextSize(int legendTextSize) {
        this.legendTextSize = legendTextSize;
    }

    public void setTipTextSize(int tipTextSize) {
        this.tipTextSize = tipTextSize;
    }

    public void setTipTextColor(int tipTextColor) {
        this.tipTextColor = tipTextColor;
    }

    public void setDataLineWidth(int dataLineWidth) {
        this.dataLineWidth = dataLineWidth;
    }

    public void setGridLineWidth(int gridLineWidth) {
        this.gridLineWidth = gridLineWidth;
    }

    public void setSelectedBoxStrokeWidth(int selectedBoxStrokeWidth) {
        this.selectedBoxStrokeWidth = selectedBoxStrokeWidth;
    }

    public void setChartPaddingLeft(int chartPaddingLeft) {
        this.chartPaddingLeft = chartPaddingLeft;
    }

    public void setChartPaddingRight(int chartPaddingRight) {
        this.chartPaddingRight = chartPaddingRight;
    }

    public void setChartPaddingTop(int chartPaddingTop) {
        this.chartPaddingTop = chartPaddingTop;
    }

    public void setChartPaddingBottom(int chartPaddingBottom) {
        this.chartPaddingBottom = chartPaddingBottom;
    }

    public void setDrawVerticalLines(boolean drawVerticalLines) {
        this.drawVerticalLines = drawVerticalLines;
    }

    public void setDrawHorizontalLines(boolean drawHorizontalLines) {
        this.drawHorizontalLines = drawHorizontalLines;
    }

    public void setDrawVerticalLegend(boolean drawVerticalLegend) {
        this.drawVerticalLegend = drawVerticalLegend;
    }

    public void setDrawHorizontalLegend(boolean drawHorizontalLegend) {
        this.drawHorizontalLegend = drawHorizontalLegend;
    }

    public void setDrawTip(boolean drawTip) {
        this.drawTip = drawTip;
    }


}
