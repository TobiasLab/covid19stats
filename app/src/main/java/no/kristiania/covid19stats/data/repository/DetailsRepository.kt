package no.kristiania.covid19stats.data.repository

import no.kristiania.covid19stats.data.api.Covid19StatsApi
import no.kristiania.covid19stats.data.models.details.DetailsItem
import no.kristiania.covid19stats.data.models.details.DetailsResponse
import no.kristiania.covid19stats.data.utils.SafeApiRequest

class DetailsRepository(private val api: Covid19StatsApi) : SafeApiRequest() {
    suspend fun getCountryDetails(country: String): DetailsResponse? {
        val countryDetailsResponse = apiRequest { api.fetchCountryDetails(country) }
        return countryDetailsResponse
    }
}