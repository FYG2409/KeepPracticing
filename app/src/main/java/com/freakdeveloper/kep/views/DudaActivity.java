package com.freakdeveloper.kep.views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.freakdeveloper.kep.R;
import com.freakdeveloper.kep.model.Buzon;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DudaActivity extends AppCompatActivity {

    //PARA FIREBASE
    private DatabaseReference databaseReference;
    private static final String nodoBuzon = "Buzon";
    private FirebaseUser firebaseUser;
    private  static final String nodoPersona="Personas";
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth firebaseAuth;



    private EditText duda;
    private Button btnDuda;
    private String dudaTxt, idUsr, emailUsr;
    private Long conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duda);

        //PARA FIREBASE
        databaseReference = FirebaseDatabase.getInstance().getReference();
        duda = (EditText) findViewById(R.id.txtDuda);
        btnDuda = (Button) findViewById(R.id.btnDuda);
        btnDuda.setVisibility(View.GONE);
        traePersona();
    }

    public void enviaDuda(View view){
        dudaTxt = duda.getText().toString();
        if(dudaTxt.isEmpty()){
            Toast.makeText(this, "Aun no escribes tu duda", Toast.LENGTH_SHORT).show();
        }else{
            final Buzon buzon = new Buzon(dudaTxt, emailUsr);

            databaseReference.child(nodoBuzon).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    conta = dataSnapshot.getChildrenCount() + 1;
                    databaseReference.child(nodoBuzon).child(String.valueOf(conta)).setValue(buzon);
                    Toast.makeText(DudaActivity.this, "Listo", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

    public void traePersona(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = firebaseAuth.getCurrentUser();
                idUsr = firebaseUser.getUid();
                emailUsr = firebaseUser.getEmail();
                btnDuda.setVisibility(View.VISIBLE);
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    public void irAFAQS(View view){
        Intent intent = new Intent(this, FaqsActivity.class);
        startActivity(intent);
    }

}
