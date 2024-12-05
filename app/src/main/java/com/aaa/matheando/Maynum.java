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

public class Maynum extends AppCompatActivity {

    private RadioButton rb1,rb2;
    private RadioGroup rg;
    private int may, men, op;
    private Button btn;
    private progress progressBar;
    private int progreso = 0;
    private int numactividad = 1;
    private int progressIncrement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.carrera_numeros_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        progressBar=findViewById(R.id.progressBar4);

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

        rb1=findViewById(R.id.rbComparacion1);
        rb2=findViewById(R.id.rbComparacion2);
        rg=findViewById(R.id.rgComparacionRespuestas);

        btn=findViewById(R.id.btnComparacionContinuar);

        rg.clearCheck();

        //numero randome del 1 al 30
        may=(int)(Math.random()*30+1);
        //numero menor del 1 a may
        men=(int)(Math.random()*may+1);

        //si son iguales se repite hasta que sean diferentes
        while(men==may){
            men=(int)(Math.random()*may+1);
        }

        //opcion del 1 al 2
        op=(int)(Math.random()*2+1);

        if(op==1){
            rb1.setText(String.valueOf(may));
            rb2.setText(String.valueOf(men));
        }else {
            rb2.setText(String.valueOf(may));
            rb1.setText(String.valueOf(men));
        }

        btn.setOnClickListener(view -> {
            RadioButton rf=findViewById(rg.getCheckedRadioButtonId());
            int selectedNumber = Integer.parseInt(rf.getText().toString());
            if(selectedNumber==may){
                Toast.makeText(Maynum.this, "¡Correcto! Seleccionaste el número mayor.", Toast.LENGTH_SHORT).show();
                //aunmetar el progreso
                progreso += progressIncrement;
                progressBar.setProgreso(progreso);
                // Retrasar la finalización para que se vea el Toast
                new Handler().postDelayed(this::finalizarActividad, 1000);
            }else{
                Toast.makeText(Maynum.this, "¡Incorrecto! Seleccionaste el número menor.", Toast.LENGTH_SHORT).show();
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
}