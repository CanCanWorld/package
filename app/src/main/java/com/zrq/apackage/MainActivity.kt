package com.zrq.apackage

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.zrq.apackage.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val list = mutableListOf<Package>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)

        initEvent()

    }

    private fun initEvent() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val resolveInfos = packageManager.queryIntentActivities(intent, 0)
        resolveInfos.forEach {
            val pkg = it.activityInfo.packageName
            Log.d(TAG, "initEvent: $pkg")
//            val file = File(packageManager.getApplicationInfo(pkg, 0).sourceDir)
            list.add(Package(pkg))
        }
        val packages = packageManager.getInstalledPackages(0)
        packages.forEach {
            Log.d(TAG, "onCreate: ${it.packageName}")
        }
        val adapter = PkgAdapter(this, list) {}
        mBinding.recyclerView.adapter = adapter
    }

    private companion object {
        const val TAG = "MainActivity"
    }
}