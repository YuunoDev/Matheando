package com.aaa.matheando;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.progressindicator.CircularProgressIndicator;

public class Actfinalizar extends AppCompatActivity {
    private int porcentaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actfinalizar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener referencias a las vistas
        CircularProgressIndicator progressCircular = findViewById(R.id.progressCircular);
        TextView tvPorcentaje = findViewById(R.id.tvPorcentaje);
        TextView tvMensaje = findViewById(R.id.tvMensaje);
        TextView tvDescripcion = findViewById(R.id.tvDescripcion);
        Button btnContinuar = findViewById(R.id.btnContinuar);

        //resivir intent
        Intent intent = getIntent();
        if (intent != null) {
            porcentaje = intent.getIntExtra("progreso", 0);
        }

        // Actualizar el progreso y el texto del porcentaje
        progressCircular.setProgress(porcentaje);
        tvPorcentaje.setText(porcentaje + "%");

        // Establecer mensaje según el porcentaje
        if (porcentaje >= 90) {
            tvMensaje.setText("¡Excelente trabajo!");
            tvDescripcion.setText("Has demostrado un dominio excepcional");
            progressCircular.setIndicatorColor(Color.parseColor("#4CAF50")); // Verde
            tvPorcentaje.setTextColor(Color.parseColor("#4CAF50"));
        } else if (porcentaje >= 75) {
            tvMensaje.setText("¡Muy bien hecho!");
            tvDescripcion.setText("Has completado la actividad satisfactoriamente");
            progressCircular.setIndicatorColor(Color.parseColor("#8BC34A")); // Verde claro
            tvPorcentaje.setTextColor(Color.parseColor("#8BC34A"));
        } else if (porcentaje >= 60) {
            tvMensaje.setText("¡Buen trabajo!");
            tvDescripcion.setText("Has alcanzado los objetivos básicos");
            progressCircular.setIndicatorColor(Color.parseColor("#FFC107")); // Amarillo
            tvPorcentaje.setTextColor(Color.parseColor("#FFC107"));
        } else {
            tvMensaje.setText("Actividad completada");
            tvDescripcion.setText("Continúa practicando para mejorar");
            progressCircular.setIndicatorColor(Color.parseColor("#FF5722")); // Naranja
            tvPorcentaje.setTextColor(Color.parseColor("#FF5722"));
        }

        // Manejar el click del botón continuar
        btnContinuar.setOnClickListener(v ->  {
            Intent intents = new Intent(this, Selects.class);
            intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            porcentaje = 0;
            intents.putExtra("progreso", porcentaje);
            intents.putExtra("returning", false);
            startActivity(intents);
            finish();
        });
    }

}