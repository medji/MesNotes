package com.mobiletech.medji.mesnotes.Model;

/**
 * Created by root on 19/12/17.
 */

public class Matiere {
    private int id;
    private String libelle;
    private float note1;
    private float note2;
    private float notefinale;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public float getNote1() {
        return note1;
    }

    public void setNote1(float note1) {
        this.note1 = note1;
    }

    public float getNote2() {
        return note2;
    }

    public void setNote2(float note2) {
        this.note2 = note2;
    }

    public float getNotefinale() {
        return notefinale;
    }

    public void setNotefinale(float notefinale) {
        this.notefinale = notefinale;
    }
}
