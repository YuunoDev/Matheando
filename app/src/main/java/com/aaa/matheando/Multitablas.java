package com.aaa.matheando;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Multitablas extends AppCompatActivity {

    private TextView txtPregunta;
    private progress progressBar;
    private Button btnGlobo1;
    private Button btnGlobo2;
    private Button btnGlobo3;
    private Button btnGlobo4;
    private Button btnContinuar;
    private int progreso = 0;
    private int numero1Actual, numero2Actual; // Para mantener los números actuales
    private int numactividad = 1;
    private int progressIncrement;
    private int respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablas_multi_activity);

        txtPregunta = findViewById(R.id.txtMultiplicacionPregunta);
        progressBar = findViewById(R.id.progressBar5);
        btnGlobo1 = findViewById(R.id.btnGlobo1);
        btnGlobo2 = findViewById(R.id.btnGlobo2);
        btnGlobo3 = findViewById(R.id.btnGlobo3);
        btnGlobo4 = findViewById(R.id.btnGlobo4);
        btnContinuar = findViewById(R.id.btnContinuar);

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

        btnContinuar.setVisibility(View.GONE); // Ocultar el botón al inicio
        generarPregunta();

        View.OnClickListener respuestaListener = view -> {
            Button botonPresionado = (Button) view;
            if(botonPresionado == btnGlobo1){
                btnGlobo1.setTextColor(Color.BLUE);
                btnGlobo2.setTextColor(Color.WHITE);
                btnGlobo3.setTextColor(Color.WHITE);
                btnGlobo4.setTextColor(Color.WHITE);
            } else if(botonPresionado == btnGlobo2){
                btnGlobo1.setTextColor(Color.WHITE);
                btnGlobo2.setTextColor(Color.BLUE);
                btnGlobo3.setTextColor(Color.WHITE);
                btnGlobo4.setTextColor(Color.WHITE);
            } else if (botonPresionado == btnGlobo3) {
                btnGlobo1.setTextColor(Color.WHITE);
                btnGlobo2.setTextColor(Color.WHITE);
                btnGlobo3.setTextColor(Color.BLUE);
                btnGlobo4.setTextColor(Color.WHITE);
            } else if (botonPresionado == btnGlobo4) {
                btnGlobo1.setTextColor(Color.WHITE);
                btnGlobo2.setTextColor(Color.WHITE);
                btnGlobo3.setTextColor(Color.WHITE);
                btnGlobo4.setTextColor(Color.BLUE);
            }
            respuesta = Integer.parseInt(botonPresionado.getText().toString());
            btnContinuar.setVisibility(View.VISIBLE); // Mostrar el botón después de responder
        };

        btnGlobo1.setOnClickListener(respuestaListener);
        btnGlobo2.setOnClickListener(respuestaListener);
        btnGlobo3.setOnClickListener(respuestaListener);
        btnGlobo4.setOnClickListener(respuestaListener);

        btnContinuar.setOnClickListener(view -> {
            if (validarRespuesta(respuesta)) {
                Toast.makeText(Multitablas.this, "¡Correcto! Seleccionaste el número mayor.", Toast.LENGTH_SHORT).show();
                //aunmetar el progreso
                progreso += progressIncrement;
                progressBar.setProgreso(progreso);
                // Retrasar la finalización para que se vea el Toast
                new Handler().postDelayed(this::finalizarActividad, 1000);
            }else{
                Toast.makeText(Multitablas.this, "¡Incorrecto! Seleccionaste el número menor.", Toast.LENGTH_SHORT).show();
                // Puedes decidir si quieres que continúe aunque se equivoque
                new Handler().postDelayed(this::finalizarActividad, 1000);
            }
        });
    }

    private void generarPregunta() {
        Random random = new Random();
        numero1Actual = random.nextInt(10) + 1;
        numero2Actual = random.nextInt(10) + 1;
        int respuestaCorrecta = numero1Actual * numero2Actual;

        // Usar formato directo en lugar de string resource para evitar problemas de formato
        txtPregunta.setText(numero1Actual + " × " + numero2Actual);

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
        btnGlobo2.setText(String.valueOf(opciones.get(1)));
        btnGlobo3.setText(String.valueOf(opciones.get(2)));
        btnGlobo4.setText(String.valueOf(opciones.get(3)));

        // Establecer descripciones de contenido para accesibilidad
        btnGlobo1.setContentDescription(getString(R.string.respuesta, opciones.get(0)));
        btnGlobo2.setContentDescription(getString(R.string.respuesta, opciones.get(1)));
        btnGlobo3.setContentDescription(getString(R.string.respuesta, opciones.get(2)));
        btnGlobo4.setContentDescription(getString(R.string.respuesta, opciones.get(3)));

        resetearColores();
    }

    private boolean validarRespuesta(int respuesta) {
        int respuestaCorrecta = numero1Actual * numero2Actual;

        if (respuesta == respuestaCorrecta) {
            txtPregunta.setTextColor(Color.GREEN);
            return true;
        }
        txtPregunta.setTextColor(Color.RED);
        return false;
    }

    private void resetearColores() {
        txtPregunta.setTextColor(Color.BLACK);
    }

    private void finalizarActividad() {

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