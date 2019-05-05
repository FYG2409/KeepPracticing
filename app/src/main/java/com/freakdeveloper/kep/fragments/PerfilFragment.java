package com.freakdeveloper.kep.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.freakdeveloper.kep.R;
import com.freakdeveloper.kep.model.Persona;
import com.freakdeveloper.kep.model.Ranking;
import com.freakdeveloper.kep.model.Respuestas;
import com.freakdeveloper.kep.views.AjustesActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PerfilFragment extends Fragment
{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //FIREBASE

    DatabaseReference databaseReference;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    private  static final String nodoPersona="Personas";
    private  static final String nodoRespuestas="Respuestas";

    //Colores
    private int CA= Color.rgb(167, 254, 58); //(aciertos)
    private int CE= Color.rgb(255, 97 , 97); //(errores);

    private int C1= Color.rgb(97  , 185 , 255);
    private int C2= Color.rgb(255 , 167 , 97);
    private int C3= Color.rgb(252 , 166 , 246);
    private int C4= Color.rgb(246 , 255 , 131);
    private int C5= Color.rgb(166 , 166 , 252);
    private int C6= Color.rgb(166 , 252 , 197);
    private int C7= Color.rgb(188 , 106 , 106);
    private int C8= Color.rgb(2   , 71  , 117);
    private int C9= Color.rgb(181 , 103 , 255);
    private int C10= Color.rgb(217, 176 , 80);
    private int C11= Color.rgb(138, 220 , 201);

    private int [] Colores =  new int [] {C1, C2 , C3 , C4 , C5 , C6, C7, C8 , C9 , C10, C11};


    //VARIABLES
    private String Email;
    private int[] totales={0,0,0,0,0,0,0,0,0,0,0};
    private int[] aciertos={0,0,0,0,0,0,0,0,0,0,0};
    private String[] materias=new String[]{"Alge" , "Bio" , " CInteg" , "ComTex" , "Fisica" ,"GeoAn" , "GeoYTri" , "Proba" , "ProEsc", "Quimi" , "RazMat"};
    private int[] aciertosPor = new int[11];
    private BarChart barChart;
    //private int [] Colores =  new int [] {Color.BLACK , Color.RED , Color.BLUE , Color.MAGENTA};


    //TRAER DATOS
    private String NickName="";
    private String Correo="";
    private String EActual="";
    private String EIngresar="";


    //VISTAS
    private TextView Usu;
    private TextView Ran;
    private Button btnAjustes;
    private Button btnEliminar;

    //Ranking
    private TextView Rankin;
    private int[] totalest={0,0,0,0,0,0,0,0,0,0,0};
    private int[] aciertost={0,0,0,0,0,0,0,0,0,0,0};
    private ArrayList<Ranking> Datos=new ArrayList<>();
    private ArrayList<String> id=new ArrayList<>();
    public String Nick;
    public int Lugar,Num;
    float m;
    private Float[] Punta;
    private String[] ID;
    private ArrayList<Float> Pun = new ArrayList<>();
    private Ranking global=new Ranking();


    public PerfilFragment()
    {

    }


    public static PerfilFragment newInstance(String param1, String param2)
    {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        //Trae NickName, Correo, Escuela Actual y Escuela a Ingresar
        //PARA FIREBASE
        Nick="";
        Num=0;
        Lugar=0;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        btnAjustes = (Button) view.findViewById(R.id.btnAjust);
        btnEliminar = (Button) view.findViewById(R.id.btnDelete);
        Usu = (TextView) view.findViewById(R.id.NickN);
        Rankin = (TextView) view.findViewById(R.id.rank);
        this.barChart = (BarChart)view.findViewById(R.id.barChar);


        btnAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AjustesActivity.class);
                startActivity(intent);
            }
        });


        //-----TRAYENDO PERSONA----
        user = FirebaseAuth.getInstance().getCurrentUser();
        Email=user.getEmail();

        databaseReference.child(nodoPersona).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Persona persona = snapshot.getValue(Persona.class);
                        String mail=persona.getEmail();
                        if(mail.equals(Email)){
                            //SE ENCONTRO LA PERSONA CON EL EMAIL INDICADO
                            NickName = persona.getNickName();
                            Correo=persona.getEmail();
                            EActual=persona.getEscActual();
                            EIngresar=persona.getEscingresar();
                            break;
                        }
                        else {

                        }
                    }
                    Usu.setText("NickName: " + NickName );


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Trae Respuestas

        databaseReference.child(nodoRespuestas).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Respuestas respuestas = snapshot.getValue(Respuestas.class);

                        String IDres=snapshot.getKey();
                        if(IDres.equals(user.getUid())){
                            //SE ENCONTRO LA PERSONA CON EL ID INDICADO
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

                            break;
                        }

                    }
                    Porcentaje();
                    createChart();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
                ad.setCancelable(true);
                ad.setTitle("Importante");
                ad.setMessage("¿Esta seguro de querer eliminar su cuenta?");
                ad.setButton("si", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int which) {
                        databaseReference.child(nodoPersona).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                        Persona persona = snapshot.getValue(Persona.class);
                                        String idp=snapshot.getKey();
                                        String idu=user.getUid();
                                        if(idp.equals(idu)){
                                            //SE ENCONTRO LA PERSONA CON EL ID INDICADO
                                            FirebaseAuth us = FirebaseAuth.getInstance();
                                            user.delete();
                                            us.signOut();
                                            databaseReference.child(nodoPersona).child(idp).removeValue();
                                            dialog.dismiss();

                                            break;
                                        }
                                        else {

                                        }
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                });
                ad.show();
            }
        });
        //trae RANKING
        databaseReference.child(nodoRespuestas).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Respuestas respuestas = snapshot.getValue(Respuestas.class);
                        String IDres=snapshot.getKey();
                        int[] y=new int[2];
                        aciertost[0]=respuestas.getAlgebra();
                        totalest[0]=respuestas.getTotalAlgebra();
                        aciertost[1]=respuestas.getBiologia();
                        totalest[1]=respuestas.getTotalBiologia();
                        aciertost[2]=respuestas.getCalculoDiferencialeIntegral();
                        totalest[2]=respuestas.getTotalCalculoDiferencialeIntegral();
                        aciertost[3]=respuestas.getComprensiondeTextos();
                        totalest[3]=respuestas.getTotalComprensiondeTextos();
                        aciertost[4]=respuestas.getFisica();
                        totalest[4]=respuestas.getTotalFisica();
                        aciertost[5]=respuestas.getGeometriaAnalitica();
                        totalest[5]=respuestas.getTotalGeometriaAnalitica();
                        aciertost[6]=respuestas.getGeometriayTrigonometria();
                        totalest[6]=respuestas.getTotalGeometriayTrigonometria();
                        aciertost[7]=respuestas.getProbabilidadyEstadistica();
                        totalest[7]=respuestas.getTotalProbabilidadyEstadistica();
                        aciertost[8]=respuestas.getProduccionEscrita();
                        totalest[8]=respuestas.getTotalProduccionEscrita();
                        aciertost[9]=respuestas.getQuimica();
                        totalest[9]=respuestas.getTotalQuimica();
                        aciertost[10]=respuestas.getRazonamientoMatematico();
                        totalest[10]=respuestas.getTotalRazonamientoMatematico();

                        //y=puntaje();

                        for(int i=0;i<11;i++)
                        {
                            y[0]=y[0]+aciertost[i];
                            y[1]=y[1]+totalest[i];
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

                    //Lugar=obtenLugar();
                    Num=ID.length;
                    global.setImagen(R.drawable.mex);
                    global.setNickName(Nick);
                    global.setPosicion(Lugar);
                    global.setNumUsu(Num);
                    Datos.add(global);

                    Rankin.setText("Lugar: "+Lugar+" / "+Num);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }


    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private Chart getSameChart(Chart chart , String Des , int TextC , int Back , int Time)
    {
        chart.getDescription().setText(Des);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(Back);
        chart.animateY(Time);

        legend(chart);
        return chart;
    }

    private void legend(Chart chart)
    {
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> entries = new ArrayList<> ();
        for(int i=0; i<11 ; i++)
        {
            LegendEntry entry = new LegendEntry();
            entry.formColor= Colores[i];
            entry.label = materias[i];
            entries.add(entry);
        }

        legend.setCustom(entries);
    }

    private ArrayList<BarEntry>getBarEntries()
    {
        ArrayList <BarEntry> entries = new ArrayList<> ();
        for(int i=0; i<11 ; i++)
        {
            if(aciertos == null)
            {
                entries.add(new BarEntry(i , 0 ));
            }
            else {
                entries.add(new BarEntry(i, aciertosPor[i]));
            }
        }
        return entries;
    }


    private void axisX(XAxis axis)
    {
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(materias));
    }

    private void axisLeft(YAxis axis)
    {
        axis.setSpaceBottom(60);
        axis.setAxisMinimum(0);
    }

    private void axisRight(YAxis axis)
    {
        axis.setEnabled(false);
    }

    private void createChart()
    {
        barChart = (BarChart) getSameChart(barChart, "ACIERTOS" , Color.BLACK , Color.WHITE , 5000);
        barChart.setDrawGridBackground(true);
        barChart.setDrawBarShadow(false);
        barChart.setData(getBarData());
        axisX(barChart.getXAxis());
        axisLeft(barChart.getAxisLeft());
        axisRight(barChart.getAxisRight());
        barChart.getLegend().setEnabled(false);

    }

    private DataSet getData (DataSet dataSet)
    {
        dataSet.setColors(this.Colores);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(10);
        return dataSet;
    }

    private BarData getBarData()
    {
        BarDataSet barDataSet= (BarDataSet) getData(new BarDataSet(getBarEntries() , ""));
        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.25f);
        return barData;
    }

    private void Porcentaje()
    {
        int TotalP = 0;
        for(int i=0; i<materias.length ; i++)
        {
            TotalP = TotalP + totales [i] ;
        }

        for(int i=0; i<materias.length ; i++)
        {
            if(TotalP==0){
                aciertosPor[i] = 0;
            }else{
                aciertosPor [i] = (aciertos [i]*100)/TotalP;
            }
        }

    }

}
