package com.banquemisr.currencyconverter.business.domain.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.banquemisr.currencyconverter.util.printLogD


class StateEventManager {

    private val activeStateEvents: HashMap<String, StateEvent> = HashMap()

    private val _shouldDisplayProgressBar: MutableLiveData<Boolean> = MutableLiveData()

    val shouldDisplayProgressBar: LiveData<Boolean>
            get() = _shouldDisplayProgressBar

    fun getActiveJobNames(): MutableSet<String>{
        return activeStateEvents.keys
    }

    fun clearActiveStateEventCounter(){
        printLogD("DCM", "Clear active state events")
        //EspressoIdlingResource.clear()
        activeStateEvents.clear()
        syncNumActiveStateEvents()
    }

    fun addStateEvent(stateEvent: StateEvent){
        //EspressoIdlingResource.increment()
        activeStateEvents.put(stateEvent.eventName(), stateEvent)
        syncNumActiveStateEvents()
    }

    fun removeStateEvent(stateEvent: StateEvent?){
        printLogD("DCM", "remove state event: ${stateEvent?.eventName()}")
        stateEvent?.let {
          //  EspressoIdlingResource.decrement()
        }
        activeStateEvents.remove(stateEvent?.eventName())
        syncNumActiveStateEvents()
    }

    fun isStateEventActive(stateEvent: StateEvent): Boolean{
        printLogD("DCM", "is state event active? " +
                "${activeStateEvents.containsKey(stateEvent.eventName())}")
        return activeStateEvents.containsKey(stateEvent.eventName())
    }

    private fun syncNumActiveStateEvents(){
        var shouldDisplayProgressBar = false
        for(stateEvent in activeStateEvents.values){
            if(stateEvent.shouldDisplayProgressBar()){
                shouldDisplayProgressBar = true
            }
        }
        _shouldDisplayProgressBar.value = shouldDisplayProgressBar
    }
}

















