package com.example.shoppinglist.presentor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var rcView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private lateinit var addButton: FloatingActionButton
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        viewModel.listShopItem.observe(this) {
            shopListAdapter.submitList(it)
        }
        addButton.setOnClickListener {
            val intent = ShopItemActivity.getAddIntent(this)
            startActivity(intent)
        }
        setupOnClickListener()
        setupOnLongClickListener()
        setupOnSwipeListener()
    }

    private fun initViews() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        rcView = binding.rcViewShopList
        rcView.layoutManager = LinearLayoutManager(this)
        addButton = binding.btAddItem
        shopListAdapter = ShopListAdapter()
        rcView.adapter = shopListAdapter

    }

    private fun setupOnClickListener() {
        shopListAdapter.shopItemClick = {
            val intent = ShopItemActivity.getEditIntent(this,it.id)
            startActivity(intent)
        }
    }

    private fun setupOnLongClickListener() {
        shopListAdapter.shopItemLongClick = {
            viewModel.changeStateShopItem(it)
        }
    }

    private fun setupOnSwipeListener() {
        val calback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }

        }
        val touchHelper = ItemTouchHelper(calback)
        touchHelper.attachToRecyclerView(rcView)

    }


}