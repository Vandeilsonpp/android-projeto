package com.example.ultimavez.persistence.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ultimavez.MyCustomApplication;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Product;
import com.example.ultimavez.model.enums.CategoryEnum;
import com.example.ultimavez.persistence.ProductPersistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductTableHelper extends SQLiteOpenHelper implements ProductPersistence {

    private static final String DATABASE_NAME = "Cupcake.db";

    public static final String TABLE_NAME = "products";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_SELLER = "seller";

    private static final String CREATE_PRODUCTS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_CATEGORY + " TEXT CHECK (" +
                    COLUMN_CATEGORY + " IN ('GOURMET', 'VEGANO', 'PREMIUM', 'ESPECIAIS')), " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_PRICE + " REAL, " +
                    COLUMN_IMAGE + " BLOB, " +
                    COLUMN_SELLER + " INTEGER);";

    public ProductTableHelper(Context context) {
        //super(context, DATABASE_NAME, null, DATABASE_VERSION);
        super(context, DATABASE_NAME, null, MyCustomApplication.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(sqLiteDatabase);
    }

    @Override
    public Result<Product> save(Product product, long sellerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, product.getName());
        cv.put(COLUMN_CATEGORY, product.getCategory().toString());
        cv.put(COLUMN_DESCRIPTION, product.getDescription());
        cv.put(COLUMN_PRICE, product.getPrice());
        cv.put(COLUMN_IMAGE, product.getImage());
        cv.put(COLUMN_SELLER, sellerId);

        long result = db.insert(TABLE_NAME, null, cv);
        if (dbErrorHasHappened(result)) {
            return Result.invalid("Aconteceu um erro inesperado. Tente novamente mais tarde");
        }
        return Result.valid(product);
    }

    @Override
    public boolean existsByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        onCreate(db);

        String query = "SELECT 1 FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = ?";
        String[] selectionArgs = {name};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        boolean recordExists = cursor.moveToFirst();

        cursor.close();
        db.close();

        return recordExists;
    }

    @Override
    public Result<Product> update(Product newProduct) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, newProduct.getName());
        cv.put(COLUMN_CATEGORY, newProduct.getCategory().toString());
        cv.put(COLUMN_DESCRIPTION, newProduct.getDescription());
        cv.put(COLUMN_PRICE, newProduct.getPrice());
        cv.put(COLUMN_IMAGE, newProduct.getImage());

        long result = db.update(TABLE_NAME, cv, "id = ?", new String[] {String.valueOf(newProduct.getId())});

         if (dbErrorHasHappened(result)) {
            return Result.invalid("Aconteceu um erro inesperado. Tente novamente mais tarde");
        }
        return Result.valid(newProduct);
    }

    @Override
    public Result<Product> deleteById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(TABLE_NAME, "id = ?", new String[] {id});

        if (dbErrorHasHappened(result)) {
            return Result.invalid("Aconteceu um erro inesperado. Tente novamente mais tarde");
        }
        return Result.valid(null);
    }

    @Override
    public Optional<List<Product>> findAllProducts(long sellerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_SELLER + " = ?";
        String[] selectionArgs = {String.valueOf(sellerId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        List<Product> products = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Product product = new Product(
                        cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        CategoryEnum.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY))),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE)),
                        cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE)),
                        cursor.getLong(cursor.getColumnIndex(COLUMN_SELLER))
                );

                products.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return products.isEmpty() ? Optional.empty() : Optional.of(products);
    }

    @Override
    public Optional<List<Product>> findProductByCategory(CategoryEnum valueOf) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_CATEGORY + " = ?";
        String[] selectionArgs = {valueOf.name()};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        List<Product> products = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Product product = new Product(
                        cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        CategoryEnum.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY))),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE)),
                        cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE)),
                        cursor.getLong(cursor.getColumnIndex(COLUMN_SELLER))
                );

                products.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return products.isEmpty() ? Optional.empty() : Optional.of(products);
    }

    private boolean dbErrorHasHappened(long dbResult) {
        return dbResult == -1;
    }

}
