package com.example.sql_lite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, "memodb", null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //앱이 설치한 후 SQLiteOpenHelper가 최초로 이용되는 순간 한 번 호출
        //대부분 테이블을 생성하는 코드를 작성
        String memoSQL = "create table tb_memo (" +

                "_id integer primary key autoincrement, " +
                "title, " +
                "content)";
        db.execSQL(memoSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersionon)
            //SQLiteDatabase db ; oldversion의 DB 파일을 연 SQLiteDatabase 객체
            //테이블의 스키마 부분을 변경하기 위한 용도로 사용
    {
        if(newVersionon ==DATABASE_VERSION)
        {
            db.execSQL("drop table tb_memo");
            onCreate(db);
        }
    }
}
