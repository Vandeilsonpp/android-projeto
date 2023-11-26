package com.example.ultimavez.persistence.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ultimavez.MyCustomApplication;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.User;
import com.example.ultimavez.model.enums.UserEnum;
import com.example.ultimavez.persistence.UserPersistence;

import java.util.Optional;

public class UserTableHelper extends SQLiteOpenHelper implements UserPersistence {

    private static final String DATABASE_NAME = "Cupcake.db";

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_FULLNAME = "fullname";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_PHONENUMBER = "phonenumber";
    public static final String COLUMN_DOCUMENT = "document";
    public static final String ZIP_CODE = "zipcode";
    public static final String CITY = "city";

    private static final String CREATE_USERS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
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

    public UserTableHelper(Context context) {
        super(context, DATABASE_NAME, null, MyCustomApplication.DATABASE_VERSION);
        //super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(sqLiteDatabase);
    }

    @Override
    public Result<User> saveUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TYPE, user.getType().toString());
        contentValues.put(COLUMN_FULLNAME, user.getFullName());
        contentValues.put(COLUMN_EMAIL, user.getEmail());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        contentValues.put(COLUMN_ADDRESS, user.getAddress());
        contentValues.put(COLUMN_PHONENUMBER, user.getPhoneNumber());
        contentValues.put(COLUMN_DOCUMENT, user.getDocument());
        contentValues.put(ZIP_CODE, user.getZipCode());
        contentValues.put(CITY, user.getCity());

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (dbErrorHasHappened(result)) {
            return Result.invalid("Aconteceu um erro inesperado. Tente novamente mais tarde");
        }
        return Result.valid(user);
    }

    @Override
    public Optional<User> findByEmailAndType(String email, UserEnum userType) {
        SQLiteDatabase db = this.getReadableDatabase();
        onCreate(db);
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_TYPE + " = ?";
        String[] selectionArgs = {email, userType.toString()};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") User user = new User(
                    cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                    UserEnum.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE))),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_FULLNAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DOCUMENT)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PHONENUMBER)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(ZIP_CODE)),
                    cursor.getString(cursor.getColumnIndex(CITY))
            );
            cursor.close();
            return Optional.of(user);
        } else {
            cursor.close();
            return Optional.empty();
        }
    }


    @Override
    public void updatePassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String whereClause = COLUMN_EMAIL + " = ?";
        String[] whereArgs = {email};

        contentValues.put(COLUMN_PASSWORD, newPassword);

        db.update(TABLE_NAME, contentValues, whereClause, whereArgs);
    }

    @Override
    public boolean existsByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT 1 FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        boolean recordExists = cursor.moveToFirst();

        cursor.close();
        db.close();

        return recordExists;
    }


    private boolean dbErrorHasHappened(long dbResult) {
        return dbResult == -1;
    }

}
