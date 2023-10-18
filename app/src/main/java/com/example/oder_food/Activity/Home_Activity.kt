package com.example.oder_food.Activity;

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.oder_food.R
import com.example.oder_food_app.Adapter.Photo
import com.example.oder_food_app.Adapter.PhotoAdapter
import com.example.oder_food_app.Adapter.RvAdapterFood
import com.example.oder_food_app.DataBase.FoodDT
import com.example.tablayout_bottomnavigation.Data.MyDatabaseFood
import com.example.tablayout_bottomnavigation.interfacee.Rvinterface
import com.google.android.material.navigation.NavigationView
import me.relex.circleindicator.CircleIndicator3
import java.text.Normalizer

//, Filterable
class Home_Activity : AppCompatActivity() {
    private lateinit var circleIndicator3: CircleIndicator3
    private lateinit var view_paper_slide: ViewPager2
    private lateinit var mlistphoto:List<Photo>
    private var number_dish = 0
    private lateinit var foodList:List<FoodDT>
    private lateinit var adapter: RvAdapterFood
    private lateinit var filteredList: ArrayList<FoodDT>
    private val autoScrollHandler = Handler(Looper.getMainLooper())
    private var currentPage = 0
    private val scrollDelay = 3000L
    @SuppressLint("CutPasteId", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val myDatabase = MyDatabaseFood(this)
        foodList = myDatabase.getAllFood()
        number_dish = myDatabase.getAllFood_Cart().size
        findViewById<TextView>(R.id.number_product).text = number_dish.toString()

        filteredList = ArrayList(foodList)

        val intent = intent
        val toast = intent.getStringExtra("login")
        if (toast != null) {
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
        }

        Slide_ViewPager()
        rcv_food()
        Img_Cart()
        Search_View()
        Navigation()
        btnimg_Navigation()


    }

    private fun btnimg_Navigation(){
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_lefmenu)
        val btnNav = findViewById<ImageButton>(R.id.btn_nav)
        btnNav.setOnClickListener {
            drawerLayout.openDrawer(navView)
        }
    }
    private fun Search_View(){
        val searchView = findViewById<androidx.appcompat.widget.SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                filterList(p0)
                return true
            }
        })
    }
    private fun filterList(query: String?) {
        if (query != null) {
            val normalizedQuery = removeDiacritics(query.toLowerCase())
            filteredList = ArrayList<FoodDT>()
            for (i in foodList) {
                val normalizedTitle = removeDiacritics(i.title.toLowerCase())
                if (normalizedTitle.contains(normalizedQuery)) {
                    filteredList.add(i)
                }
            }
            adapter.clearData()
            adapter.setFilteredList(filteredList)
        }
    }

    private fun removeDiacritics(input: String): String {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
            .replace("[^\\p{ASCII}]".toRegex(), "")
    }


    @SuppressLint("CutPasteId")
    private fun rcv_food(){
        adapter = RvAdapterFood(foodList, object : Rvinterface {
            override fun OnclickFood(pos: Int) {
                val clickedFood = filteredList[pos]
                val originalPosition = foodList.indexOf(clickedFood)
                val intent = Intent(this@Home_Activity, Detail_Activity::class.java)
                val bundle = Bundle()
                val foodListParcelable = ArrayList<Parcelable>(foodList)
                bundle.putParcelableArrayList("foodlist", foodListParcelable)
                bundle.putInt("position", originalPosition)
                intent.putExtras(bundle)
                startActivity(intent)

            }
        })
        findViewById<RecyclerView>(R.id.rcv_food).adapter = adapter
        findViewById<RecyclerView>(R.id.rcv_food).layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL,false)
    }
    private fun Img_Cart(){
        findViewById<ImageView>(R.id.imgCart).setOnClickListener {
            val intent = Intent(this@Home_Activity, Cart_Activity::class.java)
            startActivity(intent)
        }
    }
    private fun Slide_ViewPager(){
        view_paper_slide = findViewById(R.id.view_paper_slide)
        circleIndicator3 = findViewById(R.id.circle_indicator)
        mlistphoto = getListPhoto()
        val adapterphoto = PhotoAdapter(mlistphoto)
        view_paper_slide.adapter = adapterphoto
        circleIndicator3.setViewPager(view_paper_slide)
        //setting
        view_paper_slide.offscreenPageLimit = 3
        view_paper_slide.clipToPadding = false
        view_paper_slide.clipChildren = false
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            val scale: Float
            if (position < -1) { // Khi trang không hiển thị
                scale = 0.85f // Đặt kích thước nhỏ
            } else if (position <= 1) { // Khi trang hiển thị (trái và phải)
                scale = 0.85f + (1 - Math.abs(position)) * 0.15f // Tính toán kích thước dựa trên vị trí
            } else { // Khi trang không hiển thị
                scale = 0.85f // Đặt kích thước nhỏ
            }
            // Đặt thuộc tính scaleX và scaleY của trang
            page.scaleX = scale
            page.scaleY = scale
        }
        view_paper_slide.setPageTransformer(compositePageTransformer)
        startAutoScroll()
    }
    private fun startAutoScroll() {
        autoScrollHandler.postDelayed(object : Runnable {
            override fun run() {
                if (currentPage < getListPhoto().size) {
                    view_paper_slide.currentItem = currentPage
                    currentPage++
                } else {
                    view_paper_slide.currentItem = 0
                    currentPage = 0
                }

                // Lặp lại việc tự động chuyển trang
                autoScrollHandler.postDelayed(this, scrollDelay)
            }
        }, scrollDelay)
    }

    private fun Navigation(){
        findViewById<NavigationView>(R.id.nav_lefmenu).setNavigationItemSelectedListener {
            val isCurrentActivityHome = this is Home_Activity
            when(it.itemId){
                R.id.nav_home -> {
                    if(!isCurrentActivityHome){
                        val intent = Intent(this@Home_Activity,Home_Activity::class.java)
                        startActivity(intent)
                    }
                }
                R.id.nav_history -> {
                    val intent = Intent(this@Home_Activity,History_Activity::class.java)
                    startActivity(intent)
                }
                R.id.nav_out -> {
                    val intent = Intent(this@Home_Activity,LogIn_Activity::class.java)
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
    private fun getListPhoto(): List<Photo> {
        val list: MutableList<Photo> = ArrayList()
        list.add(Photo(R.drawable.img5))
        list.add(Photo(R.drawable.img6))
        list.add(Photo(R.drawable.img7))
        list.add(Photo(R.drawable.img8))
        list.add(Photo(R.drawable.img9))
        list.add(Photo(R.drawable.img10))
        return list
    }

}