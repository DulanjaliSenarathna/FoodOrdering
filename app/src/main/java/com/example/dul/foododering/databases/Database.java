package com.example.dul.foododering.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.dul.foododering.Model.Favourites;
import com.example.dul.foododering.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
        private static final String DB_NAME = "mydb.db";
        private static final int DB_VER = 2;




        public Database(Context context) {

            super(context, DB_NAME, null, DB_VER);
        }

        public boolean checkFoodExists(String foodId,String userPhone)
        {
            Boolean flag = false;
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = null;

            String SQLQuery = String.format("SELECT * From OrderDetail WHERE UserPhone='%s' AND ProductId='%s'",userPhone,foodId);
            cursor = db.rawQuery(SQLQuery,null);
            if(cursor.getCount()>0)
                flag = true;
            else
                flag = false;
            cursor.close();
            return flag;

        }

        public List<Order> getCarts(String userPhone) {
            SQLiteDatabase db = getReadableDatabase();
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

            String[] sqlSelect = {"UserPhone", "ProductId", "ProductName", "Quantity", "Price", "Discount", "Image"};
            String sqlTable = "OrderDetail";

            qb.setTables(sqlTable);
            Cursor c = qb.query(db, sqlSelect, "UserPhone=?", new String[]{userPhone}, null, null, null);

            final List<Order> result = new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    result.add(new Order(
                            c.getString(c.getColumnIndex("UserPhone")),
                            c.getString(c.getColumnIndex("ProductId")),
                            c.getString(c.getColumnIndex("ProductName")),
                            c.getString(c.getColumnIndex("Quantity")),
                            c.getString(c.getColumnIndex("Price")),
                            c.getString(c.getColumnIndex("Discount")),
                            c.getString(c.getColumnIndex("Image"))
                    ));

                } while (c.moveToNext());
            }

            return result;
        }

        public void addToCart(Order order) {
            SQLiteDatabase db = getReadableDatabase();
            String query = String.format("INSERT OR REPLACE INTO OrderDetail(UserPhone,ProductId,ProductName,Quantity,Price,Discount,Image) VALUES ('%s','%s','%s','%s','%s','%s','%s');",
                    order.getUserPhone(),
                    order.getProductID(),
                    order.getProductName(),
                    order.getQuantity(),
                    order.getPrice(),
                    order.getDiscount(),
                    order.getImage());
            db.execSQL(query);
        }

        public void cleanCart(String userPhone) {
            SQLiteDatabase db = getReadableDatabase();
            String query = String.format("DELETE FROM OrderDetail WHERE UserPhone='%s'", userPhone);
            db.execSQL(query);
        }

        public int getCountCart(String userPhone) {

            int count = 0;

            SQLiteDatabase db = getReadableDatabase();
            String query = String.format("SELECT COUNT (*) FROM OrderDetail Where UserPhone='%s'",userPhone);
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getInt(0);
                } while (cursor.moveToNext());
            }
            return count;
        }

        public void updateCart(Order order) {
            SQLiteDatabase db = getReadableDatabase();
            String query = String.format("UPDATE OrderDetail SET Quantity= '%s' WHERE UserPhone= '%s' AND ProductId='%s'", order.getQuantity(), order.getUserPhone(),order.getProductID());
            db.execSQL(query);
        }

        public void increaseCart(String userPhone,String foodId) {
            SQLiteDatabase db = getReadableDatabase();
            String query = String.format("UPDATE OrderDetail SET Quantity= Quantity+1 WHERE UserPhone= '%s' AND ProductId='%s'", userPhone,foodId);
            db.execSQL(query);
        }

        public void removeFromCart(String productID, String phone) {
            SQLiteDatabase db = getReadableDatabase();
            String query = String.format("DELETE FROM OrderDetail WHERE UserPhone='%s' and ProductId='%s'", phone,productID);
            db.execSQL(query);
        }


        public void addToFavourites(Favourites food) {
            SQLiteDatabase db = getReadableDatabase();
            String query = String.format("INSERT INTO Favourites("+
                    "FoodId,FoodName,FoodPrice,FoodMenuId,FoodImage,FoodDiscount,FoodDescription,UserPhone)" +
                    "VALUES ('%s','%s','%s','%s','%s','%s','%s','%s');",
                    food.getFoodId(),
                    food.getFoodName(),
                    food.getFoodPrice(),
                    food.getFoodMenuId(),
                    food.getFoodImage(),
                    food.getFoodDiscount(),
                    food.getFoodDescription(),
                    food.getUserPhone());
            db.execSQL(query);
        }

        public void removeFromFavourites(String foodId, String phone) {
            SQLiteDatabase db = getReadableDatabase();
            String query = String.format("DELETE FROM Favourites WHERE FoodId ='%s';", foodId);
            db.execSQL(query);
        }

        public boolean isFavourite(String foodId) {
            SQLiteDatabase db = getReadableDatabase();
            String query = String.format("SELECT * FROM Favourites WHERE FoodId ='%s';", foodId);
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() <= 0) {
                cursor.close();
                return false;
            }
            cursor.close();
            return true;
        }

        public List<Favourites> getAllFavourites(String userPhone) {
            SQLiteDatabase db = getReadableDatabase();
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

            String[] sqlSelect = {"UserPhone", "FoodId", "FoodName", "FoodPrice", "FoodMenuId", "FoodImage","FoodDiscount","FoodDescription"};
            String sqlTable = "Favourites";

            qb.setTables(sqlTable);
            Cursor c = qb.query(db, sqlSelect, "UserPhone=?", new String[]{userPhone}, null, null, null);

            final List<Favourites> result = new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    result.add(new Favourites(

                                    c.getString(c.getColumnIndex("FoodId")),
                                    c.getString(c.getColumnIndex("FoodName")),
                                    c.getString(c.getColumnIndex("FoodPrice")),
                                    c.getString(c.getColumnIndex("FoodMenuId")),
                                    c.getString(c.getColumnIndex("FoodImage")),
                                    c.getString(c.getColumnIndex("FoodDiscount")),
                                    c.getString(c.getColumnIndex("FoodDescription")),
                                    c.getString(c.getColumnIndex("UserPhone"))
                            ));




                } while (c.moveToNext());
            }

            return result;
        }

}







