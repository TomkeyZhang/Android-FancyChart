
package com.anjuke.library.uicomponent.chart.linechart;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.anjuke.library.uicomponent.chart.AxisValue;
import com.anjuke.library.uicomponent.chart.Point;

public class LineChart extends View {

    private static int VERTICAL_LEGEND_WIDTH = 10;
    private static int VERTICAL_LEGEND_MARGIN_RIGHT = 5;

    private static int HORIZONTAL_LEGEND_HEIGHT = 10;
    private static int HORIZONTAL_LEGEND_MARGIN_TOP = 20;

    private static int TOUCH_THRESHOLD = 20;

    private LineChartStyle chartStyle;
    private ChartPointListener onPointClickListener;

    private List<LineChartData> chartData;
    private DisplayMetrics dm = new DisplayMetrics();

    public LineChart(Context context) {
        super(context);
        init();
    }

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.chartData = new ArrayList<LineChartData>();
        this.chartStyle = new LineChartStyle();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    public void addData(LineChartData data) {
        if (data.getXValues().size() == 0 && data.getPoints().size() > 0) {
            data.automaticallyAddXValues();
        }

        if (data.getYValues().size() == 0 && data.getPoints().size() > 0) {
            data.automaticallyAddYValues();
        }

        chartData.add(data);
    }

    public void setOnPointClickListener(ChartPointListener
            onPointClickListener) {
        this.onPointClickListener = onPointClickListener;
    }

    public LineChartStyle getChartStyle() {
        return chartStyle;
    }

    public List<LineChartData> getChartData() {
        return chartData;
    }

    public LineChartData getLastChartData() {
        return chartData.get(chartData.size() - 1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mViewWidth = measureWidth(widthMeasureSpec);
        int mViewHeight = measureHeight(heightMeasureSpec);
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    private int measureWidth(int measureSpec) {
        return getMeasurement(measureSpec);
    }

    private int measureHeight(int measureSpec) {
        return getMeasurement(measureSpec);
    }

    private int getMeasurement(int measureSpec) {
        int specSize = MeasureSpec.getSize(measureSpec);
        int measurement;
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.EXACTLY:// fill_parent
                measurement = specSize;
                break;
            case MeasureSpec.AT_MOST:// wrap_content
                measurement = specSize;
                break;
            default:// HorizontalScrollView下的 wrap_content
                measurement = 2 * dm.widthPixels;
                break;
        }
        return measurement;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        drawVerticalLinesAndLegend(canvas, chartStyle.isDrawVerticalLegend(), chartStyle.isDrawVerticalLines());
        drawHorizontalLinesAndLegend(canvas, chartStyle.isDrawHorizontalLegend(), chartStyle.isDrawHorizontalLines());
        calculateCanvasCoordinates();
        if (chartStyle.isDrawBackgroundBelowLine()) {
            drawBackgroundBelowLine(canvas);
        }
        drawLineAndCircleAndTip(canvas);
    }

    protected void drawLineAndCircleAndTip(Canvas canvas) {
        Paint backgroundColorPaint = new Paint();
        backgroundColorPaint.setColor(chartStyle.getPointColor());
        backgroundColorPaint.setStyle(Paint.Style.FILL);
        backgroundColorPaint.setAntiAlias(true);

        Paint paintLine = new Paint();
        paintLine.setStyle(Paint.Style.FILL);
        paintLine.setStrokeWidth(chartStyle.getDataLineWidth());
        paintLine.setAntiAlias(true);

        for (LineChartData data : chartData) {
            Paint paintSelectedBorder = new Paint();
            paintSelectedBorder.setColor(data.getBelowLineColor());
            paintSelectedBorder.setStrokeWidth(chartStyle.getSelectedBoxStrokeWidth());
            paintSelectedBorder.setStyle(Paint.Style.FILL_AND_STROKE);
            paintSelectedBorder.setAntiAlias(true);

            paintLine.setColor(data.getLineColor());
            List<Point> points = data.getPoints();

            drawLinesBetweenPoints(points, canvas, paintLine);

            for (Point point : points) {
                if (chartStyle.isDrawTip())
                    drawTip(canvas, point, data);
                if (point.isSelected()) {
                    // Draw selected point
                    canvas.drawCircle(point.getCanvasX(), point.getCanvasY(),
                            chartStyle.getPointRadius() + chartStyle.getPointStrokeWidth(), paintSelectedBorder);
                    canvas.drawCircle(point.getCanvasX(), point.getCanvasY(), chartStyle.getPointRadius(), paintLine);

                    // drawPopupBox(canvas, point, data);
                } else {
                    canvas.drawCircle(point.getCanvasX(), point.getCanvasY(), chartStyle.getPointRadius(), paintLine);
                    canvas.drawCircle(point.getCanvasX(), point.getCanvasY(),
                            chartStyle.getPointRadius() - chartStyle.getPointStrokeWidth(), backgroundColorPaint);
                }
            }
        }
    }

    protected void drawPonitCircle(Canvas canvas, Point point, Paint backgroundColorPaint, Paint paintLine) {
        canvas.drawCircle(point.getCanvasX(), point.getCanvasY(), chartStyle.getPointRadius(), paintLine);
        canvas.drawCircle(point.getCanvasX(), point.getCanvasY(),
                chartStyle.getPointRadius() - chartStyle.getPointStrokeWidth(), backgroundColorPaint);
    }

    /**
     * 给线条下方涂色，默认为线条颜色加30%的透明度的色值
     */
    protected void drawBackgroundBelowLine(Canvas canvas) {
        for (LineChartData data : chartData) {
            int minCanvasX = VERTICAL_LEGEND_WIDTH + VERTICAL_LEGEND_MARGIN_RIGHT
                    + chartStyle.getChartPaddingLeft();
            int minCanvasY = getHeight() - HORIZONTAL_LEGEND_HEIGHT - HORIZONTAL_LEGEND_MARGIN_TOP
                    - chartStyle.getChartPaddingBottom();

            int maxCanvasX = getWidth() - chartStyle.getChartPaddingRight();

            Path path = new Path();
            path.moveTo(minCanvasX, minCanvasY);

            List<Point> points = data.getPoints();
            for (Point point : points) {
                path.lineTo(point.getCanvasX(), point.getCanvasY());
            }

            path.lineTo(maxCanvasX, minCanvasY);
            path.lineTo(minCanvasX, minCanvasY);
            path.close();

            Paint paintBelowLine = new Paint();
            paintBelowLine.setColor(data.getBelowLineColor());
            paintBelowLine.setStyle(Paint.Style.FILL);
            paintBelowLine.setAntiAlias(true);

            canvas.drawPath(path, paintBelowLine);
        }
    }

    protected void drawTip(Canvas canvas, Point point, LineChartData data) {
        Paint paintSelectedBorder = new Paint();
        // paintSelectedBorder.setColor(data.getBelowLineColor());
        paintSelectedBorder.setStrokeWidth(chartStyle.getSelectedBoxStrokeWidth());
        paintSelectedBorder.setStyle(Paint.Style.FILL_AND_STROKE);
        paintSelectedBorder.setAntiAlias(true);
        String y = String.valueOf(point.getY());
        int maxTextRowLength = y.length();

        // TODO: Crashes on smaller text lengths
        int rWidth = (int) (maxTextRowLength * (chartStyle.getTipTextSize() / 1.75));
        int rHeight = (int) (chartStyle.getTipTextSize() * 1.78 * 2);
        RectF borderRectangle = new RectF(point.getCanvasX() - (rWidth / 2), point.getCanvasY() - (rHeight / 2)
                - chartStyle.getPointRadius() * 3, point.getCanvasY() + (rWidth / 2), point.getCanvasY()
                + (rHeight / 2)
                - chartStyle.getPointRadius() * 3);
        Paint textPaint = new Paint();
        textPaint.setColor(chartStyle.getTipTextColor());
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(chartStyle.getTipTextSize());

        textPaint.setTypeface(Typeface.DEFAULT);
        canvas.drawText(y, borderRectangle.left, borderRectangle.centerY(), textPaint);
    }

    // protected void drawPopupBox(Canvas canvas, Point point, ChartData data) {
    // String title = point.getTitle();
    // String subtitle = point.getSubtitle();
    // if (title == null || subtitle == null) {
    // return;
    // }
    //
    // Paint paintSelectedBorder = new Paint();
    // paintSelectedBorder.setColor(data.getBelowLineColor());
    // paintSelectedBorder.setStrokeWidth(chartStyle.getSelectedBoxStrokeWidth());
    // paintSelectedBorder.setStyle(Paint.Style.FILL_AND_STROKE);
    // paintSelectedBorder.setAntiAlias(true);
    //
    // int maxTextRowLength = Math.max(title.length(), subtitle.length());
    //
    // // TODO: Crashes on smaller text lengths
    // int rWidth = (int) (maxTextRowLength * (chartStyle.getBoxTextSize() /
    // 1.75));
    // int rHeight = (int) (chartStyle.getBoxTextSize() * 1.78 * 2);
    //
    // RectF borderRectangle = new RectF(point.getCanvasX() - (rWidth / 2),
    // point.getCanvasY() - (rHeight / 2)
    // - chartStyle.getPointRadius() * 6, point.getCanvasX() + (rWidth / 2),
    // point.getCanvasY()
    // + (rHeight / 2)
    // - chartStyle.getPointRadius() * 6);
    // canvas.drawRoundRect(borderRectangle, 5, 5, paintSelectedBorder);
    //
    // Paint rectangle = new Paint();
    // rectangle.setColor(data.getBelowLineColor());
    // rectangle.setStyle(Paint.Style.FILL);
    // rectangle.setAntiAlias(true);
    //
    // RectF rInside = new RectF(borderRectangle.left +
    // chartStyle.getSelectedBoxStrokeWidth(), borderRectangle.top
    // + chartStyle.getSelectedBoxStrokeWidth(), borderRectangle.right
    // - chartStyle.getSelectedBoxStrokeWidth(), borderRectangle.bottom
    // - chartStyle.getSelectedBoxStrokeWidth());
    // // canvas.drawRoundRect(rInside, 5, 5, rectangle);
    //
    // drawTextInsideBox(canvas, title, subtitle, rInside);
    // drawTriangleAtBottom(canvas, borderRectangle, rectangle, data);
    // }

    // private void drawTriangleAtBottom(Canvas canvas, RectF borderRectangle,
    // Paint rectangle, ChartData data) {
    // Paint paintSelectedBorder = new Paint();
    // paintSelectedBorder.setColor(data.getBelowLineColor());
    // paintSelectedBorder.setStrokeWidth(chartStyle.getSelectedBoxStrokeWidth());
    // paintSelectedBorder.setStyle(Paint.Style.FILL_AND_STROKE);
    // paintSelectedBorder.setAntiAlias(true);
    //
    // int triangleSideSize = 10;
    //
    // float centerX = (borderRectangle.right - borderRectangle.left) / 2 +
    // borderRectangle.left;
    // float bottomY = (borderRectangle.bottom);
    //
    // Path triangleBorder = new Path();
    // triangleBorder.moveTo(centerX - triangleSideSize, bottomY);
    // triangleBorder.lineTo(centerX + triangleSideSize, bottomY);
    // triangleBorder.lineTo(centerX, bottomY + triangleSideSize);
    // triangleBorder.lineTo(centerX - triangleSideSize, bottomY);
    // triangleBorder.close();
    // canvas.drawPath(triangleBorder, paintSelectedBorder);
    //
    // int triangleSideSizeSmall = 10 - chartStyle.getSelectedBoxStrokeWidth() +
    // 1;
    // float centerXSmall = (borderRectangle.right - borderRectangle.left) / 2 +
    // borderRectangle.left;
    // float bottomYSmall = (borderRectangle.bottom) -
    // chartStyle.getSelectedBoxStrokeWidth();
    //
    // Path triangle = new Path();
    // triangle.moveTo(centerXSmall - triangleSideSizeSmall, bottomYSmall);
    // triangle.lineTo(centerXSmall + triangleSideSizeSmall, bottomYSmall);
    // triangle.lineTo(centerXSmall, bottomYSmall + triangleSideSizeSmall);
    // triangle.lineTo(centerXSmall - triangleSideSizeSmall, bottomYSmall);
    // triangle.close();
    // canvas.drawPath(triangle, rectangle);
    // }
    //
    // private void drawTextInsideBox(Canvas canvas, String title, String
    // subtitle, RectF rInside) {
    // Paint textPaint = new Paint();
    // textPaint.setColor(chartStyle.getBoxTextColor());
    // textPaint.setStyle(Paint.Style.FILL);
    // textPaint.setAntiAlias(true);
    // textPaint.setTextSize(chartStyle.getBoxTextSize());
    //
    // textPaint.setTypeface(Typeface.DEFAULT_BOLD);
    // canvas.drawText(title, rInside.left + 7, rInside.top + 20, textPaint);
    // textPaint.setTypeface(Typeface.DEFAULT);
    // canvas.drawText(subtitle, rInside.left + 7, rInside.top + 20 + 15,
    // textPaint);
    // }

    protected void drawLinesBetweenPoints(List<Point> points, Canvas canvas, Paint paintLine) {
        for (int i = 0; i < points.size() - 1; i++) {
            Point startPoint = points.get(i);
            Point endPoint = points.get(i + 1);

            canvas.drawLine(startPoint.getCanvasX(), startPoint.getCanvasY(), endPoint.getCanvasX(),
                    endPoint.getCanvasY(), paintLine);
        }
    }

    private void calculateCanvasCoordinates() {
        int minCanvasX = VERTICAL_LEGEND_WIDTH + VERTICAL_LEGEND_MARGIN_RIGHT + chartStyle.getChartPaddingLeft();
        int maxCanvasX = getWidth() - chartStyle.getChartPaddingRight();

        int minCanvasY = chartStyle.getChartPaddingTop();
        int maxCanvasY = getHeight() - HORIZONTAL_LEGEND_HEIGHT - HORIZONTAL_LEGEND_MARGIN_TOP
                - chartStyle.getChartPaddingBottom();

        for (LineChartData data : chartData) {
            List<Point> points = data.getPoints();

            for (Point point : points) {
                int newXValue = transformTo(getMinX(), getMaxX(), minCanvasX, maxCanvasX, point.getX());
                int newYValue = (maxCanvasY + chartStyle.getChartPaddingBottom())
                        - transformTo(getMinY(), getMaxY(), minCanvasY, maxCanvasY, point.getY());

                point.setCanvasX(newXValue);
                point.setCanvasY(newYValue);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (onPointClickListener == null)
            return super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();

            boolean selected = false;
            for (LineChartData data : chartData) {
                List<Point> points = data.getPoints();

                for (Point point : points) {
                    int minX = point.getCanvasX() - TOUCH_THRESHOLD;
                    int maxX = point.getCanvasX() + TOUCH_THRESHOLD;

                    int minY = point.getCanvasY() - TOUCH_THRESHOLD;
                    int maxY = point.getCanvasY() + TOUCH_THRESHOLD;

                    if ((x > minX && x < maxX) && (y > minY && y < maxY)) {
                        // Point pressed!
                        if (point.isSelected()) {
                            point.setSelected(false);
                        } else if (selected == false) {
                            selected = true;
                            point.setSelected(true);

                            if (onPointClickListener != null) {
                                onPointClickListener.onClick(point);
                            }
                        }

                        invalidate();
                    } else {
                        point.setSelected(false);
                    }
                }
            }
        }

        return true;
    }

    private double getMinX() {
        double minX = -1;

        for (LineChartData data : chartData) {
            if (minX == -1 || Double.compare(minX, data.getMinX()) < 0) {
                minX = data.getMinX();
            }
        }

        return minX == -1 ? 0 : minX;
    }

    private double getMaxX() {
        double maxX = 0;

        for (LineChartData data : chartData) {
            if (Double.compare(data.getMaxX(), maxX) > 0) {
                maxX = data.getMaxX();
            }
        }

        return maxX;
    }

    private double getMinY() {
        double minY = -1;

        for (LineChartData data : chartData) {
            if (minY == -1 || Double.compare(minY, data.getMinY()) < 0) {
                minY = data.getMinY();
            }
        }

        return minY == -1 ? 0 : minY;
    }

    private double getMaxY() {
        double maxY = 0;

        for (LineChartData data : chartData) {
            if (Double.compare(data.getMaxY(), maxY) > 0) {
                maxY = data.getMaxY();
            }
        }

        return maxY;
    }

    public void drawHorizontalLinesAndLegend(Canvas canvas, boolean drawLegend, boolean drawLine) {
        if (!drawLegend && !drawLine)
            return;
        int canvasHeight = canvas.getHeight() - HORIZONTAL_LEGEND_HEIGHT - HORIZONTAL_LEGEND_MARGIN_TOP;
        int canvasMin = chartStyle.getChartPaddingTop();
        int canvasMax = canvasHeight - chartStyle.getChartPaddingBottom();

        Paint paint = new Paint();
        paint.setColor(chartStyle.getHorizontalGridColor());
        paint.setStrokeWidth(chartStyle.getGridLineWidth());

        Paint paintLegend = new Paint();
        paintLegend.setColor(chartStyle.getyAxisLegendColor());
        paintLegend.setStyle(Paint.Style.FILL);
        paintLegend.setTextSize(chartStyle.getLegendTextSize());
        paintLegend.setAntiAlias(true);

        double min = getMinY();
        double max = getMaxY();

        List<AxisValue> yValues = new ArrayList<AxisValue>();
        for (LineChartData data : chartData) {
            if (yValues.size() == 0 || Double.compare(data.getMaxY(), yValues.get(yValues.size() - 1).value) > 0) {
                yValues = data.getYValues();
            }
        }

        List<AxisValue> reduced = new ArrayList<AxisValue>();
        if (yValues.size() > 10) {
            int step = yValues.size() / 8;

            for (int i = 0; i < yValues.size(); i += step) {
                reduced.add(yValues.get(i));
            }

            min = reduced.get(0).value;
            max = reduced.get(reduced.size() - 1).value;
        } else {
            reduced = yValues;
        }

        for (AxisValue value : reduced) {
            int y = canvasHeight - transformTo(min, max, canvasMin, canvasMax, value.value);
            if (drawLine)
                canvas.drawLine(VERTICAL_LEGEND_WIDTH + VERTICAL_LEGEND_MARGIN_RIGHT, y, getWidth(), y, paint);

            if (value.title != null && drawLegend) {
                int length = value.title.length();
                canvas.drawText(value.title, 20 - length * 6, y + 5, paintLegend);
            }
        }
    }

    private void drawVerticalLinesAndLegend(Canvas canvas, boolean drawLegend, boolean drawLine) {
        if (!drawLegend && !drawLine)
            return;
        double min = getMinX();
        double max = getMaxX();

        int canvasWidth = canvas.getWidth();
        int canvasMin = VERTICAL_LEGEND_WIDTH + VERTICAL_LEGEND_MARGIN_RIGHT + chartStyle.getChartPaddingLeft();
        int canvasMax = canvasWidth - chartStyle.getChartPaddingRight();

        Paint paint = new Paint();
        paint.setColor(chartStyle.getVerticalGridColor());
        paint.setStrokeWidth(chartStyle.getGridLineWidth());

        Paint paintLegend = new Paint();
        paintLegend.setColor(chartStyle.getxAxisLegendColor());
        paintLegend.setStyle(Paint.Style.FILL);
        paintLegend.setTextSize(chartStyle.getLegendTextSize());
        paintLegend.setAntiAlias(true);

        List<AxisValue> xValues = new ArrayList<AxisValue>();
        for (LineChartData data : chartData) {
            if (xValues.size() == 0 || Double.compare(data.getMaxX(), xValues.get(xValues.size() - 1).value) > 0) {
                xValues = data.getXValues();
            }
        }

        for (AxisValue value : xValues) {

            int x = transformTo(min, max, canvasMin, canvasMax, value.value);
            if (drawLine)
                canvas.drawLine(x, 0, x, getHeight() - HORIZONTAL_LEGEND_HEIGHT - HORIZONTAL_LEGEND_MARGIN_TOP, paint);

            if (value.title != null && drawLegend) {
                int length = value.title.length();
                canvas.drawText(value.title, x - length * 6 / 2, getHeight() - chartStyle.getLegendTextSize() / 2,
                        paintLegend);
            }
        }
    }

    private int transformTo(double oldMin, double oldMax, int newMin, int newMax, double oldValue) {
        double oldRange = (oldMax - oldMin);
        double newRange = (newMax - newMin);
        double newValue = (((oldValue - oldMin) * newRange) / oldRange) + newMin;

        return (int) newValue;
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }

    public void clearValues() {
        chartData.clear();
    }

}
