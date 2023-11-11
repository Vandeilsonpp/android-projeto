package com.example.ultimavez.persistence.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.ultimavez.MyCustomApplication;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Pedido;
import com.example.ultimavez.persistence.PedidoPersistence;

public class PedidoTableHelper extends SQLiteOpenHelper implements PedidoPersistence {

    private static final String DATABASE_NAME = "Cupcake.db";
    public static final String TABLE_NAME = "pedidos";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ID_COMPRADOR = "idComprador";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_VALOR_ORIGINAL = "valorOriginal";
    public static final String COLUMN_DESCONTO = "desconto";
    public static final String COLUMN_VALOR_FINAL = "valorFinal";
    public static final String COLUMN_CRIADO_EM = "criadoEm";
    public static final String COLUMN_CUPOM = "cupom";
    public static final String COLUMN_TIPO_PAGAMENTO = "tipoPagamento";

    private static final String CREATE_PEDIDOS_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
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

    public PedidoTableHelper(Context context) {
        //super(context, DATABASE_NAME, null, DATABASE_VERSION);
        super(context, DATABASE_NAME, null, MyCustomApplication.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PEDIDOS_TABLE);
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
    public Result<Pedido> savePedido(Pedido pedido) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ID_COMPRADOR, pedido.getIdComprador());
        contentValues.put(COLUMN_STATUS, pedido.getStatus().name());
        contentValues.put(COLUMN_VALOR_ORIGINAL, pedido.getValorOriginal());
        contentValues.put(COLUMN_DESCONTO, pedido.getDesconto());
        contentValues.put(COLUMN_VALOR_FINAL, pedido.getValorFinal());
        contentValues.put(COLUMN_CRIADO_EM, pedido.getCriadoEm().toString());
        contentValues.put(COLUMN_CUPOM, pedido.getCodigoCupom());
        contentValues.put(COLUMN_TIPO_PAGAMENTO, pedido.getTipoPaagamento().name());

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (dbErrorHasHappened(result)) {
            return Result.invalid("Aconteceu um erro inesperado. Tente novamente mais tarde");
        }
        return Result.valid(pedido);
    }
}
