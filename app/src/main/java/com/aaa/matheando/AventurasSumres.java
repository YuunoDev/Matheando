package com.aaa.matheando;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class AventurasSumres extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView txtProgreso, txtPregunta;
    private RadioGroup rgRespuestas;
    private RadioButton rbRespuesta1, rbRespuesta2, rbRespuesta3, rbRespuesta4;
    private Button btnContinuar, btnGuardarProgreso;

    private int progreso = 0;
    private int respuestaCorrecta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aventuras_sumres_activity);

        //Inicializando las vistas
        progressBar = findViewById(R.id.progressBar);
        txtProgreso = findViewById(R.id.TextProgreso);
        txtPregunta = findViewById(R.id.txtSumasRestasPregunta);
        rgRespuestas = findViewById(R.id.rgSumasRestasRespuestas);
        rbRespuesta1 = findViewById(R.id.rbRespuesta1);
        rbRespuesta2 = findViewById(R.id.rbRespuesta2);
        rbRespuesta3 = findViewById(R.id.rbRespuesta3);
        rbRespuesta4 = findViewById(R.id.rbRespuesta4);
        btnContinuar = findViewById(R.id.btnContinuar);
        btnGuardarProgreso = findViewById(R.id.btnGuardarProgreso);

        generarEjercicio();

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarRespuesta();
            }
        });

        btnGuardarProgreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarProgreso();
            }
        });
    }

    private void generarEjercicio() {
        Random rand = new Random();
        int num1 = rand.nextInt(10) + 1;
        int num2 = rand.nextInt(10) + 1;
        boolean esSuma = rand.nextBoolean();

        if (esSuma) {
            txtPregunta.setText("¿Cuánto es " + num1 + " + " + num2 + "?");
            respuestaCorrecta = num1 + num2;
        } else {
            txtPregunta.setText("¿Cuánto es " + num1 + " - " + num2 + "?");
            respuestaCorrecta = num1 - num2;
        }

        int[] opciones = generarOpciones(respuestaCorrecta);
        rbRespuesta1.setText(String.valueOf(opciones[0]));
        rbRespuesta2.setText(String.valueOf(opciones[1]));
        rbRespuesta3.setText(String.valueOf(opciones[2]));
        rbRespuesta4.setText(String.valueOf(opciones[3]));
        rgRespuestas.clearCheck();
    }

    private int[] generarOpciones(int correcta) {
        Random rand = new Random();
        int[] opciones = new int[4];
        int posicionCorrecta = rand.nextInt(4);
        opciones[posicionCorrecta] = correcta;

        for (int i = 0; i < 4; i++) {
            if (i == posicionCorrecta) continue;
            int opcion;
            do {
                opcion = correcta + rand.nextInt(10) - 5;
            } while (opcion == correcta || contiene(opciones, opcion));
            opciones[i] = opcion;
        }
        return opciones;
    }

    private boolean contiene(int[] opciones, int opcion) {
        for (int op : opciones) {
            if (op == opcion) return true;
        }
        return false;
    }

    private void verificarRespuesta() {
        int seleccionId = rgRespuestas.getCheckedRadioButtonId();
        if (seleccionId == -1) {
            Toast.makeText(this, "Selecciona una respuesta", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton seleccion = findViewById(seleccionId);
        int respuestaSeleccionada = Integer.parseInt(seleccion.getText().toString());

        if (respuestaSeleccionada == respuestaCorrecta) {
            Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show();
            progreso += 10;  // Aumentar el progreso en 10%
            actualizarProgreso();
            generarEjercicio();
        } else {
            Toast.makeText(this, "Incorrecto. Intenta nuevamente.", Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarProgreso() {
        progressBar.setProgress(progreso);
        txtProgreso.setText(progreso + "%");
    }

    private void guardarProgreso() {
        Toast.makeText(this, "Progreso guardado: " + progreso + "%", Toast.LENGTH_SHORT).show();
    }
}