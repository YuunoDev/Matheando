package com.aaa.matheando;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class AventurasSumres extends AppCompatActivity {

    private TextView txtSumasRestasPregunta;
    private RadioGroup rgSumasRestasRespuestas;
    private RadioButton rbRespuesta1, rbRespuesta2, rbRespuesta3, rbRespuesta4;
    private progress progressBar;
    private TextView TextProgreso;
    private Button btnContinuar, btnGuardarProgreso;

    private int progreso = 0;
    private int respuestaCorrecta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aventuras_sumres_activity);

        //Inicializando las vistas
        progressBar = findViewById(R.id.progressBar);
        txtSumasRestasPregunta = findViewById(R.id.txtSumasRestasPregunta);
        rgSumasRestasRespuestas = findViewById(R.id.rgSumasRestasRespuestas);
        rbRespuesta1 = findViewById(R.id.rbRespuesta1);
        rbRespuesta2 = findViewById(R.id.rbRespuesta2);
        rbRespuesta3 = findViewById(R.id.rbRespuesta3);
        rbRespuesta4 = findViewById(R.id.rbRespuesta4);
        TextProgreso = findViewById(R.id.TextProgreso);
        btnContinuar = findViewById(R.id.btnContinuar);
        btnGuardarProgreso = findViewById(R.id.btnGuardarProgreso);

        //Configuración de botón Continuar
        btnContinuar.setOnClickListener(v -> verificarRespuesta());

        //Configuración de botón Guardar Progreso
        btnGuardarProgreso.setOnClickListener(v -> guardarProgreso());

        generarPregunta(); //Generar primera pregunta
    }

    private void generarPregunta() {
        Random random = new Random();
        int numero1 = random.nextInt(20) + 1; // Genera un número entre 1 y 20
        int numero2 = random.nextInt(20) + 1; // Genera otro número entre 1 y 20

        boolean esSuma = random.nextBoolean(); //Determina si es suma o resta
        int resultado;

        if (esSuma) {
            resultado = numero1 + numero2;
            txtSumasRestasPregunta.setText("¿Cuánto es " + numero1 + " + " + numero2 + "?");
        } else {
            resultado = numero1 - numero2;
            txtSumasRestasPregunta.setText("¿Cuánto es " + numero1 + " - " + numero2 + "?");
        }

        respuestaCorrecta = resultado;
        asignarOpciones(resultado);
    }

    private void asignarOpciones(int respuestaCorrecta) {
        Random random = new Random();
        int posicionCorrecta = random.nextInt(4); //Posición aleatoria para la respuesta correcta
        int[] opciones = new int[4]; //Asigna las opciones de respuesta a los RadioButtons
        opciones[posicionCorrecta] = respuestaCorrecta;

        // Genera opciones incorrectas cercanas a la respuesta correcta
        for (int i = 0; i < 4; i++) {
            if (i != posicionCorrecta) {
                int opcion;
                do {
                    opcion = respuestaCorrecta + random.nextInt(10) - 5;
                } while (opcion == respuestaCorrecta || contiene(opciones, opcion));
                opciones[i] = opcion;
            }
        }

        //Asignar opciones a los RadioButtons
        rbRespuesta1.setText(String.valueOf(opciones[0]));
        rbRespuesta2.setText(String.valueOf(opciones[1]));
        rbRespuesta3.setText(String.valueOf(opciones[2]));
        rbRespuesta4.setText(String.valueOf(opciones[3]));

        //Limpiar selección previa
        rgSumasRestasRespuestas.clearCheck();
    }

    private boolean contiene(int[] arr, int value) {
        for (int i : arr) {
            if (i == value) return true;
        }
        return false;
    }

    private void verificarRespuesta() {
        int respuestaSeleccionadaId = rgSumasRestasRespuestas.getCheckedRadioButtonId();
        if (respuestaSeleccionadaId == -1) {
            Toast.makeText(this, "Selecciona una respuesta", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton respuestaSeleccionada = findViewById(respuestaSeleccionadaId);
        int respuestaUsuario = Integer.parseInt(respuestaSeleccionada.getText().toString());

        if (respuestaUsuario == respuestaCorrecta) {
            Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show();
            progreso += 10;
            progressBar.setProgreso(progreso);
        } else {
            Toast.makeText(this, "Incorrecto, intenta nuevamente", Toast.LENGTH_SHORT).show();
        }

        generarPregunta();//Genera una nueva pregunta
    }

    private void guardarProgreso() {
        Toast.makeText(this, "Progreso guardado: " + progreso + "%", Toast.LENGTH_SHORT).show();
    }
}