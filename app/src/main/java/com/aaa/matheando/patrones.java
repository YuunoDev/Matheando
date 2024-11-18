package com.aaa.matheando;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class patrones extends AppCompatActivity {
    TextView txtPatronPregunta,pista;
    EditText etPatronRespuesta;
    Button btnPatronContinuar;
    //variables para el patron
    private int[] patron;
    int numer;


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

        //generar el patron
        generarPatron();

        txtPatronPregunta.setText("Completa el patrón: "+patron[0]+","+patron[1]+","+patron[2]+","+patron[3]);
        Toast.makeText(this, "patrón: "+numer, Toast.LENGTH_SHORT).show();

        btnPatronContinuar.setOnClickListener(view -> {
            String respuesta=etPatronRespuesta.getText().toString();
            if(respuesta.equals(String.valueOf(numer))){
                Toast.makeText(this, " Correcto", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Incorrecto", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void generarPatron(){
        patron=new int[5];
        int select=(int)(Math.random()*2+1);

        //numer puede ser negativo o positivo
        numer=(int)(Math.random()*6+1);
        if((int)(Math.random()*2+1)==1){
            numer=-numer;
        }

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
                break;
        }

    }
}
