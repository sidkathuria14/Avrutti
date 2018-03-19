package com.example.sidkathuria14.inventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sidkathuria14.inventory.models.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sidkathuria14 on 9/3/18.
 */

public class DataBaseHandler extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME= "ItemsManager";
    public static final String DB_TABLE = "items";
public static final String TAG = "inventory";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name" ;
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_QUANTITY = "quantity";
    public static final String KEY_IMAGEPATH = "imagepath";
    public DataBaseHandler(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + DB_TABLE + "( " + KEY_ID +  " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT," + KEY_DESCRIPTION + " TEXT," +  KEY_QUANTITY + " INTEGER," + KEY_IMAGEPATH + " TEXT"
                + " ) ";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void addItem(Item item){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,item.getName());
        values.put(KEY_DESCRIPTION,item.getDescription());
        values.put(KEY_QUANTITY,item.getQuantity());
        values.put(KEY_IMAGEPATH,item.getImagePath());
        db.insert(DB_TABLE,null,values);
        db.close();

    }
    public Item getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DB_TABLE, new String[]{
                KEY_ID, KEY_NAME, KEY_DESCRIPTION , KEY_QUANTITY,KEY_IMAGEPATH
        }, KEY_ID + " =? ", new String[]{
                String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
//        Item item = new Item(Integer.parseInt(cursor.getString(0))
//                , cursor.getString(1)
//                , cursor.getString(2));
        Item item = new Item(
                Integer.parseInt(cursor.getString(0)),
//                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                Integer.parseInt(cursor.getString(3)),
        cursor.getString(4));
//                cursor.getInt(3));
        return item;
    }

    public List<Item> getAllItems(){
        List<Item> contactList = new ArrayList<Item>();
        String selectQuery = "SELECT * FROM " + DB_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
                item.setDescription(cursor.getString(2));
                item.setQuantity(Integer.parseInt(cursor.getString(3)));
                item.setImagePath(cursor.getString(4));

            } while(cursor.moveToNext());
        }
        return contactList;

    }
    public int getItemsCount(){

        String countQuery= "SELECT  * FROM " + DB_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    public int updateItem(Item item){
        Log.d(TAG, "updateItem: ");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,item.getName());
        values.put(KEY_DESCRIPTION,item.getDescription());
        values.put(KEY_QUANTITY,item.getQuantity());
        values.put(KEY_IMAGEPATH,item.getImagePath());
        return  db.update(DB_TABLE,values,KEY_ID + " =? ",new String[]{
                String.valueOf(item.getId())
        });
    }

    public void deleteItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE,KEY_ID + " =? ",new String[]{
                String.valueOf(item.getId())
        });

        db.close();
    }

}
