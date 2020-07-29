package com.example.dancingrobot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var m3DView: MyDrawView3D? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        m3DView = MyDrawView3D(this)
        setContentView(m3DView)
    }
}
