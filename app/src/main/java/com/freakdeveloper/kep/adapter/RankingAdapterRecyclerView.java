package com.freakdeveloper.kep.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.freakdeveloper.kep.R;
import com.freakdeveloper.kep.model.Ranking;

import java.util.ArrayList;

public class RankingAdapterRecyclerView extends RecyclerView.Adapter<RankingAdapterRecyclerView.ViewHolderDatos>
{
    ArrayList<Ranking> Datos;

    public RankingAdapterRecyclerView(ArrayList<Ranking> datos)
    {
        Datos = datos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ranking, null , false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos viewHolderDatos, int i)
    {
        viewHolderDatos.asignarNickName(Datos.get(i).getNickName());
        viewHolderDatos.asignarPosicion(Integer.toString(Datos.get(i).getPosicion()));
        viewHolderDatos.asignarNumUsu(Float.toString(Datos.get(i).getNumUsu()));
    }

    @Override
    public int getItemCount()
    {
        return Datos.size();
    }



    public class ViewHolderDatos extends RecyclerView.ViewHolder
    {
        TextView NickName,Posicion,NumUsu;
        public ViewHolderDatos(@NonNull View itemView)
        {
            super(itemView);
            NickName = itemView.findViewById(R.id.nick);
            Posicion = itemView.findViewById(R.id.pos);
            NumUsu= itemView.findViewById(R.id.num);

        }


        public void asignarNickName(String s)
        {
            NickName.setText(s);
        }
        public void asignarPosicion(String s)
        {
            Posicion.setText(s);
        }
        public void asignarNumUsu(String s)
        {
            NumUsu.setText(s);
        }

    }
}