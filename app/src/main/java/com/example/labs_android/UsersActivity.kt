package com.example.labs_android

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class UsersActivity : AppCompatActivity() {
    @SuppressLint("UnspecifiedImmutableFlag", "PrivateResource", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        val context: Context = applicationContext
        val toast = Toast.makeText(
            context, "Это уведомление",
            Toast.LENGTH_LONG
        )
        toast.show()

        val imageView : ImageView = findViewById(R.id.imageView)
        imageView.setImageResource(R.drawable.test3)
    }





}