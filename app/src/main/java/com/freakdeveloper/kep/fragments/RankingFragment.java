package com.freakdeveloper.kep.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.freakdeveloper.kep.R;
import com.freakdeveloper.kep.adapter.RankingAdapterRecyclerView;
import com.freakdeveloper.kep.model.Persona;
import com.freakdeveloper.kep.model.Ranking;
import com.freakdeveloper.kep.model.Respuestas;
import com.freakdeveloper.kep.views.REscolarActivity;
import com.freakdeveloper.kep.views.RGlobalActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class RankingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //VISTA
    private TextView LU,LU2,NU,NU2;
    private ImageView IM,IM2;
    private Button BU,BU2;

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
    public String Nick,Escuela;
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
    String Nick2="",Escuela2="";
    int Lugar2=0,Num2=0;
    float m2;
    private int[] totales2=new int[11];
    private int[] aciertos2=new int[11];
    private Float[] Punta2;
    private Float[] Punta3;
    private String[] ID2;
    private String[] ID3;
    private ArrayList<Float> Pun2 = new ArrayList<>();
    private Ranking global2=new Ranking();

    public RankingFragment() {
        // Required empty public constructor
    }

    public static RankingFragment newInstance(String param1, String param2) {
        RankingFragment fragment = new RankingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_ranking, container, false);

        LU=(TextView) v.findViewById(R.id.lugra);
        //NU=(TextView) v.findViewById(R.id.numra);
        LU2=(TextView) v.findViewById(R.id.lug2ra);
        //NU2=(TextView) v.findViewById(R.id.num2ra);
        IM=(ImageView) v.findViewById(R.id.imagen);
        IM2=(ImageView) v.findViewById(R.id.imagen2);
        BU=(Button) v.findViewById(R.id.btnG);
        BU2=(Button) v.findViewById(R.id.btnE);



        //Trae Registros
        Nick="";
        Nick2="";
        Escuela="";
        Num=0;
        Num2=0;
        Lugar=0;
        Lugar2=0;


        IM.setImageResource(R.drawable.mex);
        IM2.setImageResource(R.drawable.gorro);
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(nodoPersona).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Persona persona = snapshot.getValue(Persona.class);
                        String xID=persona.getIdPersona();

                        if(xID.equals(user.getUid()))
                        {
                            Nick=persona.getNickName();
                            Nick2=Nick;
                            Escuela=persona.getEscingresar();
                            break;
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
                        String IDres=snapshot.getKey();
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

                        for(int i=0;i<11;i++)
                        {
                            y[0]=y[0]+aciertos[i];
                            y[1]=y[1]+totales[i];
                        }

                        m=(float)y[0]/(float)y[1];

                        Pun.add(m);
                        id.add(IDres);

                    }

                    Punta=new Float[Pun.size()];
                    ID=new String[id.size()];
                    for(int i=0;i<Pun.size();i++)
                    {
                        Punta[i]=Pun.get(i);
                        ID[i]=id.get(i);
                        for (int j=0;j<id2.size();j++) {
                            if (id2.get(j).equals(id.get(i))) {
                                id3.add(id2.get(j));
                                Pun3.add(Pun.get(i));
                            }
                        }
                    }

                    float aux;
                    String auxid;
                    for (int i = 0; i < Punta.length - 1; i++) {
                        for (int x = i + 1; x < Punta.length; x++) {
                            if (Punta[x] > Punta[i]) {
                                aux = Punta[i];
                                auxid= ID[i];
                                Punta[i]=Punta[x];
                                ID[i]=ID[x];
                                Punta[x]=aux;
                                ID[x]=auxid;
                            }
                        }

                    }
                    Punta3=new Float[Pun3.size()];
                    ID3=new String[id3.size()];
                    for (int k=0;k<id3.size();k++)
                    {
                        Punta3[k]=Pun3.get(k);
                        ID3[k]=id3.get(k);
                    }


                    float aux3;
                    String auxid3;
                    for (int i = 0; i < Pun3.size() - 1; i++) {
                        for (int x = i + 1; x < Pun3.size(); x++) {
                            if (Punta3[x] > Punta3[i]) {
                                aux3 = Punta3[i];
                                auxid3= ID3[i];
                                Punta3[i]=Punta3[x];
                                ID3[i]=ID3[x];
                                Punta3[x]=aux3;
                                ID3[x]=auxid3;
                            }
                        }

                    }
                    //ordenarPuntaje();
                    int n=0;
                    for (int i=0;i<ID.length;i++)
                    {
                        n=i+1;
                        if(ID[i].equals(user.getUid()))
                        {
                            Lugar=n;
                            break;
                        }
                    }

                    int o=0;
                    for (int i=0;i<ID3.length;i++)
                    {
                        o=i+1;
                        if(ID3[i].equals(user.getUid()))
                        {
                            Lugar2=o;
                            break;
                        }
                    }

                    //Lugar=obtenLugar();
                    Num=ID.length;
                    Num2=ID3.length;
                    global=new Ranking("0",Lugar,Num);
                    escolar=new Ranking("0",Lugar2,Num2);
                    int Lu = Lugar;
                    int Lu2= Lugar2;
                    int Nu= Num;
                    int Nu2= Num2;
                    //manda nulos al desplegar
                    Vistas(Lu , Nu , Lu2 , Nu2);

                    //no son nulos antes de desplegar
                    Toast.makeText(getContext(), "lugar"+Lugar+"num"+Num, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "lugar2"+Lugar2+"num2"+Num2, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        BU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RGlobalActivity.class);
                startActivity(intent);
            }
        });
        BU2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), REscolarActivity.class);
                startActivity(intent);
            }
        });



        return v;
    }

    private void Vistas(int Lu1 , int num11 , int Lu2 , int num22)
    {
        this.LU.setText(Lu1 + "/" + num11);

        this.LU2.setText(Lu2 + "/" + num22);

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}