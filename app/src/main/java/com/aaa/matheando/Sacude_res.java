package com.aaa.matheando;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class Sacude_res extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;

    private ImageView balanceImage;
    private TextView operationText;
    private TextView leftAnswerText;
    private TextView rightAnswerText;

    private float currentRotation = 0f;
    private int correctAnswer = 0;
    private boolean isLeftCorrect = false;
    private Random random;

    private progress progressBar;
    private int progreso = 0;
    private int numactividad = 1;
    private int progressIncrement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sacude_res);

        // Inicializar componentes
        initializeViews();
        initializeSensors();

        random = new Random();

        // Generar primera operación
        generateNewOperation();
    }

    private void initializeViews() {
        balanceImage = findViewById(R.id.balanceImage);
        operationText = findViewById(R.id.operationText);
        leftAnswerText = findViewById(R.id.leftAnswer);
        rightAnswerText = findViewById(R.id.rightAnswer);
        progressBar = findViewById(R.id.progressC);

        //resivir intent
        Intent intent = getIntent();
        if (intent != null) {
            progreso = intent.getIntExtra("progreso", 0);
            numactividad = intent.getIntExtra("numeroActividades", 1);
            // Calcular cuánto debe incrementar por cada actividad
            progressIncrement = 100 / numactividad;

            // Actualizar la barra de progreso
            progressBar.setProgreso(progreso);
        }
    }

    private void initializeSensors() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    private void generateNewOperation() {
        // Generar números aleatorios entre 1 y 20
        int num1 = random.nextInt(20) + 1;
        int num2 = random.nextInt(20) + 1;

        // Decidir si será suma o resta
        boolean isAddition = random.nextBoolean();

        // Calcular respuesta correcta y mostrar operación
        if (isAddition) {
            operationText.setText(num1 + " + " + num2 + " = ?");
            correctAnswer = num1 + num2;
        } else {
            operationText.setText(num1 + " - " + num2 + " = ?");
            correctAnswer = num1 - num2;
        }

        // Decidir aleatoriamente qué lado tendrá la respuesta correcta
        isLeftCorrect = random.nextBoolean();

        // Generar respuesta incorrecta (diferente a la correcta)
        int wrongAnswer = correctAnswer;
        while (wrongAnswer == correctAnswer) {
            wrongAnswer = correctAnswer + random.nextInt(11) - 5; // número entre -5 y 5
        }

        // Asignar respuestas a los lados
        if (isLeftCorrect) {
            leftAnswerText.setText(String.valueOf(correctAnswer));
            rightAnswerText.setText(String.valueOf(wrongAnswer));
        } else {
            leftAnswerText.setText(String.valueOf(wrongAnswer));
            rightAnswerText.setText(String.valueOf(correctAnswer));
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];

            // Calcular el ángulo de rotación basado en la inclinación
            float rotation = x * -3; // Multiplicar por -3 para amplificar el efecto

            // Limitar la rotación a ±30 grados
            rotation = Math.max(-30f, Math.min(30f, rotation));

            // Animar la rotación de la balanza
            RotateAnimation rotateAnimation = new RotateAnimation(
                    currentRotation,
                    rotation,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
            );
            rotateAnimation.setDuration(50);
            rotateAnimation.setFillAfter(true);

            balanceImage.startAnimation(rotateAnimation);
            currentRotation = rotation;

            // Verificar respuesta cuando la inclinación es suficiente
            if (Math.abs(rotation) > 20) {
                boolean selectedLeft = rotation < 0;
                if (selectedLeft == isLeftCorrect) {
                    // ¡Respuesta correcta!
                    Toast.makeText(this, "Respuesta correcta!", Toast.LENGTH_SHORT).show();
                    //parar sensor y animación
                    onPause();
                    // Retrasar la finalización para que se vea el Toast
                    new Handler().postDelayed(this::finalizarActividad, 1000);
                } else {
                    // Respuesta incorrecta
                    Toast.makeText(this, "Respuesta incorrecta", Toast.LENGTH_SHORT).show();
                    // Puedes decidir si quieres que continúe aunque se equivoque
                    new Handler().postDelayed(this::finalizarActividad, 1000);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No se necesita implementación
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private void finalizarActividad() {
        //aunmetar el progreso
        progreso += progressIncrement;
        progressBar.setProgreso(progreso);

        // Asegurarse de que no exceda el 100%
        if (progreso > 100) progreso = 100;

        Intent intent = new Intent(this, Selects.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("progreso", progreso);
        intent.putExtra("returning", true);
        startActivity(intent);
        finish();
    }
}