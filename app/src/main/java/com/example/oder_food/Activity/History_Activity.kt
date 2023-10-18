package com.example.oder_food.Activity;


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oder_food.R
import com.example.oder_food_app.Adapter.RvAdapterCart
import com.example.oder_food_app.Adapter.RvAdapterHistory
import com.example.oder_food_app.DataBase.FoodDT_Cart
import com.example.oder_food_app.DataBase.FoodDT_History
import com.example.tablayout_bottomnavigation.Data.MyDatabaseFood
import com.google.android.material.navigation.NavigationView

class History_Activity : AppCompatActivity() {
    private lateinit var foodlist:ArrayList<FoodDT_History>
    private lateinit var adapter: RvAdapterHistory
    private var number_dish = 0
    @SuppressLint("CutPasteId", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val myDatabase = MyDatabaseFood(this)
        foodlist = myDatabase.getAllFood_History() as ArrayList<FoodDT_History>
        /* số lượng sản phẩm ở ảnh giỏi hàng */
        number_dish = myDatabase.getAllFood_Cart().size
        findViewById<TextView>(R.id.number_product).text = number_dish.toString()
        adapter = RvAdapterHistory(foodlist, this)
        findViewById<RecyclerView>(R.id.rcv_history).adapter = adapter
        findViewById<RecyclerView>(R.id.rcv_history).layoutManager = LinearLayoutManager(this, GridLayoutManager.VERTICAL, false )

        Navigation()
        btn_Back()
        Img_Cart()
    }
    private fun btn_Back() {
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            val intent = Intent(this@History_Activity, Home_Activity::class.java)
            startActivity(intent)
        }
    }
    private fun Img_Cart(){
        findViewById<ImageView>(R.id.imgCart).setOnClickListener {
            val intent = Intent(this@History_Activity, Cart_Activity::class.java)
            startActivity(intent)
        }
    }
    private fun Navigation(){
        findViewById<NavigationView>(R.id.nav_lefmenu).setNavigationItemSelectedListener {
            val isCurrentActivityHistory = this is History_Activity
            when(it.itemId){
                R.id.nav_home -> {
                    val intent = Intent(this@History_Activity,Home_Activity::class.java)
                    startActivity(intent)
                }
                R.id.nav_history -> {
                    if(!isCurrentActivityHistory) {
                        val intent = Intent(this@History_Activity, History_Activity::class.java)
                        startActivity(intent)
                    }
                }
                R.id.nav_out -> {
                    val intent = Intent(this@History_Activity, LogIn_Activity::class.java)
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


