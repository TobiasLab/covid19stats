package no.kristiania.covid19stats.data.models.countrylist


import com.google.gson.annotations.SerializedName

data class CountriesResponse(
    @SerializedName("Countries")
    val countries: List<Country>,
    @SerializedName("Global")
    val global: Global
)
