package xyz.carlesllobet.livesoccer.Domain.Objects;

import android.net.Uri;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by CarlesLlobet on 31/01/2016.
 */
public class Partit {

    //private variables
    Equip visitant;
    Equip local;
    Integer puntuacioVisitant;
    Integer puntuacioLocal;
    ArrayList<Jugador> golejadors;

    // Empty constructor
    public Partit() {

    }

    // constructor
    public Partit(Equip l, Equip v, Integer pL, Integer pV) {
        this.visitant = v;
        this.local = l;
        this.puntuacioVisitant = pV;
        this.puntuacioLocal = pL;
    }

    public Partit(Equip l, Equip v, Integer pL, Integer pV, ArrayList<Jugador> g) {
        this.visitant = v;
        this.local = l;
        this.puntuacioVisitant = pV;
        this.puntuacioLocal = pL;
        this.golejadors = g;
    }

    // getting name
    public Equip getGuanyador() {
        Equip aux = null;
        if(puntuacioVisitant>puntuacioLocal) return visitant;
        else if(puntuacioLocal>puntuacioVisitant) return local;
        return aux;
    }

    // setting name
    public Equip getVisitant() {
        return this.visitant;
    }

    //getting foto
    public Equip getLocal() {
        return this.local;
    }

    //getting foto
    public Integer getPuntVisitant() {
        return this.puntuacioVisitant;
    }

    // getting punctuation
    public Integer getPuntLocal() {
        return this.puntuacioLocal;
    }

    public ArrayList<Jugador> getGolejadors(){ return this.golejadors; }

    public void setGolejadors(ArrayList<Jugador> g){ this.golejadors = g; }

    public void setLocal(Equip l){ this.local = l; }
    public void setVisitant(Equip v){ this.visitant = v; }
    public void setPuntuacioLocal(Integer l){ this.puntuacioLocal = l; }
    public void setPuntuacioVisitant(Integer v){ this.puntuacioLocal = v; }
}
