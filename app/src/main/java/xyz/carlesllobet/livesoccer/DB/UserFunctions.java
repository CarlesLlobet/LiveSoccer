package xyz.carlesllobet.livesoccer.DB;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import xyz.carlesllobet.livesoccer.Domain.Objects.Equip;
import xyz.carlesllobet.livesoccer.Domain.Objects.Jornada;
import xyz.carlesllobet.livesoccer.Domain.Objects.Jugador;
import xyz.carlesllobet.livesoccer.Domain.Objects.Partit;
import xyz.carlesllobet.livesoccer.R;
import xyz.carlesllobet.livesoccer.UI.HomeActivity;

public class UserFunctions {

    public ArrayList<Jornada> getPartits(Context context) {
        DatabaseHandler db = new DatabaseHandler(context);
        ArrayList<Jornada> llista = db.getPartits();
        db.closeDB();
        return llista;
    }

    public ArrayList<Equip> getEquips(Context context) {
        DatabaseHandler db = new DatabaseHandler(context);
        ArrayList<Equip> llista = db.getEquips();
        db.closeDB();
        return llista;
    }

    public ArrayList<Jugador> getJugadors(Context context) {
        DatabaseHandler db = new DatabaseHandler(context);
        ArrayList<Jugador> llista = db.getJugadors();
        db.closeDB();
        return llista;
    }

    public ArrayList<Jugador> getJugadors(Context context, String nomEquip) {
        DatabaseHandler db = new DatabaseHandler(context);
        ArrayList<Jugador> llista = db.getJugadors(nomEquip);
        db.closeDB();
        return llista;
    }

    public Equip getEquip(Context context, String nombre) {
        DatabaseHandler db = new DatabaseHandler(context);
        Equip equip = db.getEquip(nombre);
        db.closeDB();
        return equip;
    }

    public Jugador getJugador(Context context, String nombre) {
        DatabaseHandler db = new DatabaseHandler(context);
        Jugador jugador = db.getJugador(nombre);
        db.closeDB();
        return jugador;
    }

    public Jugador getJugador(Context context, Integer pos) {
        DatabaseHandler db = new DatabaseHandler(context);
        Jugador jugador = db.getJugador(pos);
        db.closeDB();
        return jugador;
    }

    public Equip getEquip(Context context, Integer pos) {
        DatabaseHandler db = new DatabaseHandler(context);
        Equip equip = db.getEquip(pos);
        db.closeDB();
        return equip;
    }

    public boolean addPartit(Context context, Equip local, Equip visitant, Integer golsLocal, Integer golsVisitant) {
        DatabaseHandler db = new DatabaseHandler(context);
        boolean res = db.addPartit(local.getName(), visitant.getName(), golsLocal, golsVisitant);
        db.closeDB();
        return res;
    }

    public boolean addTeam(Context context, String nombre, Uri escut) {
        DatabaseHandler db = new DatabaseHandler(context);
        boolean res = db.addTeam(nombre, escut);
        db.closeDB();
        return res;
    }

    public boolean addJugador(Context context, String nombre, Integer dorsal, String equip, Boolean titular) {
        DatabaseHandler db = new DatabaseHandler(context);
        boolean res = db.addJugador(nombre, dorsal, equip, titular);
        db.closeDB();
        return res;
    }

    public void setLang(Context context, String lang) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("Language", lang);
        editor.commit();
    }

    public void setGol(Context context, String partit, String jugador) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        String anteriors = preferences.getString(partit, "");
        editor.putString(partit, anteriors + "," + jugador);
        editor.commit();
    }

    public ArrayList<Jugador> getGols(Context context, String partit) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String[] jugadors = (preferences.getString(partit, "")).split(",");
        ArrayList<Jugador> res = new ArrayList<Jugador>();
        for (int i = 0; i < jugadors.length; ++i) {
            Log.d("Afegint",jugadors[i]);
            res.add(getJugador(context, jugadors[i]));
        }
        return res;
    }

    public void borraGols(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("Partit 1");
        editor.remove("Partit 2");
        editor.remove("Partit 3");
        editor.remove("Partit 4");
        editor.remove("Partit 5");
        editor.commit();
    }

    public void borraGol(Context context, String partit, Integer posicio){
        posicio -= 1;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String res = preferences.getString(partit,"");
        String[] jugadors = (res).split(",");
        if (jugadors.length > 0 && !jugadors[0].equals("")) {
            if (posicio==0) {
                res = jugadors[1];
                for (int i = 2; i < jugadors.length; ++i) {
                    if (posicio != i) {
                        res += ",";
                        res += jugadors[i];
                    }
                }
            } else {
                res = jugadors[0];
                for (int i = 1; i < jugadors.length; ++i) {
                    if (posicio != i) {
                        res += ",";
                        res += jugadors[i];
                    }
                }
            }
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(partit, res);
    }

    public String getLang(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("Language", "notExists");
    }

    public Boolean checkInitValues(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getString("InitValues", "notExists").equals("notExists")) {
            //Fiquem valors inicials
            Uri ath = Uri.parse("android.resource://xyz.carlesllobet.livesoccer/" + R.mipmap.ic_athletic);
            Uri at = Uri.parse("android.resource://xyz.carlesllobet.livesoccer/" + R.mipmap.ic_atletico);
            Uri bar = Uri.parse("android.resource://xyz.carlesllobet.livesoccer/" + R.mipmap.ic_barcelona);
            Uri cel = Uri.parse("android.resource://xyz.carlesllobet.livesoccer/" + R.mipmap.ic_celta);
            Uri pal = Uri.parse("android.resource://xyz.carlesllobet.livesoccer/" + R.mipmap.ic_las_palmas);
            Uri mal = Uri.parse("android.resource://xyz.carlesllobet.livesoccer/" + R.mipmap.ic_malaga);
            Uri re_ma = Uri.parse("android.resource://xyz.carlesllobet.livesoccer/" + R.mipmap.ic_real_madrid);
            Uri sev = Uri.parse("android.resource://xyz.carlesllobet.livesoccer/" + R.mipmap.ic_sevilla);
            Uri va = Uri.parse("android.resource://xyz.carlesllobet.livesoccer/" + R.mipmap.ic_valencia);
            Uri vi = Uri.parse("android.resource://xyz.carlesllobet.livesoccer/" + R.mipmap.ic_villareal);

            addTeam(context, "Athletic", ath);
            addTeam(context, "Atlético", at);
            addTeam(context, "Barcelona", bar);
            addTeam(context, "Celta", cel);
            addTeam(context, "Palmas", pal);
            addTeam(context, "Málaga", mal);
            addTeam(context, "R.Madrid", re_ma);
            addTeam(context, "Sevilla", sev);
            addTeam(context, "Valencia", va);
            addTeam(context, "Villareal", vi);

            //Afegir Jugadors Athletic
            addJugador(context, "Iraizoz", 00, "Athletic", true);
            addJugador(context, "Laporte", 13, "Athletic", true);
            addJugador(context, "Iturraspe", 12, "Athletic", true);
            addJugador(context, "Susaeta", 11, "Athletic", true);
            addJugador(context, "Aduriz", 21, "Athletic", true);

            addJugador(context, "Sabin", 14, "Athletic", false);
            addJugador(context, "Gurpegi", 15, "Athletic", false);
            addJugador(context, "Rico", 10, "Athletic", false);
            addJugador(context, "Lekue", 16, "Athletic", false);
            addJugador(context, "Etxeita", 17, "Athletic", false);
            addJugador(context, "Bóveda", 28, "Athletic", false);
            addJugador(context, "Herrerín", 29, "Athletic", false);

            //Afegir Jugadors Atlético
            addJugador(context, "Bernabé", 00, "Atlético", true);
            addJugador(context, "Godín", 13, "Atlético", true);
            addJugador(context, "Juanfran", 12, "Atlético", true);
            addJugador(context, "Koke", 11, "Atlético", true);
            addJugador(context, "Torres", 21, "Atlético", true);

            addJugador(context, "Tiago", 14, "Atlético", false);
            addJugador(context, "Griezmann", 15, "Atlético", false);
            addJugador(context, "Augusto", 10, "Atlético", false);
            addJugador(context, "Moyá", 16, "Atlético", false);
            addJugador(context, "Oblak", 17, "Atlético", false);
            addJugador(context, "Savic", 28, "Atlético", false);
            addJugador(context, "Filipe L.", 29, "Atlético", false);

            //Afegir Jugadors Barça
            addJugador(context, "Valdes", 00, "Barcelona", true);
            addJugador(context, "Piqué", 13, "Barcelona", true);
            addJugador(context, "Iniesta", 12, "Barcelona", true);
            addJugador(context, "Messi", 11, "Barcelona", true);
            addJugador(context, "Puyol", 21, "Barcelona", true);

            addJugador(context, "Busquets", 14, "Barcelona", false);
            addJugador(context, "Neymar", 15, "Barcelona", false);
            addJugador(context, "Ronaldinho", 10, "Barcelona", false);
            addJugador(context, "Adriano", 16, "Barcelona", false);
            addJugador(context, "Alba", 17, "Barcelona", false);
            addJugador(context, "Suárez", 28, "Barcelona", false);
            addJugador(context, "Bartra", 29, "Barcelona", false);

            //Afegir Jugadors Celta
            addJugador(context, "Álvarez", 00, "Celta", true);
            addJugador(context, "Gómez", 13, "Celta", true);
            addJugador(context, "Nolito", 12, "Celta", true);
            addJugador(context, "Guidetti", 11, "Celta", true);
            addJugador(context, "Orellana", 21, "Celta", true);

            addJugador(context, "Johny", 14, "Celta", false);
            addJugador(context, "H. Mallo", 15, "Celta", false);
            addJugador(context, "Planas", 10, "Celta", false);
            addJugador(context, "Marcelo", 16, "Celta", false);
            addJugador(context, "Wass", 17, "Celta", false);
            addJugador(context, "Bongonda", 28, "Celta", false);
            addJugador(context, "Iago", 29, "Celta", false);

            //Afegir Jugadors Palmas -
            addJugador(context, "Lizoain", 00, "Palmas", true);
            addJugador(context, "Varas", 13, "Palmas", true);
            addJugador(context, "Ángel", 12, "Palmas", true);
            addJugador(context, "Lemos", 11, "Palmas", true);
            addJugador(context, "Castellano", 21, "Palmas", true);

            addJugador(context, "Montoro", 14, "Palmas", false);
            addJugador(context, "Valerón", 15, "Palmas", false);
            addJugador(context, "Jonathan", 10, "Palmas", false);
            addJugador(context, "Wakaso", 16, "Palmas", false);
            addJugador(context, "Momo", 17, "Palmas", false);
            addJugador(context, "Betancor", 28, "Palmas", false);
            addJugador(context, "Tana", 29, "Palmas", false);

            //Afegir Jugadors Málaga -
            addJugador(context, "Kameni", 00, "Málaga", true);
            addJugador(context, "Albentosa", 13, "Málaga", true);
            addJugador(context, "Filipenko", 12, "Málaga", true);
            addJugador(context, "Cifuentes", 11, "Málaga", true);
            addJugador(context, "Camacho", 21, "Málaga", true);

            addJugador(context, "Tissone", 14, "Málaga", false);
            addJugador(context, "Atsu", 15, "Málaga", false);
            addJugador(context, "Chory", 10, "Málaga", false);
            addJugador(context, "Horta", 16, "Málaga", false);
            addJugador(context, "Kuki", 17, "Málaga", false);
            addJugador(context, "Ikechukwu", 28, "Málaga", false);
            addJugador(context, "Hachim", 29, "Málaga", false);

            //Afegir Jugadors R.Madrid -
            addJugador(context, "K.Navas", 00, "R.Madrid", true);
            addJugador(context, "Arbeloa", 13, "R.Madrid", true);
            addJugador(context, "Carbajal", 12, "R.Madrid", true);
            addJugador(context, "Ramos", 11, "R.Madrid", true);
            addJugador(context, "C.Ronaldo", 21, "R.Madrid", true);

            addJugador(context, "Benzema", 14, "R.Madrid", false);
            addJugador(context, "Bale", 15, "R.Madrid", false);
            addJugador(context, "Modric", 10, "R.Madrid", false);
            addJugador(context, "Odegaard", 16, "R.Madrid", false);
            addJugador(context, "Kovacic", 17, "R.Madrid", false);
            addJugador(context, "Casemiro", 28, "R.Madrid", false);
            addJugador(context, "Llorente", 29, "R.Madrid", false);

            //Afegir Jugadors Sevilla -
            addJugador(context, "Beto", 00, "Sevilla", true);
            addJugador(context, "Andreolli", 13, "Sevilla", true);
            addJugador(context, "Coke", 12, "Sevilla", true);
            addJugador(context, "Gameiro", 11, "Sevilla", true);
            addJugador(context, "Luismi", 21, "Sevilla", true);

            addJugador(context, "Cotán", 14, "Sevilla", false);
            addJugador(context, "Iborra", 15, "Sevilla", false);
            addJugador(context, "Curro", 10, "Sevilla", false);
            addJugador(context, "Konoplyanka", 16, "Sevilla", false);
            addJugador(context, "Lasso", 17, "Sevilla", false);
            addJugador(context, "Pareja", 28, "Sevilla", false);
            addJugador(context, "Escudero", 29, "Sevilla", false);

            //Afegir Jugadors Valencia -
            addJugador(context, "Alves", 00, "Valencia", true);
            addJugador(context, "Gil", 13, "Valencia", true);
            addJugador(context, "Bakkali", 12, "Valencia", true);
            addJugador(context, "Salvador", 11, "Valencia", true);
            addJugador(context, "Rodrigo", 21, "Valencia", true);

            addJugador(context, "Tropi", 14, "Valencia", false);
            addJugador(context, "Danilo", 15, "Valencia", false);
            addJugador(context, "Cheryshev", 10, "Valencia", false);
            addJugador(context, "Parejo", 16, "Valencia", false);
            addJugador(context, "Sito", 17, "Valencia", false);
            addJugador(context, "Siqueira", 28, "Valencia", false);
            addJugador(context, "Mustafi", 29, "Valencia", false);

            //Afegir Jugadors Villareal -
            addJugador(context, "Asenjo", 00, "Villareal", true);
            addJugador(context, "Bailly", 13, "Villareal", true);
            addJugador(context, "Íñiguez", 12, "Villareal", true);
            addJugador(context, "Rukavina", 11, "Villareal", true);
            addJugador(context, "Baptistao", 21, "Villareal", true);

            addJugador(context, "Leiva", 14, "Villareal", false);
            addJugador(context, "Soldado", 15, "Villareal", false);
            addJugador(context, "Dos Santos", 10, "Villareal", false);
            addJugador(context, "Trigueros", 16, "Villareal", false);
            addJugador(context, "Bruno", 17, "Villareal", false);
            addJugador(context, "Castillejo", 28, "Villareal", false);
            addJugador(context, "Denis", 29, "Villareal", false);

            //Jornada d'anada
            addPartit(context, getEquip(context, "Athletic"), getEquip(context, "Atlético"), 2, 1);
            addPartit(context, getEquip(context, "Barcelona"), getEquip(context, "Celta"), 6, 0);
            addPartit(context, getEquip(context, "Palmas"), getEquip(context, "Málaga"), 1, 1);
            addPartit(context, getEquip(context, "R.Madrid"), getEquip(context, "Sevilla"), 3, 2);
            addPartit(context, getEquip(context, "Valencia"), getEquip(context, "Villareal"), 2, 1);

            //Jornada de tornada
            addPartit(context, getEquip(context, "Atlético"), getEquip(context, "Athletic"), 3, 0);
            addPartit(context, getEquip(context, "Celta"), getEquip(context, "Barcelona"), 1, 5);
            addPartit(context, getEquip(context, "Málaga"), getEquip(context, "Palmas"), 2, 1);
            addPartit(context, getEquip(context, "Sevilla"), getEquip(context, "R.Madrid"), 2, 2);
            addPartit(context, getEquip(context, "Villareal"), getEquip(context, "Valencia"), 3, 2);

            //Guardem que s'han posat els valors inicials
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("InitValues", "inserted");
            editor.commit();
            return false;
        } else return true;
    }
}
