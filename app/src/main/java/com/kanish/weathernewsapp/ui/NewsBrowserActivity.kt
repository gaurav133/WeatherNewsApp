package com.kanish.weathernewsapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.kanish.weathernewsapp.R
import kotlinx.android.synthetic.main.activity_news_browser.*


/**
 * Created by Kanish Roshan on 2020-03-01.
 */

class NewsBrowserActivity : AppCompatActivity() {
    lateinit var progressBar: ProgressBar
    var wbCLient: WebCLient? = null
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_browser)
        wb_news.settings.apply {
            this.javaScriptEnabled = true
            this.domStorageEnabled = true
            this.allowContentAccess = true
        }

        wb_news.webChromeClient = WebChromeClient()
        progressBar = findViewById(R.id.pb_wb)

        wb_news.webViewClient = WebCLient()
        val i: Intent = this.intent
        val url = i.getStringExtra("uri")
        wb_news.loadUrl(url)
        progressBar.visibility = View.VISIBLE

    }


    override fun onBackPressed() {
        finish()
    }

    inner class WebCLient() : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

            super.onPageStarted(view, url, favicon)


            progressBar.visibility = View.GONE


        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)


        }
    }
}
