package com.example.oder_food.Activity;


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.oder_food.R
import com.example.oder_food_app.DataBase.FoodDT
import com.example.oder_food_app.DataBase.FoodDT_Cart
import com.example.tablayout_bottomnavigation.Data.MyDatabaseFood
import com.google.android.material.navigation.NavigationView
import kotlin.properties.Delegates

class Detail_Activity : AppCompatActivity() {
    private lateinit var foodlist: ArrayList<FoodDT>
    private var pos by Delegates.notNull<Int>()
    private var isAddedCart : Boolean = false
    private var number_dish = 0
    private lateinit var list_cart:ArrayList<FoodDT_Cart>
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val intent = intent
        val bundle = intent.extras
        val mydatabase = MyDatabaseFood(this)
        number_dish = mydatabase.getAllFood_Cart().size
        list_cart = mydatabase.getAllFood_Cart() as ArrayList<FoodDT_Cart>
        findViewById<TextView>(R.id.number_product).text = number_dish.toString()
        if (bundle != null) {
            foodlist = bundle.getParcelableArrayList<FoodDT>("foodlist") as ArrayList<FoodDT>
        }
        pos = bundle?.getInt("position")!!
        btn_Back()
        update()
        btn_addCart()
        Img_Cart()
        Navigation()
    }

    private fun btn_Back(){
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            val intent = Intent(this@Detail_Activity, Home_Activity::class.java)
            startActivity(intent)
        }
    }
    @SuppressLint("SetTextI18n")
    private fun update(){
        findViewById<TextView>(R.id.txttitle).text = foodlist[pos].title
        findViewById<TextView>(R.id.txtprice).text = "Giá: " + foodlist[pos].price.toString() + "00 VNĐ"
        Glide.with(applicationContext)
            .load(foodlist[pos].img) // Đường dẫn URL của hình ảnh
            .into(findViewById<ImageView>(R.id.imgchitiet))
    }
    @SuppressLint("SetTextI18n", "Range", "CutPasteId")
    private fun btn_addCart() {
        val addButton = findViewById<Button>(R.id.btnaddcart)
        addButton.setOnClickListener {
            if (!isAddedCart) {
                val dbHelper = MyDatabaseFood(this)
                val db = dbHelper.writableDatabase

                val Name = foodlist[pos].title
                val Price = foodlist[pos].price
                val Image = foodlist[pos].img

                val cursor = db.rawQuery("SELECT AMOUNT FROM CART WHERE NAME=?", arrayOf(Name))
                if (cursor.moveToFirst()) {

                    val currentAmount = cursor.getInt(cursor.getColumnIndex("AMOUNT"))
                    val newAmount = currentAmount + 1
                    val values = ContentValues()
                    values.put("AMOUNT", newAmount)

                    db.update("CART", values, "NAME=?", arrayOf(Name))
                } else {
                    val values = ContentValues()
                    values.put("NAME", Name)
                    values.put("PRICE", Price)
                    values.put("IMAGE", Image)
                    values.put("AMOUNT", 1)

                    db.insert("CART", null, values)
                }

                cursor.close()
                db.close()

                addButton.setBackgroundResource(R.drawable.bg_custom_added_cart)
                addButton.text = "Đã Thêm"
                isAddedCart = true

                var isProductInCart = false

                for (i in list_cart) {
                    if (Name.equals(i.title)) {
                        isProductInCart = true
                        break
                    }
                }
                if (isProductInCart) {
                } else {
                    number_dish++
                    findViewById<TextView>(R.id.number_product).text = number_dish.toString()
                }

            }
        }
    }

    private fun Img_Cart(){
        findViewById<ImageView>(R.id.imgCart).setOnClickListener {
            val intent = Intent(this@Detail_Activity, Cart_Activity::class.java)
            startActivity(intent)
        }
    }
    private fun Navigation(){
        findViewById<NavigationView>(R.id.nav_lefmenu).setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> {
                    val intent = Intent(this@Detail_Activity,Home_Activity::class.java)
                    startActivity(intent)
                }
                R.id.nav_history -> {
                    val intent = Intent(this@Detail_Activity,History_Activity::class.java)
                    startActivity(intent)
                }
                R.id.nav_out -> {
                    val intent = Intent(this@Detail_Activity,LogIn_Activity::class.java)
                    startActivity(intent)
                }
//                R.id.nav_home -> {
//                    HistoryFragment()
//                }
//                R.id.nav_home -> {
//                    HistoryFragment()
//                }
            }
            true
        }
    }
}