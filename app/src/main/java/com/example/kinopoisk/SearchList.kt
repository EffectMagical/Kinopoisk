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

data class SearchItem(
    val id: Int,
    var image: String,
    var nameRu: String,
    var nameEn: String,
    var year: Int,
    var duration: String,
    var country: String,
    var genre: String,
    var mark: String
)

class SearchListAdapter(
    private var list: List<SearchItem>,
): RecyclerView.Adapter<SearchItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.search_list_item,parent, false)
        return SearchItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.imgV.load(list[position].image){
            crossfade(true)
        }
        var yearTimeName = ""

        if (list[position].nameEn != "") {
            yearTimeName =
                list[position].nameEn + ", " + list[position].year.toString() + ", " + list[position].duration + " мин."
        } else {
            yearTimeName =
                list[position].year.toString() + ", " + list[position].duration + " мин."
        }

        holder.nameRuV.text = list[position].nameRu
        holder.yearTimeNameV.text = yearTimeName
        holder.countryV.text = list[position].country
        holder.genreV.text = list[position].genre
        holder.markV.text = list[position].mark
    }

}

class SearchItemViewHolder(itemView: View): ViewHolder(itemView){ // отображение итемов RecyclerView
    val imgV: ImageView = itemView.findViewById(R.id.image_item_search)
    val nameRuV: TextView = itemView.findViewById(R.id.search_ru_name_item)
    val yearTimeNameV: TextView = itemView.findViewById(R.id.search_name_year_duration_item)
    val countryV: TextView = itemView.findViewById(R.id.search_country_item)
    val genreV: TextView = itemView.findViewById(R.id.search_genre_item)
    val markV: TextView = itemView.findViewById(R.id.search_mark_item)
}