package com.example.ultimavez.persistence.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ultimavez.MyCustomApplication;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Cupom;
import com.example.ultimavez.persistence.CupomPersistence;

import java.util.Optional;

public class CupomTableHelper extends SQLiteOpenHelper implements CupomPersistence {

    private static final String DATABASE_NAME = "Cupcake.db";

    public static final String TABLE_NAME = "cupom";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CODIGO = "codigo";
    public static final String COLUMN_VALIDO = "valido";
    public static final String COLUMN_VALOR = "valor";


    private static final String CREATE_CUPOM_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CODIGO + " TEXT NOT NULL, " +
                    COLUMN_VALIDO + " TEXT NOT NULL, " +
                    COLUMN_VALOR + " TEXT NOT NULL " +
                    ");";

    public CupomTableHelper(Context context) {
        super(context, DATABASE_NAME, null, MyCustomApplication.DATABASE_VERSION);
        //super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CUPOM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(sqLiteDatabase);
    }

    private boolean dbErrorHasHappened(long dbResult) {
        return dbResult == -1;
    }

    @Override
    public Result<Cupom> saveCupom(Cupom cupom) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_CODIGO, cupom.getCodigo());
        contentValues.put(COLUMN_VALIDO, cupom.eValido());
        contentValues.put(COLUMN_VALOR, String.valueOf(cupom.getValorDoDesconto()));

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (dbErrorHasHappened(result)) {
            return Result.invalid("Aconteceu um erro inesperado. Tente novamente mais tarde");
        }
        return Result.valid(cupom);
    }

    @Override
    public Optional<Cupom> findByCodigo(String codigo) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_CODIGO + " = ?" + " AND " + COLUMN_VALIDO + " = ?";
        String[] selectionArgs = {codigo, "1"};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") Cupom cupom = new Cupom(
                 cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                 cursor.getString(cursor.getColumnIndex(COLUMN_CODIGO)),
                 cursor.getInt(cursor.getColumnIndex(COLUMN_VALIDO)) == 1,
                 cursor.getDouble(cursor.getColumnIndex(COLUMN_VALOR))
            );

            cursor.close();
            return Optional.of(cupom);
        } else {
            cursor.close();
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByCodigo(String codigo) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT 1 FROM " + TABLE_NAME + " WHERE " + COLUMN_CODIGO + " = ?";
        String[] selectionArgs = {codigo};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        boolean recordExists = cursor.moveToFirst();

        cursor.close();
        db.close();

        return recordExists;
    }
}
