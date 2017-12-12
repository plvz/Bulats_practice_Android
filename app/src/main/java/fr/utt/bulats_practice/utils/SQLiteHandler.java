package fr.utt.bulats_practice.utils;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import fr.utt.bulats_practice.R;

/**
 * Created by kevin on 12/12/2017.
 */

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // table name
    private static final String TABLE_SENTENCE = "sentence";
    private static final String TABLE_ANSWER = "user";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_SENTENCE_TYPE = "type";
    private static final String KEY_SENTENCE_POSITION = "position";
    private static final String KEY_SENTENCE_PARAGRAPH = "paragraph";
    private static final String KEY_SENTENCE_TEXT = "sentence";

    private static final String KEY_SENTENCE_ID = "fk_sentence";
    private static final String KEY_ANSWER_POSITION = "answer";
    private static final String KEY_ANSWER_PARAGRAPH = "valid";
    private SQLiteDatabase db;

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        String CREATE_SENTENCE_TABLE = "CREATE TABLE " + TABLE_SENTENCE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_SENTENCE_TYPE + " TEXT,"
                + KEY_SENTENCE_POSITION + " INTEGER," + KEY_SENTENCE_PARAGRAPH + " TEXT,"
                + KEY_SENTENCE_TEXT + " TEXT" + ")";
        db.execSQL(CREATE_SENTENCE_TABLE);

        String CREATE_ANSWER_TABLE = "CREATE TABLE " + TABLE_ANSWER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_SENTENCE_ID + " INTEGER ,"
                + KEY_ANSWER_POSITION + " TEXT UNIQUE," + KEY_ANSWER_PARAGRAPH + " TEXT ) ";
        db.execSQL(CREATE_ANSWER_TABLE);

        Log.d(TAG, "Database tables created");

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SENTENCE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWER);

        // Create tables again
        onCreate(db);
    }

    public void insertCSVData(InputStream is, String tableName) {

        String colunmNames = null, str1 = null;
        open(db);

        try {

            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
            String line = "";

            String str2 = ");";

            db.beginTransaction();

            int i = 0;
            while ((line = buffer.readLine()) != null) {
                i++;
                if (i == 1) {
                    colunmNames = line;
                    str1 = "INSERT INTO " + tableName + " (" + colunmNames + ") values (";
                } else {

                    StringBuilder sb = new StringBuilder(str1);
                    String[] str = line.split(",");

                    for (int h = 0; h < str.length; h++) {

                        if (h == str.length - 1) {
                            sb.append("'" + str[h] + "'");
                        } else {
                            sb.append("'" + str[h] + "',");
                        }
                    }

                    sb.append(str2);
                    db.execSQL(sb.toString());
                }
            }

            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (Exception e) {

            close(db);

            e.printStackTrace();
        }

        close(db);
    }

    public void open(SQLiteDatabase db) {
        db = this.getWritableDatabase();
    }

    public void close(SQLiteDatabase db) {
        db.close();
    }
}
