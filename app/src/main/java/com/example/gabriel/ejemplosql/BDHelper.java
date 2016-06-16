package com.example.gabriel.ejemplosql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Gabriel on 9/21/2015.
 */
public class BDHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NOMBRE = "MiBD.db";
    public static final String TABLE_NOMBRE = "productos";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_DESCRIPCION = "descripcion";
    public static final String COLUMN_PRECIO = "precio";

    public BDHelper(Context context) {
        super(context, DATABASE_NOMBRE, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NOMBRE + "(" + COLUMN_NOMBRE +
                " text primary key," + COLUMN_DESCRIPCION +
                " text, " + COLUMN_PRECIO + " text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NOMBRE);
        onCreate(db);
    }
    public boolean insertContact(String nombre, String descripcion, String precio){
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put(COLUMN_NOMBRE, nombre);
        registro.put(COLUMN_DESCRIPCION, descripcion);
        registro.put(COLUMN_PRECIO, precio);
        bd.insert(TABLE_NOMBRE, null, registro);
        bd.close();
        return true;
    }

    public boolean updateContact(String oldName, String newName, String descripcion, String precio){
        try {
            SQLiteDatabase bd = this.getWritableDatabase();

            ContentValues registro = new ContentValues();
            registro.put(COLUMN_NOMBRE, newName);
            registro.put(COLUMN_DESCRIPCION, descripcion);
            registro.put(COLUMN_PRECIO, precio);
            bd.update(TABLE_NOMBRE, registro, COLUMN_NOMBRE + "='" + oldName+"'", null);
        bd.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public Integer deleteContact(String nombre){
        SQLiteDatabase bd = this.getWritableDatabase();
        return bd.delete(TABLE_NOMBRE, COLUMN_NOMBRE+"='" + nombre+"'", null);
    }
    //Nos permite movernos en la tabla con los datos obtenidos
    public Cursor getData(String nombre){
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor fila = bd.rawQuery(
                "select * from " + TABLE_NOMBRE + " where " + COLUMN_NOMBRE + "='" + nombre + "'", null);
        return fila;
    }
    public ArrayList<ListData> getListData(){
        String selectQuery = "SELECT  * FROM " + TABLE_NOMBRE;
        SQLiteDatabase db = this.getReadableDatabase()  ;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<ListData> arrayListData = new ArrayList<ListData>();
        if (cursor.moveToFirst()) {
            do {
                ListData list = new ListData();
                list.setNombre(cursor.getString(0));
                list.setDescription(cursor.getString(1));
                list.setPrecio(cursor.getString(2));
                arrayListData.add(list);
            } while (cursor.moveToNext());
        }
        return arrayListData;
    }

}

