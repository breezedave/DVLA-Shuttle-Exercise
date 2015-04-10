package gov.uk.dvla.dsd.shuttle.resources;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLite extends SQLiteOpenHelper {

        public static final String PARAM_TABLE_NAME = "params";
        public static final String SHUTTLE_TABLE_NAME = "shuttle";
        public static final String COLUMN_ID = "_id";
        public static final String PARAM_NAME = "param_name";
        public static final String PARAM_VAL = "value";
        public static final String SHUTTLE_VRM = "vrm";
        public static final String SHUTTLE_LOC = "location";
        public static final String SHUTTLE_TIME = "loc_time";

        private static final String DATABASE_NAME = "shuttle.db";
        private static final int DATABASE_VERSION = 1;

        // Database creation sql statement
        private static final String CREATE_PARAMS = "create table "
                + PARAM_TABLE_NAME + "("
                + COLUMN_ID + " integer primary key autoincrement, "
                + PARAM_NAME + " text null, "
                + PARAM_VAL + " text null"
                + ");";
        private static final String CREATE_SHUTTLE = "create table "
            + SHUTTLE_TABLE_NAME + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + SHUTTLE_VRM + " text null, "
            + SHUTTLE_LOC + " text null,"
            + SHUTTLE_TIME + " text null"
            + ");";


        public MySQLite(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(CREATE_PARAMS);
            database.execSQL(CREATE_SHUTTLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(MySQLite.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + PARAM_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + SHUTTLE_TABLE_NAME);
            onCreate(db);
        }

}

