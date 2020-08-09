package no.kristiania.covid19stats.ui.details

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_details.*
import no.kristiania.covid19stats.R
import no.kristiania.covid19stats.data.api.Covid19StatsApi
import no.kristiania.covid19stats.data.repository.DetailsRepository
import no.kristiania.covid19stats.data.utils.NetworkConnectionInterceptor

class DetailsActivity : AppCompatActivity() {

    private var country: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        country = intent.getStringExtra("country")
        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = Covid19StatsApi(networkConnectionInterceptor)
        val repository = DetailsRepository(api)

        val viewModel by viewModels<DetailsViewModel> {
            DetailsViewModelFactory(
                repository,
                country!!
            )
        }
        viewModel.details.observe(this, Observer { details ->

            val confirmedCases = details
            confirmed_number.text = confirmedCases.toString()

        })
    }
}