package com.example.sergey.test_junior.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String sDbName = "db";
    private final String mTable = "BUTTON";
    private final String mKeyId = "id";
    private final String mListMain = "listMain";
    private final String mListSettings = "listSettings";
    private static final int DATABASE_VERSION = 1;

    public interface DatabaseHand<T> {
        void onComplete(boolean success, T result);
    }

    /**
     * AsyncTask  load data from database
     * @param <T> return type param
     */

    private abstract class DatabaseAsyncTask<T> extends AsyncTask<Void, Void, T> {
        private DatabaseHand<T> handler;
        private RuntimeException error;

        public DatabaseAsyncTask(DatabaseHand<T> handler) {
            this.handler = handler;
        }

        @Override
        protected T doInBackground(Void... params) {
            try {
                return executeMethod();
            } catch (RuntimeException error) {
                this.error = error;
                return null;
            }
        }

        protected abstract T executeMethod();

        @Override
        protected void onPostExecute(T result) {
            handler.onComplete(error == null, result);
        }
    }

    public DataBaseHelper(Context context) {
        super(context, sDbName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_APPLICATIONS_TABLE = "CREATE TABLE " + mTable + "(" +
                mKeyId + " INTEGER PRIMARY KEY AUTOINCREMENT," + mListMain + " TEXT," +
                mListSettings + " TEXT" + ")";
        db.execSQL(CREATE_APPLICATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + mTable);
        onCreate(db);
    }

    /**
     * add record in database
     * @param listMainSerialize serialize mainLList
     */

    public void addMainList(String listMainSerialize) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(mListMain, listMainSerialize);
        db.insert(mTable, null, values);
        db.close();
    }

    /**
     *  async add record in database
     * @param listMain serialize mainLList
     * @param handler return state task
     */
    public void addMainListAsync(final String listMain, DatabaseHand<Void> handler) {
        new DatabaseAsyncTask<Void>(handler) {
            @Override
            protected Void executeMethod() {
                addMainList(listMain);
                return null;
            }
        }.execute();
    }

    /**
     * get record listMain in database
     * @return serialize main list
     */
    public String getMainList() {
        String temp = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + mTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            temp = (cursor.getString(cursor.getColumnIndex(mListMain)));
        }
        cursor.close();
        return temp;
    }

    /**
     * async get record in database
     * @param handler return state task
     */
    public void getMainListAsync(DatabaseHand<String> handler) {
        new DatabaseAsyncTask<String>(handler) {

            @Override
            protected String executeMethod() {
                return getMainList();
            }
        }.execute();
    }

    /**
     * get record settingsList in database
     * @return serialize settings list
     */
    public String getSettingsList() {
        String temp = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + mTable;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            temp = (cursor.getString(cursor.getColumnIndex(mListSettings)));
        }
        cursor.close();
        return temp;
    }

    /**
     * async get record settingsList in database
     * @param handler return state task
     */
    public void getSettingsListAsync(DatabaseHand<String> handler) {
        new DatabaseAsyncTask<String>(handler) {

            @Override
            protected String executeMethod() {
                return getSettingsList();
            }
        }.execute();
    }

    /**
     * update column listMain in database
     * @param list serialize MainList
     */
    public void updateMainList(String list) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(mListMain, list);
        db.update(mTable, values, mKeyId + " =?", new String[]{String.valueOf(1)});
    }

    /**
     * async update record in database
     * @param listMain serialize MainList
     * @param handler return state task
     */
    public void updateMainListAsync(final String listMain, DatabaseHand<Void> handler) {
        new DatabaseAsyncTask<Void>(handler) {
            @Override
            protected Void executeMethod() {
                updateMainList(listMain);
                return null;
            }
        }.execute();
    }

    /**
     * update column listSettings in database
     * @param listSet serialize SettingsList
     */
    public void updateSettingsList(String listSet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(mListSettings, listSet);
        db.update(mTable, values, mKeyId + " =?", new String[]{String.valueOf(1)});
    }

    /**
     * async update column listSettings in database
     * @param listSettings update column listSettings in database
     * @param handler return state task
     */
    public void updateSettingsListAsync(final String listSettings, DatabaseHand<Void> handler) {
        new DatabaseAsyncTask<Void>(handler) {
            @Override
            protected Void executeMethod() {
                updateSettingsList(listSettings);
                return null;
            }
        }.execute();
    }

    /**
     * check column settingList is null
     * @return true if column not null else return false
     */
    public boolean checkParameters() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + mTable + " WHERE " +
                mListSettings + " IS NOT NULL";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToNext()) {
            if (cursor.getCount() > 0) {
                return true;
            }
        }
        return false;
    }
}
