package no.kristiania.covid19stats.data.repository

import no.kristiania.covid19stats.data.api.Covid19StatsApi
import no.kristiania.covid19stats.data.models.countrylist.Country
import no.kristiania.covid19stats.data.utils.SafeApiRequest

class CountryListRepository(private val api: Covid19StatsApi) : SafeApiRequest() {
    suspend fun getCountry(): List<Country>? {
        val countryResponse = apiRequest { api.fetchCountries() }
        return countryResponse?.countries?.sortedByDescending { it.totalConfirmed }//countryResponse!!.countries
    }
}