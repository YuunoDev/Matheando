package com.aaa.matheando;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Patrones_act extends AppCompatActivity {
    private TextView txtPatronPregunta,pista;
    private EditText etPatronRespuesta;
    private Button btnPatronContinuar;
    //variables para el patron
    private int[] patron;
    private int numer;
    private progress progressBar;
    private int progreso = 0;
    private int numactividad = 1;
    private int progressIncrement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.caminos_patrones_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtPatronPregunta=findViewById(R.id.txtPatronPregunta);
        etPatronRespuesta=findViewById(R.id.etPatronRespuesta);
        btnPatronContinuar=findViewById(R.id.btnPatronContinuar);
        pista=findViewById(R.id.pista);
        progressBar=findViewById(R.id.progress);

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

        //generar el patron
        generarPatron();

        txtPatronPregunta.setText("Completa el patrón: "+patron[0]+","+patron[1]+","+patron[2]+","+patron[3]);

        btnPatronContinuar.setOnClickListener(view -> {
            String respuesta=etPatronRespuesta.getText().toString();

            if(respuesta.equals(String.valueOf(numer))){
                Toast.makeText(this, " Correcto", Toast.LENGTH_SHORT).show();
                //aunmetar el progreso
                progreso += progressIncrement;
                progressBar.setProgreso(progreso);
                // Retrasar la finalización para que se vea el Toast
                new Handler().postDelayed(this::finalizarActividad, 1000);
            }else{
                Toast.makeText(this, "Incorrecto", Toast.LENGTH_SHORT).show();
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

    private void generarPatron(){
        patron=new int[5];
        int select=(int)(Math.random()*2+1);

        numer=(int)(Math.random()*6+1);

        switch (select){
            case 1:
                pista.setText("Es de suma");
                patron[0]=(int)(Math.random()*6+1);
                patron[1]=patron[0]+numer;
                patron[2]=patron[1]+numer;
                patron[3]=patron[2]+numer;
                break;
            case 2:
                pista.setText("Es de resta");
                patron[0]=(int)(Math.random()*6+1);
                patron[1]=patron[0]-numer;
                patron[2]=patron[1]-numer;
                patron[3]=patron[2]-numer;
                numer=-numer;
                break;
        }

    }
}
