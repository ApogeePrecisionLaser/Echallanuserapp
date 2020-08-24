package com.echallanuser.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.echallanuser.Bean.Bean;

import java.util.ArrayList;
import java.util.Iterator;
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
    public long inserttp_id(List<Bean> list) {
        long result = 0;
        try {
            Iterator<Bean> iterator = list.iterator();
            database.beginTransaction();
            while (iterator.hasNext() && result != -1) {
                ContentValues values = new ContentValues();
                Bean bleBean = iterator.next();
                values.put("trafficpoliceid",bleBean.getTpid());

                result = database.insert("checksumdata", null, values);
            }
            if (result > 0) {
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {
            Log.e(TAG, "insertNodeDetail error: " + e);
        } finally {
            database.endTransaction();
        }
        return result;
    }
    public ArrayList<String> getid() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Cursor cursor = database.rawQuery("SELECT trafficpoliceid FROM checksumdata", null);
            cursor.moveToPosition(0);
            int a= cursor.getCount();
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                list.add(cursor.getString(0));
                // list.add(surveyBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "getItemData error: " + e);
        }
        return list;
    }
}

