package com.example.shadedetector

import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<ProgressBar>(R.id.progressBar1).visibility=View.GONE
    }
    inner class myClass : AsyncTask<Void, Void, String>() {
        var text1=""
        override fun doInBackground(vararg params: Void?): String {
            var toReturn=""
            var buildUri1= Uri.parse(
                "https://api.meaningcloud.com/sentiment-2.1").buildUpon()
                .appendQueryParameter("key", "db6501c06bde422e3ac5d1c7771f5b0e")
                .appendQueryParameter("lang", "en")
                .appendQueryParameter("txt",text1)
                .build()
            toReturn= URL(buildUri1.toString()).readText()
            return toReturn
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            findViewById<ProgressBar>(R.id.progressBar1).visibility = View.GONE
            var myJson= JSONObject(result)
            var sTag=myJson.getString("score_tag")
            var confidence=myJson.getString("confidence")
            var irony=myJson.getString("irony")
            var subjectivity=myJson.getString("subjectivity")
            tagText.text="Score: "+sTag
            confidenceText.text="Confidence: "+confidence
            ironyText.text="Irony: " + irony
            subjectivityText.text="Subjectivity: "+subjectivity
        }
    }
    fun buttonClicked(view: View) {
        if(editText.text.isNotEmpty()) {
            findViewById<ProgressBar>(R.id.progressBar1).visibility =View.VISIBLE
            var i = myClass()
            i.text1=editText.text.toString()
            i.execute()
        }
    }
}
