package com.aaa.matheando;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class fraccion extends AppCompatActivity {
    private FractionPizzaView pizzaView;
    private int denom;
    private int num;
    private RadioGroup rg;
    private RadioButton r1, r2, r3;
    private progress progressBar;
    private int progreso = 0;
    private int numactividad = 1;
    private int progressIncrement;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.rompe_fracciones_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rompe_fracc_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        pizzaView = findViewById(R.id.pizzaView);
        rg = findViewById(R.id.rgFraccionRespuestas);
        r1 = findViewById(R.id.rbFraccion1);
        r2 = findViewById(R.id.rbFraccion2);
        r3 = findViewById(R.id.rbFraccion3);
        progressBar = findViewById(R.id.progressBarr);
        btn = findViewById(R.id.btnFraccionContinuar);

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

        //selecionar aleatorio la fracción
        denom = (int) (Math.random() * 10) + 1;
        num = (int) (Math.random() * denom) + 1;

        pizzaView.setFraction(num, denom); // Establece la fracción inicial

        //poner la respues correcta en el radio button aletoriamente
        int random = (int) (Math.random() * 3);
        int dif = (denom - num);

        if (dif < 0) {
            dif = dif * -1;
        } else if (dif==num) {
            dif = dif-1;
        }

        switch (random) {
            case 0:
                r1.setText(num + "/" + denom);
                r2.setText((dif) + "/" + denom);
                r3.setText(num + "/" + (dif));
                break;
            case 1:
                r2.setText(num + "/" + denom);
                r1.setText((dif) + "/" + denom);
                r3.setText(num + "/" + (dif));
                break;
            case 2:
                r3.setText(num + "/" + denom);
                r2.setText((dif) + "/" + denom);
                r1.setText(num + "/" + (dif));
                break;
        }

        btn.setOnClickListener(view -> {
            RadioButton rf = findViewById(rg.getCheckedRadioButtonId());
            String selectedNumber = rf.getText().toString();

            if (checkAnswer(selectedNumber)) {
                Toast.makeText(fraccion.this, "¡Correcto! Seleccionaste la fracción correcta.", Toast.LENGTH_SHORT).show();
                //aunmetar el progreso
                progreso += progressIncrement;
                progressBar.setProgreso(progreso);
                // Retrasar la finalización para que se vea el Toast
                new Handler().postDelayed(this::finalizarActividad, 1000);
            }else{
                Toast.makeText(fraccion.this, "¡Incorrecto! Seleccionaste la fracción incorrecta.", Toast.LENGTH_SHORT).show();
                // Puedes decidir si quieres que continúe aunque se equivoque
                new Handler().postDelayed(this::finalizarActividad, 1000);
            }
        });

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

    private boolean checkAnswer(String selectedAnswer) {
        if (selectedAnswer.equals(num + "/" + denom)) {
            return true;
        }
        return false;
    }
}