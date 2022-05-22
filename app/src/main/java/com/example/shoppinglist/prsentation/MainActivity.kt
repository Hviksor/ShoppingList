package com.example.shoppinglist.prsentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoppinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var shopItemAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopItemAdapter.submitList(it)
        }
        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        buttonAddItem.setOnClickListener {
            val intent = ShopItemActivity.getAddIntent(this)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        shopItemAdapter = ShopListAdapter()
        rvShopList.adapter = shopItemAdapter
        rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.LAYOUT_ENABLED,
            ShopListAdapter.MAX_RECYCLER_ITEM
        )
        rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.LAYOUT_DISABLED,
            ShopListAdapter.MAX_RECYCLER_ITEM
        )

        setupClickListener()
        setupLongClickListener()
        setupSwipeListener(rvShopList)

    }

    private fun setupSwipeListener(rvShopList: RecyclerView) {
        val callBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: ViewHolder,
                target: ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(
                viewHolder: ViewHolder,
                direction: Int
            ) {
                val item = shopItemAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callBack)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun setupClickListener() {

        shopItemAdapter.onShopClick = {

            val intent = ShopItemActivity.getEditIntent(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupLongClickListener() {
        shopItemAdapter.onShopItemOnLongClick = {
            viewModel.changeEnableState(it)
        }
    }


}