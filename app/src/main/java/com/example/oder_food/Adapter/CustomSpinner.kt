package com.example.oder_food.Adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.oder_food.Database.AbateData
import com.example.oder_food.R

class CustomSpinner(val activity: Activity, val list2:List<AbateData>) : ArrayAdapter<AbateData>(activity,R.layout.layout_thanhtoan) {
    override fun getCount(): Int {
        return list2.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //position: Int vị trí của món ăn trong list
        //convertView: View chế độ xem cho từng mục spinner
        //parent: ViewGroup : ViewGroup cha đang chứa spinner

        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    //Hàm xử lí View
    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contexts = activity.layoutInflater
        val rowview = contexts.inflate(R.layout.layout_thanhtoan, parent, false)
        //cở bản là dòng trên sẽ biến xml layout thành view
        val images = rowview.findViewById<ImageView>(R.id.images)
        val txtmonan = rowview.findViewById<TextView>(R.id.txtmonan)

        images.setImageResource(list2[position].image)
        txtmonan.text = list2[position].mieuta

        return rowview
    }
}