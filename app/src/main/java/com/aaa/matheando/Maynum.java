package com.aaa.matheando;

import android.os.Bundle;
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

    RadioButton rb1,rb2;
    RadioGroup rg;
    int may, men, op;
    Button btn;

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

        rb1=findViewById(R.id.rbComparacion1);
        rb2=findViewById(R.id.rbComparacion2);
        rg=findViewById(R.id.rgComparacionRespuestas);

        btn=findViewById(R.id.btnComparacionContinuar);

        rg.clearCheck();

        //numero randome del 1 al 30
        may=(int)(Math.random()*30+1);
        //numero menor del 1 a may
        men=(int)(Math.random()*may+1);
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
            }else{
                Toast.makeText(Maynum.this, "¡Incorrecto! Seleccionaste el número menor.", Toast.LENGTH_SHORT).show();
            }
        });
    }


}