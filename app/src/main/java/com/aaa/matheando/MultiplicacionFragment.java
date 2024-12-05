package com.aaa.matheando;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MultiplicacionFragment extends Fragment {
    private GridLayout gridContainer;
    private TextView numero1, numero2, resultado;
    private Button btnAnimar, btnReiniciar;
    private View rootView;
    private List<TextView> dots = new ArrayList<>();
    private int num1, num2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_multiplicacion, container, false);
        initializeViews();
        setupListeners();
        return rootView;
    }

    private void initializeViews() {
        gridContainer = rootView.findViewById(R.id.gridContainer);
        numero1 = rootView.findViewById(R.id.numero1);
        numero2 = rootView.findViewById(R.id.numero2);
        resultado = rootView.findViewById(R.id.resultado);
        btnAnimar = rootView.findViewById(R.id.btnAnimar);
        btnReiniciar = rootView.findViewById(R.id.btnReiniciar);

        numbers();
    }

    private void numbers(){
        num1=(int)(Math.random()*8+1);
        num2=(int)(Math.random()*8+1);

        gridContainer.setColumnCount(num2);
        gridContainer.setRowCount(num1);

        numero1.setText(String.valueOf(num1));
        numero2.setText(String.valueOf(num2));

        resultado.setText(String.valueOf(num1*num2));
    }

    private void setupListeners() {
        btnAnimar.setOnClickListener(v -> startAnimation());
        btnReiniciar.setOnClickListener(v -> resetAnimation());
    }

    private void startAnimation() {
        gridContainer.removeAllViews();
        dots.clear();

        // Crear matriz de puntos (3 x 4 = 12)
        for (int i = 0; i < num1; i++) {
            for (int j = 0; j < num2; j++) {
                TextView dot = new TextView(getContext());
                dot.setText("●");
                dot.setTextSize(20);
                dot.setAlpha(0f);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = GridLayout.LayoutParams.WRAP_CONTENT;
                params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                params.setMargins(16, 5, 16, 16);
                params.rowSpec = GridLayout.spec(i);
                params.columnSpec = GridLayout.spec(j);

                gridContainer.addView(dot, params);
                dots.add(dot);
            }
        }

        // Animar aparición de puntos uno por uno
        for (int i = 0; i < dots.size(); i++) {
            final TextView dot = dots.get(i);
            dot.animate()
                    .alpha(1f)
                    .setStartDelay(i * 100L)
                    .setDuration(300)
                    .withEndAction(() -> {
                        if (dots.indexOf(dot) == dots.size() - 1) {
                            // Mostrar resultado
                            resultado.setAlpha(0f);
                            resultado.setVisibility(View.VISIBLE);
                            resultado.animate()
                                    .alpha(1f)
                                    .setDuration(500)
                                    .start();
                        }
                    })
                    .start();
        }
    }

    private void resetAnimation() {
        gridContainer.removeAllViews();
        dots.clear();
        resultado.setVisibility(View.INVISIBLE);
        numbers();
    }
}
