package com.example.cranberry_glass;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.cranberry_glass.model.Node;
import com.example.cranberry_glass.util.Dynamics;
import com.example.cranberry_glass.util.OrientationManager;

public class LineChartView extends View {

    private static final int MIN_LINES = 3;
    private static final int MAX_LINES = 8;
    private static final int[] DISTANCES = { 1, 2, 5 };
    private static final float GRAPH_SMOOTHNES = 0.15f;
    private static final float RATIO = 4f / 4f;

    private Dynamics[] datapoints = null;
    private Paint paint = new Paint();

    private Runnable animator = new Runnable() {
        @Override
        public void run() {
            boolean needNewFrame = false;
            long now = AnimationUtils.currentAnimationTimeMillis();
            for (Dynamics dynamics : datapoints) {
                dynamics.update(now);
                if (!dynamics.isAtRest()) {
                    needNewFrame = true;
                }
            }
            if (needNewFrame) {
                postDelayed(this, 20);
            }
            invalidate();
        }
    };
    private OrientationManager mOrientationManager;
    private String title;

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Sets the y data points of the line chart. The data points are assumed to
     * be positive and equally spaced on the x-axis. The line chart will be
     * scaled so that the entire height of the view is used.
     * 
     * @param datapoints
     *            y values of the line chart
     */
    public void setChartData(float[] newDatapoints, String newTitle) {
        title = newTitle;
        long now = AnimationUtils.currentAnimationTimeMillis();
        if (datapoints == null || datapoints.length != newDatapoints.length) {
            datapoints = new Dynamics[newDatapoints.length];
            for (int i = 0; i < newDatapoints.length; i++) {
                datapoints[i] = new Dynamics(1.1f, 0.30f);
                datapoints[i].setPosition(newDatapoints[i], now);
                datapoints[i].setTargetPosition(newDatapoints[i], now);
            }
            invalidate();
        } else {
            for (int i = 0; i < newDatapoints.length; i++) {
                datapoints[i].setTargetPosition(newDatapoints[i], now);
            }
            removeCallbacks(animator);
            post(animator);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - 20;
        int heigthWithoutPadding = height - 10 - 10;

        int maxWidth = (int) (heigthWithoutPadding * RATIO);
        int maxHeight = (int) (widthWithoutPadding / RATIO);

        if (widthWithoutPadding > maxWidth) {
            width = maxWidth + 10 + 10;
        } else {
            height = maxHeight + 10 + 10;
        }

        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            width = getMeasuredWidth();
        }
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            height = getMeasuredHeight();
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (datapoints != null) {
            float maxValue = getMax(datapoints);
            float minValue = getMin(datapoints);

            drawBackground(canvas, maxValue, minValue);
            drawLineChart(canvas, maxValue, minValue);
        }
    }

    private void drawBackground(Canvas canvas, float maxValue, float minValue) {

        int range = getLineDistance(maxValue, minValue);
        paint.setTextSize(24);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Align.CENTER);
        paint.setStrokeWidth(1);
        canvas.drawText(title, getWidth() / 2, 20, paint);

        paint.setTextAlign(Align.LEFT);
        paint.setStrokeWidth(2);
        paint.setAntiAlias(false);
        canvas.drawLine(10 * 4, getHeight() - 10, 10 * 4, 40, paint);

        canvas.drawLine(10, getHeight() - 10 * 2, getWidth(),
                getHeight() - 10 * 2, paint);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1);

        for (int y = 0; y < maxValue; y += range) {
            final int yPos = (int) getYPos(y, maxValue, minValue);
            // turn on anti alias again for the text
            paint.setAntiAlias(true);
            canvas.drawText(String.valueOf(y), 0, yPos - 2, paint);
        }
    }

    private int getLineDistance(float maxValue, float minValue) {
        long distance;
        int distanceIndex = 0;
        long distanceMultiplier = 1;
        int numberOfLines = MIN_LINES;

        do {
            distance = DISTANCES[distanceIndex] * distanceMultiplier;
            numberOfLines = (int) FloatMath.ceil(maxValue / distance);

            distanceIndex++;
            if (distanceIndex == DISTANCES.length) {
                distanceIndex = 0;
                distanceMultiplier *= 10;

            }
        } while (numberOfLines < MIN_LINES || numberOfLines > MAX_LINES);

        return (int) distance;
    }

    private void drawLineChart(Canvas canvas, float maxValue, float minValue) {
        Path path = createSmoothPath(maxValue, minValue);

        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setShadowLayer(4, 2, 2, 0x81000000);
        canvas.drawPath(path, paint);
        paint.setShadowLayer(0, 0, 0, 0);
    }

    private Path createSmoothPath(float maxValue, float minValue) {

        Path path = new Path();
        path.moveTo(getXPos(0),
                getYPos(datapoints[0].getPosition(), maxValue, minValue));
        for (int i = 0; i < datapoints.length - 1; i++) {
            float thisPointX = getXPos(i);
            float thisPointY = getYPos(datapoints[i].getPosition(), maxValue,
                    minValue);
            float nextPointX = getXPos(i + 1);
            float nextPointY = getYPos(datapoints[si(i + 1)].getPosition(),
                    maxValue, minValue);

            float startdiffX = (nextPointX - getXPos(si(i - 1)));
            float startdiffY = (nextPointY - getYPos(
                    datapoints[si(i - 1)].getPosition(), maxValue, minValue));
            float endDiffX = (getXPos(si(i + 2)) - thisPointX);
            float endDiffY = (getYPos(datapoints[si(i + 2)].getPosition(),
                    maxValue, minValue) - thisPointY);

            float firstControlX = thisPointX + (GRAPH_SMOOTHNES * startdiffX);
            float firstControlY = thisPointY + (GRAPH_SMOOTHNES * startdiffY);
            float secondControlX = nextPointX - (GRAPH_SMOOTHNES * endDiffX);
            float secondControlY = nextPointY - (GRAPH_SMOOTHNES * endDiffY);

            path.cubicTo(firstControlX, firstControlY, secondControlX,
                    secondControlY, nextPointX, nextPointY);
        }
        return path;
    }

    /**
     * Given an index in datapoints, it will make sure the the returned index is
     * within the array
     * 
     * @param i
     * @return
     */
    private int si(int i) {
        if (i > datapoints.length - 1) {
            return datapoints.length - 1;
        } else if (i < 0) {
            return 0;
        }
        return i;
    }

    private float getMax(Dynamics[] array) {
        float max = array[0].getPosition();
        for (int i = 1; i < array.length; i++) {
            if (array[i].getPosition() > max) {
                max = array[i].getPosition();
            }
        }
        return max;
    }

    private float getMin(Dynamics[] array) {
        float min = array[0].getPosition();
        for (int i = 1; i < array.length; i++) {
            if (array[i].getPosition() < min) {
                min = array[i].getPosition();
            }
        }
        return min;
    }

    private float getYPos(float value, float maxValue, float minValue) {
        float height = getHeight() - 20 - 20;

        // scale it to the view size
        value = (value / maxValue) * height;

        // invert it so that higher values have lower y
        value = height - value + 40;

        // offset it to adjust for padding
        value += 0;

        return value;
    }

    private float getXPos(float value) {
        float width = getWidth() - 10 - 10;
        float maxValue = datapoints.length - 1;

        // scale it to the view size
        value = (value / maxValue) * width;

        // offset it to adjust for padding
        value += 10 * 4.3f;

        return value;
    }

    public void setNearbyNodes(List<Node> nodes) {
        // TODO Auto-generated method stub

    }

    public void setOrientationManager(OrientationManager orientationManager) {
        mOrientationManager = orientationManager;
    }

}