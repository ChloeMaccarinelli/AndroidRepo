/**
 * Code par Maccarinelli Chloé & Fernandez Arnaud
 *
 * Dans le cadre du TP2 de développement d'application Android
 * MBDS Sophia Antipolis
 * Promotion 2019 / 2020
 */


package com.mbds.m2.tp2




import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = ArticleFragment()

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        supportFragmentManager.beginTransaction().apply {

            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }.commit()
    }


}