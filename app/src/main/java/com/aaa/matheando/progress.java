package com.aaa.matheando;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class progress extends LinearLayout {
    ProgressBar progressBar;
    TextView TextProgreso;
    Button btnGuardarProgreso;
    private int progreso = 0;

    public progress(Context context) {
        super(context);
        init();
    }

    public progress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.progress, this, true);
        progressBar=findViewById(R.id.progressBar);
        TextProgreso=findViewById(R.id.TextProgreso);
        btnGuardarProgreso=findViewById(R.id.btnGuardarProgreso);
    }

    public void setProgreso(int progreso) {
        this.progreso = Math.min(Math.max(progreso, 0), 100);
        progressBar.setProgress(progreso);
        TextProgreso.setText(progreso + "%");
        invalidate();
    }

    public int getProgreso() {
        return progreso;
    }
}
