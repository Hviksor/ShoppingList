package com.example.shoppinglist.presentation

import android.content.Context
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
import kotlin.concurrent.fixedRateTimer

class ShopItemFragment() : Fragment() {
    lateinit var binding: ShopItemFragmentBinding
    lateinit var viewModel: ShopItemViewModel
    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.DEFAULT_ID
    private lateinit var onEditingFinishListener: OnEditingFinishListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishListener) {
            onEditingFinishListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsParam()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ShopItemFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        setViewModelFun()
        addChangeTextListener()
        selectMod()
    }

    private fun addChangeTextListener() = with(binding) {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                inputLayoutName.error = null
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                inputLayoutCount.error = null
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun setViewModelFun() = with(binding) {

        viewModel.errorInputName.observe(viewLifecycleOwner) {
            if (it) {
                inputLayoutName.error = "Error"
            }
        }
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            if (it) {
                inputLayoutCount.error = "Error"
            }
        }
        viewModel.isCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishListener.onEditingFinished()
        }

    }

    private fun selectMod() {
        when (screenMode) {
            MODE_ADD -> launchModeAdd()
            MODE_EDIT -> launchModeEdit()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
    }

    private fun launchModeEdit() = with(binding) {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        buttonSave.setOnClickListener {
            viewModel.editShopItem(etName.text.toString(), etCount.text.toString())

        }

    }

    interface OnEditingFinishListener {
        fun onEditingFinished()
    }

    private fun launchModeAdd() = with(binding) {
        buttonSave.setOnClickListener {
            viewModel.addShopItem(etName.text.toString(), etCount.text.toString())
        }
    }

    private fun parsParam() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Параметр скрин мода отсутствует")
        }
        val mode = args.getString(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Неизвестный мод экрана")
        }
        screenMode = mode
        if (mode == MODE_EDIT) {
            if (!args.containsKey(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("ИД покупки не найдено")
            }
            shopItemId = args.getInt(EXTRA_SHOP_ITEM_ID, ShopItem.DEFAULT_ID)
        }

    }

    companion object {
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOWN = ""
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"


        fun getInstanceAddFragment(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun getInstanceEditFragment(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_EDIT)
                    putInt(EXTRA_SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }
}