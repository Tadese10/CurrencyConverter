package com.banquemisr.currencyconverter.business.interactors.details

import com.banquemisr.currencyconverter.framework.datasource.network.model.Histories

class DetailsInteractors (
    val histories: GetHistories,
    val popularHistories: GetPopularHistories
)