package com.zrq.apackage

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.zrq.apackage.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)

        initEvent()

        val packages = packageManager.getInstalledPackages(0)
        packages.forEach {
            Log.d(TAG, "onCreate: ${it.packageName}")
            val file = File(packageManager.getApplicationInfo(it.packageName, 0).sourceDir)
            Log.d(TAG, "onCreate: ${file.absoluteFile}")
        }
    }

    private fun initEvent() {
        mBinding.recyclerView.adapter
    }

    private companion object {
        const val TAG = "MainActivity"
    }
}