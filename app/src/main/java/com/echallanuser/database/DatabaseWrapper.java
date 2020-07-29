package com.echallanuser.database;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

/**
 * Created by
 *
 */
public class DatabaseWrapper extends SQLiteOpenHelper
{

    private static final String TAG = "DatabaseWrapper.java";
    private static final String DATABASE_NAME = "e_challanuser.db";
    private static final int DATABASE_VERSION = 2;
    private static final SQLiteDatabase.CursorFactory CURSOR_FACTORY = null;

    Context context;
    private static final String checksumdataquery = " CREATE TABLE checksumdata ( "
            + "  challanno INTEGER unsigned NOT NULL, "
            + "  oderid TEXT NOT NULL,"
            + "  custid TEXT NOT NULL,"
            + "  remark TEXT DEFAULT NULL,"
            + "  timestamp TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP "
            + ");";

    private static final String Offencedataquery = " CREATE TABLE Offencedata ( "
                + "  offenceid INTEGER PRIMARY KEY NOT NULL, "
                + "  offendername TEXT NOT NULL,"
                + "  location TEXT NOT NULL,"
                + "  vehicleno TEXT NOT NULL,"
                + "  vehicletype TEXT NOT NULL,"
                + "  dateofoffence TEXT NOT NULL,"
                + "  act TEXT NOT NULL,"
                + "  offencetype INTEGER NOT NULL,"
                + "  amount TEXT DEFAULT NULL,"
                + "  timestamp TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP); ";




    public DatabaseWrapper(Context context)
    {
               super(context, DATABASE_NAME, CURSOR_FACTORY, DATABASE_VERSION);
                this.context = context;
                // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
                boolean result = false;
                try
                {
                        File dbFile = new ContextWrapper(context).getDatabasePath(DATABASE_NAME);
                        String path=dbFile.getAbsolutePath();
                        Log.d("Info", "db path : " + dbFile.getAbsolutePath());
                        try
                        {
//                                db.execSQL(prescriptionQuery);
                            db.execSQL(checksumdataquery);
                            db.execSQL(Offencedataquery);


                                result = true;
                        } catch (Exception e)
                        {
                                result = false;
                        }
                        if(result)
                        {
                                result = true;
                        }
                } catch (Exception e)
                {
                        Log.e(TAG, "personQuery execution error "+e);
                        result = false;
                }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
                // TODO Auto-generated method stub
//                db.execSQL("DROP TABLE IF EXISTS prescriptionQuery");
                db.execSQL("DROP TABLE IF EXISTS checksumdata");
                db.execSQL("DROP TABLE IF EXISTS Offencedata");
               onCreate(db);
        }
}
