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

public class SumaFragment extends Fragment {
    private TextView numero1, numero2, resultado;
    private Button btnAnimar, btnReiniciar;
    private View rootView;
    private int num1, num2;
    private GridLayout gridContainer;
    private List<TextView> dots = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_suma, container, false);
        initializeViews();
        setupListeners();
        return rootView;
    }

    private void initializeViews() {
        numero1 = rootView.findViewById(R.id.numero1);
        numero2 = rootView.findViewById(R.id.numero2);
        resultado = rootView.findViewById(R.id.resultado);
        btnAnimar = rootView.findViewById(R.id.btnAnimar);
        btnReiniciar = rootView.findViewById(R.id.btnReiniciar);
        gridContainer = rootView.findViewById(R.id.gridContainer);

        numbers();
    }

    private void numbers(){
        // Generar números aleatorios
        num1 = (int) (Math.random() * 9) + 1;
        num2 = 10 - num1;

        numero1.setText(String.valueOf(num1));
        numero2.setText(String.valueOf(num2));
        resultado.setText(String.valueOf(num1 + num2));
    }

    private void setupListeners() {
        btnAnimar.setOnClickListener(v -> startAnimation());
        btnReiniciar.setOnClickListener(v -> resetAnimation());
    }

    private void startAnimation() {
        gridContainer.removeAllViews();
        dots.clear();

        //llenar matriz de puntos
        for (int i = 0; i < 10; i++) {
            TextView dot = new TextView(getContext());
            if(i<num1){
                dot.setText("●");
                dot.setTextSize(30);
            }else{
                dot.setText("○");
                dot.setTextSize(30);
                dot.setAlpha(0f);
            }

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setMargins(16, 16, 16, 16);
            params.rowSpec = GridLayout.spec(i);
            params.rowSpec = GridLayout.spec(1);

            gridContainer.addView(dot, params);
            dots.add(dot);
        }


        // Animación de aparición de puntos
        for (int i = num1; i < dots.size(); i++) {
            final TextView dot = dots.get(i);
            dot.animate()
                    .alpha(1f)
                    .setStartDelay(i * 100L)
                    .setDuration(1000L)
                    .withEndAction(() -> {
                        if (dots.indexOf(dot) == dots.size() - 1) {
                            // Mostrar resultado con animación
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
        numero1.setTranslationX(0f);
        numero2.setTranslationX(0f);
        resultado.setVisibility(View.INVISIBLE);

        gridContainer.removeAllViews();
        dots.clear();

        numbers();
    }
}

