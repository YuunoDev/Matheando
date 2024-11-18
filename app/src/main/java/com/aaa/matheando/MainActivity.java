package com.aaa.matheando;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    FractionPizzaView pizzaView;
    int denom;
    int num;
    RadioGroup rg;
    RadioButton r1, r2, r3;


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

        rg.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbFraccion1) {
                //mandar su texto
                checkAnswer(r1.getText().toString());
            } else if (checkedId == R.id.rbFraccion2) {
                checkAnswer(r2.getText().toString());
            } else if (checkedId == R.id.rbFraccion3) {
                checkAnswer(r3.getText().toString());
            }
        });
    }

    private void checkAnswer(String selectedAnswer) {
        if (selectedAnswer.equals(num + "/" + denom)) {
            Toast.makeText(this, "Respuesta correcta!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Respuesta incorrecta. La respuesta correcta era " + num + "/" + denom, Toast.LENGTH_SHORT).show();
        }
    }
}