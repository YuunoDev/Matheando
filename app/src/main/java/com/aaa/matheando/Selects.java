package com.aaa.matheando;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;

public class Selects extends AppCompatActivity {
    private Button btnop, btnsim, btnor, btndif;
    private SharedPreferences preferences;
    private static final String PREF_NAME = "ActivityProgress";
    private static final String ACTIVITIES_ORDER = "ActivitiesOrder";
    private static final String CURRENT_ACTIVITY = "CurrentActivity";
    private static final String PROGRESS_KEY = "CurrentProgress";
    private int currentProgress = 0;
    private boolean isReturningFromActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.seleccion_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Recuperar el progreso si viene de una actividad
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            currentProgress = receivedIntent.getIntExtra("progreso", 0);
            isReturningFromActivity = receivedIntent.getBooleanExtra("returning", false);
        }

        preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        btnop = findViewById(R.id.btnPrimero);
        btnsim = findViewById(R.id.btnSegundo);
        btnor = findViewById(R.id.btnTercero);
        btndif = findViewById(R.id.btnCuarto);

        btnop.setOnClickListener(v -> {
            Intent intent = new Intent(this, Repaso.class);
            startActivity(intent);
        });

        btnsim.setOnClickListener(v -> {
            //selecion de actividades de operaciones básicas 3 o 4 actividades
            activitiesSimples();
            isReturningFromActivity = false;
            startNextActivity();
        });

        btnor.setOnClickListener(v -> {
            activitiesNormales();
            isReturningFromActivity = false;
            startNextActivity();
        });

        btndif.setOnClickListener(v -> {
            activitiesDificiles();
            isReturningFromActivity = false;
            startNextActivity();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isReturningFromActivity) {
            // Verificar si hay más actividades pendientes
            String activitiesOrder = preferences.getString(ACTIVITIES_ORDER, "");
            int currentIndex = preferences.getInt(CURRENT_ACTIVITY, 0);

            if (!activitiesOrder.isEmpty() && currentIndex < activitiesOrder.split(",").length) {
                startNextActivity();
            } else if (!activitiesOrder.isEmpty()) {
                // Si ya no hay más actividades, mostrar mensaje y resetear
                Intent intent = new Intent(this, Actfinalizar.class);
                intent.putExtra("progreso", currentProgress);
                resetProgress();
                startActivity(intent);
            }
        }
    }

    private void activitiesSimples(){
        ArrayList<Class<?>> activities = new ArrayList<>();
        activities.add(Maynum.class);
        activities.add(AventurasSumres_act.class);
        activities.add(Multitablas.class);

        Collections.shuffle(activities);

        SharedPreferences.Editor editor = preferences.edit();
        StringBuilder order = new StringBuilder();
        for (Class<?> activity : activities) {
            order.append(activity.getName()).append(",");
        }
        editor.putString(ACTIVITIES_ORDER, order.toString());
        editor.putInt(CURRENT_ACTIVITY, 0);
        editor.apply();
    }

    private void activitiesNormales(){
        ArrayList<Class<?>> activities = new ArrayList<>();
        activities.add(Maynum.class);
        activities.add(AventurasSumres_act.class);
        activities.add(Multitablas.class);
        activities.add(fraccion.class);

        Collections.shuffle(activities);

        SharedPreferences.Editor editor = preferences.edit();
        StringBuilder order = new StringBuilder();
        for (Class<?> activity : activities) {
            order.append(activity.getName()).append(",");
        }
        editor.putString(ACTIVITIES_ORDER, order.toString());
        editor.putInt(CURRENT_ACTIVITY, 0);
        editor.apply();
    }


    private void activitiesDificiles(){
        ArrayList<Class<?>> activities = new ArrayList<>();
        activities.add(Maynum.class);
        activities.add(AventurasSumres_act.class);
        activities.add(fraccion.class);
        activities.add(patrones.class);
        activities.add(Multitablas.class);
        activities.add(Sacude_res.class);

        Collections.shuffle(activities);

        SharedPreferences.Editor editor = preferences.edit();
        StringBuilder order = new StringBuilder();
        for (Class<?> activity : activities) {
            order.append(activity.getName()).append(",");
        }
        editor.putString(ACTIVITIES_ORDER, order.toString());
        editor.putInt(CURRENT_ACTIVITY, 0);
        editor.apply();
    }

    private void startNextActivity() {
        String activitiesOrder = preferences.getString(ACTIVITIES_ORDER, "");
        int currentIndex = preferences.getInt(CURRENT_ACTIVITY, 0);

        if (!activitiesOrder.isEmpty()){
            String[] activities = activitiesOrder.split(",");
            if (currentIndex < activities.length) {
                try {
                    Class<?> nextActivity = Class.forName(activities[currentIndex]);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt(CURRENT_ACTIVITY, currentIndex + 1);
                    editor.apply();

                    Intent intent = new Intent(this, nextActivity);
                    // Enviar el progreso actual y el total de actividades
                    intent.putExtra("progreso", currentProgress);
                    intent.putExtra("numeroActividades", activities.length);
                    intent.putExtra("returning", true); // Añadir flag para indicar que debe volver
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }else{
            //mandar a felicidades

        }
    }

    private void resetProgress() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        currentProgress=0;
        isReturningFromActivity = false;
    }
}