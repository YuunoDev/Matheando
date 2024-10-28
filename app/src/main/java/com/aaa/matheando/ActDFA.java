package com.aaa.matheando;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActDFA extends AppCompatActivity {
    Button borrar;
    Canvasd canvasd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.detective_figuras_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dfa), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        borrar=findViewById(R.id.btnBorrarDibujo);
        canvasd=findViewById(R.id.drawingcanvas);

        borrar.setOnClickListener(view ->
                canvasd.clearCanvas());
    }
}