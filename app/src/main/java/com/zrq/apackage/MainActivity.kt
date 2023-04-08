package com.zrq.apackage

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.Menu
import android.view.MenuItem
import com.zrq.apackage.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val list = mutableListOf<Package>()
    private lateinit var mAdapter: PkgAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 0)

        initEvent()

    }

    private fun initEvent() {
        mAdapter = PkgAdapter(this, list) {
            share(list[it].file)
        }
        mBinding.recyclerView.adapter = mAdapter
        refresh()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> {
                refresh()
            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun share(file: File) {
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "application/octet-stream"
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
        startActivity(intent)
    }

    private fun refresh() {
        Thread {
            list.clear()
            runOnUiThread {
                mAdapter.notifyDataSetChanged()
            }
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            val resolveInfos = packageManager.queryIntentActivities(intent, 0)
            resolveInfos.forEach {
                val name = it.loadLabel(packageManager).toString()
                val drawable = it.loadIcon(packageManager)
                val file = File(packageManager.getApplicationInfo(it.activityInfo.packageName, 0).sourceDir)
                list.add(Package(name, drawable, file))
            }
            runOnUiThread {
                mAdapter.notifyDataSetChanged()
            }
        }.start()
    }

    private companion object {
        const val TAG = "MainActivity"
    }
}