package com.example.shoppinglist.presentor

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.presentor.ShopListAdapter.Companion.ENABLED_VIEW
import com.example.shoppinglist.presentor.ShopListAdapter.Companion.MAX_POOL_SIZE

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var rcView: RecyclerView
    lateinit var shopListAdapter: ShopListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRcView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }


    }

    private fun initRcView() {
        rcView = findViewById(R.id.rv_shop_list)
        shopListAdapter = ShopListAdapter()
        rcView.adapter = shopListAdapter
        rcView.recycledViewPool.setMaxRecycledViews(ENABLED_VIEW, MAX_POOL_SIZE)
        rcView.recycledViewPool.setMaxRecycledViews(ShopListAdapter.DISABLED_VIEW, MAX_POOL_SIZE)
        setupClickListener()
        setUpLongClick()
        setupSwipeListener()
    }

    private fun setUpLongClick() {
        shopListAdapter.onShopItemLongClick = {

            viewModel.changeShopItem(it)
        }
    }

    private fun setupClickListener() {
        shopListAdapter.onShopItemClick = {
            Toast.makeText(this, "позиция ${it.id}", Toast.LENGTH_SHORT).show()

        }
    }

    private fun setupSwipeListener() {
        val touchCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(touchCallBack)
        itemTouchHelper.attachToRecyclerView(rcView)
    }


}