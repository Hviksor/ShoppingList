package com.example.shoppinglist.presentor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.FragmentShopItemBinding
import com.example.shoppinglist.domain.ShopItem


class ShopItemFragment : Fragment() {
    private lateinit var onFinishEditingListener: OnFinishEditingListener
    private var shopItemId = ShopItem.DEFAULT_ID
    private var screenMode = UNKNOWN_MODE
    lateinit var viewModel: ShopItemViewModel
    private lateinit var binding: FragmentShopItemBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFinishEditingListener) {
            onFinishEditingListener = context
        } else {
            throw RuntimeException("")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsElements()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopItemBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        addChangeTextListener()
        setupViewModelFields()
        selectScreenMode()
    }

    private fun setupViewModelFields() {
        viewModel.finishWork.observe(viewLifecycleOwner) {
            onFinishEditingListener.finishEditing()
        }
        viewModel.errorName.observe(viewLifecycleOwner) {
            if (it) {
                binding.itName.error = "Error"
            }
        }
        viewModel.errorCount.observe(viewLifecycleOwner) {
            if (it) {
                binding.itCount.error = "Error"
            }
        }
    }

    private fun addChangeTextListener() {
        binding.tvName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.itName.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.tvCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.itCount.error = null
            }

            override fun afterTextChanged(s: Editable?) {


            }
        })
    }

    private fun selectScreenMode() {
        when (screenMode) {
            ADD_MODE -> launchAddMode()
            EDIT_MODE -> launchEditMode()
            else -> throw RuntimeException("Unknown screen in fragment mode: $screenMode")
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.value?.let {
            binding.tvName.setText(it.name)
            binding.tvCount.setText(it.count.toString())
        }

        binding.saveButton.setOnClickListener {
            val name = binding.tvName.text.toString()
            val count = binding.tvCount.text.toString()
            viewModel.editShopItem(name, count)
        }
    }

    private fun launchAddMode() {
        binding.saveButton.setOnClickListener {
            val name = binding.tvName.text.toString()
            val count = binding.tvCount.text.toString()
            viewModel.addShopItem(name, count)
        }
    }


    private fun parsElements() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Screen mode in fragment is absent!")
        }
        screenMode = args.getString(EXTRA_SCREEN_MODE).toString()
        if (screenMode == EDIT_MODE) {
            if (!args.containsKey(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Item id  in fragment is absent!")
            }
            shopItemId = args.getInt(EXTRA_SHOP_ITEM_ID, ShopItem.DEFAULT_ID)
        }

    }

    interface OnFinishEditingListener {
        fun finishEditing()


    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "screen_mode"
        private const val EXTRA_SHOP_ITEM_ID = "shop_item_id"
        private const val ADD_MODE = "add_mode"
        private const val EDIT_MODE = "edit_mode"
        private const val UNKNOWN_MODE = "unknown_mode"

        fun getAddInstance(): Fragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, ADD_MODE)
                }
            }
        }

        fun getEditInstance(shopItemId: Int): Fragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, EDIT_MODE)
                    putInt(EXTRA_SHOP_ITEM_ID, shopItemId)
                }
            }
        }

    }
}