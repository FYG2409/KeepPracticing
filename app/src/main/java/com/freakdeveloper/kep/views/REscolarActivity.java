package com.freakdeveloper.kep.views;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freakdeveloper.kep.R;
import com.freakdeveloper.kep.adapter.RankingAdapterRecyclerView;
import com.freakdeveloper.kep.model.Persona;
import com.freakdeveloper.kep.model.Ranking;
import com.freakdeveloper.kep.model.Respuestas;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class REscolarActivity extends AppCompatActivity {

    //VISTA
    RecyclerView reclyclerViewR;
    RecyclerView reclyclerViewRE;
    //FIREBASE

    DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener authStateListener;
    FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private  static final String nodoPersona="Personas";
    private  static final String nodoRespuestas="Respuestas";

    //VARIABLES
    private ArrayList<Ranking> Datos=new ArrayList<>();
    private ArrayList<String> id=new ArrayList<>();
    private ArrayList<String> Nick=new ArrayList<>();
    private ArrayList<String> Nick2=new ArrayList<>();
    private ArrayList<String> Nick2t=new ArrayList<>();
    private ArrayList<String> id2t=new ArrayList<>();
    private String[] Nick2N;
    private int[] Lugar2;
    public String Escuela;
    public int Lugar,Num;
    float m;
    private int[] totales=new int[11];
    private int[] aciertos=new int[11];
    private Float[] Punta;
    private String[] ID;
    private ArrayList<Float> Pun = new ArrayList<>();
    private ArrayList<Float> Pun3 = new ArrayList<>();
    private Ranking global=new Ranking();
    private Ranking escolar=new Ranking();
    //var 2
    private ArrayList<Ranking> Datos2=new ArrayList<>();
    private ArrayList<String> id2=new ArrayList<>();
    private ArrayList<String> id3=new ArrayList<>();
    int Num2=0;
    float m2;
    private int[] totales2=new int[11];
    private int[] aciertos2=new int[11];
    private Float[] Punta2;
    private Float[] Punta3;
    private String[] ID2;
    private String[] ID3;
    private ArrayList<Float> Pun2 = new ArrayList<>();
    private Ranking global2=new Ranking();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescolar);

        reclyclerViewRE= (RecyclerView) findViewById(R.id.RVidRE);
        reclyclerViewRE.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false));


        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(nodoPersona).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Persona persona = snapshot.getValue(Persona.class);
                        id.add(persona.getIdPersona());
                        Nick.add(persona.getNickName());
                        if(persona.getIdPersona().equals(user.getUid())) {
                            Escuela = persona.getEscingresar();
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //traeNickName();
        databaseReference.child(nodoPersona).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Persona persona = snapshot.getValue(Persona.class);
                        String Escu=persona.getEscingresar();
                        if(Escu.equals(Escuela))
                        {
                            Nick2.add(persona.getNickName());
                            id2.add(persona.getIdPersona());
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //obtenIdEsc();

        databaseReference.child(nodoRespuestas).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Respuestas respuestas = snapshot.getValue(Respuestas.class);
                        String IDres = snapshot.getKey();
                        for (int i = 0; i < id2.size(); i++) {
                            if (IDres.equals(id2.get(i))) {
                                id2t.add(id2.get(i));
                                Nick2t.add(Nick2.get(i));

                                int[] y=new int[2];
                                aciertos[0]=respuestas.getAlgebra();
                                totales[0]=respuestas.getTotalAlgebra();
                                aciertos[1]=respuestas.getBiologia();
                                totales[1]=respuestas.getTotalBiologia();
                                aciertos[2]=respuestas.getCalculoDiferencialeIntegral();
                                totales[2]=respuestas.getTotalCalculoDiferencialeIntegral();
                                aciertos[3]=respuestas.getComprensiondeTextos();
                                totales[3]=respuestas.getTotalComprensiondeTextos();
                                aciertos[4]=respuestas.getFisica();
                                totales[4]=respuestas.getTotalFisica();
                                aciertos[5]=respuestas.getGeometriaAnalitica();
                                totales[5]=respuestas.getTotalGeometriaAnalitica();
                                aciertos[6]=respuestas.getGeometriayTrigonometria();
                                totales[6]=respuestas.getTotalGeometriayTrigonometria();
                                aciertos[7]=respuestas.getProbabilidadyEstadistica();
                                totales[7]=respuestas.getTotalProbabilidadyEstadistica();
                                aciertos[8]=respuestas.getProduccionEscrita();
                                totales[8]=respuestas.getTotalProduccionEscrita();
                                aciertos[9]=respuestas.getQuimica();
                                totales[9]=respuestas.getTotalQuimica();
                                aciertos[10]=respuestas.getRazonamientoMatematico();
                                totales[10]=respuestas.getTotalRazonamientoMatematico();

                                //y=puntaje();

                                for(int j=0;j<11;j++)
                                {
                                    y[0]=y[0]+aciertos[j];
                                    y[1]=y[1]+totales[j];
                                }

                                m=(float)(y[0]*100)/(float)y[1];

                                Pun.add(m);

                            }
                        }


                    }

                    Nick2N = new String[Nick2t.size()];
                    Punta = new Float[Pun.size()];
                    ID = new String[id2t.size()];
                    for (int i = 0; i < Pun.size(); i++) {
                        Punta[i] = Pun.get(i);
                        ID[i] = id2t.get(i);
                        Nick2N[i] = Nick2t.get(i);
                    }

                    float aux;
                    String auxid;
                    String auxni;
                    for (int i = 0; i < Punta.length - 1; i++) {
                        for (int x = i + 1; x < Punta.length; x++) {
                            if (Punta[x] > Punta[i]) {
                                aux = Punta[i];
                                auxid = ID[i];
                                auxni = Nick2N[i];
                                Punta[i] = Punta[x];
                                ID[i] = ID[x];
                                Nick2N[i] = Nick2N[x];
                                Punta[x] = aux;
                                ID[x] = auxid;
                                Nick2N[x] = auxni;
                            }
                        }

                    }
                    Lugar2 = new int[ID.length];
                    int n = 0;
                    for (int i = 0; i < ID.length; i++) {
                        n = i + 1;
                        Lugar2[i] = n;
                        Num = n - 1;
                    }

                    for (int k = 0; k < ID.length; k++) {
                        Ranking r = new Ranking(Nick2N[k], Lugar2[k], Punta[k]);
                        Datos.add(r);
                    }

                    RankingAdapterRecyclerView Adap = new RankingAdapterRecyclerView(Datos);
                    reclyclerViewRE.setAdapter(Adap);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
