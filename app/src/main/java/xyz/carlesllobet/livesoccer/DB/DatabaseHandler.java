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
 
    // Teams Table Columns names
    private static final String KEY_NOM_EQUIP = "nomEquip";
    private static final String KEY_ESCUT = "escut";
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

    // Matches Table name
    private static final String TABLE_JUGADORS = "jugadors";

    // Matches Table Columns names
    private static final String KEY_NAME = "name";
    private static final String KEY_DORSAL = "dorsal";
    private static final String KEY_EQUIP = "equip";
    private static final String KEY_TITULAR = "titular";
    private static final String KEY_GOL = "gols_lliga";

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
                + KEY_PUNT + " INTEGER,"
                + KEY_GOL + " INTEGER,"
                + KEY_GUANYATS + " INTEGER,"
                + KEY_PERDUTS + " INTEGER,"
                + KEY_EMPATATS + " INTEGER"+ ")";
        String CREATE_JUGADORS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_JUGADORS + "("
                + KEY_NAME + " TEXT NOT NULL,"
                + KEY_DORSAL + " INTEGER NOT NULL,"
                + KEY_EQUIP + " STRING NOT NULL,"
                + KEY_TITULAR + " BOOLEAN NOT NULL,"
                + KEY_GOL + " INTEGER"+ ")";
        String CREATE_PARTITS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PARTITS + "("
                + KEY_LOCAL + " TEXT NOT NULL,"
                + KEY_VISITANT + " TEXT NOT NULL,"
                + KEY_PUNT_LOCAL + " INTEGER,"
                + KEY_PUNT_VISITANT + " INTEGER"+ ")";
        db.execSQL(CREATE_EQUIPS_TABLE);
        db.execSQL(CREATE_JUGADORS_TABLE);
        db.execSQL(CREATE_PARTITS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JUGADORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTITS);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public boolean addTeam(String nombre, Uri escut) {
    	db = this.getWritableDatabase();
        String stringUri = escut.toString();

        //Si existeix, retorna fals, i no es pot afegir
        if (checkExist(nombre)) return false;

        if ((nombre.equals("")) || (stringUri.equals(""))) return false;

        ContentValues values = new ContentValues();
        values.put(KEY_NOM_EQUIP, nombre); // Name
        values.put(KEY_ESCUT, stringUri); // Photo
        values.put(KEY_PUNT, 0); // Puntuation
        values.put(KEY_GOL, 0); //Gols
        values.put(KEY_GUANYATS, 0); //Partits guanyats
        values.put(KEY_PERDUTS, 0); //Partits perduts
        values.put(KEY_EMPATATS, 0); //Partits empatats

        // Inserting Row
        db.insert(TABLE_EQUIPS, null, values);
        return true;
    }

    public boolean addJugador(String nombre, Integer dorsal, String nomEquip, Boolean titular) {
        db = this.getWritableDatabase();

        //Si existeix, retorna fals, i no es pot afegir
        if (checkExist(nombre)) return false;
        if (CheckDorsal(nomEquip, dorsal)) return false;

        if ((nombre.equals("")) || (nomEquip.equals("")) || (dorsal < 0 || dorsal > 99)) return false;

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

    public boolean addPartit(String nomLocal, String nomVisitant, Integer golsLocal, Integer golsVisitant) {
        db = this.getWritableDatabase();

        //Si existeix, retorna fals, i no es pot afegir
        if ((!checkExist(nomLocal)) || !checkExist(nomVisitant)) return false;

        ContentValues values = new ContentValues();
        values.put(KEY_LOCAL, nomLocal); // Nom Local
        values.put(KEY_VISITANT, nomVisitant); // Nom Visitant
        values.put(KEY_PUNT_LOCAL, golsLocal); // Gols
        values.put(KEY_PUNT_VISITANT, golsVisitant); // Gols

        // Inserting Row
        db.insert(TABLE_PARTITS, null, values);
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

    public Boolean CheckDorsal(String equip, Integer dorsal) {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_JUGADORS + " WHERE " + KEY_EQUIP + " = '" + equip + "' AND " + KEY_DORSAL + " = '" + dorsal + "'";

        Cursor c = db.rawQuery(selectQuery, null);
        Boolean b = c.moveToFirst();
        c.close();
        return b;
    }

    public Equip getEquip (String name) {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_EQUIPS + " WHERE " + KEY_NOM_EQUIP + " = '" + name + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            Equip res = new Equip();
            res.setName(cursor.getString(0));
            res.setEscut(Uri.parse(cursor.getString(1)));
            res.setPunt(cursor.getInt(2));
            res.setGols(cursor.getInt(3));
            res.setGuanyats(cursor.getInt(4));
            res.setPerduts(cursor.getInt(5));
            res.setEmpatats(cursor.getInt(6));
            cursor.close();
            return res;
        } else {
            cursor.close();
            return null;
        }
    }

    public Jugador getJugador (String name) {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_JUGADORS + " WHERE " + KEY_NAME + " = '" + name + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            Jugador res = new Jugador();
            res.setName(cursor.getString(0));
            res.setDorsal(cursor.getInt(1));
            res.setEquip(cursor.getString(2));
            Boolean b;
            if (cursor.getInt(3)==1) b = true;
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

    public Jugador getJugador (Integer posicio) {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_JUGADORS + " ORDER BY " + KEY_GOL + " ASC";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToPosition(posicio)) {
            Jugador res = new Jugador();
            res.setName(cursor.getString(0));
            res.setDorsal(cursor.getInt(1));
            res.setEquip(cursor.getString(2));
            Boolean b;
            if (cursor.getInt(3)==1) b = true;
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

    public Equip getEquip (Integer posicio) {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_EQUIPS + " ORDER BY " + KEY_PUNT + " ASC";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToPosition(posicio)) {
            Equip res = new Equip();
            res.setName(cursor.getString(0));
            res.setEscut(Uri.parse(cursor.getString(1)));
            res.setPunt(cursor.getInt(2));
            res.setGols(cursor.getInt(3));
            res.setGuanyats(cursor.getInt(4));
            res.setPerduts(cursor.getInt(5));
            res.setEmpatats(cursor.getInt(6));
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
        String selectQuery = "SELECT  * FROM " + TABLE_EQUIPS + " ORDER BY " + KEY_PUNT + " ASC";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Equip equip = new Equip(cursor.getString(0), Uri.parse(cursor.getString(1)),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6));
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
        String selectQuery = "SELECT  * FROM " + TABLE_JUGADORS + " ORDER BY " + KEY_GOL + " ASC";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Boolean t;
                if (cursor.getInt(3) == 1) t = true;
                else t = false;
                Jugador jugador = new Jugador(cursor.getString(0),cursor.getInt(1),cursor.getString(2),t,cursor.getInt(4));
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
        String selectQuery = "SELECT  * FROM " + TABLE_JUGADORS + " WHERE " + KEY_EQUIP + " = '" + nomEquip + "'" + " ORDER BY " + KEY_GOL + " ASC";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Boolean t;
                if (cursor.getInt(3) == 1) t = true;
                else t = false;
                Jugador jugador = new Jugador(cursor.getString(0),cursor.getInt(1),cursor.getString(2),t,cursor.getInt(4));
                // Adding person to list
                jugadorsList.add(jugador);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return contact list
        return jugadorsList;
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
                Jornada j = new Jornada();
                for (int i = 1; i < 6; ++i) {
                    Partit partit = new Partit(getEquip(cursor.getString(0)),getEquip(cursor.getString(1)),cursor.getInt(2),cursor.getInt(3));
                    j.setPartit(i,partit);
                    if (!cursor.isLast()) cursor.moveToNext();
                }
                partitsList.add(j);
            } while (!cursor.isLast());
        }
        cursor.close();
        // return contact list
        return partitsList;
    }

    /*public String getTuto (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_TUTORIAL};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            return (c.getString(0));
        }
        db.close();
        return "true";
    }

    public String getToast (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_TOAST};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            return (c.getString(0));
        }
        db.close();
        return "true";
    }

    public String getLastNotif (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_LNOTIF};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            return (c.getString(0));
        }
        db.close();
        return "true";
    }

    public String getLang (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_LANG};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            return (c.getString(0));
        }
        db.close();
        return "true";
    }

    public String getName (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_NOM_EQUIPBRE};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            return (c.getString(0));
        }
        db.close();
        return "";
    }

    public String getAddress (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_ADDRESS};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            return (c.getString(0));
        }
        db.close();
        return "";
    }

    public String getPass (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_PASSWORD};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            return (c.getString(0));
        }
        db.close();
        return "";
    }

    public Integer getPunt (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_PUNT};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            return c.getInt(0);
        }
        db.close();
        return 0;
    }

    public Integer getPunt2 (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_PUNT2};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            return c.getInt(0);
        }
        db.close();
        return 0;
    }

    public boolean setNotif (String user, boolean notif) {
        ContentValues values = new ContentValues();
        String valu;
        if (notif) valu = "true";
        else valu = "false";
        values.put(KEY_NOTIF, valu);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(
                TABLE_LOGIN,
                values,
                "userName = '" + user + "'",
                null
        );
        db.close();
        return CheckExist(user);
    }

    public boolean setToast (String user, boolean toast) {
        ContentValues values = new ContentValues();
        String valu;
        if (toast) valu = "true";
        else valu = "false";
        values.put(KEY_TOAST, valu);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(
                TABLE_LOGIN,
                values,
                "userName = '" + user + "'",
                null
        );
        db.close();
        return CheckExist(user);
    }

    public boolean setTuto (String user, boolean tuto) {
        ContentValues values = new ContentValues();
        String valu;
        if (tuto) valu = "true";
        else valu = "false";
        values.put(KEY_TUTORIAL,valu);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(
                TABLE_LOGIN,
                values,
                "userName = '" + user + "'",
                null
        );
        db.close();
        return CheckExist(user);
    }


    public void setPuntuacion (Integer p, String user) {
        ContentValues values = new ContentValues();
        values.put(KEY_PUNT,p);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(
                TABLE_LOGIN,
                values,
                "userName = '" + user + "'",
                null
        );
        db.close();
    }

    public boolean setLang (String user, String lang) {
        ContentValues values = new ContentValues();
        values.put(KEY_LANG,lang);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(
                TABLE_LOGIN,
                values,
                "userName = '" + user + "'",
                null
        );
        db.close();
        return true;
    }

    public void setPuntuacion2 (Integer p, String user) {
        ContentValues values = new ContentValues();
        values.put(KEY_PUNT2, p);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(
                TABLE_LOGIN,
                values,
                "userName = '" + user + "'",
                null
        );
        db.close();
    }

    public boolean setPass (String user, String newP) {
        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD,newP);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(
                TABLE_LOGIN,
                values,
                "userName = '" + user + "'",
                null
        );
        db.close();
        return CheckExist(user);
    }

    public boolean setLastNotif (String user, String lnotif) {
        ContentValues values = new ContentValues();
        values.put(KEY_LNOTIF,lnotif);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(
                TABLE_LOGIN,
                values,
                "userName = '" + user + "'",
                null
        );
        db.close();
        return CheckExist(user);
    }


    public boolean setFoto (String user, Uri path) {
        String stringUri = path.toString();
        ContentValues values = new ContentValues();
        values.put(KEY_FOTO, stringUri);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(
                TABLE_LOGIN,
                values,
                "userName = '" + user + "'",
                null
        );
        db.close();
        return CheckExist(user);
    }


    public Uri getFoto (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_FOTO};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            if (c.getString(0).equals("")) return null;
            else return Uri.parse(c.getString(0));
        }
        db.close();
        return null;
    }*/
     
    /**
     * Re crate database
     * Delete all tables and create them again
     * */
    public void resetTables() {
        db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_JUGADORS, null, null);
        db.delete(TABLE_EQUIPS, null, null);
        db.delete(TABLE_PARTITS, null, null);
        db.close();
    }

    public void closeDB(){
        db.close();
    }
}
