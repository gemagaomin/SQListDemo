package com.gm.soft.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class DBUtil {
    private static DBUtil dbUtil;
    public SQLiteDatabase db;
    private Map<String,String> tableNameMap=new HashMap();
    private Map<String,String> hadTableNameMap=new HashMap();
    private DBUtil() {
        if( db==null){
            String path= Environment.getExternalStorageDirectory().getPath()+"/JZGK/database";
            File file=new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
            db=SQLiteDatabase.openOrCreateDatabase(path+"/jzgk.db",null);
            HaveData();
        }
    }

    public void setTableSql(){
        tableNameMap.put("traintype","CREATE TABLE traintype(" +
                "traintypeid char(3) PRIMARY KEY," +
                "traintypename varchar(16) NOT NULL )");
    }


    public static DBUtil getInstance(){
        if(dbUtil==null){
            synchronized (DBUtil.class){
                if(dbUtil==null){
                    dbUtil=new DBUtil();
                }
            }
        }
        return dbUtil;
    };
    public boolean  delete(String table, String whereClause, String[] whereArgs){
        int i=db.delete(table,whereClause,whereArgs);
        if(i>0){
            return true;
        }
        return false;
    }

    public void   deleteAll(String table){
        db.execSQL("delete from "+table);
    }
    public boolean insert(String table, String nullColumnHack, ContentValues values){
        long ret=db.insert(table,nullColumnHack,values);
        if(ret!=-1){
            return true;
        }
        return false;
    }

    public boolean update(String table, ContentValues values, String whereClause, String[] whereArgs){
        int i=db.update(table,values,whereClause,whereArgs);
        if(i>0){
            return true;
        }
        return false;
    }

    public Cursor select(String sql, String[] selectionArgs){
        return db.rawQuery(sql,selectionArgs);
    }

    public Cursor selectALL(String tableName){
        return db.rawQuery("select * from "+tableName,null);
    }

    public  void HaveData(){
        Cursor cursor;
       // db.execSQL("create table examine(`userId` varchar(50)  NOT NULL )");
        cursor = db.rawQuery("select name from sqlite_master where type='table' ", null);
        cursor.moveToFirst();
        while(cursor.moveToNext()){
            //遍历出表名
            String name = cursor.getString(0);
            hadTableNameMap.put(name,name);
        }
    }

    public void createTable(){
        if(tableNameMap!=null){
            if(tableNameMap.size()==0){
                setTableSql();
            }
            for ( Map.Entry<String, String> o:tableNameMap.entrySet()
                 ) {
                if(TextUtils.isEmpty(hadTableNameMap.get(o.getKey()))){
                    db.execSQL(o.getValue());
                }
            }
        }
    }
}
