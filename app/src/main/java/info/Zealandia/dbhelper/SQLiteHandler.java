/**
 * Author: Yar Htut
 * URL: www.yar.cloudns.org
 * linkedIn: http://linkedin.com/yarunity3d
 * */
package info.Zealandia.dbhelper;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import info.Zealandia.SanctuaryActivity;

public class SQLiteHandler extends SQLiteOpenHelper {

	private static final String TAG = SQLiteHandler.class.getSimpleName();

	// All Static variables
	// Database Version

    SQLiteDatabase db;
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "android_api";

	// Login table name
	private static final String TABLE_LOGIN = "login";

    //Activty table name
    private static final String TABLE_ACTIVITY = "activity_table";

	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_UID = "uid";
	private static final String KEY_CREATED_AT = "created_at";

    //Activity Table Columns names

    private  static final String KEY_CATID = "catId";

	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
				+ KEY_CREATED_AT + " TEXT" + ")";
		db.execSQL(CREATE_LOGIN_TABLE);

		Log.d(TAG, "Database tables created");

        //Activities Tables Create

        String CREATE_ACTIVITY_TABLE = "CREATE TABLE " + TABLE_ACTIVITY +
                "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CATID + " INTEGER UNIQUE," +
                " CLICKED INTEGER " +
                ")";


        db.execSQL(CREATE_ACTIVITY_TABLE);



    }


    //insect value to the Activity Table.
    public  void insectCategoriesId (int _catId){

        SQLiteDatabase db = this.getWritableDatabase();
        //Create row's data

        ContentValues values = new ContentValues();
        values.put(KEY_CATID, _catId);
        values.put("CLICKED" , "1");

        try{
            // Inserting Row
            int id = (int) db.insertWithOnConflict(TABLE_ACTIVITY, null, values,SQLiteDatabase.CONFLICT_IGNORE);

            if (id == -1){
                Log.d(TAG, "Database tables Updated");
                String UPDATE = "UPDATE " + TABLE_ACTIVITY + " SET CLICKED = CLICKED + 1 WHERE " + KEY_CATID + " = " + _catId;


                db.execSQL(UPDATE);
            }
        }

        catch(SQLiteConstraintException ex){
            //If already exist get current value
            //db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY);
            //db.update(TABLE_ACTIVITY, null, values);



        }
        db.close(); // Closing database connection


        //Log.d(TAG, "New Categories ID inserted into SQLite: " + id);
    }

    public String getUpdateClicked(int catId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        String _catId = "";
        try{
            String query = "SELECT CLICKED FROM "+ TABLE_ACTIVITY + " WHERE "+ KEY_CATID + " = " + catId;
            cursor = db.rawQuery(query, null);



            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                _catId = cursor.getString(cursor.getColumnIndex("CLICKED"));
            }

        }finally {
            //
        }
        cursor.close();
        return _catId;
    }
   /**
            * Getting user login status return true if rows are there in table
    * */
    public int getCategoriesID(int id) {
        String countQuery = "SELECT  * FROM " + TABLE_ACTIVITY + " WHERE id = "+ id ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }


	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */
	public void addUser(String name, String email, String uid, String created_at) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name); // Name
		values.put(KEY_EMAIL, email); // Email
		values.put(KEY_UID, uid); // Id
		values.put(KEY_CREATED_AT, created_at); // Created At

		// Inserting Row
		long id = db.insert(TABLE_LOGIN, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, "New user inserted into SQLite: " + id);
	}

	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();

		String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("name", cursor.getString(1));
			user.put("email", cursor.getString(2));
			user.put("uid", cursor.getString(3));
			user.put("created_at", cursor.getString(4));
		}
		cursor.close();
		db.close();
		// return user
		Log.d(TAG, "Fetching user from SQLite: " + user.toString());

		return user;
	}
    /**
     * Getting user data from database
     * */
    public String getUserDetailsAsJson() {
        String selectQuery = "SELECT * FROM login";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        JSONArray resultUser     = new JSONArray();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        if( cursor.getString(i) != null )
                        {
                            Log.d("TAG_NAME", cursor.getString(i) );
                            rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                        }
                        else
                        {
                            rowObject.put( cursor.getColumnName(i) ,  "" );
                        }
                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            resultUser.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("TAG_NAME", resultUser.toString());
        return resultUser.toString();



}
	/**
	 * Getting user login status return true if rows are there in table
	 * */
	public int getRowCount() {
		String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();

		// return row count
		return rowCount;
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_LOGIN, null, null);
		db.close();

		Log.d(TAG, "Deleted all user info from SQLite");
	}

    /**     *
     * Compose JSON out of SQLITE records     *
     */
    public String composeJSONfromSQLite(){


        HashMap<String, String> catList = new HashMap<String, String>();
        ArrayList theList = new ArrayList();

        String selectQuery = "SELECT * FROM activity_table";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        cursor.moveToFirst();

        cursor.moveToFirst();

       return null;

    }
    /**
     * parse Sqlite to JSON Array Object
     * */
    public String getResults()
    {

        String selectQuery = "SELECT * FROM activity_table";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        JSONArray resultSet     = new JSONArray();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        if( cursor.getString(i) != null )
                        {
                            Log.d("TAG_NAME", cursor.getString(i) );
                            rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                        }
                        else
                        {
                            rowObject.put( cursor.getColumnName(i) ,  "" );
                        }
                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("TAG_NAME", resultSet.toString());
        return resultSet.toString();
    }

}


/*

  if (cursor.getCount() == 0) {

        }



            int totalColumn = cursor.getColumnCount();
            Log.d(TAG, "totalColumn" +totalColumn);
            for (int i = 0; i < totalColumn; i++) {
                while(cursor.moveToNext()) {
                    Log.d(TAG, "KEY_ID " + cursor.getString(0));
                    catList.put(KEY_ID, cursor.getString(0));
                    catList.put(KEY_CATID, cursor.getString(1));
                    catList.put("CLICKED", cursor.getString(2));

            }

            theList.add(catList);
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching user from catList: " + catList.toString());
        Log.d(TAG, "Fetching user from theList: " + theList.toString());

        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(catList);
 */