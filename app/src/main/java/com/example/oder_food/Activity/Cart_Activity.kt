package com.example.oder_food.Activity;


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oder_food.R
import com.example.oder_food_app.Adapter.RvAdapterCart
import com.example.oder_food_app.DataBase.FoodDT_Cart
import com.example.tablayout_bottomnavigation.Data.MyDatabaseFood
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import java.lang.ref.WeakReference
import java.util.Calendar
import kotlin.random.Random

class Cart_Activity : AppCompatActivity() {
    private lateinit var foodList:ArrayList<FoodDT_Cart>
    lateinit var dialog:AlertDialog
    private lateinit var adapter: RvAdapterCart
    private lateinit var bView: View
    var totalSum = 0.0f
    @SuppressLint("CutPasteId", "NotifyDataSetChanged", "SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val myDatabase = MyDatabaseFood(this)
        foodList = myDatabase.getAllFood_Cart() as ArrayList<FoodDT_Cart>
        adapter = RvAdapterCart(foodList, this,WeakReference(this) )
        findViewById<RecyclerView>(R.id.rcv_cart).adapter = adapter
        findViewById<RecyclerView>(R.id.rcv_cart).layoutManager = LinearLayoutManager(this, GridLayoutManager.VERTICAL,false)
        findViewById<Button>(R.id.btnabate).setOnClickListener {
            btn_abate()
        }
        btn_Back()
        Navigation()
        sumMoney()
    }
    @SuppressLint("SetTextI18n")
    fun sumMoney() {
        totalSum = 0.0f
        for (i in foodList) {
            totalSum += (i.price * i.amount)
        }
        val txtsum = findViewById<TextView>(R.id.txtsummoney)
        txtsum.text = totalSum.toString() + "00 VNĐ"
        txtsum.setTextColor(Color.parseColor("#FA4D47"))
    }

    @SuppressLint("SetTextI18n")
    fun updatePrice(newPrice: Float) {
        totalSum = newPrice.toFloat()
        val txtsum = findViewById<TextView>(R.id.txtsummoney)
        txtsum.text = totalSum.toString() + "00 VNĐ"
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n", "MissingInflatedId", "CutPasteId")
    private fun btn_abate(){
        bView = layoutInflater.inflate(R.layout.fragment_new_task_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(bView)
        dialog.show()
        val edtname = bView.findViewById<EditText>(R.id.edthoten)
        val edtsdt = bView.findViewById<EditText>(R.id.edtsdt)
        val edtaddress = bView.findViewById<EditText>(R.id.edtdiachi)
        val edtmethod = bView.findViewById<EditText>(R.id.edtthanhtoan)
        bView.findViewById<TextView>(R.id.txtsum_dialog).text = findViewById<TextView>(R.id.txtsummoney).text.toString()
        bView.findViewById<Button>(R.id.btndathang).setOnClickListener {
            if (edtname.text?.isEmpty() == true || edtsdt.text?.isEmpty() == true
                || edtaddress.text?.isEmpty() == true || edtmethod.text?.isEmpty() == true
            ) {
                if (edtname.text?.isEmpty() == true) {
                    edtname.error = "Vui lòng nhập họ tên"
                }
                if (edtsdt.text?.isEmpty() == true) {
                    edtsdt.error = "Vui lòng nhập số điện thoại"
                }
                if (edtaddress.text?.isEmpty() == true) {
                    edtaddress.error = "Vui lòng nhập địa chỉ"
                }
                if (edtmethod.text?.isEmpty() == true) {
                    edtmethod.error = "Vui lòng nhập phương thức thanh toán"
                }
            } else {
                val maDonHang = taoMaDonHang()
                val hoten = bView.findViewById<EditText>(R.id.edthoten).text.toString()
                val sdt = bView.findViewById<EditText>(R.id.edtsdt).text.toString()
                val diachi = bView.findViewById<EditText>(R.id.edtdiachi).text.toString()
                val calendar = Calendar.getInstance()
                val currentDateTime = calendar.time.toString()
                val tongtien = bView.findViewById<TextView>(R.id.txtsum_dialog).text.toString()
                val thanhtoan = bView.findViewById<EditText>(R.id.edtthanhtoan).text.toString()
                val dbHelper = MyDatabaseFood(this)
                val db = dbHelper.writableDatabase
                val values = ContentValues()
                values.put("MADON", maDonHang)
                values.put("HOTEN", hoten)
                values.put("SDT", sdt)
                values.put("DIACHI", diachi)
                val cartItems = mutableListOf<String>()
                for (i in foodList) {
                    cartItems.add(i.title + " (" + i.amount + ")")
                }
                val thucDonString = cartItems.joinToString(", ")
                values.put("THUCDON", thucDonString)
                values.put("NGAYDAT", currentDateTime)
                values.put("TONGTIEN", tongtien)
                values.put("THANHTOAN", thanhtoan)

                db.insert("HISTORY", null, values)
                db.close()
                Toast.makeText(this, "Đặt hàng thành công !", Toast.LENGTH_SHORT).show()
                val myDatabase = dbHelper.writableDatabase
                myDatabase.delete("CART", null, null)
                myDatabase.close()
                findViewById<TextView>(R.id.txtsummoney).text = "0.000 VNĐ"
                foodList.clear()
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }

        }
    }
    private fun taoMaDonHang(): String {
        val soChuSo = 5
        val soChuCai = 3
        val soChuCaiCuoi = 1

        val chuSo = "0123456789"
        val chuCai = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val chuCuoi = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

        val random = Random(System.currentTimeMillis())

        val maSo = (1..soChuSo).map { chuSo[random.nextInt(chuSo.length)] }.joinToString("")
        val maChuCai = (1..soChuCai).map { chuCai[random.nextInt(chuCai.length)] }.joinToString("")
        val maChuCaiCuoi = (1..soChuCaiCuoi).map { chuCuoi[random.nextInt(chuCuoi.length)] }.joinToString("")

        return "$maChuCai$maSo$maChuCaiCuoi"
    }
    private fun btn_Back() {
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            val intent = Intent(this@Cart_Activity, Home_Activity::class.java)
            startActivity(intent)
        }
    }

    private fun Navigation(){
        findViewById<NavigationView>(R.id.nav_lefmenu).setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> {
                    val intent = Intent(this@Cart_Activity,Home_Activity::class.java)
                    startActivity(intent)
                }
                R.id.nav_history -> {
                    val intent = Intent(this@Cart_Activity,History_Activity::class.java)
                    startActivity(intent)
                }
                R.id.nav_out -> {
                    val intent = Intent(this@Cart_Activity,LogIn_Activity::class.java)
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


