package com.aaa.matheando;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class FractionPizzaView extends View {
    private Paint pizzaPaint;
    private Paint slicePaint;
    private int numerator = 1; // Numerador de la fracción
    private int denominator = 1; // Denominador de la fracción
    private float centerX, centerY, radius;

    public FractionPizzaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        pizzaPaint = new Paint();
        pizzaPaint.setColor(Color.rgb(255, 223, 186)); // Color de la pizza
        pizzaPaint.setStyle(Paint.Style.FILL);

        slicePaint = new Paint();
        slicePaint.setColor(Color.RED); // Color de la porción seleccionada
        slicePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2f;
        centerY = h / 2f;
        radius = Math.min(w, h) / 2f - 50; // Radio con margen
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Dibuja la pizza completa
        canvas.drawCircle(centerX, centerY, radius, pizzaPaint);


        // Calcula el ángulo de cada porción
        float sweepAngle = 360f / denominator;

        // Dibuja la porción seleccionada
        for (int i = 0; i < numerator; i++) {
            float startAngle = i * sweepAngle - 90; // Comienza desde la parte superior
            slicePaint.setColor(Color.argb(120, 255, 0, 0));
            canvas.drawArc(centerX - radius, centerY - radius,
                    centerX + radius, centerY + radius,
                    startAngle, sweepAngle, true, slicePaint);
        }

        // Dibuja las líneas de la pizza
        for (int i = 0; i < denominator; i++) {
            float startAngle = i * 360f / denominator - 90; // Comienza desde la parte superior
            slicePaint.setColor(Color.BLACK);
            slicePaint.setStrokeWidth(10);
            canvas.drawLine(
                    centerX, centerY,
                    (float) (centerX + radius * Math.cos(Math.toRadians(startAngle))),
                    (float) (centerY + radius * Math.sin(Math.toRadians(startAngle))),
                    slicePaint);
        }
    }

    // Método para establecer la fracción
    public void setFraction(int num, int denom) {
        this.numerator = num;
        this.denominator = denom;
        invalidate(); // Redibuja la vista
    }
}
