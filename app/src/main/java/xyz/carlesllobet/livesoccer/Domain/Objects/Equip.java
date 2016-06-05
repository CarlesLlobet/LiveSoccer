package xyz.carlesllobet.livesoccer.Domain.Objects;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by CarlesLlobet on 31/01/2016.
 */
public class Equip {

    //private variables
    String nom;
    Uri escut;
    String ciutat;
    Integer puntsLliga;
    Integer gols;
    Integer guanyats;
    Integer perduts;
    Integer empatats;

    // Empty constructor
    public Equip() {

    }

    // constructor
    public Equip(String name, Uri e, String c) {
        this.nom = name;
        this.escut = e;
        this.ciutat = c;
        this.puntsLliga = 0;
        this.gols = 0;
        this.guanyats = 0;
        this.perduts = 0;
        this.empatats = 0;
    }

    public Equip(String name, Uri e, String c, Integer p, Integer g,Integer pg,Integer pp,Integer pe) {
        this.nom = name;
        this.escut = e;
        this.ciutat = c;
        this.puntsLliga = p;
        this.gols = g;
        this.guanyats = pg;
        this.perduts = pp;
        this.empatats = pe;
    }

    // getting name
    public String getName() {
        return this.nom;
    }

    public void setName(String n) {
        this.nom = n;
    }

    // getting punctuation
    public Integer getPunt() {
        return this.puntsLliga;
    }

    // setting punctuation
    public void setPunt(Integer punt) {
        this.puntsLliga = punt;
    }

    // getting punctuation
    public Integer getGols() {
        return this.gols;
    }

    // setting punctuation
    public void setGols(Integer gols) {
        this.gols = gols;
    }

    // getting punctuation
    public Integer getGuanyats() {
        return this.guanyats;
    }

    // setting punctuation
    public void setGuanyats(Integer pg) {
        this.guanyats = pg;
    }

    // getting punctuation
    public Integer getPerduts() {
        return this.perduts;
    }

    // setting punctuation
    public void setPerduts(Integer pp) {
        this.perduts = pp;
    }

    // getting punctuation
    public Integer getEmpatats() {
        return this.empatats;
    }

    // setting punctuation
    public void setEmpatats(Integer pe) {
        this.empatats = pe;
    }

    public Uri getEscut() {return this.escut;}

    public void setEscut(Uri e) {this.escut = e;}

    public String getCiutat() {return this.ciutat;}

    public void setCiutat(String c) {this.ciutat = c;}
}
