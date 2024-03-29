package xyz.carlesllobet.livesoccer.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import xyz.carlesllobet.livesoccer.Domain.Objects.Equip;
import xyz.carlesllobet.livesoccer.Domain.Objects.Jornada;
import xyz.carlesllobet.livesoccer.Domain.Objects.Jugador;
import xyz.carlesllobet.livesoccer.Domain.Objects.Partit;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "db";

    // Teams Table name
    private static final String TABLE_EQUIPS = "equips";
    private static final String TABLE_EQUIPS_ELIMINATS = "equips_eliminats";

    // Teams Table Columns names
    private static final String KEY_NOM_EQUIP = "nomEquip";
    private static final String KEY_ESCUT = "escut";
    private static final String KEY_CIUTAT = "ciutat";
    private static final String KEY_PUNT = "punts_lliga";
    private static final String KEY_GUANYATS = "guanyats";
    private static final String KEY_PERDUTS = "perduts";
    private static final String KEY_EMPATATS = "empatats";

    // Players Table name
    private static final String TABLE_PARTITS = "partits";

    // Players Table Columns names
    private static final String KEY_LOCAL = "nomLocal";
    private static final String KEY_VISITANT = "nomVisitant";
    private static final String KEY_PUNT_LOCAL = "golsLocal";
    private static final String KEY_PUNT_VISITANT = "golsVisitant";
    private static final String KEY_GOLEJADORS = "golejadors";

    // Matches Table name
    private static final String TABLE_JUGADORS = "jugadors";
    private static final String TABLE_GOLEJADORS = "golejadors";

    // Matches Table Columns names
    private static final String KEY_NAME = "name";
    private static final String KEY_DORSAL = "dorsal";
    private static final String KEY_EQUIP = "equip";
    private static final String KEY_TITULAR = "titular";
    private static final String KEY_GOL = "gols_lliga";

    // Matches Table name


    private SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EQUIPS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EQUIPS + "("
                + KEY_NOM_EQUIP + " TEXT NOT NULL,"
                + KEY_ESCUT + " STRING,"
                + KEY_CIUTAT + " STRING,"
                + KEY_PUNT + " INTEGER,"
                + KEY_GOL + " INTEGER,"
                + KEY_GUANYATS + " INTEGER,"
                + KEY_PERDUTS + " INTEGER,"
                + KEY_EMPATATS + " INTEGER" + ")";
        String CREATE_JUGADORS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_JUGADORS + "("
                + KEY_NAME + " TEXT NOT NULL,"
                + KEY_DORSAL + " INTEGER NOT NULL,"
                + KEY_EQUIP + " STRING NOT NULL,"
                + KEY_TITULAR + " BOOLEAN NOT NULL,"
                + KEY_GOL + " INTEGER" + ")";
        String CREATE_PARTITS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PARTITS + "("
                + KEY_LOCAL + " TEXT NOT NULL,"
                + KEY_VISITANT + " TEXT NOT NULL,"
                + KEY_PUNT_LOCAL + " INTEGER,"
                + KEY_PUNT_VISITANT + " INTEGER,"
                + KEY_GOLEJADORS + " STRING NOT NULL" + ")";
        String CREATE_GOLEJADORS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GOLEJADORS + "("
                + KEY_NAME + " TEXT NOT NULL,"
                + KEY_DORSAL + " INTEGER NOT NULL,"
                + KEY_EQUIP + " STRING NOT NULL" + ")";
        String CREATE_EQUIPS_ELIMINATS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EQUIPS_ELIMINATS + "("
                + KEY_NOM_EQUIP + " TEXT NOT NULL,"
                + KEY_ESCUT + " STRING" + ")";
        db.execSQL(CREATE_EQUIPS_TABLE);
        db.execSQL(CREATE_JUGADORS_TABLE);
        db.execSQL(CREATE_PARTITS_TABLE);
        db.execSQL(CREATE_GOLEJADORS_TABLE);
        db.execSQL(CREATE_EQUIPS_ELIMINATS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JUGADORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTITS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOLEJADORS);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     */
    public boolean addTeam(String nombre, Uri escut, String ciutat) {
        db = this.getWritableDatabase();
        String stringUri = escut.toString();

        //Si existeix, retorna fals, i no es pot afegir
        if (checkExist(nombre)) return false;

        if ((nombre.equals("")) || (stringUri.equals(""))) return false;

        ContentValues values = new ContentValues();
        values.put(KEY_NOM_EQUIP, nombre); // Name
        values.put(KEY_ESCUT, stringUri); // Photo
        values.put(KEY_CIUTAT, ciutat); // Photo
        values.put(KEY_PUNT, 0); // Puntuation
        values.put(KEY_GOL, 0); //Gols
        values.put(KEY_GUANYATS, 0); //Partits guanyats
        values.put(KEY_PERDUTS, 0); //Partits perduts
        values.put(KEY_EMPATATS, 0); //Partits empatats

        // Inserting Row
        db.insert(TABLE_EQUIPS, null, values);
        return true;
    }

    public boolean addEquipEliminat(String nombre, Uri escut) {
        db = this.getWritableDatabase();
        String stringUri = escut.toString();

        ContentValues values = new ContentValues();
        values.put(KEY_NOM_EQUIP, nombre); // Name
        values.put(KEY_ESCUT, stringUri); // Photo

        // Inserting Row
        db.insert(TABLE_EQUIPS_ELIMINATS, null, values);
        return true;
    }

    public boolean addJugador(String nombre, Integer dorsal, String nomEquip, Boolean titular) {
        db = this.getWritableDatabase();

        //Si existeix, retorna fals, i no es pot afegir
        if (checkExist(nombre)) return false;
        if (checkDorsal(nomEquip, dorsal)) return false;

        if ((nombre.equals("")) || (nomEquip.equals("")) || (dorsal < 0 || dorsal > 99))
            return false;

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, nombre); // Name
        values.put(KEY_DORSAL, dorsal); // Dorsal
        values.put(KEY_EQUIP, nomEquip); // Equip
        values.put(KEY_TITULAR, titular);
        values.put(KEY_GOL, 0); // Gols

        // Inserting Row
        db.insert(TABLE_JUGADORS, null, values);
        return true;
    }

    public boolean addJugadorEliminat(String nombre, Integer dorsal, String nomEquip) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, nombre); // Name
        values.put(KEY_DORSAL, dorsal); // Dorsal
        values.put(KEY_EQUIP, nomEquip); // Equip

        // Inserting Row
        db.insert(TABLE_GOLEJADORS, null, values);
        return true;
    }

    public boolean addPartit(String nomLocal, String nomVisitant, ArrayList<Jugador> golejadors) {
        db = this.getWritableDatabase();
        Integer golsLocal = 0;
        Integer golsVisitant = 0;

        //Si existeix, retorna fals, i no es pot afegir
        if ((!checkExist(nomLocal)) || !checkExist(nomVisitant)) return false;
        String csv = "";
        if (golejadors.size() > 0) {
            csv = golejadors.get(0).getName();
            addGolJugador(golejadors.get(0).getName());
            addGolEquip(golejadors.get(0).getEquip());
            if (golejadors.get(0).getEquip().equals(nomLocal)) ++golsLocal;
            else if (golejadors.get(0).getEquip().equals(nomVisitant)) ++golsVisitant;
        }
        for (int i = 1; i < golejadors.size(); i++) {
            csv += ",";
            csv += golejadors.get(i).getName();
            addGolJugador(golejadors.get(i).getName());
            addGolEquip(golejadors.get(i).getEquip());
            if (golejadors.get(i).getEquip().equals(nomLocal)) ++golsLocal;
            else if (golejadors.get(i).getEquip().equals(nomVisitant)) ++golsVisitant;
        }

        ContentValues values = new ContentValues();
        values.put(KEY_LOCAL, nomLocal); // Nom Local
        values.put(KEY_VISITANT, nomVisitant); // Nom Visitant
        values.put(KEY_PUNT_LOCAL, golsLocal); // Gols
        values.put(KEY_PUNT_VISITANT, golsVisitant); // Gols
        values.put(KEY_GOLEJADORS, csv);

        //Afegir guanyat/perdut/empatat i puntsLliga als dos equips
        if (golsLocal > golsVisitant) {
            addPuntsEquip(nomLocal, 3);
            addGpeEquip(nomLocal, "Guanyat");
            addGpeEquip(nomVisitant, "Perdut");
        } else if (golsLocal < golsVisitant) {
            addPuntsEquip(nomVisitant, 3);
            addGpeEquip(nomLocal, "Perdut");
            addGpeEquip(nomVisitant, "Guanyat");
        } else {
            addPuntsEquip(nomLocal, 1);
            addPuntsEquip(nomVisitant, 1);
            addGpeEquip(nomLocal, "Empatat");
            addGpeEquip(nomVisitant, "Empatat");
        }

        // Inserting Row
        db.insert(TABLE_PARTITS, null, values);
        return true;
    }

    public Boolean addGolEquip(String name) {
        db = this.getWritableDatabase();
        String updateQuery = "UPDATE " + TABLE_EQUIPS + " SET " + KEY_GOL + " = " + KEY_GOL + "+1 WHERE " + KEY_NOM_EQUIP + " = '" + name + "'";

        db.execSQL(updateQuery);
        return true;
    }

    public Boolean addGpeEquip(String name, String resultat) {
        db = this.getWritableDatabase();
        String updateQuery = "SELECT * FROM " + TABLE_EQUIPS;
        switch (resultat) {
            case "Guanyat":
                updateQuery = "UPDATE " + TABLE_EQUIPS + " SET " + KEY_GUANYATS + " = " + KEY_GUANYATS + "+1 WHERE " + KEY_NOM_EQUIP + " = '" + name + "'";
                break;
            case "Perdut":
                updateQuery = "UPDATE " + TABLE_EQUIPS + " SET " + KEY_PERDUTS + " = " + KEY_PERDUTS + "+1 WHERE " + KEY_NOM_EQUIP + " = '" + name + "'";
                break;
            case "Empatat":
                updateQuery = "UPDATE " + TABLE_EQUIPS + " SET " + KEY_EMPATATS + " = " + KEY_EMPATATS + "+1 WHERE " + KEY_NOM_EQUIP + " = '" + name + "'";
                break;
        }

        db.execSQL(updateQuery);
        return true;
    }

    public Boolean addPuntsEquip(String name, Integer punts) {
        db = this.getWritableDatabase();
        String updateQuery = "UPDATE " + TABLE_EQUIPS + " SET " + KEY_PUNT + " = " + KEY_PUNT + "+" + punts + " WHERE " + KEY_NOM_EQUIP + " = '" + name + "'";

        db.execSQL(updateQuery);
        return true;
    }

    public Boolean addGolJugador(String name) {
        db = this.getWritableDatabase();
        String updateQuery = "UPDATE " + TABLE_JUGADORS + " SET " + KEY_GOL + " = " + KEY_GOL + "+1 WHERE " + KEY_NAME + " = '" + name + "'";

        db.execSQL(updateQuery);
        return true;
    }

    public Boolean checkExist(String nom) {
        db = this.getReadableDatabase();
        String[] columns = {KEY_NOM_EQUIP};
        String[] where = {nom};
        Cursor c = db.query(
                TABLE_EQUIPS,
                columns,
                KEY_NOM_EQUIP + "=?",
                where,
                null,
                null,
                null
        );
        Boolean b = c.moveToFirst();
        c.close();
        return b;
    }

    public Boolean checkExistJug(String nom) {
        db = this.getReadableDatabase();
        String[] columns = {KEY_NAME};
        String[] where = {nom};
        Cursor c = db.query(
                TABLE_JUGADORS,
                columns,
                KEY_NAME + "=?",
                where,
                null,
                null,
                null
        );
        Boolean b = c.moveToFirst();
        c.close();
        return b;
    }

    public Boolean checkDorsal(String equip, Integer dorsal) {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_JUGADORS + " WHERE " + KEY_EQUIP + " = '" + equip + "' AND " + KEY_DORSAL + " = '" + dorsal + "'";

        Cursor c = db.rawQuery(selectQuery, null);
        Boolean b = c.moveToFirst();
        c.close();
        return b;
    }

    public Equip getEquip(String name) {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_EQUIPS + " WHERE " + KEY_NOM_EQUIP + " = '" + name + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            Equip res = new Equip();
            res.setName(cursor.getString(0));
            res.setEscut(Uri.parse(cursor.getString(1)));
            res.setCiutat(cursor.getString(2));
            res.setPunt(cursor.getInt(3));
            res.setGols(cursor.getInt(4));
            res.setGuanyats(cursor.getInt(5));
            res.setPerduts(cursor.getInt(6));
            res.setEmpatats(cursor.getInt(7));
            cursor.close();
            return res;
        } else {
            cursor.close();
            Equip ee = getEquipEliminat(name);
            if (ee!=null) return ee;
            else return null;
        }
    }

    public Equip getEquipEliminat(String name) {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_EQUIPS_ELIMINATS + " WHERE " + KEY_NOM_EQUIP + " = '" + name + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            Equip res = new Equip();
            res.setName(cursor.getString(0));
            res.setEscut(Uri.parse(cursor.getString(1)));
            cursor.close();
            return res;
        } else {
            cursor.close();
            return null;
        }
    }

    public Boolean deleteEquip(String name) {
        Equip aux = getEquip(name);
        addEquipEliminat(aux.getName(),aux.getEscut());
        db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_EQUIPS + " WHERE " + KEY_NOM_EQUIP + " = '" + name + "'";
        db.execSQL(deleteQuery);

        ArrayList<Jugador> plantilla = getJugadors(name);
        for (int i = 0; i < plantilla.size(); i++){
            deleteJugador(plantilla.get(i).getName());
        }
        return true;
    }

    public Boolean deleteJugador(String name) {
        Jugador j = getJugador(name);
        addJugadorEliminat(j.getName(),j.getDorsal(),j.getEquip());
        db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_JUGADORS + " WHERE " + KEY_NAME + " = '" + name + "'";
        db.execSQL(deleteQuery);
        return true;
    }

    public Integer getPosicioEquip(String name) {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_EQUIPS + " ORDER BY " + KEY_PUNT + " DESC";

        Cursor cursor = db.rawQuery(selectQuery, null);
        Integer res = 1;
        Boolean trobat = false;
        if (cursor.moveToFirst()) {
            if (cursor.getString(0).equals(name)) return 0;
            else {
                while (cursor.moveToNext() && !trobat) {
                    if (!cursor.getString(0).equals(name)) res += 1;
                    else trobat = true;
                }
                cursor.close();
                Log.d("Posicio", res.toString());
                return res;
            }
        } else {
            cursor.close();
            return null;
        }
    }

    public Jugador getJugador(String name) {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_JUGADORS + " WHERE " + KEY_NAME + " = '" + name + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.getPosition();
        if (cursor.moveToFirst()) {
            Jugador res = new Jugador();
            res.setName(cursor.getString(0));
            res.setDorsal(cursor.getInt(1));
            res.setEquip(cursor.getString(2));
            Boolean b;
            if (cursor.getInt(3) == 1) b = true;
            else b = false;
            res.setTitular(b);
            res.setGols(cursor.getInt(4));
            cursor.close();
            return res;
        } else {
            cursor.close();
            return null;
        }
    }

    public Jugador getJugadorEliminat(String name) {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GOLEJADORS + " WHERE " + KEY_NAME + " = '" + name + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.getPosition();
        if (cursor.moveToFirst()) {
            Jugador res = new Jugador();
            res.setName(cursor.getString(0));
            res.setDorsal(cursor.getInt(1));
            res.setEquip(cursor.getString(2));
            cursor.close();
            return res;
        } else {
            cursor.close();
            return null;
        }
    }

    public Jugador getJugador(Integer posicio) {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_JUGADORS + " ORDER BY " + KEY_GOL + " DESC";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToPosition(posicio)) {
            Jugador res = new Jugador();
            res.setName(cursor.getString(0));
            res.setDorsal(cursor.getInt(1));
            res.setEquip(cursor.getString(2));
            Boolean b;
            if (cursor.getInt(3) == 1) b = true;
            else b = false;
            res.setTitular(b);
            res.setGols(cursor.getInt(4));
            cursor.close();
            return res;
        } else {
            cursor.close();
            return null;
        }
    }

    public Equip getEquip(Integer posicio) {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_EQUIPS + " ORDER BY " + KEY_PUNT + " DESC";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToPosition(posicio)) {
            Equip res = new Equip();
            res.setName(cursor.getString(0));
            res.setEscut(Uri.parse(cursor.getString(1)));
            res.setCiutat(cursor.getString(2));
            res.setPunt(cursor.getInt(3));
            res.setGols(cursor.getInt(4));
            res.setGuanyats(cursor.getInt(5));
            res.setPerduts(cursor.getInt(6));
            res.setEmpatats(cursor.getInt(7));
            cursor.close();
            return res;
        } else {
            cursor.close();
            return null;
        }
    }

    public ArrayList<Equip> getEquips() {
        ArrayList<Equip> equipsList = new ArrayList<Equip>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EQUIPS + " ORDER BY " + KEY_PUNT + " DESC";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Equip equip = new Equip(cursor.getString(0), Uri.parse(cursor.getString(1)), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getInt(7));
                // Adding person to list
                equipsList.add(equip);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return contact list
        return equipsList;
    }

    public ArrayList<Jugador> getJugadors() {
        ArrayList<Jugador> jugadorsList = new ArrayList<Jugador>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_JUGADORS + " ORDER BY " + KEY_GOL + " DESC";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Boolean t;
                if (cursor.getInt(3) == 1) t = true;
                else t = false;
                Jugador jugador = new Jugador(cursor.getString(0), cursor.getInt(1), cursor.getString(2), t, cursor.getInt(4));
                // Adding person to list
                jugadorsList.add(jugador);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return contact list
        return jugadorsList;
    }

    public ArrayList<Jugador> getJugadors(String nomEquip) {
        ArrayList<Jugador> jugadorsList = new ArrayList<Jugador>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_JUGADORS + " WHERE " + KEY_EQUIP + " = '" + nomEquip + "'" + " ORDER BY " + KEY_GOL + " DESC";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Boolean t;
                if (cursor.getInt(3) == 1) t = true;
                else t = false;
                Jugador jugador = new Jugador(cursor.getString(0), cursor.getInt(1), cursor.getString(2), t, cursor.getInt(4));
                // Adding person to list
                jugadorsList.add(jugador);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return contact list
        return jugadorsList;
    }

    public Jornada getJornada(Integer posicio) {
        Jornada jornada = new Jornada();
        String selectQuery = "SELECT  * FROM " + TABLE_PARTITS;
        Integer posicioBona=posicio*5;

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToPosition(posicioBona)) {
            for (int i = 1; i < 6; i++) {
                ArrayList<Jugador> golejadors = new ArrayList<Jugador>();
                String[] csv = (cursor.getString(4)).split(",");
                for (int j = 0; j < csv.length; j++) {
                    if (getJugador(csv[j])==null){
                        golejadors.add(getJugadorEliminat(csv[j]));
                    }
                    else golejadors.add(getJugador(csv[j]));
                }
                Equip local = getEquip(cursor.getString(0));
                Equip visitant = getEquip(cursor.getString(1));
                if (local == null) local = getEquipEliminat(cursor.getString(0));
                if (visitant == null) visitant = getEquipEliminat(cursor.getString(1));
                Partit res = new Partit(local,visitant, cursor.getInt(2), cursor.getInt(3), golejadors);
                jornada.setPartit(i, res);
                if (!cursor.isLast()) cursor.moveToNext();
            }
            cursor.close();
            return jornada;
        } else {
            cursor.close();
            return null;
        }
    }

    public ArrayList<Jornada> getPartits() {
        ArrayList<Jornada> partitsList = new ArrayList<Jornada>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PARTITS;

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Jornada jornada = new Jornada();
                for (int i = 1; i < 6; ++i) {
                    ArrayList<Jugador> golejadors = new ArrayList<Jugador>();
                    String[] csv = (cursor.getString(4)).split(",");
                    for (int j = 0; j < csv.length; j++) {
                        golejadors.add(getJugador(csv[j]));
                    }
                    Equip local = getEquip(cursor.getString(0));
                    Equip visitant = getEquip(cursor.getString(1));
                    if (local == null) local = getEquipEliminat(cursor.getString(0));
                    if (visitant == null) visitant = getEquipEliminat(cursor.getString(1));
                    Partit partit = new Partit(local,visitant, cursor.getInt(2), cursor.getInt(3), golejadors);
                    jornada.setPartit(i, partit);
                    if (!cursor.isLast()) cursor.moveToNext();
                }
                partitsList.add(jornada);
            } while (!cursor.isLast());
        }
        cursor.close();
        // return contact list
        return partitsList;
    }

    /**
     * Re crate database
     * Delete all tables and create them again
     */
    public void resetTables() {
        db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_JUGADORS, null, null);
        db.delete(TABLE_EQUIPS, null, null);
        db.delete(TABLE_PARTITS, null, null);
        db.close();
    }

    public void closeDB() {
        db.close();
    }
}
