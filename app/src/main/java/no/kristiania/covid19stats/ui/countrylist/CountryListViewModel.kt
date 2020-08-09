package no.kristiania.covid19stats.ui.countrylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import no.kristiania.covid19stats.data.repository.CountryListRepository

class CountryListViewModel(private val repository: CountryListRepository) : ViewModel() {
    val countryList = liveData {
        emit(repository.getCountry())
    }
}