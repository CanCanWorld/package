package com.zrq.apackage

import android.graphics.drawable.Drawable
import java.io.File

data class Package(
    val name: String,
    val drawable: Drawable,
    val file: File,
)
