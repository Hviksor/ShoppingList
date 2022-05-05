package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var shopItemAdapter: ShopListAdapter
    private lateinit var rcview: RecyclerView
    private var count = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRCV()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopItemAdapter.shopList = it
        }
    }

    private fun initRCV() {
        shopItemAdapter = ShopListAdapter()
        rcview = findViewById(R.id.rv_shop_list)
        rcview.adapter = shopItemAdapter
        onShopItemClick()
        onShopItemLongClick()
        onShopItemSwap()

    }

    private fun onShopItemSwap() {
        val callBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopItemAdapter.shopList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)

            }

        }
        val touchHelper = ItemTouchHelper(callBack)
        touchHelper.attachToRecyclerView(rcview)
    }

    private fun onShopItemLongClick() {
        shopItemAdapter.onShopItemLongClick = {
            viewModel.changeEnableState(it)
        }
    }

    private fun onShopItemClick() {
        shopItemAdapter.onShopItemClick = {
            Log.e("Main", it.toString())
        }
    }

}