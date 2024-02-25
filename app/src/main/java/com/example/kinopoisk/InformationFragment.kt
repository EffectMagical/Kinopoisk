package com.example.kinopoisk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.kinopoisk.databinding.FragmentInformationBinding

class InformationFragment(id: Int) : Fragment() {

    private lateinit var binding: FragmentInformationBinding
    private lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        queue = Volley.newRequestQueue(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInformationBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    private fun makeRequest(s: String) = object: JsonObjectRequest(
        Method.GET,
        "https://kinopoiskapiunofficial.tech/api/v2.1/films/search-by-keyword?keyword=$s&page=1",
        null,
        {
            val films = it.getJSONArray("films")
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
}