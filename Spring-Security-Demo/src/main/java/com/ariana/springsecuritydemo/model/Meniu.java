package com.ariana.springsecuritydemo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Meniu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID_Preparat;
    private String Nume_Preparat;
    private Double Pret;
    private Integer Stoc;

    public Meniu() {

    }

    public int getID_Preparat() {
        return ID_Preparat;
    }

    public void setID_Preparat(int ID_Preparat) {
        this.ID_Preparat = ID_Preparat;
    }

    public String getNume_Preparat() {
        return Nume_Preparat;
    }

    public void setNume_Preparat(String nume_Preparat) {
        Nume_Preparat = nume_Preparat;
    }

    public Double getPret() {
        return Pret;
    }

    public void setPret(Double pret) {
        Pret = pret;
    }

    public int getStoc() {
        return Stoc;
    }

    public void setStoc(Integer stoc) {
        Stoc = stoc;
    }

    public Meniu(int ID_Preparat, String nume_Preparat, Double pret, Integer stoc) {
        this.ID_Preparat = ID_Preparat;
        Nume_Preparat = nume_Preparat;
        Pret = pret;
        Stoc = stoc;
    }
}
