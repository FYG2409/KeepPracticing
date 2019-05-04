package com.freakdeveloper.kep.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.freakdeveloper.kep.R;
import com.freakdeveloper.kep.model.Enciclopedia;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroEnciclopediaActivity extends AppCompatActivity {

    private String Id,Mate,Tem,Descrip,Ejemp;
    private DatabaseReference databaseReference;
    private EditText Tema,Descripcion,Ejemplo;
    private Spinner Materi;

    private  static final String nodoEnciclopedia="Enciclopedia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_enciclopedia);

        Tema=(EditText) findViewById(R.id.tema);
        Descripcion=(EditText) findViewById(R.id.descripcion);
        Ejemplo=(EditText) findViewById(R.id.ejemplo);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Materi = (Spinner) findViewById(R.id.MSpinner);
        String[] diciplina={"Materia","RazonamientoMatematico","Algebra","GeometriayTrigonometria","GeometriaAnalitica",
                "CalculoDiferencialeIntegral","ProbabilidadyEstadistica","ProduccionEscrita","ComprensiondeTextos",
                "Biologia","Quimica","Fisica"};
        Materi.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, diciplina));

    }

    public void RegistraEnci(View view){
        Mate=Materi.getSelectedItem().toString();
        Tem=Tema.getText().toString();
        Descrip=Descripcion.getText().toString();
        Ejemp=Ejemplo.getText().toString();


        Enciclopedia enci = new Enciclopedia(databaseReference.push().getKey(),Mate, Tem, Descrip,Ejemp);
        databaseReference.child(nodoEnciclopedia).child(enci.getID()).setValue(enci);
        Toast.makeText(this, "Se registro nuevo tema", Toast.LENGTH_SHORT).show();
    }

}
