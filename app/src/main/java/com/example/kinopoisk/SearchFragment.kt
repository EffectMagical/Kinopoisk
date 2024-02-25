package com.example.kinopoisk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.kinopoisk.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        queue = Volley.newRequestQueue(context)

    }

    private fun makeRequest(s: String) = object: JsonObjectRequest(
        Method.GET,
        "https://kinopoiskapiunofficial.tech/api/v2.1/films/search-by-keyword?keyword=$s&page=1",
        null,
        {
            val films = it.getJSONArray("films")
            var searchList = mutableListOf<SearchItem>()
            for (i in 0..<films.length()){

                var countries = ""
                for (j in 0..<3){
                    val cL = "" + films.getJSONObject(i).getJSONArray("countries")[j]
                    countries += ", " + cL.slice(cL.indexOf(":")+2..cL.lastIndexOf("}")-2)
                    if (j+1 == films.getJSONObject(i).getJSONArray("countries").length()) break
                }

                var genres = ""
                for (j in 0..<2){
                    val gL = "" + films.getJSONObject(i).getJSONArray("genres")[j]
                    genres += " " + gL.slice(gL.indexOf(":")+2..gL.lastIndexOf("}")-2)
                    if (j+1 == films.getJSONObject(i).getJSONArray("genres").length()) break
                }

                var name = ""
                if (films.getJSONObject(i).getString("nameRu").length > 24){
                    name = films.getJSONObject(i).getString("nameRu").slice(0..24) + "..."
                }
                else{
                    name = films.getJSONObject(i).getString("nameRu")
                }

                var markL = films.getJSONObject(i).getString("rating")
                if (markL == "null") markL = "Нет оценки"

                searchList.add(
                    SearchItem(
                        id = films.getJSONObject(i).getInt("filmId"),
                        image = films.getJSONObject(i).getString("posterUrlPreview"),
                        nameRu = name,
                        nameEn = films.getJSONObject(i).getString("nameEn"),
                        year = films.getJSONObject(i).getInt("year"),
                        duration = films.getJSONObject(i).getString("filmLength"),
                        country = countries.drop(2),
                        genre = genres.drop(1),
                        mark = markL
                    )
                )
            }
            binding.searchList.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            binding.searchList.adapter = SearchListAdapter(searchList)
        },
        {
        }
    ){
        override fun getHeaders(): MutableMap<String, String> {
            val headers = HashMap<String, String>()
            headers ["content-type"] = "application/json"
            headers ["X-API-KEY"] = "a6af6441-4383-4437-a9a4-67e85285838b"
            return headers
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater,container,false)

        binding.searchButton.setOnClickListener {
            queue.add(makeRequest(binding.searchInput.text.toString()))
        }
        return binding.root
    }
}
