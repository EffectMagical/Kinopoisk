package com.example.kinopoisk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.Request.Method
import com.android.volley.Request.Method.GET
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.kinopoisk.databinding.FragmentInnovationsBinding
import com.google.gson.Gson
import okhttp3.Headers
import org.json.JSONArray


class InnovationsFragment : Fragment() {

    private lateinit var binding: FragmentInnovationsBinding
    private lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        queue = Volley.newRequestQueue(context)

        val makeRequest = object: JsonObjectRequest(
            GET,
            "https://kinopoiskapiunofficial.tech/api/v2.2/films/premieres?year=2023&month=JANUARY",
            null,
            {
                val total = it.getInt("total")
                val items = it.getJSONArray("items")
                var premierList = mutableListOf<InnovationsItem>()
                for (i in 0..<total){

                    var countries = ""
                    for (j in 0..<items.getJSONObject(i).getJSONArray("countries").length()){
                        val cL = "" + items.getJSONObject(i).getJSONArray("countries")[j]
                        countries += ", " + cL.slice(cL.indexOf(":")+2..cL.lastIndexOf("}")-2)
                    }

                    var genres = ""
                    for (j in 0..<items.getJSONObject(i).getJSONArray("genres").length()){
                        val gL = "" + items.getJSONObject(i).getJSONArray("genres")[j]
                        genres += " " + gL.slice(gL.indexOf(":")+2..gL.lastIndexOf("}")-2)
                    }

                    var name = ""
                    if (items.getJSONObject(i).getString("nameRu").length > 24){
                        name = items.getJSONObject(i).getString("nameRu").slice(0..24) + "..."
                    }
                    else{
                        name = items.getJSONObject(i).getString("nameRu")
                    }

                    premierList.add(
                        InnovationsItem(
                            id = items.getJSONObject(i).getInt("kinopoiskId"),
                            image = items.getJSONObject(i).getString("posterUrlPreview"),
                            nameRu = name,
                            nameEn = items.getJSONObject(i).getString("nameEn"),
                            year = items.getJSONObject(i).getInt("year"),
                            duration = items.getJSONObject(i).getInt("duration"),
                            country = countries.drop(2),
                            genre = genres.drop(1),
                            date = items.getJSONObject(i).getString("premiereRu")
                        )
                    )
                }
                binding.innovationsList.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                binding.innovationsList.adapter = InnovationsListAdapter(premierList)
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
        queue.add(makeRequest)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInnovationsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
}
