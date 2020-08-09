package no.kristiania.covid19stats.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import no.kristiania.covid19stats.data.repository.DetailsRepository

class DetailsViewModel(private val repository: DetailsRepository, private val country: String) :
    ViewModel() {
    val details = liveData {
        emit(repository.getCountryDetails(country))
    }
}