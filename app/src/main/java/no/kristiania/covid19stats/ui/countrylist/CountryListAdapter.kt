package no.kristiania.covid19stats.ui.countrylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.countries_row.view.*
import no.kristiania.covid19stats.R
import no.kristiania.covid19stats.data.models.countrylist.CountriesResponse
import no.kristiania.covid19stats.data.models.countrylist.Country
import no.kristiania.covid19stats.data.models.countrylist.Global
import no.kristiania.covid19stats.ui.utils.Listener

class CountryListAdapter(private val country: List<Country>, var onClickListener: Listener) :
    RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.countries_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return country.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewHolder(country[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindViewHolder(item: Country) {

            itemView.country_total.text = item.totalConfirmed.toString()
            itemView.country_name.text = item.country



            itemView.countries_row.setOnClickListener {
                onClickListener.onListClick(item.country)
            }
        }
    }
}