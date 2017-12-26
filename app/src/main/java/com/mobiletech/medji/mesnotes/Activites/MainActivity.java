package com.mobiletech.medji.mesnotes.Activites;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.mobiletech.medji.mesnotes.Db.DataBaseHelper;
import com.mobiletech.medji.mesnotes.Model.Matiere;
import com.mobiletech.medji.mesnotes.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MyAdapter adapter;
    ArrayList<Integer> id_tab;
    ArrayList<String> libelle_tab;
    ArrayList<Float> note1_tab;
    ArrayList<Float> note2_tab;
    ArrayList<Float> notefinale_tab;
    ListView conteneur;
    int svg_position=0;
    DataBaseHelper myDb;
    Switch aSwitch;
    ViewGroup formulaire;
    Matiere m;
    EditText identifiant;
    EditText libelle;
    EditText n1;
    EditText n2;
    EditText nf;
    Animation animation_fade_in;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        myDb = new DataBaseHelper(this);
        conteneur = (ListView) findViewById(R.id.conteneur);
        animation_fade_in = AnimationUtils.loadAnimation(this, R.anim.bounce);
        final Button valider = (Button) findViewById(R.id.valider);
        aSwitch = (Switch) findViewById(R.id.switcher);
        formulaire = (ViewGroup) findViewById(R.id.form);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    formulaire.setVisibility(View.GONE);
                }
                else
                {
                    formulaire.setVisibility(View.VISIBLE);
                }
            }
        });
        charger_donnees();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!n1.getText().toString().equals("") && !nf.getText().toString().equals(""))
                n2.setText(String.valueOf((Float.parseFloat(nf.getText().toString()) * 100 - (Float.parseFloat(n1.getText().toString()) * 40)) / 60));
                else n2.setText("0");
            }
        };

        identifiant = (EditText) findViewById(R.id.id);
        libelle = (EditText) findViewById(R.id.libelle);
        n1 = (EditText) findViewById(R.id.note1);
        n2 = (EditText) findViewById(R.id.note2);
        nf = (EditText) findViewById(R.id.notefinale);

        n1.addTextChangedListener(textWatcher);

        nf.addTextChangedListener(textWatcher);

        conteneur.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("Supprimer")) {
                            myDb.deletData(id_tab.get(position));
                            charger_donnees();

                        }
                        else{
                            identifiant.setText(String.valueOf(id_tab.get(position)));
                            libelle.setText(libelle_tab.get(position));
                            n1.setText(String.valueOf(note1_tab.get(position)));
                            n2.setText(String.valueOf(note2_tab.get(position)));
                            nf.setText(String.valueOf(notefinale_tab.get(position)));
                            valider.setText("Modifier");
                            svg_position=position;
                        }

                        return true;
                    }
                });
                popupMenu.show();
                return true;

            }
        });


        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(libelle.getText().toString().isEmpty() || n1.getText().toString().isEmpty() || n2.getText().toString().isEmpty() || nf.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Champs vides", Toast.LENGTH_SHORT).show();
                }
                else {
                    m = new Matiere();

                    m.setLibelle(libelle.getText().toString());
                    m.setNote1(Float.parseFloat(n1.getText().toString()));
                    m.setNote2(Float.parseFloat(n2.getText().toString()));
                    m.setNotefinale(Float.parseFloat(nf.getText().toString()));
                    if (valider.getText().toString().equals("Enregistrer")) {
                        myDb.insertData(m);
                    } else {
                        m.setId(Integer.parseInt(identifiant.getText().toString()));
                        myDb.updateData(m);
                        valider.setText("Enregistrer");
                    }
                    charger_donnees();
                    refresh();
                }
            }
        });
    }

    public void charger_donnees()
    {
        final DataBaseHelper myDb = new DataBaseHelper(this);

        Cursor res = myDb.getAllData();
        id_tab = new ArrayList<>();
        libelle_tab = new ArrayList<>();
        note1_tab = new ArrayList<>();
        note2_tab = new ArrayList<>();
        notefinale_tab = new ArrayList<>();

        while(res.moveToNext())
        {
            id_tab.add(Integer.parseInt(res.getString(0)));
            libelle_tab.add(res.getString(1));
            note1_tab.add(Float.parseFloat(res.getString(2)));
            note2_tab.add(Float.parseFloat(res.getString(3)));
            notefinale_tab.add(Float.parseFloat(res.getString(4)));
        }

        adapter = new MyAdapter(getBaseContext(),id_tab, libelle_tab, note1_tab, note2_tab, notefinale_tab);

        conteneur.setAdapter(adapter);
    }

    public void refresh()
    {
        libelle.setText("");
        n1.setText("");
        n2.setText("");
        nf.setText("");
    }
}