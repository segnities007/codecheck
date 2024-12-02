/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class TopActivity : AppCompatActivity(R.layout.activity_top) {

    companion object {
        var lastSearchDate: Date = Date()
    }
}
