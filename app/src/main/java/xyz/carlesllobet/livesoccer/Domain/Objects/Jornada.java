package xyz.carlesllobet.livesoccer.Domain.Objects;

import java.util.ArrayList;

/**
 * Created by CarlesLlobet on 31/01/2016.
 */
public class Jornada {

    //private variables
    Partit partit1;
    Partit partit2;
    Partit partit3;
    Partit partit4;
    Partit partit5;

    // Empty constructor
    public Jornada() {

    }

    // constructor
    public Jornada(Partit p1, Partit p2, Partit p3, Partit p4, Partit p5) {
        this.partit1 = p1;
        this.partit2 = p2;
        this.partit3 = p3;
        this.partit4 = p4;
        this.partit5 = p5;
    }

    // getting name
    public Partit getPrimer() {
        return this.partit1;
    }

    public Partit getSegon() {
        return this.partit2;
    }

    public Partit getTercer() {
        return this.partit3;
    }

    public Partit getQuart() {
        return this.partit4;
    }

    public Partit getCinque() {
        return this.partit5;
    }

    // setting name
    public void setPartit(int n, Partit p) {
        switch (n){
            case 1:
                this.partit1 = p;
                break;
            case 2:
                this.partit2 = p;
                break;
            case 3:
                this.partit3 = p;
                break;
            case 4:
                this.partit4 = p;
                break;
            case 5:
                this.partit5 = p;
                break;
        }
    }
}
