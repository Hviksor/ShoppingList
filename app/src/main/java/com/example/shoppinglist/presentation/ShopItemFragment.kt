package com.example.shoppinglist.presentation

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

class ShopItemFragment(
    private val screenMode: String = MODE_UNKNOWN,
    private val shopItemId: Int = ShopItem.DEFAULT_ID
) : Fragment() {
    lateinit var binding: ShopItemFragmentBinding
    lateinit var viewModel: ShopItemViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ShopItemFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parsParam()
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
            activity?.finish()
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

    private fun launchModeAdd() = with(binding) {
        buttonSave.setOnClickListener {
            viewModel.addShopItem(etName.text.toString(), etCount.text.toString())
        }
    }

    private fun parsParam() {
        if (screenMode != MODE_ADD && screenMode != MODE_EDIT) {
            throw RuntimeException("Неизвестный мод экрана")
        }

        if (screenMode == MODE_EDIT && shopItemId < 0) {
            throw RuntimeException("ИД покупки не найдено")
        }

    }

    companion object {
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOWN = ""


        fun getInstanceAddFragment(): ShopItemFragment {
            return ShopItemFragment(MODE_ADD)
        }

        fun getInstanceEditFragment(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment(MODE_EDIT, shopItemId)
        }
    }
}