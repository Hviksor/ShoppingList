package com.example.shoppinglist.prsentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.databinding.FragmentShopItemBinding
import com.example.shoppinglist.domain.ShopItem

class ShopItemFragment(
    private val screenMode: String = UNKNOWN_MODE,
    private val shopItemId: Int = ShopItem.DEFAULT_ID
) : Fragment() {
    private lateinit var binding: FragmentShopItemBinding
    private lateinit var viewModel: ShopItemViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopItemBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        selectScreenMode()
        addChangeTextListener()
        addViewModelFields()
    }

    private fun addViewModelFields() = with(binding) {
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            if (it) {
                tillCount.error = "Error"
            }
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            if (it) {
                tillName.error = "Error"
            }
        }
        viewModel.isCloseScreen.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }

    }

    private fun addChangeTextListener() = with(binding) {

        edName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tillName.error = null
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        edCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tillCount.error = null
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun selectScreenMode() {
        when (screenMode) {
            ADD_MODE -> launchModeAdd()
            EDIT_MODE -> launchModeEdit()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
    }

    private fun launchModeEdit() = with(binding) {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner) {
            edName.setText(it.name)
            edCount.setText(it.count.toString())
        }
        button.setOnClickListener {
            viewModel.editShopItem(edName.text.toString(), edCount.text.toString())
        }
    }

    private fun launchModeAdd() = with(binding) {
        button.setOnClickListener {
            viewModel.addShopItem(edName.text.toString(), edCount.text.toString())
        }

    }


    private fun parseParams() {
        if (screenMode != EDIT_MODE && screenMode != ADD_MODE) {
            throw RuntimeException("Unknown screen mode $screenMode")
        }
        if (screenMode == EDIT_MODE && shopItemId == ShopItem.DEFAULT_ID) {
            throw RuntimeException("SHOP_ITEM_ID mode is absent")
        }
    }


    companion object {
        private const val ADD_MODE = "add_mode"
        private const val EDIT_MODE = "edit_mode"
        private const val UNKNOWN_MODE = "unknown_mode"


        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment(ADD_MODE)
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment(EDIT_MODE, shopItemId)
        }


    }
}