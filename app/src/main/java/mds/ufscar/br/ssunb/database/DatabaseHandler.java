package mds.ufscar.br.ssunb.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "ssunb";
    private static final int DB_VERSION = 1;

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table book(code integer not null primary key, title text not null, " +
                "author text not null, category text, synopsis text, publication date, " +
                "edition integer, publisher text, pages integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists book");
        onCreate(db);
    }
}
