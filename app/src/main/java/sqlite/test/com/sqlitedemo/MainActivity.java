package sqlite.test.com.sqlitedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import sqlite.test.com.sqlitedemo.bean.Person;
import sqlite.test.com.sqlitedemo.util.Constant;
import sqlite.test.com.sqlitedemo.util.DBManager;
import sqlite.test.com.sqlitedemo.util.MySqliteHelper;

/**
 * 数据库的基本操作
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MySqliteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = DBManager.getInstance(this);

        findViewById(R.id.createDB).setOnClickListener(this);
        findViewById(R.id.raw_query).setOnClickListener(this);
        findViewById(R.id.sql_api).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.createDB:
                createDB();
                break;

            case R.id.raw_query:
                rawQuey();
                break;

            case R.id.sql_api:
                apiQuery();
                break;

            default:
                break;

        }
    }

    private void apiQuery() {
        SQLiteDatabase db = helper.getWritableDatabase();

        /**
         1  （设置为true,每一行的数据必须唯一。反之亦然。）
         2. （query函数要操作的表名。）
         3. （要返回的列的名字的数组。如果设置为null，返回所有列，如果不需要使用所有列，不建议这么做。）
         4. （一个决定返回哪一行的过滤器，相当于SQL语句中的 WHERE 关键字。传递null则会返回给定表的所有行。）
         5. （用于替换上一个参数中的 ？ ,顺序对应selection中？的顺序。格式限制为String格式。）
         6. （用于设定返回行的分组方式，相当于SQL语句中的GROUP BY 关键字。传递null表示返回的行不会被分组。）
         7. （决定哪一行被放到Cursor中的过滤器。如果使用了行分组，相当于SQL语句中的HAVING关键字。传递null会导致所有的行都包含在内，前提是groupBy属性也设置为null。）
         8. （行的排列方式，相当于SQL语句中的“ORDER BY”关键字，传递null表示使用默认的排序方式，可能是无序排列。）
         9. （设置query语句返回行的数量，相当于SQL语句中的“LIMIT”关键字，传递null表示没有设置limit语句。注意格式为String,传递的时候需要传递数字字符串，例如“12”）
         10.（取消程序操作的信号，如果没有则设置为null。如果操作取消了，query语句运行时会抛出OperationCanceledException异常。）

         含有7个参数的query函数不包含1，9，10，也就是distinct，limit，cancellationSignal。
         含有8个参数的query函数不包含1，10，也就是distinct，cancellationSignal。
         含有9个参数的query函数不包含10，也就是cancellationSignal。
         */

        /**
         * select _id,name,age from person where _id>10 group by x having x order by x
         * query(String table,String[] columns,String selection,
         * String[] selectionArgs,String groupBy,String having ,String orderBy)
         *
         * String table 表名
         * String[] columns  查询的字段名称，null 表示查询索引
         * String selection  查询条件 where 语句
         * String[] selectionArgs 查询条件占位符
         * String groupBy 没有null
         * String having
         * String orderBy 排序
         */
        Cursor cursor = db.query(Constant.TABLE_NAME,null,Constant._ID + ">=?",
                new String[] {"15"},null,null,Constant._ID + " desc");

        List<Person> list = DBManager.cursorToLis(cursor);
        for(Person person : list){
            Log.d("TTTT","person22 = " + person.toString());
        }

        db.close();

    }


    private void rawQuey() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql  = "select * from " + Constant.TABLE_NAME;
        Cursor cur = DBManager.selectBySql(db,sql,null);

        List<Person> list = DBManager.cursorToLis(cur);
        for(Person person : list){
            Log.d("TTTT","person = " + person.toString());
        }

        db.close();
    }

    private void createDB() {
        SQLiteDatabase db = helper.getWritableDatabase();
        for(int a = 0; a< 30; a++){
            String sql = "insert into " + Constant.TABLE_NAME + " values(" + a + ",'zhangsan "+a+"',20)";
            db.execSQL(sql);
        }

        db.close();
    }
}
