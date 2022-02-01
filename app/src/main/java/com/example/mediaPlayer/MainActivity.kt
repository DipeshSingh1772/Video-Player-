package com.example.mediaPlayer

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.mediaPlayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        this.window.statusBarColor = Color.rgb(18,18,18)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestRuntimePermission()
        setFragment(VideoFragment())

        binding.bottomNav.setOnItemSelectedListener {

            when(it.itemId){
                R.id.video_view -> setFragment(VideoFragment())
                R.id.file_view -> setFragment(FolderFragment())
            }
            return@setOnItemSelectedListener true
        }

    }

    private fun setFragment(fragment:Fragment){

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_fmlayout, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }

    private fun requestRuntimePermission():Boolean{
        if(ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE),13)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==13){

            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show()
            else
                ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE),13)

        }
    }
}