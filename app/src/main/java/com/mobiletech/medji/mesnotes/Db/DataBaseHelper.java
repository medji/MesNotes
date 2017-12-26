package com.mobiletech.medji.mesnotes.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.ViewGroup;

import com.mobiletech.medji.mesnotes.Model.Matiere;

/**
 * Created by root on 19/12/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MesNotes.db";
    public static final String TABLE_NAME = "matiere_table";
    public static final String COL_1 = "id";
    public static final String COL_2 = "libelle";
    public static final String COL_3 = "note1";
    public static final String COL_4 = "note2";
    public static final String COL_5 = "notefinale";


    public DataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, libelle TEXT, note1 DECIMAL(2,2), note2 DECIMAL(2,2), notefinale DECIMAL(2,2))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME + " ORDER BY libelle ASC", null);
        return res;
    }

    public boolean insertData(Matiere m)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, m.getLibelle());
        contentValues.put(COL_3, m.getNote1());
        contentValues.put(COL_4, m.getNote2());
        contentValues.put(COL_5, m.getNotefinale());

        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();

        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean deletData(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
        return true;
    }

    public boolean updateData(Matiere m)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, m.getLibelle());
        contentValues.put(COL_3, m.getNote1());
        contentValues.put(COL_4, m.getNote2());
        contentValues.put(COL_5, m.getNotefinale());
        db.update(TABLE_NAME, contentValues, "id=?", new String[]{String.valueOf(m.getId())});
        return true;
    }
}
