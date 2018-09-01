package com.example.minhnguyen.tripappuser.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.minhnguyen.tripappuser.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME = "TripDB.db";
    private static final int DB_VER = 1;
    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Order> getCards()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"PlaceName","PlaceID","Address"};
        String sqlTable="PlaceDetail";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null, null, null, null, null);

        final List<Order> result = new ArrayList<>();
        if (c.moveToFirst())
        {
            do{
                result.add(new Order(c.getString(c.getColumnIndex("PlaceID")),
                        c.getString(c.getColumnIndex("PlaceName")),
                        c.getString(c.getColumnIndex("Address"))
                        ));
            }while(c.moveToNext());
        }
        return result;
    }

    public void addToCard (Order order)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO PlaceDetail(PlaceID, PlaceName, Address) VALUES ('%s','%s','%s');",
                order.getPlaceID(),
                order.getPlaceName(),
                order.getAddress());
        db.execSQL(query);
    }

    public void cleanCard ()
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM PlaceDetail");
        db.execSQL(query);
    }
}
