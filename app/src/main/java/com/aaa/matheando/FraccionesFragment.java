package com.aaa.matheando;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.Nullable;

public class FraccionesFragment extends Fragment {
    private TextView numerador1, denominador1;
    private Button btnReiniciar;
    private View rootView;
    private View fraccionDivider1;
    private int numerador2, denominador2;
    private FractionPizzaView pizzaView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_fracciones, container, false);
        initializeViews();
        setupListeners();
        return rootView;
    }

    private void initializeViews() {
        numerador1 = rootView.findViewById(R.id.numerador1);
        denominador1 = rootView.findViewById(R.id.denominador1);
        fraccionDivider1 = rootView.findViewById(R.id.fraccionDivider1);
        btnReiniciar = rootView.findViewById(R.id.btnReiniciar);
        pizzaView = rootView.findViewById(R.id.pizzaView);

        denominador2 = (int) (Math.random() * 10) + 1;
        numerador2 = (int) (Math.random() * denominador2) + 1;

        pizzaView.setFraction(numerador2, denominador2);

        numerador1.setText(String.valueOf(numerador2));
        denominador1.setText(String.valueOf(denominador2));
    }

    private void setupListeners() {
        btnReiniciar.setOnClickListener(v -> resetAnimation());
    }

    private void resetAnimation() {
        numerador1.setScaleX(1f);
        numerador1.setScaleY(1f);
        denominador1.setScaleX(1f);
        denominador1.setScaleY(1f);
        fraccionDivider1.setScaleX(1f);


        denominador2 = (int) (Math.random() * 10) + 1;
        numerador2 = (int) (Math.random() * denominador2) + 1;

        pizzaView.setFraction(numerador2, denominador2);
        numerador1.setText(String.valueOf(numerador2));
        denominador1.setText(String.valueOf(denominador2));

    }
}