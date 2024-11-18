package com.aaa.matheando;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class CarreraNumeros extends AppCompatActivity {

    private TextView txtComparacionPregunta;
    private RadioButton rbComparacion1, rbComparacion2;
    private RadioGroup rgComparacionRespuestas;

    private Random random;

    private int correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carrera_numeros_activity);

        // Inicializar vistas
        txtComparacionPregunta = findViewById(R.id.txtComparacionPregunta);
        rbComparacion1 = findViewById(R.id.rbComparacion1);
        rbComparacion2 = findViewById(R.id.rbComparacion2);
        rgComparacionRespuestas = findViewById(R.id.rgComparacionRespuestas);
        Button btnComparacionContinuar = findViewById(R.id.btnComparacionContinuar);

        random = new Random();

        // Generar la primera pregunta
        generarPregunta();

        // Configurar el botón Continuar
        btnComparacionContinuar.setOnClickListener(v -> {
            if (rgComparacionRespuestas.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Selecciona una respuesta", Toast.LENGTH_SHORT).show();
            } else {
                verificarRespuesta();
                generarPregunta();
            }
        });
    }

    private void generarPregunta() {
        // Generar dos números aleatorios entre 1 y 100
        int num1 = random.nextInt(100) + 1;
        int num2 = random.nextInt(100) + 1;

        // Decidir la respuesta correcta
        if (num1 > num2) {
            correctAnswer = 1;
        } else {
            correctAnswer = 2;
        }

        // Asignar texto a los RadioButtons aleatoriamente
        if (random.nextBoolean()) {
            rbComparacion1.setText(String.valueOf(num1));
            rbComparacion2.setText(String.valueOf(num2));
        } else {
            rbComparacion1.setText(String.valueOf(num2));
            rbComparacion2.setText(String.valueOf(num1));
        }

        // Actualizar el texto de la pregunta
        txtComparacionPregunta.setText("¿Cuál número es mayor?");
        rgComparacionRespuestas.clearCheck(); //Deseleccionar las opciones
    }

    private void verificarRespuesta() {
        int selectedId = rgComparacionRespuestas.getCheckedRadioButtonId();

        if ((selectedId == R.id.rbComparacion1 && correctAnswer == 1) ||
                (selectedId == R.id.rbComparacion2 && correctAnswer == 2)) {
            Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrecto. Intenta de nuevo.", Toast.LENGTH_SHORT).show();
        }
    }
}