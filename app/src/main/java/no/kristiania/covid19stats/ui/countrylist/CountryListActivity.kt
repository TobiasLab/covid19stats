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

class CountryListActivity : AppCompatActivity(), Listener {

    private lateinit var adapter: CountryListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = Covid19StatsApi(networkConnectionInterceptor)
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
    }

    override fun onListClick(country: String) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("country", country)
        startActivity(intent)
    }
}