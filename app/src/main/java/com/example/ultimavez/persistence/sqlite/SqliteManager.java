package com.example.ultimavez.persistence.sqlite;

import static com.example.ultimavez.persistence.sqlite.CupomTableHelper.COLUMN_CODIGO;
import static com.example.ultimavez.persistence.sqlite.CupomTableHelper.COLUMN_VALIDO;
import static com.example.ultimavez.persistence.sqlite.CupomTableHelper.COLUMN_VALOR;
import static com.example.ultimavez.persistence.sqlite.PedidoTableHelper.COLUMN_CRIADO_EM;
import static com.example.ultimavez.persistence.sqlite.PedidoTableHelper.COLUMN_CUPOM;
import static com.example.ultimavez.persistence.sqlite.PedidoTableHelper.COLUMN_DESCONTO;
import static com.example.ultimavez.persistence.sqlite.PedidoTableHelper.COLUMN_ID_COMPRADOR;
import static com.example.ultimavez.persistence.sqlite.PedidoTableHelper.COLUMN_STATUS;
import static com.example.ultimavez.persistence.sqlite.PedidoTableHelper.COLUMN_TIPO_PAGAMENTO;
import static com.example.ultimavez.persistence.sqlite.PedidoTableHelper.COLUMN_VALOR_FINAL;
import static com.example.ultimavez.persistence.sqlite.PedidoTableHelper.COLUMN_VALOR_ORIGINAL;
import static com.example.ultimavez.persistence.sqlite.ProductTableHelper.COLUMN_CATEGORY;
import static com.example.ultimavez.persistence.sqlite.ProductTableHelper.COLUMN_DESCRIPTION;
import static com.example.ultimavez.persistence.sqlite.ProductTableHelper.COLUMN_IMAGE;
import static com.example.ultimavez.persistence.sqlite.ProductTableHelper.COLUMN_NAME;
import static com.example.ultimavez.persistence.sqlite.ProductTableHelper.COLUMN_PRICE;
import static com.example.ultimavez.persistence.sqlite.ProductTableHelper.COLUMN_SELLER;
import static com.example.ultimavez.persistence.sqlite.UserTableHelper.CITY;
import static com.example.ultimavez.persistence.sqlite.UserTableHelper.COLUMN_ADDRESS;
import static com.example.ultimavez.persistence.sqlite.UserTableHelper.COLUMN_DOCUMENT;
import static com.example.ultimavez.persistence.sqlite.UserTableHelper.COLUMN_EMAIL;
import static com.example.ultimavez.persistence.sqlite.UserTableHelper.COLUMN_FULLNAME;
import static com.example.ultimavez.persistence.sqlite.UserTableHelper.COLUMN_ID;
import static com.example.ultimavez.persistence.sqlite.UserTableHelper.COLUMN_PASSWORD;
import static com.example.ultimavez.persistence.sqlite.UserTableHelper.COLUMN_PHONENUMBER;
import static com.example.ultimavez.persistence.sqlite.UserTableHelper.COLUMN_TYPE;
import static com.example.ultimavez.persistence.sqlite.UserTableHelper.ZIP_CODE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Cupcake.db";
    public static final int DATABASE_VERSION = 20;

    private static final String CREATE_USERS_TABLE =
            "CREATE TABLE " + "users" + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TYPE + " TEXT CHECK(" + COLUMN_TYPE + " IN ('CUSTOMER', 'SELLER')) NOT NULL, " +
                    COLUMN_FULLNAME + " TEXT NOT NULL, " +
                    COLUMN_EMAIL + " TEXT NOT NULL, " +
                    COLUMN_PASSWORD + " TEXT NOT NULL, " +
                    COLUMN_ADDRESS + " TEXT NOT NULL, " +
                    COLUMN_PHONENUMBER + " TEXT NOT NULL, " +
                    COLUMN_DOCUMENT + " TEXT NOT NULL, " +
                    ZIP_CODE + " TEXT NOT NULL, " +
                    CITY + " TEXT NOT NULL" +
                    ");";

    private static final String CREATE_PEDIDOS_TABLE =
            "CREATE TABLE " + "pedidos" + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ID_COMPRADOR + " INTEGER NOT NULL, " +
                    COLUMN_STATUS + " TEXT NOT NULL, " +
                    COLUMN_VALOR_ORIGINAL + " REAL NOT NULL, " +
                    COLUMN_DESCONTO + " REAL NOT NULL, " +
                    COLUMN_VALOR_FINAL + " REAL NOT NULL, " +
                    COLUMN_CRIADO_EM + " TEXT NOT NULL, " +
                    COLUMN_CUPOM + " TEXT, " +
                    COLUMN_TIPO_PAGAMENTO + " TEXT NOT NULL " +
                    ");";

    private static final String CREATE_PRODUCTS_TABLE =
            "CREATE TABLE " + "products" + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_CATEGORY + " TEXT CHECK (" +
                    COLUMN_CATEGORY + " IN ('GOURMET', 'VEGANO', 'PREMIUM', 'ESPECIAIS')), " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_PRICE + " REAL, " +
                    COLUMN_IMAGE + " BLOB, " +
                    COLUMN_SELLER + " INTEGER);";

    private static final String CREATE_CUPOM_TABLE =
            "CREATE TABLE " + "cupom" + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CODIGO + " TEXT NOT NULL, " +
                    COLUMN_VALIDO + " TEXT NOT NULL, " +
                    COLUMN_VALOR + " TEXT NOT NULL " +
                    ");";

    public SqliteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(CREATE_USERS_TABLE);
        //db.execSQL(CREATE_PEDIDOS_TABLE);
        //db.execSQL(CREATE_PRODUCTS_TABLE);
        //db.execSQL(CREATE_CUPOM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + "users" + ";");
        //db.execSQL("DROP TABLE IF EXISTS " + "pedidos" + ";");
        //db.execSQL("DROP TABLE IF EXISTS " + "products" + ";");
        //db.execSQL("DROP TABLE IF EXISTS " + "cupom" + ";");
        //onCreate(db);
    }
}
