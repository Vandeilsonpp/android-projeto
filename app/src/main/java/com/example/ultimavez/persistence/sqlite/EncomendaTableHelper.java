package com.example.ultimavez.persistence.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ultimavez.MyCustomApplication;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Encomenda;
import com.example.ultimavez.persistence.EncomendaPersistence;

public class EncomendaTableHelper extends SQLiteOpenHelper implements EncomendaPersistence {

    private static final String DATABASE_NAME = "Cupcake.db";
    public static final String TABLE_NAME = "encomendas";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ID_COMPRADOR = "idComprador";
    public static final String COLUMN_VALOR_ORIGINAL = "valorOriginal";
    public static final String COLUMN_DESCONTO = "desconto";
    public static final String COLUMN_VALOR_FINAL = "valorFinal";
    public static final String COLUMN_CRIADO_EM = "criadoEm";
    public static final String COLUMN_QUANTIDADE = "quantidade";

    private static final String CREATE_ENCOMENDAS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ID_COMPRADOR + " INTEGER NOT NULL, " +
                    COLUMN_VALOR_ORIGINAL + " REAL NOT NULL, " +
                    COLUMN_DESCONTO + " REAL NOT NULL, " +
                    COLUMN_VALOR_FINAL + " REAL NOT NULL, " +
                    COLUMN_CRIADO_EM + " TEXT NOT NULL, " +
                    COLUMN_QUANTIDADE + " INTEGER NOT NULL " +
                    ");";

    public EncomendaTableHelper(Context context) {
        //super(context, DATABASE_NAME, null, DATABASE_VERSION);
        super(context, DATABASE_NAME, null, MyCustomApplication.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENCOMENDAS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(db);
    }

    @Override
    public Result<Encomenda> saveEncomenda(Encomenda encomenda) {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ID_COMPRADOR, encomenda.getConsumerId());
        contentValues.put(COLUMN_VALOR_ORIGINAL, encomenda.getValor());
        contentValues.put(COLUMN_DESCONTO, encomenda.getDesconto());
        contentValues.put(COLUMN_VALOR_FINAL, encomenda.getValorFinal());
        contentValues.put(COLUMN_CRIADO_EM, encomenda.getCriadoEm().toString());
        contentValues.put(COLUMN_QUANTIDADE, encomenda.getQuantidade());

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (dbErrorHasHappened(result)) {
            return Result.invalid("Aconteceu um erro inesperado. Tente novamente mais tarde");
        }
        return Result.valid(encomenda);
    }

    private boolean dbErrorHasHappened(long dbResult) {
        return dbResult == -1;
    }
}
