package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(),ShopItemFragment.OnEditingFinishListener {
    private var itemShopContainer: FragmentContainerView? = null
    private lateinit var viewModel: MainViewModel
    private lateinit var shopItemAdapter: ShopListAdapter
    private lateinit var rcview: RecyclerView
    private lateinit var buttonAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRCV()
        itemShopContainer = findViewById(R.id.item_shop_container)
        buttonAdd = findViewById(R.id.button_add_shop_item)
        buttonAdd.setOnClickListener {
            if (isViewsInOneLine()) {
                val intent = ShopItemActivity.getAddIntent(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.getInstanceAddFragment())
            }

        }
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopItemAdapter.submitList(it)
        }
    }

    private fun isViewsInOneLine(): Boolean {
        return (itemShopContainer == null)
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.item_shop_container, fragment)
            .addToBackStack(null)
            .commit()
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
                val item = shopItemAdapter.currentList[viewHolder.adapterPosition]
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
            if (isViewsInOneLine()) {
                val intent = ShopItemActivity.getEditIntent(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.getInstanceEditFragment(it.id))
            }
        }


    }

    override fun onEditingFinished() {
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

}