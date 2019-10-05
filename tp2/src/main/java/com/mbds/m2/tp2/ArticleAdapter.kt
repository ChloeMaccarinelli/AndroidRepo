/**
 * Code par Maccarinelli Chloé & Fernandez Arnaud
 *
 * Dans le cadre du TP2 de développement d'application Android
 * MBDS Sophia Antipolis
 * Promotion 2019 / 2020
 */



package com.mbds.m2.tp2

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.io.InputStream
import java.net.URL
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import java.net.HttpURLConnection
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.widget.ImageButton


data class Article(val title: String, val description: String,  val urlToImage: String , val url: String)

data class ArticleResponse(val status : String, val totalResults : Int, val articles : List<Article>)

class ArticleAdapter(private val dataset: List<Article>) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    override fun getItemCount() = 15

    class ViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
        fun bind(item: Article) {
            val txtTitle = root.findViewById<TextView>(R.id.title)
            val txtDesc = root.findViewById<TextView>(R.id.description)
            val buttonToLink = root.findViewById<ImageButton>(R.id.url)

            if (item != null) {
                txtTitle.text = item.title
                txtDesc.text = item.description

                downloadImageFromPath(item.urlToImage, root)

                buttonToLink.setOnClickListener {
                    onBrowseClick(root, item.url)
                }

            }

        }

        fun onBrowseClick(v: View, url : String) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(v.context,browserIntent, null)

        }




        fun downloadImageFromPath(path: String, root: View) {

            if(path != null) {
                var ins: InputStream? = null
                var bmp: Bitmap? = null
                val iv = root.findViewById(R.id.urlToImage) as ImageView
                var responseCode = -1

                val url = URL(path)
                val con = url.openConnection() as HttpURLConnection
                con.doOutput = false
                //con.requestMethod = "GET"
                con.connect()
                responseCode = con.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    ins = con.inputStream
                    bmp = BitmapFactory.decodeStream(ins)
                    ins.close()
                    iv.setImageBitmap(bmp)
                } else {
                    println("Error getting image from server / Error code : " + responseCode)
                }
            }
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }


}
