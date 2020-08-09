package no.kristiania.covid19stats.ui.countrylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import no.kristiania.covid19stats.data.repository.CountryListRepository

class GlobalListViewModelFactory(private val repository: CountryListRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GlobalListViewModel(repository) as T
    }
}