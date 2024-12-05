package com.aaa.matheando;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class Repaso extends AppCompatActivity {
    private Button btnSuma, btnResta, btnMultiplicacion, btnFracciones, btnRegresar;
    private FrameLayout fragmentContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_repaso);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        btnSuma = findViewById(R.id.btnSuma);
        btnResta = findViewById(R.id.btnResta);
        btnRegresar = findViewById(R.id.button);
        btnMultiplicacion = findViewById(R.id.btnMultiplicacion);
        btnFracciones = findViewById(R.id.btnFracciones);
        fragmentContainer = findViewById(R.id.fragmentContainer);
    }

    private void setupListeners() {
        btnSuma.setOnClickListener(v -> showFragment(new SumaFragment()));
        btnResta.setOnClickListener(v -> showFragment(new RestaFragment()));
        btnMultiplicacion.setOnClickListener(v -> showFragment(new MultiplicacionFragment()));
        btnFracciones.setOnClickListener(v -> showFragment(new FraccionesFragment()));
        btnRegresar.setOnClickListener(v -> finish());
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}