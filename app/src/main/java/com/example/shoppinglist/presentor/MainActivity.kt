package com.example.shoppinglist.presentor

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.presentor.ShopListAdapter.Companion.ENABLED_VIEW
import com.example.shoppinglist.presentor.ShopListAdapter.Companion.MAX_POOL_SIZE
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(),ShopItemFragment.OnFinishEditingListener {
    private var itemContainer: FragmentContainerView? = null
    lateinit var viewModel: MainViewModel
    lateinit var rcView: RecyclerView
    lateinit var shopListAdapter: ShopListAdapter
    lateinit var addButton: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRcView()
        itemContainer = findViewById(R.id.item_container)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }
    }

    private fun initRcView() {
        rcView = findViewById(R.id.rv_shop_list)
        addButton = findViewById(R.id.button_add_shop_item)
        shopListAdapter = ShopListAdapter()
        rcView.adapter = shopListAdapter
        rcView.recycledViewPool.setMaxRecycledViews(ENABLED_VIEW, MAX_POOL_SIZE)
        rcView.recycledViewPool.setMaxRecycledViews(ShopListAdapter.DISABLED_VIEW, MAX_POOL_SIZE)
        setupClickListener()
        setUpLongClick()
        setupSwipeListener()
        addButton.setOnClickListener {
            if (inOneLineViews()) {
                val intent = ShopItemActivity.getAddIntent(this)
                startActivity(intent)
            } else {
                val fragment = launchFragment(ShopItemFragment.getAddFragmentInstance())
            }
        }
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.item_container, fragment)
            .commit()
    }

    private fun inOneLineViews(): Boolean {
        return (itemContainer == null)
    }

    private fun setUpLongClick() {
        shopListAdapter.onShopItemLongClick = {
            viewModel.changeShopItem(it)
        }
    }

    private fun setupClickListener() {
        shopListAdapter.onShopItemClick = {
            if (inOneLineViews()) {
                val intent = ShopItemActivity.getEditIntent(this, it.id)
                startActivity(intent)
            } else {
                val fragment = launchFragment(ShopItemFragment.getEditFragmentInstance(it.id))
            }
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

    override fun onFinishEdit() {
        supportFragmentManager.popBackStack()
    }


}