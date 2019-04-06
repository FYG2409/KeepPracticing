package com.freakdeveloper.kep.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.freakdeveloper.kep.R;
import com.freakdeveloper.kep.model.Faq;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarFaqsActivity extends AppCompatActivity {

    private EditText pregunta, respuesta;
    private String txtPregunta, txtRepuesta;

    //PARA FIREBASE
    private DatabaseReference databaseReference;
    private static final String nodoFAQS = "FAQS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_faqs);

        pregunta = (EditText) findViewById(R.id.pregunta);
        respuesta = (EditText) findViewById(R.id.respuesta);

        //PARA FIREBASE
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void registra(View view){
        txtPregunta = pregunta.getText().toString();
        txtRepuesta = respuesta.getText().toString();

        Faq Faq = new Faq(txtPregunta, txtRepuesta);

        String xId = databaseReference.push().getKey();

        databaseReference.child(nodoFAQS).child(xId).setValue(Faq);

        Toast.makeText(this, "FAQ REGISTRADA", Toast.LENGTH_SHORT).show();

    }
}
