package com.brooks.runtimepermissiontest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.contentValuesOf
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        makeCall.setOnClickListener {
            // 权限检查
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                // 权限请求
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1)
            } else {
                call()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call()
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun call() {
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:10086")
//            val uri = Uri.parse("content://com.example.app.provider/table1")
//            val cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)
//            while (cursor.moveToNext()){
//                val column1 = cursor.getString(cursor.getColumnIndex("column1"))
//                val column2 = cursor.getInt(cursor.getColumnIndex("column2"))
//            }
//            cursor.close()
//            val values = contentValuesOf("column1" to "text", "column2" to 1)
//            contentResolver.insert(uri,values)
//            val values = contentValuesOf("column1" to "")
//            contentResolver.update(uri, values, "column1 = ? and column2 = ?", arrayOf("text", "1"))
//            contentResolver.delete(uri, "column2 = ?", arrayOf("1"))
            startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }


}