package com.aaa.matheando;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Canvasd extends View {
    private Canvas canvas;
    private Paint drawPaint;
    private Path path;
    private ArrayList<Path> paths;
    private ArrayList<Paint> paints;
    private String shape;
    //coordenadas del touch
    private float initialX, initialY, touchXc, touchYc, currentRadius;
    private boolean isDrawingFigure = false;
    private float startAngle;
    private float sweepAngle = 180;
    //texto
    private ArrayList<TextItem> textItems;

    private String drawText = "Texto predeterminado";


    public Canvasd(Context context) {
        super(context);
        init();
    }

    public Canvasd(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Canvasd(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public Canvasd(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    // Inicializa los objetos necesarios
    private void init() {
        path = new Path();
        paths = new ArrayList<>();
        paints = new ArrayList<>();
        textItems = new ArrayList<>();
        shape = "pincel";

        drawPaint = new Paint();
        drawPaint.setAntiAlias(true);
        drawPaint.setColor(Color.BLUE);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeWidth(10f);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint.setTextSize(70); // Tamaño del texto
        drawPaint.setColor(Color.BLACK);
        drawPaint.setTextAlign(Paint.Align.LEFT);

        //color de fondo
        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int ancho = calcularAncho(widthMeasureSpec);
        int alto = calcularAlto(heightMeasureSpec);
        setMeasuredDimension(ancho, alto);
    }

    private int calcularAlto(int heightMeasureSpec) {
        int res = 100;
        int modo = View.MeasureSpec.getMode(heightMeasureSpec);
        int limite = View.MeasureSpec.getSize(heightMeasureSpec);
        if (modo == View.MeasureSpec.EXACTLY || modo == View.MeasureSpec.AT_MOST) {
            res = limite;
        }
        return res;
    }

    private int calcularAncho(int widthMeasureSpec) {
        int res = 100;
        int modo = View.MeasureSpec.getMode(widthMeasureSpec);
        int limite = View.MeasureSpec.getSize(widthMeasureSpec);
        if (modo == View.MeasureSpec.EXACTLY || modo == View.MeasureSpec.AT_MOST) {
            res = limite;
        }
        return res;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        super.onDraw(canvas);

        // Dibuja todos los trazos previos
        for (int i = 0; i < paths.size(); i++) {
            canvas.drawPath(paths.get(i), paints.get(i));
        }

        // Dibuja todos los textos previos
        for (TextItem textItem : textItems) {
            canvas.drawText(textItem.text, textItem.x, textItem.y, drawPaint);
        }

        // Dibuja la figura actual
        if (isDrawingFigure) {
            switch (shape) {
                case "circle":
                    canvas.drawCircle(initialX, initialY, currentRadius, drawPaint);
                    break;
                case "linea":
                    canvas.drawLine(initialX, initialY, touchXc, touchYc, drawPaint);
                    break;
                case "Rectangulo":
                    canvas.drawRect(initialX, initialY, touchXc, touchYc, drawPaint);
                    break;
                case "oval":
                    canvas.drawOval(initialX, initialY, touchXc, touchYc, drawPaint);
                    break;
                case "arc":
                    canvas.drawArc(initialX, initialY, touchXc, touchYc, startAngle, sweepAngle, false, drawPaint);
                    break;
                case "cubo":
                    canvas.drawRect(initialX, initialY, touchXc, touchYc, drawPaint);
                    break;
                case "text":
                    canvas.drawText(drawText, initialX, initialY, drawPaint);
                    break;
                default:
                    canvas.drawPath(path, drawPaint);
                    break;
            }
        } else {
            canvas.drawPath(path, drawPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Iniciar un nuevo trazo
                path.moveTo(touchX, touchY);

                switch (shape) {
                    case "circle":
                        initialX = touchX;
                        initialY = touchY;
                        currentRadius = 0;
                        isDrawingFigure = true;
                        break;
                    case "linea":
                    case "Rectangulo":
                    case "oval":
                    case "cubo":
                        initialX = touchX;
                        initialY = touchY;
                        touchXc = touchX;
                        touchYc = touchY;
                        isDrawingFigure = true;
                        break;
                    case "arc":
                        initialX = touchX;
                        initialY = touchY;
                        touchXc = touchX;
                        touchYc = touchY;
                        isDrawingFigure = true;
                        startAngle = calculateStartAngle(initialX, initialY, touchX, touchY); // Calcular el ángulo inicial
                        break;
                    case "text":
                        initialX = touchX;
                        initialY = touchY;
                        isDrawingFigure = true;
                        invalidate();
                        return true;
                    default:
                        path.lineTo(touchX, touchY);
                        isDrawingFigure = false;
                        break;
                }

                invalidate(); // Forzar la redibujación
                return true;

            case MotionEvent.ACTION_MOVE:
                switch (shape) {
                    case "circle":
                        // Actualizar el radio del círculo basado en la distancia desde el toque inicial
                        currentRadius = Math.abs(touchX - initialX);
                        break;
                    case "linea":
                    case "Rectangulo":
                    case "oval":
                        touchXc = touchX;
                        touchYc = touchY;
                        break;
                    case "cubo":
                        float sideLength = calculateSquareSideLength(initialX, initialY, touchX, touchY);
                        touchXc = initialX + sideLength * (touchX > initialX ? 1 : -1);  // Ajustar la dirección del movimiento
                        touchYc = initialY + sideLength * (touchY > initialY ? 1 : -1);
                        break;
                    case "arc":
                        touchXc = touchX;
                        touchYc = touchY;
                        startAngle = calculateStartAngle(initialX, initialY, touchX, touchY);
                        sweepAngle = calculateSweepDirection(initialX, touchX);
                        break;

                    case "text":
                        initialX = touchX;
                        initialY = touchY;
                        break;

                    default:
                        path.lineTo(touchX, touchY);
                        break;
                }

                invalidate();
                break;

            case MotionEvent.ACTION_UP:

                switch (shape) {
                    case "circle":
                        // Finalizar el círculo actual
                        path.addCircle(initialX, initialY, currentRadius, Path.Direction.CW);
                        isDrawingFigure = false;
                        break;
                    case "linea":
                        // Dibujar la línea final
                        path.moveTo(initialX, initialY);
                        path.lineTo(touchXc, touchYc);
                        isDrawingFigure = false;
                        break;
                    case "Rectangulo":
                        path.addRect(initialX, initialY, touchX, touchY, Path.Direction.CW);
                        isDrawingFigure = false;
                        break;
                    case "oval":
                        path.addOval(initialX, initialY, touchX, touchY, Path.Direction.CW);
                        isDrawingFigure = false;
                        break;
                    case "arc":
                        path.addArc(initialX, initialY, touchX, touchY, startAngle, sweepAngle);
                        isDrawingFigure = false;
                        break;
                    case "cubo":
                        float finalSideLength = calculateSquareSideLength(initialX, initialY, touchX, touchY);
                        touchXc = initialX + finalSideLength * (touchX > initialX ? 1 : -1);
                        touchYc = initialY + finalSideLength * (touchY > initialY ? 1 : -1);
                        path.addRect(initialX, initialY, touchXc, touchYc, Path.Direction.CW);
                        isDrawingFigure = false;
                        break;
                    case "text":
                        //canvas.drawTextOnPath(drawText, path, touchX, touchY, drawPaint);
                        textItems.add(new TextItem(drawText, initialX, initialY));
                        isDrawingFigure = false;
                        invalidate();
                        break;

                    default:
                        path.lineTo(touchX, touchY);
                        break;
                }

                // Guardar el trazo actual
                paths.add(path);
                paints.add(new Paint(drawPaint)); // Guardar la configuración del trazo actual
                path = new Path(); // Iniciar un nuevo Path para nuevos trazos
                invalidate();
                break;
        }

        return true;
    }

    // Permite cambiar el color del dibujo
    public void setPaintColor(int color) {
        drawPaint.setColor(color);
    }

    // Permite cambiar el grosor del trazo
    public void setStrokeWidth(float width) {
        drawPaint.setStrokeWidth(width);
    }

    // Método para limpiar la pantalla
    public void clearCanvas() {
        paths.clear();
        paints.clear();
        textItems.clear();
        invalidate(); // Redibujar
    }

    public void setStyle(Paint.Style style) {
        drawPaint.setStyle(style);
    }

    // Metodo para cambiar pincel
    public void setShape(String shape) {
        this.shape = shape;
    }

    public void setDrawText(String text) {
        this.drawText = text;
    }

    private float calculateStartAngle(float initialX, float initialY, float touchX, float touchY) {
        // Calcular el ángulo de inicio basado en las coordenadas iniciales y finales
        double deltaX = touchX - initialX;
        double deltaY = touchY - initialY;
        return (float) Math.toDegrees(Math.atan2(deltaY, deltaX));
    }


    private float calculateSweepDirection(float initialX, float touchX) {
        if (touchX > initialX) {
            return 180;
        } else {
            return -180;
        }
    }

    private float calculateSquareSideLength(float initialX, float initialY, float touchX, float touchY) {
        // Calcula la diferencia en X y en Y
        float deltaX = Math.abs(touchX - initialX);
        float deltaY = Math.abs(touchY - initialY);

        return Math.min(deltaX, deltaY);
    }
}

class TextItem {
    String text;
    float x, y;

    TextItem(String text, float x, float y) {
        this.text = text;
        this.x = x;
        this.y = y;
    }
}

