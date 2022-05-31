package com.example.shoppinglist.presentor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ShopItemFragment.OnFinishEditingListener {
    private lateinit var shopListAdapter: ShopListAdapter
    private var fragmentContainer: FragmentContainerView? = null
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
            if (!isOneLineViews()) {
                val intent = ShopItemActivity.getAddIntent(this)
                startActivity(intent)
            } else {
                setUpFragment(ShopItemFragment.getAddInstance())

            }

        }
        setupOnClickListener()
        setupOnLongClickListener()
        setupOnSwipeListener()
    }

    fun setUpFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()

    }

    private fun isOneLineViews(): Boolean {
        return fragmentContainer != null
    }

    private fun initViews() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        rcView = binding.rcViewShopList
        rcView.layoutManager = LinearLayoutManager(this)
        addButton = binding.btAddItem
        shopListAdapter = ShopListAdapter()
        rcView.adapter = shopListAdapter
        fragmentContainer = findViewById(R.id.fragment_container)

    }

    private fun setupOnClickListener() {
        shopListAdapter.shopItemClick = {


            if (!isOneLineViews()) {
                val intent = ShopItemActivity.getEditIntent(this, it.id)
                startActivity(intent)
            } else {
                setUpFragment(ShopItemFragment.getEditInstance(it.id))

            }
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

    override fun finishEditing() {
        supportFragmentManager.popBackStack()
    }


}