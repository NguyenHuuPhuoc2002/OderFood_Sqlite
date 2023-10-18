package com.example.oder_food_app.Adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
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
import com.example.oder_food.R
import com.example.oder_food_app.DataBase.FoodDT_Cart
import com.example.oder_food_app.DataBase.FoodDT_History
import com.example.tablayout_bottomnavigation.Data.MyDatabaseFood


class RvAdapterHistory(val ds: ArrayList<FoodDT_History>, private val context: Context) : RecyclerView.Adapter<RvAdapterHistory.FoodHolder>(){

    inner class FoodHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var btn_delete: ImageButton = itemView.findViewById(R.id.btn_delete)
        var txtmadon: TextView = itemView.findViewById(R.id.txtmadon)
        var txthoten: TextView = itemView.findViewById(R.id.txthoten)
        var txtsdt: TextView = itemView.findViewById(R.id.txtsdt)
        var txtdiachi: TextView = itemView.findViewById(R.id.txtdiachi)
        var txtthucdon: TextView = itemView.findViewById(R.id.txtthucdon)
        var txtngaydathang: TextView = itemView.findViewById(R.id.txtngaydathang)
        var txttongtien: TextView = itemView.findViewById(R.id.txttongtien)
        var txtthanhtoan: TextView = itemView.findViewById(R.id.txtthanhtoan)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_history, parent, false)
        return FoodHolder(view)
    }

    override fun getItemCount(): Int {
        return ds.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        val currentItem = ds[position]

        holder.txtmadon.text = currentItem.madon
        holder.txthoten.text = currentItem.hoten
        holder.txtsdt.text = currentItem.sdt
        holder.txtdiachi.text = currentItem.diachi
        holder.txtthucdon.text = currentItem.thucdon
        holder.txtngaydathang.text = currentItem.ngaydat
        holder.txttongtien.text = currentItem.tongtien.toString() + "00 VNĐ"
        holder.txtthanhtoan.text = currentItem.thanhtoan

        holder.btn_delete.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val alertDialogBuilder = AlertDialog.Builder(context)
                alertDialogBuilder.setTitle("Xác nhận xóa")
                alertDialogBuilder.setMessage("Bạn có muốn xóa đơn hàng này không?")
                alertDialogBuilder.setPositiveButton("Có") { _, _ ->
                    deleteItem_SQlite(position)
                    Toast.makeText(context, "Bạn đã xóa thành công", Toast.LENGTH_SHORT).show()
                }
                alertDialogBuilder.setNegativeButton("Không") { dialog, _ ->
                    dialog.dismiss()
                }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        }
    }
    private fun deleteItem_SQlite(position: Int) {
        if (position >= 0 && position < ds.size) {
            val deletedItem = ds[position]
            ds.toMutableList().removeAt(position)
            notifyItemRemoved(position)

            val dbHelper = MyDatabaseFood(context)
            val db = dbHelper.writableDatabase
            val whereCow= "MADON = ?"
            val whereName = arrayOf(deletedItem.madon)
            db.delete("HISTORY", whereCow, whereName)
            db.close()
        }
    }
}

