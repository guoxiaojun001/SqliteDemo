package sqlite.test.com.sqlitedemo.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import sqlite.test.com.sqlitedemo.bean.Person;

/**
 * Created by HP on 2017/9/20.
 */

public class DBManager {

    private static MySqliteHelper mySqliteHelper;

    public static MySqliteHelper getInstance(Context context){

        if(null == mySqliteHelper){
            mySqliteHelper = new MySqliteHelper(context);
        }

        return mySqliteHelper;
    }


    /**
     *
     * @param db 数据库对象
     * @param sql sql语句
     * @param selectionArgs 查询条件
     * @return 查询结果
     */
    public static Cursor selectBySql(SQLiteDatabase db,String sql,String[] selectionArgs ){
        Cursor cursor = null;
        if(null !=  db){
            cursor = db.rawQuery(sql,selectionArgs);
        }

        return cursor;
    }


    /**
     *  将查询的cursor 转换成list集合
     * @param cursor 游标对象
     * @return 集合
     */
    public static List<Person> cursorToLis (Cursor cursor ){
        /**
         * 使用rawQuery()
         当然如果想使用原生的SQL语句，可以用rawQuery（）
         Cursor rawQuery (String sql, String[] selectionArgs)
         或者
         Cursor rawQuery (String sql, String[] selectionArgs, CancellationSignal cancellationSignal)
         里面的参数和query()中的参数是一致的。
         */
        List<Person> list = new ArrayList<>();
        while (cursor.moveToNext()){
            int columeIndex = cursor.getColumnIndex(Constant._ID);//获取索引值
            int _id = cursor.getInt(columeIndex);
            String name = cursor.getString(cursor.getColumnIndex(Constant.NAME));
            int age = cursor.getInt(cursor.getColumnIndex(Constant.AGE));
            Person person = new Person(_id,name,age);
            list.add(person);
        }


        return list;

    }
}
