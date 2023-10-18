package com.example.oder_food_app.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.oder_food.R
import com.example.oder_food_app.DataBase.FoodDT
import com.example.tablayout_bottomnavigation.interfacee.Rvinterface
import java.util.Locale

class RvAdapterFood(var ds: List<FoodDT>, val onclickFood: Rvinterface): RecyclerView.Adapter<RvAdapterFood.FoodHolder>() {
    inner class FoodHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imgv:ImageView = itemView.findViewById(R.id.img_item)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(ds:List<FoodDT>){
        this.ds = ds
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun clearData() {
        ds = emptyList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_food_item, parent, false)
        return FoodHolder(view)
    }

    override fun getItemCount(): Int {
        return ds.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        holder.itemView.apply {
            val title= findViewById<TextView>(R.id.title_item)
            val price = findViewById<TextView>(R.id.price_item)
            Glide.with(context)
                .load(ds[position].img)
                .into(holder.imgv)
            title.text = ds[position].title
            price.text = ds[position].price.toString() + "00 VNĐ"

            // Lắng nghe sự kiện item click
            holder.itemView.setOnClickListener {
                onclickFood.OnclickFood(position)
            }
        }
    }
}