package no.kristiania.covid19stats.data.api

import no.kristiania.covid19stats.data.models.countrylist.CountriesResponse
import no.kristiania.covid19stats.data.models.details.DetailsResponse
import no.kristiania.covid19stats.data.utils.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Covid19StatsApi {
    @GET("/summary")
    suspend fun fetchCountries(): Response<CountriesResponse>

    @GET("/country")
    suspend fun fetchCountryDetails(@Query("country") country: String): Response<DetailsResponse>

    companion object {


        operator fun invoke(
            networkInterceptor: NetworkConnectionInterceptor
        ): Covid19StatsApi {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.covid19api.com")
                .build()
                .create(Covid19StatsApi::class.java)
        }
    }
}