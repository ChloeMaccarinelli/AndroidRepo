/**
 * Code par Maccarinelli Chloé & Fernandez Arnaud
 *
 * Dans le cadre du TP2 de développement d'application Android
 * MBDS Sophia Antipolis
 * Promotion 2019 / 2020
 */



package com.mbds.m2.tp2
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbds.m2.tp2.networks.repository.ArticleRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ArticleFragment : Fragment() {
    private val repository = ArticleRepository()

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindRecyclerView(view)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        GlobalScope.launch {
            getData()
        }
    }

    private suspend fun getData() {
        withContext(Dispatchers.IO) {

            var result = repository.list()

            when(resources.getStringArray(R.array.actualites).toString()) {
                "politique" -> result = repository.getPolitique()
            }

            bindData(result)
        }
    }

    private suspend fun getNewData() {
        withContext(Dispatchers.IO) {

            var result = repository.list()

            when(resources.getStringArray(R.array.actualites).toString()) {
                "politique" -> result = repository.getPolitique()
            }

            bindData(result)
        }
    }

    private suspend fun bindData(result: List<Article>) {
        withContext(Dispatchers.Main) {
            val adapterRecycler = ArticleAdapter(result)
            recyclerView.adapter = adapterRecycler
        }
    }


    private fun bindRecyclerView(root: View) {
        val actualites = resources.getStringArray(R.array.actualites)
        val spinner: Spinner = root.findViewById(R.id.spinner)
        val adapter =
            ArrayAdapter(root.context, android.R.layout.simple_spinner_item, actualites)

        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(
                    root.context,
                    "Vous n'avez rien selectionné",
                    Toast.LENGTH_LONG
                )
                    .show()


            }

            override fun onItemSelected(
                adapter: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    root.context,
                    "Vous avez selectionné ${actualites[position]}",
                    Toast.LENGTH_LONG
                ).show()
                //on item selected on change le topic des articles

                var result = repository.list()

                when(actualites[position]) {
                    "politique" -> result = repository.getPolitique()
                    "astronomie" -> result = repository.getAstronomie()
                    "sport" -> result = repository.getSport()
                    "people" -> result = repository.getPeople()
                }


                println("RESULT : " + result)
                println("RESSOURCES STRING : " + actualites[position])
                val adapterRecycler = ArticleAdapter(result)
                recyclerView.adapter = adapterRecycler
            }

        }
        recyclerView = root.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(root.context)

    }


}