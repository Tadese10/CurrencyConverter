package com.banquemisr.currencyconverter.framework.presentation.details

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.banquemisr.currencyconverter.business.domain.state.StateMessageCallback
import com.banquemisr.currencyconverter.framework.datasource.network.model.ItemsViewModel
import com.banquemisr.currencyconverter.framework.presentation.common.BaseFragment
import com.banquemisr.currencyconverter.framework.presentation.details.state.DetailsStateEvent
import com.banquemisr.currencyconverter.util.printLogD
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import om.banquemisr.currencyconverter.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@ExperimentalCoroutinesApi
class DetailsFragment
constructor(
    private val viewModelFactory: ViewModelProvider.Factory
): BaseFragment(R.layout.fragment_details) {

    private lateinit var baseValue: String
    private lateinit var symbols: String

    @OptIn(FlowPreview::class)
    val viewModel: DetailsViewModel by viewModels {
        viewModelFactory
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
    }

    @OptIn(FlowPreview::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        baseValue = requireArguments().getString("base").toString()
        symbols = requireArguments().getString("symbols").toString()

        StartEvent()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun StartEvent(){
        val endDate = LocalDate.now( ZoneId.of( "America/Montreal" ) )
            .minusDays( 2 )

        val startDate = LocalDate.now( ZoneId.of( "America/Montreal" ))


        viewModel.setStateEvent(DetailsStateEvent.GetCurrencyConversionHistories(startDate.toString(), endDate.toString(), baseValue, symbols))

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun subscribeObservers(){

        viewModel.viewState.observe(viewLifecycleOwner, Observer { d ->
            d.Histories?.let {
                updateChartData(it.rates)
            }
        })

        viewModel.shouldDisplayProgressBar.observe(viewLifecycleOwner, Observer {
            //printActiveJobs()
            uiController.displayProgressBar(it)

        })

        viewModel.stateMessage.observe(viewLifecycleOwner, Observer { stateMessage ->
            printLogD("stateMessage", stateMessage.toString())

        })

        viewModel.stateMessage.observe(viewLifecycleOwner, Observer { stateMessage ->
            printLogD("stateMessage", stateMessage.toString())

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
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateChartData(rates: LinkedTreeMap<String, LinkedTreeMap<String, String>>?) {

       var data = arrayListOf<AASeriesElement>()
        val items = ArrayList<ItemsViewModel>()

        var today = rates?.get(LocalDate.now( ZoneId.of( "America/Montreal" ) ).toString())?.values
        var yesterday = rates?.get(LocalDate.now( ZoneId.of( "America/Montreal" ) ).minusDays( 1 ).toString())?.values
        var thirdDay = rates?.get(LocalDate.now( ZoneId.of( "America/Montreal" ) ).minusDays( 2 ).toString())?.values

        for(number in 1..3){
            if(number == 1){
                var item = AASeriesElement()
                item.name(LocalDate.now( ZoneId.of( "America/Montreal" ) ).toString())
                item.data(today?.toTypedArray()!!)
                data.add(item)
                items.add(ItemsViewModel( "${LocalDate.now( ZoneId.of( "America/Montreal" ) ).toString()}. $baseValue -> $symbols = ${today.toString()}"))
            }else if(number == 2){
                var item = AASeriesElement()
                item.name(LocalDate.now( ZoneId.of( "America/Montreal" ) ).minusDays( 1 ).toString())
                item.data(yesterday?.toTypedArray()!!)
                data.add(item)
                items.add(ItemsViewModel( "${LocalDate.now( ZoneId.of( "America/Montreal" ) ).minusDays( 1 ).toString()}. $baseValue -> $symbols = ${yesterday.toString()}"))

            }else{
                var item = AASeriesElement()
                item.name(LocalDate.now( ZoneId.of( "America/Montreal" ) ).minusDays( 2).toString())
                item.data(thirdDay?.toTypedArray()!!)
                data.add(item)
                items.add(ItemsViewModel( "${LocalDate.now( ZoneId.of( "America/Montreal" ) ).minusDays( 2 ).toString()}. $baseValue -> $symbols = ${thirdDay.toString()}"))
            }
        }

        val aaChartModel : AAChartModel = AAChartModel()
            .chartType(AAChartType.Line)
            .dataLabelsEnabled(true)
            .title("Last 3 days (day by day)")
            .backgroundColor("#4b2b7f")
            .series(data.toArray())

        lastThreeDaysChart.aa_drawChartWithChartModel(aaChartModel)

        lastThreeListView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = CustomAdapter(items)
        lastThreeListView.adapter = adapter
    }

    @OptIn(FlowPreview::class)
    override fun inject() {
        getAppComponent().inject(this)
    }

}