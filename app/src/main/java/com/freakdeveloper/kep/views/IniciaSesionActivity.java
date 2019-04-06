package com.freakdeveloper.kep.views;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IniciaSesionActivity extends AppCompatActivity
{

    //VISTAS
    private EditText Correo;
    private EditText Contra;
    private Button Iniciar;
    private ProgressDialog Dialogo;

    //FIREBASE
    private FirebaseAuth Login;
    private FirebaseAuth.AuthStateListener ListenerA;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicia_sesion);
        IniciarVistas();

        Login= FirebaseAuth.getInstance();

        Iniciar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Login();
            }
        });

        ListenerA = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                if(firebaseAuth.getCurrentUser() != null)
                {
                    FirebaseUser Usu= firebaseAuth.getCurrentUser();
                    if(!Usu.isEmailVerified())
                    {
                        Toast.makeText(IniciaSesionActivity.this , "DEBES VERIFICAR EL EMAIL" , Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                    else
                    {
                        Toast.makeText(IniciaSesionActivity.this , "INICIO DE SESION EXITOSO" , Toast.LENGTH_SHORT).show();
                        String ID= firebaseAuth.getCurrentUser().getUid();
                        Ir(ID);
                    }

                }
            }
        };
    }

    protected void onStart()
    {
        super.onStart();
        Login.addAuthStateListener(ListenerA);
    }

    private void Login()
    {
        String email = Correo.getText().toString().trim();
        String password = Contra.getText().toString().trim();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
        {
            Dialogo.setMessage("INICIANDO SESION");
            Dialogo.show();
            Login.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    Dialogo.dismiss();
                    if(task.isSuccessful())
                    {

                    }
                    else
                    {
                        Toast.makeText(IniciaSesionActivity.this , "SESION FALLIDA" , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void IniciarVistas()
    {
        Correo = (EditText) findViewById(R.id.Correo);
        Contra = (EditText) findViewById(R.id.Contraseña);
        Iniciar= (Button) findViewById(R.id.Iniciar);
        Dialogo = new ProgressDialog(this);
    }

    private void Ir(String ID)
    {
        Intent intent = new Intent(this , NavigationDrawerActivity.class );
        Bundle IDUsu = new Bundle();
        IDUsu.putString("ID" , ID);
        intent.putExtras(IDUsu);
        startActivity(intent);
    }

    public void Olvide(View V)
    {
        Intent Pass = new Intent(this , RecuperarContraActivity.class);
        startActivity(Pass);
    }

}