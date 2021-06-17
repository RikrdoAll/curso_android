package co.tiagoaguiar.codelab.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.tv.TvContract;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SqlHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "fitness_tracker.db";
    private static final int DB_VERSION = 1;

    private static SqlHelper INSTANCE;

    static SqlHelper getInstance(Context context) {
        if (INSTANCE==null)
            INSTANCE = new SqlHelper(context);

        return INSTANCE;
    }

    private SqlHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE calc         " +
                "(                            " +
                "   id INTEGER primary key,   " +
                "   type_calc TEXT,           " +
                "   res DECIMAL,              " +
                "   created_date DATETIME     " +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    long addItem(String type, double response) {
        long calcId = 0;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("pt", "BR"));
            String now = sdf.format(new Date());

            ContentValues values = new ContentValues();
            values.put("type_calc", type);
            values.put("res", response);
            values.put("created_date", now);
            calcId = db.insertOrThrow("calc", null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("SQLite", e.getMessage(), e);
            calcId = e.hashCode();
        } finally {
            if (db.isOpen()) {
                if (db.inTransaction())
                    db.endTransaction();
//                db.close();
            }
        }
        return calcId;
    }

    List<Register> getRegisterBy(String type) {
        List<Register> registers = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("" +
                "SELECT *             " +
                "  FROM calc          " +
                " WHERE type_calc = ? ",
                new String[]{type});
        try {

//            if (cursor.moveToFirst()){
//                do {
//                    Register register = new Register();
//                    registers.add(register);
//
//                    register.type = cursor.getString(cursor.getColumnIndex("type_calc"));
//                    register.response = cursor.getDouble(cursor.getColumnIndex("res"));
//                    register.createdDate = cursor.getString(cursor.getColumnIndex("created_date"));
//                } while (cursor.moveToNext());
//
//            }

            // verificar se cursor esta posicionado no primeiro registro
            while (cursor.moveToNext()) {
                Register register = new Register();
                registers.add(register);

                register.type = cursor.getString(cursor.getColumnIndex("type_calc"));
                register.response = cursor.getDouble(cursor.getColumnIndex("res"));
                register.createdDate = cursor.getString(cursor.getColumnIndex("created_date"));
            }

        } catch (Exception e){
            Log.e("SQLite", e.getMessage() +" - Causa: "+ e.getCause(), e);
        } finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }


        return registers;
    }


}
