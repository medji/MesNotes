package com.mobiletech.medji.mesnotes.Activites;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobiletech.medji.mesnotes.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by root on 21/12/17.
 */

public class MyAdapter extends ArrayAdapter {
    ArrayList<Integer> id_tab;
    ArrayList<String> libelle_tab;
    ArrayList<Float> note1_tab;
    ArrayList<Float> note2_tab;
    ArrayList<Float> notefinale_tab;
    
    public MyAdapter(Context context, ArrayList<Integer> id, ArrayList<String> libelle, ArrayList<Float> note1, ArrayList<Float> note2, ArrayList<Float> notefinale)
    {
        super(context, R.layout.liste_data, R.id.txtlib, libelle);
        this.id_tab = id;
        this.libelle_tab = libelle;
        this.note1_tab = note1;
        this.note2_tab = note2;
        this.notefinale_tab = notefinale;
    }
    
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.liste_data, parent, false);

        //TextView myid = (TextView) row.findViewById(R.id.txtid);
        TextView mylib = (TextView) row.findViewById(R.id.txtlib);
        TextView mynote1 = (TextView) row.findViewById(R.id.txtnote1);
        TextView mynote2 = (TextView) row.findViewById(R.id.txtnote2);
        TextView mynotefinale = (TextView) row.findViewById(R.id.txtnotefinale);
        
        //myid.setText(String.valueOf(id_tab.get(position)));
        mylib.setText(libelle_tab.get(position));
        mynote1.setText(String.valueOf(note1_tab.get(position)));
        mynote2.setText(String.valueOf(note2_tab.get(position)));
        mynotefinale.setText(String.valueOf(notefinale_tab.get(position)));
        if(note1_tab.get(position)>12)
        {
            row.setBackgroundColor(Color.GREEN);
        }
        else if(note1_tab.get(position)<10)
        {
            row.setBackgroundColor(Color.RED);
        }
        row.setAlpha(0.8f);
        return row;
    }
}
