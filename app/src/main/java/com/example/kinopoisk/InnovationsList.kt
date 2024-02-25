package com.example.kinopoisk

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load

data class InnovationsItem(
    val id: Int,
    var image: String,
    var nameRu: String,
    var nameEn: String,
    var year: Int,
    var duration: Int,
    var country: String,
    var genre: String,
    var date: String
)

class InnovationsListAdapter(
    private var list: List<InnovationsItem>
): RecyclerView.Adapter<InnovationsItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnovationsItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.innovations_list_item,parent, false)
        return InnovationsItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: InnovationsItemViewHolder, position: Int) {
        holder.imgV.load(list[position].image) {
            crossfade(true)
        }

        var yearTimeName = ""

        if (list[position].nameEn != "") {
            yearTimeName =
                list[position].nameEn + ", " + list[position].year.toString() + ", " + list[position].duration.toString() + " мин."
        } else {
            yearTimeName =
                list[position].year.toString() + ", " + list[position].duration.toString() + " мин."
        }

        holder.nameV.text = list[position].nameRu
        holder.yearTimeNameV.text = yearTimeName
        holder.countryV.text = list[position].country
        holder.genreV.text = list[position].genre
        holder.dateV.text = list[position].date
    }

}

class InnovationsItemViewHolder(itemView: View): ViewHolder(itemView){ // отображение итемов RecyclerView
    val imgV: ImageView = itemView.findViewById(R.id.image_item_innovations)
    val nameV: TextView = itemView.findViewById(R.id.innovations_name_item)
    val yearTimeNameV: TextView = itemView.findViewById(R.id.innovations_year_duration_en_name_item)
    val countryV: TextView = itemView.findViewById(R.id.innovations_country_item)
    val genreV: TextView = itemView.findViewById(R.id.innovations_genre_item)
    val dateV: TextView = itemView.findViewById(R.id.innovations_date_item)
}