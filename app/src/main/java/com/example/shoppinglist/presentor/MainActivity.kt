package com.example.shoppinglist.presentor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var rcView: RecyclerView
    private lateinit var shopItemAdapter: ShopItemAdapter
    private lateinit var buttonAdd: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        initViews()
        viewModel.shopList.observe(this) {
            shopItemAdapter.shopList = it
        }
        buttonAdd.setOnClickListener {
            val intent = ShopItemActivity.getAddIntent(this)
            startActivity(intent)
        }

    }

    private fun initViews() {
        shopItemAdapter = ShopItemAdapter()
        rcView = findViewById(R.id.rv_shop_list)
        rcView.adapter = shopItemAdapter
        buttonAdd = findViewById(R.id.button_add_shop_item)
        shortClick()
        longClick()
        swipe()
    }

    private fun swipe() {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopItemAdapter.shopList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        }
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(rcView)
    }

    private fun longClick() {
        shopItemAdapter.shopItemLongClick = {
            viewModel.changeEnabled(it)
        }
    }

    private fun shortClick() {
        shopItemAdapter.shopItemClick = {
            Log.e("Main", "position: ${it.id}")
            val intent = ShopItemActivity.getEditIntent(this, it.id)
            startActivity(intent)
        }
    }
}