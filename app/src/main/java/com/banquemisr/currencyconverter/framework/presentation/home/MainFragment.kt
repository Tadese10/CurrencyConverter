package com.banquemisr.currencyconverter.framework.presentation.home

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.utils.MDUtil.textChanged
import com.banquemisr.currencyconverter.business.domain.state.StateMessageCallback
import com.banquemisr.currencyconverter.framework.presentation.common.BaseFragment
import com.banquemisr.currencyconverter.framework.presentation.home.state.HomeStateEvent
import com.banquemisr.currencyconverter.util.printLogD
import com.google.gson.internal.LinkedTreeMap
import de.schaldrak.listviewwithheaders.adapter.CustomAdapter
import de.schaldrak.listviewwithheaders.model.TempModel
import de.schaldrak.listviewwithheaders.tools.doAfterItemSelected
import de.schaldrak.listviewwithheaders.tools.setSelectedItem
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import om.banquemisr.currencyconverter.R
import java.text.DecimalFormat
import java.util.*

@ExperimentalCoroutinesApi
class MainFragment
constructor(
    private val viewModelFactory: ViewModelProvider.Factory
): BaseFragment(R.layout.fragment_home) {

    @OptIn(FlowPreview::class)
    val viewModel: HomeViewModel by viewModels {
        viewModelFactory
    }
    private val ToDropDownLIst: MutableList<TempModel> = mutableListOf()
    private val FromDropDownList: MutableList<TempModel> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
    }

    @OptIn(FlowPreview::class)
    private fun subscribeObservers(){

        viewModel.viewState.observe(viewLifecycleOwner, Observer { d ->

            d.Symbols?.symbols?.let {
                setListItem(it)
                printLogD("Result, ", d.Symbols.toString())
            }

            d.CurrencyResponse?.let {
                updateCurrencyCalculatedValue(it.result)
                printLogD("Result, ", d.CurrencyResponse.toString())

            }

        })

        viewModel.shouldDisplayProgressBar.observe(viewLifecycleOwner, Observer {
            //printActiveJobs()
            uiController.displayProgressBar(it)
            if(!it){
                refreshLayout.isRefreshing = false
            }
        })

        viewModel.stateMessage.observe(viewLifecycleOwner, Observer { stateMessage ->
            printLogD("stateMessage", stateMessage.toString())
            refreshLayout.isRefreshing = false

            stateMessage?.response?.let {
                it.message?.let {
                    uiController.onResponseReceived(
                        response = stateMessage.response,
                        stateMessageCallback = object : StateMessageCallback {
                            override fun removeMessageFromStack() = viewModel.clearStateMessage()
                        }
                    )
                    }
                }

        })

        viewModel.setStateEvent(HomeStateEvent.GetAllSymbolsEvent())

        refreshLayout.setOnRefreshListener {
            viewModel.setStateEvent(HomeStateEvent.GetAllSymbolsEvent())
        }

        btnDetails.setOnClickListener {
            navDetailsFragment()
        }

        btnSwap.setOnClickListener {

            it.postDelayed({
                if(!fromText.text.isNullOrEmpty() && !toText.text.isNullOrEmpty()) {
                    val toTextValue = toText.text.toString()
                    val fromTextValue = fromText.text.toString()

                    FromDropDownList.clear()
                    val data = Arrays.asList(viewModel.getSymbols()?.symbols?.keys!!.toList())
                    var count = 1
                    for (item in data[0]) {
                        FromDropDownList.add(TempModel(headerName = "", childName = String.format("%s", item), idChild = count, isHeader = false))
                        count += 1
                    }

                    count = 1;

                    fromText.setAdapter(CustomAdapter(requireContext(), FromDropDownList))
                    fromText.setSelectedItem(data[0].indexOf(toTextValue))

                    ToDropDownLIst.clear()
                    val items = Arrays.asList(viewModel.getSymbols()?.symbols!!.keys.toList())
                    items[0]!!.drop(items[0].indexOf(toTextValue))
                    for (item in items[0]) {
                        ToDropDownLIst.add(TempModel(headerName = "", childName = String.format("%s", item), idChild = count, isHeader = false))
                        count += 1
                    }

                    toText.setAdapter(CustomAdapter(requireContext(), ToDropDownLIst))
                    toText.setSelectedItem(items[0].indexOf(fromTextValue))

                    val tempValue = toValue.text
                    toValue.text = fromValue.text
                    fromValue.text = tempValue
                }
            },100)
        }

        fromValue.textChanged {
            if(!fromText.text.isNullOrEmpty() && !toText.text.isNullOrEmpty() && !fromValue.text.isNullOrEmpty()) {
                convertCurrency(fromText.text.toString(), toText.text.toString())
            }
        }
    }

    private fun updateCurrencyCalculatedValue(result: Double?) {
        toValue.text = result.toString().toEditable()
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    @OptIn(FlowPreview::class)
    private fun setListItem(data: LinkedTreeMap<String, String>) {
        FromDropDownList.clear()
        var count = 1
        for (item: String in data.keys) {
            FromDropDownList.add(TempModel(headerName = "", childName = String.format("%s", item, data[item]), idChild = count, isHeader = false))
            count += 1
        }

        count = 1;

        fromText.setAdapter(CustomAdapter(requireContext(), FromDropDownList))
        //fromText.setSelectedItem(0)

        from.doAfterItemSelected { selectedIndex ->
            ToDropDownLIst.clear()
            val items = arrayListOf(viewModel.getSymbols()?.symbols!!.keys.toList())
            for (item in items[0]) {
                if(item != items[0].elementAt(selectedIndex)){
                    ToDropDownLIst.add(TempModel(headerName = "", childName = String.format("%s", item), idChild = count, isHeader = false))
                    count += 1
                }
            }

            toText.setAdapter(CustomAdapter(requireContext(), ToDropDownLIst))
            toText.setSelectedItem(0)

            convertCurrency(fromText.text.toString(), toText.text.toString())
        }

        To.doAfterItemSelected { selectedIndex ->
            convertCurrency(fromText.text.toString(), toText.text.toString())
        }
    }

    private fun convertCurrency(fromCurrency: String, toCurrency: String) {
        printLogD("Payload", "From: $fromCurrency, To: $toCurrency")

        viewModel.setStateEvent(HomeStateEvent.ConvertCurrencyEvent(fromCurrency, toCurrency, fromValue.text.toString()))
    }

    private fun navDetailsFragment(){
        val bundle = Bundle()
        bundle.putString("base", fromText.text.toString())
        bundle.putString("symbols", toText.text.toString())
        findNavController().navigate(R.id.action_to_detailsFragment, bundle)
    }

    fun currencyFormat(amount: String): String? {
        val formatter = DecimalFormat("###,###,##0.00")
        return formatter.format(amount.replace(",","").toDouble())
    }
    override fun inject() {
        getAppComponent().inject(this)
    }

}