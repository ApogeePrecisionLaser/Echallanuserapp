package com.echallanuser.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by Somya .
 */
public class DatabaseOperation
{
    private static final String TAG = "DatabaseOperation.java";
    public static List offence_type_id_check = new ArrayList();
    private DatabaseWrapper dbHelper;
    private SQLiteDatabase database;
    Context context;
    public DatabaseOperation(Context context)
    {
        dbHelper = new DatabaseWrapper(context);
        context=context;
    }



    public void open()
    {
        // to get a db in writable mode
        try
        {
            database = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            Log.d(TAG, "open db: " + e);
        }
    }

    public void close()
    {
        dbHelper.close();
    }
    public void datadelete()
    {
        try {
            database.delete("offence", null, null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}

