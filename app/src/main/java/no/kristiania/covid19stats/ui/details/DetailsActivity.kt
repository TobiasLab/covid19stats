package no.kristiania.covid19stats.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_details.*
import no.kristiania.covid19stats.R
import no.kristiania.covid19stats.data.api.Covid19StatsApi
import no.kristiania.covid19stats.data.repository.DetailsRepository
import no.kristiania.covid19stats.data.utils.NetworkConnectionInterceptor
import okhttp3.logging.HttpLoggingInterceptor

class DetailsActivity : AppCompatActivity() {

    private var country: String? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        country = intent.getStringExtra("country")
        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS;
        }
        val api = Covid19StatsApi(networkConnectionInterceptor, httpLoggingInterceptor)
        val repository = DetailsRepository(api)

        val viewModel by viewModels<DetailsViewModel> {
            DetailsViewModelFactory(
                repository,
                country!!
            )
        }
        viewModel.details.observe(this, Observer { details ->

            val info = details!![details.lastIndex]
            val newConfirmed = details!![details.lastIndex-1]

            confirmed_number.text = info.confirmed.toString()
            recovered_number.text = info.recovered.toString()
            deaths_number.text = info.deaths.toString()

            confirmed_new.text = "+" + (info.confirmed - newConfirmed.confirmed).toString()
            deaths_new.text = "+" + (info.deaths - newConfirmed.deaths).toString()






        })
    }
}