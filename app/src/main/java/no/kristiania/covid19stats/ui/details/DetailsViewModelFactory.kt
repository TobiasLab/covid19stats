package no.kristiania.covid19stats.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import no.kristiania.covid19stats.data.repository.DetailsRepository

class DetailsViewModelFactory(private val repository: DetailsRepository,
    private val country: String) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(repository, country) as T
    }
}