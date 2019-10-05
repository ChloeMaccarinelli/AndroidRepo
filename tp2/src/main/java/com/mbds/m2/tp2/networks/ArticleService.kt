package com.mbds.m2.tp2.networks

import com.mbds.m2.tp2.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleService {


    @GET("everything?q=news&apiKey=6c1d5c08904f44fb9dfed8d9b1da33ab")

    fun list(): Call<ArticleResponse>

    @GET("everything?q=politique&apiKey=6c1d5c08904f44fb9dfed8d9b1da33ab")
    fun getPolitique() : Call<ArticleResponse>

    @GET("everything?q=astronomie&apiKey=6c1d5c08904f44fb9dfed8d9b1da33ab")
    fun getAstronomie() : Call<ArticleResponse>

    @GET("everything?q=people&apiKey=6c1d5c08904f44fb9dfed8d9b1da33ab")
    fun getPeople() : Call<ArticleResponse>

    @GET("everything?q=sport&apiKey=6c1d5c08904f44fb9dfed8d9b1da33ab")
    fun getSport() : Call<ArticleResponse>
}