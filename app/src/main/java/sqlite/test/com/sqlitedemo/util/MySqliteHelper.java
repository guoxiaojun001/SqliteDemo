package sqlite.test.com.sqlitedemo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HP on 2017/9/20.
 */

public class MySqliteHelper extends SQLiteOpenHelper {

    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySqliteHelper(Context context){
        super(context,Constant.DB_NAME,null,Constant.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "create table " + Constant.TABLE_NAME +
                " (" + Constant._ID + " Integer primary key , " +
                Constant.NAME + "  varchar(10), " +
                Constant.AGE + "  Integer )";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
