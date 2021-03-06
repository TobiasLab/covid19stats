package no.kristiania.covid19stats.ui.countrylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_countries.*
import no.kristiania.covid19stats.R
import no.kristiania.covid19stats.data.api.Covid19StatsApi
import no.kristiania.covid19stats.data.models.countrylist.Country
import no.kristiania.covid19stats.data.models.countrylist.Global
import no.kristiania.covid19stats.data.repository.CountryListRepository
import no.kristiania.covid19stats.data.utils.NetworkConnectionInterceptor
import no.kristiania.covid19stats.ui.details.DetailsActivity
import no.kristiania.covid19stats.ui.utils.Listener
import okhttp3.logging.HttpLoggingInterceptor

//Splash screen code taken from https://gist.github.com/liminal/240095c679dc57108b6b9b3782ca0cc5

class CountryListActivity : AppCompatActivity(), Listener {

    private lateinit var adapter: CountryListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries)

        progress_wheel.visibility = View.VISIBLE

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE;
        }
        val api = Covid19StatsApi(networkConnectionInterceptor, httpLoggingInterceptor)
        val repository = CountryListRepository(api)

        val viewModel by viewModels<CountryListViewModel> { CountryListViewModelFactory(repository) }
        viewModel.countryList.observe(this, Observer { country ->
            recyclerview_countries.also {
                adapter = country?.let { sorted -> CountryListAdapter(sorted, this) }!!
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(this)
                progress_wheel.visibility = View.INVISIBLE
            }
        })

        val viewModel2 by viewModels<GlobalListViewModel> { GlobalListViewModelFactory(repository) }
        viewModel2.globalList.observe(this, Observer { global ->

            global_cases.text = global.totalConfirmed.toString()
            global_deaths.text = global.totalDeaths.toString()
            global_recovered.text = global.totalRecovered.toString()
        })

    }

    override fun onListClick(country: String) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("country", country)
        startActivity(intent)
    }
}