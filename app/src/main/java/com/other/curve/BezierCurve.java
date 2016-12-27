package com.other.curve;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinh.vo on 27/12/2016.
 */

public class BezierCurve extends View {

    private final Paint paint;
    private final Paint paintPoint;
    List<Point> points = new ArrayList<>();

    public BezierCurve(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paintPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintPoint.setColor(Color.BLUE);
        paintPoint.setStrokeWidth(20);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        points.add(new Point((int) ev.getX(), (int) ev.getY()));
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < points.size(); i++) {
            canvas.drawPoint(points.get(i).x, points.get(i).y, paintPoint);
        }

        int N = 1000;
        for (int i = 0; i < N; i++) {
            float t = i * 1.0f / N;
            float xt = (float) bezierCurveX(points.size() - 1, t);
            float yt = (float) bezierCurveY(points.size() - 1, t);
            canvas.drawPoint(xt, yt, paint);
        }
        postInvalidate();

    }

    public double bezierCurveX(int n, float t) {
        if (n <= 0)
            return 0;
        double Bt = 0;
        for (int i = 0; i <= n; i++) {
            Bt += biominal(n, i) * Math.pow(1 - t, n - i) * Math.pow(t, i) * points.get(i).x;
        }
        return Bt;
    }

    public double bezierCurveY(int n, float t) {
        if (n <= 0)
            return 0;
        double Bt = 0;
        for (int i = 0; i <= n; i++) {
            Bt += biominal(n, i) * Math.pow(1 - t, n - i) * Math.pow(t, i) * points.get(i).y;
        }
        return Bt;
    }

    public long factorial(int n) {
        long result = 1;
        for (int i = n; i > 1; i--) {
            result *= i;
        }
        return result;
    }

    public double biominal(int n, int k) {
        return 1.0 * factorial(n) / (factorial(n - k) * factorial(k));
    }
}
