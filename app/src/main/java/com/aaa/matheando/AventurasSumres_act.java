package com.aaa.matheando;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class AventurasSumres_act extends AppCompatActivity {
    private TextView txtSumasRestasPregunta;
    private RadioGroup rgSumasRestasRespuestas;
    private RadioButton rbRespuesta1, rbRespuesta2, rbRespuesta3, rbRespuesta4;
    private progress progressBar;
    private Button btnContinuar;
    private int progreso = 0;
    private int numactividad = 1;
    private int progressIncrement;
    private int respuestaCorrecta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.aventuras_sumres_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sumres_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Inicializando las vistas
        progressBar = findViewById(R.id.progressBar3);
        txtSumasRestasPregunta = findViewById(R.id.txtSumasRestasPregunta);
        rgSumasRestasRespuestas = findViewById(R.id.rgSumasRestasRespuestas);
        rbRespuesta1 = findViewById(R.id.rbRespuesta1);
        rbRespuesta2 = findViewById(R.id.rbRespuesta2);
        rbRespuesta3 = findViewById(R.id.rbRespuesta3);
        rbRespuesta4 = findViewById(R.id.rbRespuesta4);
        btnContinuar = findViewById(R.id.btnContinuar);

        //Configuración de botón Continuar
        btnContinuar.setOnClickListener(v -> verificarRespuesta());

        //Configuración de botón Guardar Progreso
        //btnGuardarProgreso.setOnClickListener(v -> guardarProgreso());

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
            if (i == value){
                return true;
            }
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
            //aunmetar el progreso
            progreso += progressIncrement;
            progressBar.setProgreso(progreso);
            // Retrasar la finalización para que se vea el Toast
            new Handler().postDelayed(this::finalizarActividad, 1000);
        } else {
            Toast.makeText(this, "Incorrecto, intenta nuevamente", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(this::finalizarActividad, 1000);
        }
    }

    private void guardarProgreso() {
        Toast.makeText(this, "Progreso guardado: " + progreso + "%", Toast.LENGTH_SHORT).show();
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