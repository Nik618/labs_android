package com.example.labs_android

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.labs_android.interfaces.RetrofitInterface
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit


class LoginActivity : AppCompatActivity() {

    @SuppressLint("ResourceType")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_cat1 -> {

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://petstore.swagger.io/v2/")
                    .build()
                val retrofitInterface = retrofit.create(RetrofitInterface::class.java)
                val jsonObject = JSONObject()
                jsonObject.put("id", 0)
                val jsonObjectCategory = JSONObject()
                jsonObjectCategory.put("id", 0)
                jsonObjectCategory.put("name", "string")
                jsonObject.put("category", jsonObjectCategory)
                jsonObject.put("name", "doggy")
                val arrayPhotoUrls = arrayOf("string")
                val jsonArrayPhotoUrls = JSONArray()
                for (i in arrayPhotoUrls.indices) {
                    jsonArrayPhotoUrls.put(arrayPhotoUrls[i])
                }
                jsonObject.put("photoUrls", jsonArrayPhotoUrls)
                val jsonObjectTags = JSONObject()
                jsonObjectTags.put("id", 0)
                jsonObjectTags.put("name", "string")
                val arrayTags = arrayOf(jsonObjectTags)
                val jsonArrayTags = JSONArray()
                for (i in arrayTags.indices) {
                    jsonArrayTags.put(arrayTags[i])
                }
                jsonObject.put("tags", jsonArrayTags)
                jsonObject.put("status", "available")

                val jsonObjectString = jsonObject.toString()

                print(jsonObjectString + "\n\n\n\n\n")

                // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
                val requestBody = RequestBody.create(MediaType.get("application/json; charset=utf-8"), jsonObjectString)

                CoroutineScope(Dispatchers.IO).launch {
                    // Do the POST request and get response
                    val response = retrofitInterface.post(requestBody)

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {

                            // Convert raw JSON to pretty JSON using GSON library
                            val gson = GsonBuilder().setPrettyPrinting().create()
                            val prettyJson = gson.toJson(
                                JsonParser().parse(
                                    response.body()
                                        ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                                )
                            )

                            Log.d("Pretty Printed JSON :", prettyJson)

                        } else {

                            Log.e("RETROFIT_ERROR", response.code().toString())

                        }
                    }
                }
                return true
            }
            R.id.action_cat2 -> {
                alert("test2")
                return true
            }
            R.id.action_cat3 -> {
                alert("test3")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun alert(text: String) {
        val context: Context = applicationContext
        val toast = Toast.makeText(
            context, text,
            Toast.LENGTH_LONG
        )
        toast.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        print("!!!!!!!!!!!!!!!!1\n\n\n\n\n")

//        val notificationIntent = Intent(Intent.ACTION_WEB_SEARCH,
//            Uri.parse("https://dkws.org.ua/"))
//        val pendingIntent = PendingIntent.getActivity(
//            this, 0,
//            notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT
//        )

        val id = "DEFAULT"
        val name = "Все уведомления"
        val channel = NotificationChannel(id, name, IMPORTANCE_DEFAULT)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(this, id)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("test")
            .setContentText("test")
            //.setContentIntent(pendingIntent)

        val notification = builder.build()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }

    fun onClick(view: View) {
        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            setTitle("Androidly Alert")
            setMessage("We have a message")
            setPositiveButton("OK", DialogInterface.OnClickListener(function = positiveButtonClick))
            setNegativeButton(android.R.string.no, negativeButtonClick)
            //setNeutralButton("Maybe", neutralButtonClick)
            show()
        }
        setContentView(R.layout.activity_main)
    }

    private val positiveButtonClick = { _: DialogInterface, _: Int ->
        Toast.makeText(applicationContext,
            android.R.string.yes, Toast.LENGTH_SHORT).show()
    }
    private val negativeButtonClick = { _: DialogInterface, _: Int ->
        Toast.makeText(applicationContext,
            android.R.string.no, Toast.LENGTH_SHORT).show()
    }
}