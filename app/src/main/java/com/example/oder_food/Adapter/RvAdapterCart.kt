package com.example.oder_food_app.Adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.oder_food.Activity.Cart_Activity
import com.example.oder_food.R
import com.example.oder_food_app.DataBase.FoodDT_Cart
import com.example.tablayout_bottomnavigation.Data.MyDatabaseFood
import java.lang.ref.WeakReference


class RvAdapterCart(val ds: ArrayList<FoodDT_Cart>, private val context: Context, private val activityRef: WeakReference<Cart_Activity>) : RecyclerView.Adapter<RvAdapterCart.FoodHolder>(){

    inner class FoodHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgv: ImageView = itemView.findViewById(R.id.img_item)
        var title: TextView = itemView.findViewById(R.id.title_itemcart)
        var price: TextView = itemView.findViewById(R.id.price_itemcart)
        var btn_delete: Button = itemView.findViewById(R.id.btn_delete)
        var pluss: ImageButton = itemView.findViewById(R.id.btnPlus)
        var Minus: ImageButton = itemView.findViewById(R.id.btnMinus)
        var txtQuantity: TextView = itemView.findViewById(R.id.txtQuantity)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layoutcartitem, parent, false)
        return FoodHolder(view)
    }

    override fun getItemCount(): Int {
        return ds.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        val currentItem = ds[position]
        val name =  currentItem.title
        holder.title.text = currentItem.title
        holder.price.text = currentItem.price.toString() + "00 VNĐ"
        Glide.with(holder.itemView.context)
            .load(currentItem.img)
            .into(holder.imgv)
        holder.btn_delete.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val alertDialogBuilder = AlertDialog.Builder(context)
                alertDialogBuilder.setTitle("Xác nhận xóa")
                alertDialogBuilder.setMessage("Bạn có muốn xóa $name không?")
                alertDialogBuilder.setPositiveButton("Có") { _, _ ->
                    deleteItem_SQlite(position)
                    Toast.makeText(context, "Bạn đã xóa $name thành công", Toast.LENGTH_SHORT).show()
                }
                alertDialogBuilder.setNegativeButton("Không") { dialog, _ ->
                    dialog.dismiss()
                }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        }
        val dbHelper = MyDatabaseFood(context)
        val foodList = dbHelper.getAllFood_Cart()
        if (position >= 0 && position < foodList.size) {
            val food = foodList[position]
            val position = holder.adapterPosition
            holder.txtQuantity.text = food.amount.toString()

            holder.pluss.setOnClickListener {
                dbHelper.writableDatabase.use { db ->
                    val newAmount = food.amount + 1
                    val values = ContentValues()
                    values.put("AMOUNT", newAmount)
                    db.update("CART", values, "NAME=?", arrayOf(food.title))
                    db.close()
                    food.amount = newAmount
                    holder.txtQuantity.text = newAmount.toString()
                }
                /* update price */
                val activity = activityRef.get()
                if (activity != null) {
                    val newTotalPrice = activity.totalSum + food.price
                    activity.updatePrice(newTotalPrice)
                }
            }

            holder.Minus.setOnClickListener {
                dbHelper.writableDatabase.use { db ->
                    if (food.amount > 1) {
                        val newAmount = food.amount - 1
                        val values = ContentValues()
                        values.put("AMOUNT", newAmount)
                        db.update("CART", values, "NAME=?", arrayOf(food.title))
                        db.close()
                        food.amount = newAmount
                        holder.txtQuantity.text = newAmount.toString()

                        /* update price */
                        val activity = activityRef.get()
                        if (activity != null) {
                            val newPrice = activity.totalSum - food.price
                            activity.updatePrice(newPrice)
                        }
                    }
                }
            }
        }

    }
    fun getItemPosition(food: FoodDT_Cart): Int {
        return ds.indexOf(food)
    }
    private fun deleteItem_SQlite(position: Int) {
        if (position >= 0 && position < ds.size) {
            val deletedItem = ds[position]
            ds.toMutableList().removeAt(position)
            notifyItemRemoved(position)

            val dbHelper = MyDatabaseFood(context)
            val db = dbHelper.writableDatabase
            val whereCow= "NAME = ?"
            val whereName = arrayOf(deletedItem.title)
            db.delete("CART", whereCow, whereName)
            db.close()
        }
    }
}

