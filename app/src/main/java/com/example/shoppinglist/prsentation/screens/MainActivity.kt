package com.example.shoppinglist.prsentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityMainBinding
import com.example.shoppinglist.databinding.ActivityShopItemBinding
import com.example.shoppinglist.prsentation.viewModel.MainViewModel
import com.example.shoppinglist.prsentation.ShopListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw RuntimeException("ActivityMainBinding=null")
    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private lateinit var shopItemAdapter: ShopListAdapter
    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        viewModel.shopList.observe(this) {
            shopItemAdapter.submitList(it)
        }
        val buttonAddItem = binding.buttonAddShopItem
        buttonAddItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.getAddIntent(this)
                startActivity(intent)
            } else {
                val fragment = ShopItemFragment.newInstanceAddItem()
                fragment.onEditingFinishListener = object : ShopItemFragment.OnEditingFinishedListener {
                    override fun onEditingFinished() {
                        Toast.makeText(this@MainActivity, "succesc", Toast.LENGTH_SHORT).show()
                        supportFragmentManager.popBackStack()
                    }
                }
                launchFragment(fragment)
            }
        }
    }

    fun onEditingFinish() {
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun isOnePaneMode(): Boolean {
        return binding.shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecyclerView() {
        val rvShopList = binding.rvShopList
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
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.getEditIntent(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }
        }

    }


    private fun setupLongClickListener() {
        shopItemAdapter.onShopItemOnLongClick = {
            viewModel.changeEnableState(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}