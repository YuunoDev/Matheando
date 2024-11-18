package com.aaa.matheando;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MultiTablas extends AppCompatActivity{

    private TextView txtPregunta, txtProgreso;
    private ProgressBar progressBar;
    private Button btnGlobo1;
    private Button btnGlobo2;
    private Button btnGlobo3;
    private Button btnGlobo4;
    private int progreso = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablas_multi_activity);

        txtPregunta = findViewById(R.id.txtMultiplicacionPregunta);
        txtProgreso = findViewById(R.id.TextProgreso);
        progressBar = findViewById(R.id.progressBar);
        btnGlobo1 = findViewById(R.id.btnGlobo1);
        btnGlobo2 = findViewById(R.id.btnGlobo2);
        btnGlobo3 = findViewById(R.id.btnGlobo3);
        btnGlobo4 = findViewById(R.id.btnGlobo4);
        Button btnContinuar = findViewById(R.id.btnContinuar);

        generarPregunta();

        View.OnClickListener respuestaListener = view -> {
            Button botonPresionado = (Button) view;
            int respuesta = Integer.parseInt(botonPresionado.getText().toString());
            validarRespuesta(respuesta);
        };

        btnGlobo1.setOnClickListener(respuestaListener);
        btnGlobo2.setOnClickListener(respuestaListener);
        btnGlobo3.setOnClickListener(respuestaListener);
        btnGlobo4.setOnClickListener(respuestaListener);

        btnContinuar.setOnClickListener(view -> generarPregunta());
    }

    private void generarPregunta() {
        Random random = new Random();
        int numero1 = random.nextInt(10) + 1; //Número entre 1 y 10
        int numero2 = random.nextInt(10) + 1; //Número entre 1 y 10
        int respuestaCorrecta = numero1 * numero2;

        txtPregunta.setText(getString(R.string.pregunta_base, numero1, numero2));

        ArrayList<Integer> opciones = new ArrayList<>();
        opciones.add(respuestaCorrecta);
        while (opciones.size() < 4) {
            int respuestaIncorrecta = random.nextInt(100) + 1;
            if (!opciones.contains(respuestaIncorrecta)) {
                opciones.add(respuestaIncorrecta);
            }
        }

        Collections.shuffle(opciones);

        btnGlobo1.setText(String.valueOf(opciones.get(0)));
        btnGlobo1.setContentDescription(getString(R.string.respuesta, opciones.get(0)));

        btnGlobo2.setText(String.valueOf(opciones.get(1)));
        btnGlobo2.setContentDescription(getString(R.string.respuesta, opciones.get(1)));

        btnGlobo3.setText(String.valueOf(opciones.get(2)));
        btnGlobo3.setContentDescription(getString(R.string.respuesta, opciones.get(2)));

        btnGlobo4.setText(String.valueOf(opciones.get(3)));
        btnGlobo4.setContentDescription(getString(R.string.respuesta, opciones.get(3)));

        resetearColores();
    }

    private void validarRespuesta(int respuesta) { //Metodo para validra respuestas
        String[] partesPregunta = txtPregunta.getText().toString()
                .replace("Encuentra la respuesta en el globo de:", "")
                .split("×");

        try {
            int numero1 = Integer.parseInt(partesPregunta[0].trim());
            int numero2 = Integer.parseInt(partesPregunta[1].trim());
            int respuestaCorrecta = numero1 * numero2;

            if (respuesta == respuestaCorrecta) {
                progreso += 10;
                txtProgreso.setText(getString(R.string.progreso, progreso));
                progressBar.setProgress(progreso);
                txtPregunta.setTextColor(Color.GREEN);
            } else {
                txtPregunta.setTextColor(Color.RED);
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            txtPregunta.setTextColor(Color.RED);
            txtPregunta.setText(getString(R.string.error_pregunta));
            generarPregunta();
        }
    }

    private void resetearColores() {
        txtPregunta.setTextColor(Color.BLACK);
    }
}