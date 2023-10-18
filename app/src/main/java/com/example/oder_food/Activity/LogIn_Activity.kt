package com.example.oder_food.Activity;

import android.accounts.Account
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.oder_food.R
import com.example.oder_food_app.DataBase.AccountFood
import com.example.tablayout_bottomnavigation.Data.MyDatabaseFood

class LogIn_Activity : AppCompatActivity() {
    private lateinit var email:EditText
    private lateinit var password:EditText
    lateinit var dialog:AlertDialog
    private lateinit var btnlogin:Button
    private lateinit var accountList:ArrayList<AccountFood>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        email = findViewById(R.id.edtemail)
        password = findViewById(R.id.edtpassword)
        btnlogin = findViewById(R.id.btnlogin)
        val myDatabase = MyDatabaseFood(this)
        accountList = myDatabase.getAllAccount()
        btnlogin.setOnClickListener {
            val edtEmail = email.text.trim().toString()
            val edtPassword = password.text.trim().toString()
            var isSuccessful = false
            for (i in accountList) {
                if (edtEmail == i.user.trim() && edtPassword == i.password.trim()) {
                    isSuccessful = true
                    break
                }
            }
            if (isSuccessful) {
                val intent = Intent(this@LogIn_Activity, Home_Activity::class.java)
                intent.putExtra("login", "Đăng nhập thành công!")
                startActivity(intent)
            } else {
                Toast.makeText(this, "Nhập sai email hoặc password!", Toast.LENGTH_SHORT).show()
            }
        }

        Register()
    }
    private fun Register(){
        findViewById<TextView>(R.id.txtregister).setOnClickListener {
            val build = AlertDialog.Builder(this, R.style.Themecustom)
            val view = layoutInflater.inflate(R.layout.layout_custom_register, null)
            build.setView(view)
            build.setCancelable(false)
            dialog = build.create()
            dialog = build.show()

            view.findViewById<Button>(R.id.btn_huyregister).setOnClickListener{
                dialog.dismiss()
            }
            view.findViewById<Button>(R.id.btn_register).setOnClickListener {
                if(view.findViewById<EditText>(R.id.edt_emailregister).text.isEmpty()
                    || view.findViewById<EditText>(R.id.edt_pwregister).text.isEmpty()
                    || view.findViewById<EditText>(R.id.edt_Re_enter_pw_register).text.isEmpty()){
                    Toast.makeText(this, "Nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
                }else{
                    val user = view.findViewById<EditText>(R.id.edt_emailregister).text.toString()
                    val pass = view.findViewById<EditText>(R.id.edt_pwregister).text.toString()
                    val passReplay = view.findViewById<EditText>(R.id.edt_Re_enter_pw_register).text.toString()
                    if(pass == passReplay){
                        val dbHelper = MyDatabaseFood(this)
                        val db = dbHelper.writableDatabase
                        val values = ContentValues()
                        values.put("USER", user)
                        values.put("PASSWORD", pass)
                        db.insert("ACCOUNT", null, values)
                        db.close()
                        Toast.makeText(this, "Khởi động lại để đăng nhập!", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }else
                    {
                        Toast.makeText(this, "Nhập lại mật khẩu không đúng!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            val window = dialog.window
            val layoutParams = window?.attributes
            layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams?.height = WindowManager.LayoutParams.WRAP_CONTENT
            layoutParams?.gravity = Gravity.CENTER
            window?.attributes = layoutParams
            val width = (resources.displayMetrics.widthPixels * 0.9).toInt()
            val height = (resources.displayMetrics.heightPixels * 0.64).toInt()
            dialog.window?.setLayout(width, height)
        }
    }
}


