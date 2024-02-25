package com.example.kinopoisk

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

data class FavouriteItem(
    var image: String,
    var nameRu: String,
    var nameEn: String,
    var year: String,
    var duration: String,
    var country: String,
    var genre: String,
)

class FavouriteListAdapter(
    private var list: List<FavouriteItem>,
    context: Context
): RecyclerView.Adapter<FavouriteItemViewHolder>(){
    val inflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteItemViewHolder {
        val view = inflater.inflate(R.layout.favourite_list_item,parent, false)
        return FavouriteItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FavouriteItemViewHolder, position: Int) {
        holder.imgV.load(list[position].image){
            crossfade(true)
        }
        val yearText = list[position].nameEn + ", " +  list[position].year + ", " + list[position].duration

        holder.nameRuV.text = list[position].nameRu
        holder.yearTimeNameV.text = yearText
        holder.countryV.text = list[position].country
        holder.genreV.text = list[position].genre
    }

}

class FavouriteItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){ // отображение итемов RecyclerView
    val imgV: ImageView = itemView.findViewById(R.id.image_item_favourite)
    val nameRuV: TextView = itemView.findViewById(R.id.favourite_name_item)
    val yearTimeNameV: TextView = itemView.findViewById(R.id.favourite_year_duration_en_name_item)
    val countryV: TextView = itemView.findViewById(R.id.favourite_country_item)
    val genreV: TextView = itemView.findViewById(R.id.favourite_genre_item)
}