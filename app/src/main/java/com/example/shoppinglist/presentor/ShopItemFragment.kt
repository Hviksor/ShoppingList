package com.example.shoppinglist.presentor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.databinding.ShopItemFragmentBinding
import com.example.shoppinglist.domain.ShopItem

class ShopItemFragment : Fragment() {
    private var screenMode = UNKNOWN_MODE
    private lateinit var viewModel: ShipItemViewModel
    private var shopItemId = ShopItem.ID_DEFAULT
    lateinit var binding: ShopItemFragmentBinding
    lateinit var onFinishEditingListener: OnFinishEditingListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ShopItemFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFinishEditingListener) {
            onFinishEditingListener = context
        } else {
            throw  RuntimeException("")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsIntent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ShipItemViewModel::class.java]
        selectScreenMode()
        setTextChangeListener()
        setViewModel()

    }

    private fun parsIntent() {
        val arg = requireArguments()
        if (!arg.containsKey(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Screen mode is absent")
        }
        val mode = arg.getString(EXTRA_SCREEN_MODE)
        if (mode != ADD_MODE && mode != EDIT_MODE) {
            throw RuntimeException("Screen mode unknown")
        }
        screenMode = mode
        if (screenMode == EDIT_MODE) {
            if (!arg.containsKey(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("ShopItemId is absent")
            }
            shopItemId = arg.getInt(EXTRA_SHOP_ITEM_ID, ShopItem.ID_DEFAULT)
        }
    }

    private fun selectScreenMode() {
        when (screenMode) {
            ADD_MODE -> launchAddMode()
            EDIT_MODE -> launchEditMode()
            else -> throw RuntimeException("Screen mode unknown")
        }
    }

    private fun launchEditMode() = with(binding) {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner) {
            eTName.setText(it.name)
            eTCount.setText(it.count.toString())
        }
        saveItem.setOnClickListener {
            val name = eTName.text.toString()
            val count = eTCount.text.toString()
            viewModel.editShopItem(name, count)
        }
    }

    private fun launchAddMode() = with(binding) {
        saveItem.setOnClickListener {
            val name = eTName.text.toString()
            val count = eTCount.text.toString()
            viewModel.addShopItem(name, count)
        }

    }

    private fun setViewModel() {
        viewModel.errorCount.observe(viewLifecycleOwner) {
            if (it) {
                binding.iLCount.error = "Error"
            }
        }
        viewModel.errorName.observe(viewLifecycleOwner) {
            if (it) {
                binding.iLName.error = "Error"
            }
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onFinishEditingListener.onFinishEdit()
        }
    }

    private fun setTextChangeListener() {
        binding.eTName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.iLName.error = null
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.eTCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.iLCount.error = null
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
    interface OnFinishEditingListener {
        fun onFinishEdit()
    }


    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_screen_mode"
        private const val EXTRA_SHOP_ITEM_ID = "shop_item_id"
        private const val ADD_MODE = "add_mode"
        private const val EDIT_MODE = "edit_mode"
        private const val UNKNOWN_MODE = "unknown_mode"

        fun getAddFragmentInstance(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, ADD_MODE)
                }
            }

        }

        fun getEditFragmentInstance(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, EDIT_MODE)
                    putInt(EXTRA_SHOP_ITEM_ID, shopItemId)
                }
            }
        }


    }
}