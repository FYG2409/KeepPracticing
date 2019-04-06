package com.freakdeveloper.kep.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.freakdeveloper.kep.R;

public class PrincipalActivity extends AppCompatActivity {

    private Button regPregunta, regCarrera, btnFaq;
    private int conta = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        regPregunta = findViewById(R.id.regPregunta);
        regCarrera = findViewById(R.id.regCarrera);
        btnFaq = findViewById(R.id.btnFaq);

    }

    public void visualiza(View view){
        if(conta==0){
            regPregunta.setVisibility(View.VISIBLE);
            regCarrera.setVisibility(View.VISIBLE);
            btnFaq.setVisibility(View.VISIBLE);
            conta = 1;
        }else if (conta == 1) {
            regPregunta.setVisibility(View.GONE);
            regCarrera.setVisibility(View.GONE);
            btnFaq.setVisibility(View.GONE);
            conta = 0;
        }
    }


    public void irAInicioSesion(View view){
        Intent intent = new Intent(this, IniciaSesionActivity.class);
        startActivity(intent);
    }

    public void irARegistro(View view){
        Intent intent = new Intent(this , RegistroActivity.class);
        startActivity(intent);
    }

    public void irARegPregunta(View view){
        Intent intent = new Intent(this, RegistroPreguntaActivity.class);
        startActivity(intent);
    }

    public void irARegCarrera(View view){
        Intent intent = new Intent(this, RegistroCarreraActivity.class);
        startActivity(intent);
    }

    public void irAFAQS(View view){
        Intent intent = new Intent(this, FaqsActivity.class);
        startActivity(intent);
    }

    public void irARegistraFAQS(View view){
        Intent intent = new Intent(this, RegistrarFaqsActivity.class);
        startActivity(intent);
    }
}
