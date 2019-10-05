/**
 * Code par Maccarinelli Chloé & Fernandez Arnaud
 *
 * Dans le cadre du TP2 de développement d'application Android
 * MBDS Sophia Antipolis
 * Promotion 2019 / 2020
 */

package com.mbds.m2.tp2.networks.repository

import com.mbds.m2.tp2.Article
import com.mbds.m2.tp2.networks.ArticleService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ArticleRepository {

    private var service: ArticleService

    init {


        val retrofit = Retrofit.Builder().apply {
            baseUrl("https://newsapi.org/v2/")
        }
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ArticleService::class.java)
    }

    fun list(): List<Article> {

        val response = service.list().execute()
        val result = response.body()?.articles ?: emptyList()

        val callPolitique = service.getPolitique()

        return result
    }

    fun getPolitique(): List<Article>{
        val response = service.getPolitique().execute()
        val result = response.body()?.articles ?: emptyList()

        return result
    }

    fun getPeople(): List<Article>{
        val response = service.getPeople().execute()
        val result = response.body()?.articles ?: emptyList()

        return result
    }

    fun getSport(): List<Article>{
        val response = service.getSport().execute()
        val result = response.body()?.articles ?: emptyList()

        return result
    }

    fun getAstronomie(): List<Article>{
        val response = service.getAstronomie().execute()
        val result = response.body()?.articles ?: emptyList()

        return result
    }
}

data class Article (
    val news : String,
    val politique : String,
    val people : String,
    val astronomie : String
)