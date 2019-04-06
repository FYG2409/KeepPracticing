package com.freakdeveloper.kep.views;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.freakdeveloper.kep.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarContraActivity extends AppCompatActivity
{
    private String Email;
    private EditText Corre;
    private ProgressDialog Dialogo ;
    private Button BR;
    //FIREBASE
    private FirebaseAuth Recuperar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contra);


        Corre = (EditText) findViewById(R.id.Correo);
        Dialogo = new ProgressDialog(this);
        BR = (Button) findViewById(R.id.Recup);
        BR.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Recuperar();
            }
        });
    }

    private void Recuperar()
    {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Email = Corre.getText().toString();
        Toast.makeText(RecuperarContraActivity.this , Email , Toast.LENGTH_SHORT).show();
        if(!TextUtils.isEmpty(Email))
        {
            Dialogo.setMessage("Procesando");
            Dialogo.show();
            auth.sendPasswordResetEmail(Email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(RecuperarContraActivity.this , "Se ha enviado un correo" , Toast.LENGTH_SHORT).show();
                                Dialogo.dismiss();
                            }
                            else
                            {
                                Toast.makeText(RecuperarContraActivity.this , " E R R O R " , Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(RecuperarContraActivity.this , "Debes Ingresar Tu Correo" , Toast.LENGTH_SHORT).show();
        }

    }


}
