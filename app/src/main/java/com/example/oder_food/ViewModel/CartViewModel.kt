package com.example.oder_food_app.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.oder_food_app.DataBase.FoodDT

class CartViewModel : ViewModel() {
    private val cartItems: MutableLiveData<List<String>> = MutableLiveData()

    // Hàm này để cập nhật giỏ hàng với một danh sách món ăn mới
    fun updateCart(newCart: List<String>) {
        cartItems.value = newCart
    }

    // Hàm này để lấy dữ liệu giỏ hàng dưới dạng LiveData
    fun getCart(): LiveData<List<String>> {
        return cartItems
    }
}
