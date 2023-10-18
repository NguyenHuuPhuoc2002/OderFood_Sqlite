package com.example.tablayout_bottomnavigation.Data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.oder_food_app.DataBase.AccountFood
import com.example.oder_food_app.DataBase.FoodDT
import com.example.oder_food_app.DataBase.FoodDT_Cart
import com.example.oder_food_app.DataBase.FoodDT_History

class MyDatabaseFood(context: Context): SQLiteOpenHelper(context, "FOODDB", null, 3) {

    override fun onCreate(db: SQLiteDatabase?) {
        //------------------------ TABLE HOME FOOD ----------------------
        db?.execSQL("CREATE TABLE FOOD(USERID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PRICE FLOAT, IMAGE TEXT)")

        // Thêm dữ liệu mẫu
        val contentValues_food = ContentValues()
        contentValues_food.put("NAME", "Chân gà sả tắc")
        contentValues_food.put("PRICE", 50.000)
        contentValues_food.put("IMAGE", "https://th.bing.com/th/id/OIP.JCdjiF40OdtrI9FmzJRSPgHaE8?pid=ImgDet&rs=1")
        db?.insert("FOOD", null, contentValues_food)

        contentValues_food.clear()
        contentValues_food.put("NAME", "Gỏi bò bóp thấu")
        contentValues_food.put("PRICE", 60.000)
        contentValues_food.put("IMAGE", "https://th.bing.com/th/id/OIP.ejld2BNNF5az9UZ4mNtitgHaE8?w=272&h=181&c=7&r=0&o=5&dpr=1.3&pid=1.7")
        db?.insert("FOOD", null, contentValues_food)
        contentValues_food.clear()

        contentValues_food.put("NAME", "Tôm sốt bơ tỏi")
        contentValues_food.put("PRICE", 40.000)
        contentValues_food.put("IMAGE", "https://th.bing.com/th/id/OIP.6sodmf-vSc3VH2cGP3pB0gHaE8?w=247&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7")
        db?.insert("FOOD", null, contentValues_food)

        contentValues_food.clear()
        contentValues_food.put("NAME", "Lòng xào dưa chua")
        contentValues_food.put("PRICE", 60.000)
        contentValues_food.put("IMAGE", "https://th.bing.com/th/id/OIP.AqnNxO6vhXn5EKfAC5nQ1wHaEK?w=251&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7")
        db?.insert("FOOD", null, contentValues_food)

        contentValues_food.clear()
        contentValues_food.put("NAME", "Ba chỉ lắc sả tắc")
        contentValues_food.put("PRICE", 50.000)
        contentValues_food.put("IMAGE", "https://th.bing.com/th/id/OIP.OJ7NfiIb3F86T36ogaoG8wHaE8?w=236&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7")
        db?.insert("FOOD", null, contentValues_food)

        contentValues_food.clear()
        contentValues_food.put("NAME", "Cơm chiên hải sản")
        contentValues_food.put("PRICE", 20.000)
        contentValues_food.put("IMAGE", "https://th.bing.com/th/id/OIP.jcJpqUVBX2h5ViGIsFUo7QHaE8?w=254&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7")
        db?.insert("FOOD", null, contentValues_food)

        contentValues_food.clear()
        contentValues_food.put("NAME", "Bắp xào bơ hành")
        contentValues_food.put("PRICE", 30.000)
        contentValues_food.put("IMAGE", "https://th.bing.com/th/id/OIP.h_wuSe7a3HCx7f8ZRhjumQHaE8?w=240&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7")
        db?.insert("FOOD", null, contentValues_food)

        contentValues_food.clear()
        contentValues_food.put("NAME", "Cút lộn sốt me")
        contentValues_food.put("PRICE", 60.000)
        contentValues_food.put("IMAGE", "https://th.bing.com/th/id/OIP._ROZp8BV2FNlkCYakmsAlQHaEv?w=260&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7")
        db?.insert("FOOD", null, contentValues_food)

        contentValues_food.clear()
        contentValues_food.put("NAME", "Khoa tây chiên")
        contentValues_food.put("PRICE", 15.000)
        contentValues_food.put("IMAGE", "https://product.hstatic.net/1000356263/product/3.-khoai-t_y-chi_n-900x600_1024x1024.jpg")
        db?.insert("FOOD", null, contentValues_food)

        contentValues_food.clear()
        contentValues_food.put("NAME", "Bia HuDa Lon")
        contentValues_food.put("PRICE", 10.000)
        contentValues_food.put("IMAGE", "https://th.bing.com/th/id/OIP._yNDcKZ5M6X8u1J98imSRAHaFj?w=281&h=211&c=7&r=0&o=5&dpr=1.3&pid=1.7")
        db?.insert("FOOD", null, contentValues_food)

        contentValues_food.clear()
        contentValues_food.put("NAME", "Bò Húc")
        contentValues_food.put("PRICE", 10.000)
        contentValues_food.put("IMAGE", "https://th.bing.com/th/id/OIP.QLL3FJcxdIhVNOhWFm_gDwHaHa?w=175&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7")
        db?.insert("FOOD", null, contentValues_food)

        contentValues_food.clear()
        contentValues_food.put("NAME", "Nước Lọc")
        contentValues_food.put("PRICE", 5.000)
        contentValues_food.put("IMAGE", "https://th.bing.com/th/id/OIP.8OrplMYy-XPf93YoHkMxNQAAAA?w=180&h=181&c=7&r=0&o=5&dpr=1.3&pid=1.7")
        db?.insert("FOOD", null, contentValues_food)


        //------------------------ TABLE CART ----------------------

        db?.execSQL("CREATE TABLE CART(USERID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PRICE FLOAT, IMAGE TEXT, AMOUNT INT)")

        //------------------------ TABLE History ----------------------

        db?.execSQL("CREATE TABLE HISTORY(USERID INTEGER PRIMARY KEY AUTOINCREMENT, MADON TEXT, HOTEN TEXT, SDT TEXT, DIACHI TEXT, THUCDON TEXT, NGAYDAT TEXT, TONGTIEN FLOAT, THANHTOAN FLOAT)")

        //------------------------ TABLE ACCOUNT ----------------------

        db?.execSQL("CREATE TABLE ACCOUNT(USERID INTEGER PRIMARY KEY AUTOINCREMENT, USER TEXT, PASSWORD TEXT)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
    @SuppressLint("Range")
    fun getAllFood(): List<FoodDT> {
        val foodList = ArrayList<FoodDT>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM FOOD", null)

        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndex("NAME"))
            val price = cursor.getFloat(cursor.getColumnIndex("PRICE"))
            val image = cursor.getString(cursor.getColumnIndex("IMAGE"))

            val food = FoodDT(name, price, image)
            foodList.add(food)
        }
        cursor.close()
        db.close()

        return foodList
    }

    @SuppressLint("Range")
    fun getAllFood_Cart(): List<FoodDT_Cart> {
        val foodList = ArrayList<FoodDT_Cart>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM CART", null)

        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndex("NAME"))
            val price = cursor.getFloat(cursor.getColumnIndex("PRICE"))
            val image = cursor.getString(cursor.getColumnIndex("IMAGE"))
            val amount = cursor.getInt(cursor.getColumnIndex("AMOUNT"))

            val food = FoodDT_Cart(name, price, image, amount)
            foodList.add(food)
        }
        cursor.close()
        db.close()

        return foodList
    }

    @SuppressLint("Range")
    fun getAllAccount(): ArrayList<AccountFood> {
        val foodList = ArrayList<AccountFood>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ACCOUNT", null)

        while (cursor.moveToNext()) {
            val user = cursor.getString(cursor.getColumnIndex("USER"))
            val pass = cursor.getString(cursor.getColumnIndex("PASSWORD"))

            val food = AccountFood(user, pass)
            foodList.add(food)
        }
        cursor.close()
        db.close()

        return foodList
    }

    @SuppressLint("Range")
    fun getAllFood_History(): List<FoodDT_History> {
        val foodList = ArrayList<FoodDT_History>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM HISTORY", null)

        while (cursor.moveToNext()) {
            val madon = cursor.getString(cursor.getColumnIndex("MADON"))
            val hoten = cursor.getString(cursor.getColumnIndex("HOTEN"))
            val sdt = cursor.getString(cursor.getColumnIndex("SDT"))
            val diachi = cursor.getString(cursor.getColumnIndex("DIACHI"))
            val thucdon = cursor.getString(cursor.getColumnIndex("THUCDON"))
            val ngaydat = cursor.getString(cursor.getColumnIndex("NGAYDAT"))
            val tongtien = cursor.getFloat(cursor.getColumnIndex("TONGTIEN"))
            val thanhtoan = cursor.getString(cursor.getColumnIndex("THANHTOAN"))

            val food = FoodDT_History(madon, hoten, sdt, diachi, thucdon, ngaydat, tongtien, thanhtoan )
            foodList.add(food)
        }
        cursor.close()
        db.close()

        return foodList
    }
}