package xyz.carlesllobet.livesoccer.Domain.Objects;

import android.net.Uri;

/**
 * Created by CarlesLlobet on 31/01/2016.
 */
public class Jugador {

    //private variables
    String nom;
    Integer dorsal;
    String equip;
    Boolean titular;
    Integer golsLliga;

    // Empty constructor
    public Jugador() {

    }

    // constructor
    public Jugador(String name, Integer num, Boolean t, String e) {
        this.nom = name;
        this.dorsal = num;
        this.equip = e;
        this.titular = t;
        this.golsLliga = 0;
    }
    public Jugador(String name, Integer num, String e, Boolean t, Integer p) {
        this.nom = name;
        this.dorsal = num;
        this.equip = e;
        this.titular = t;
        this.golsLliga = p;
    }

    // getting name
    public String getName() {
        return this.nom;
    }

    // setting name
    public void setName(String name) {
        this.nom = name;
    }

    public String getEquip(){return this.equip;}

    public void setEquip(String e) {this.equip = e;}

    //getting foto
    public Integer getDorsal() {
        return this.dorsal;
    }

    //getting foto
    public void setDorsal(Integer num) {
        this.dorsal = num;
    }

    // getting punctuation
    public Integer getGols() {
        return this.golsLliga;
    }

    // setting punctuation
    public void setGols(Integer gols) {
        this.golsLliga = gols;
    }

    public Boolean getTitular() {return this.titular;}

    public void setTitular(Boolean t) {this.titular = t;}
}
