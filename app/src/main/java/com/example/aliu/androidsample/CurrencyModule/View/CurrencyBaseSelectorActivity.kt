package com.example.aliu.androidsample.CurrencyModule.View

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import com.example.aliu.androidsample.R
import kotlinx.android.synthetic.main.currency_fragment.*
import kotlinx.android.synthetic.main.currency_selector_activity.*


class CurrencyBaseSelectorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.currency_selector_activity)
        val data: ArrayList<String> = intent.extras.get("data") as ArrayList<String>
        baseTable.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        baseTable.setOnItemClickListener { _, _, i, _ ->
            val intent = Intent()
            intent.putExtra("base", data[i])
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
    }

}
