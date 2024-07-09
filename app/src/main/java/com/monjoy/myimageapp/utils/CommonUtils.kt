package com.monjoy.myimageapp.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.monjoy.myimageapp.R
import com.monjoy.myimageapp.retrofit.model.DogBreedsResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

object CommonUtils {

    fun showSnackBar(view: View?, msg: String?) {
        //findViewById(android.R.id.content)
        Snackbar.make(view!!, msg!!, Snackbar.LENGTH_SHORT)
            .show()
    }

    fun setVisibility(visible: View, gone: View) {
        visible.visibility = View.VISIBLE
        gone.visibility = View.GONE
    }

    fun showToastMessage(context: Context,message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun parseDogBreedsJson(jsonString: String): DogBreedsResponse {
        val json = Json { ignoreUnknownKeys = true }
        return json.decodeFromString(jsonString)
    }


    fun getAppName(context: Context): String? {
        var applicationInfo: ApplicationInfo? = null
        try {
            applicationInfo = context.packageManager.getApplicationInfo(context.applicationInfo.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            Log.d("TAG", "The package with the given name cannot be found on the system.")
        }
        return (if (applicationInfo != null) context.packageManager.getApplicationLabel(applicationInfo).toString() else "Unknown")
    }


    @SuppressLint("SimpleDateFormat")
    fun getTimeSimple(date: Date?): String? {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        return dateFormat.format(date!!)
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateFormat(inputDateString: String): String {
        val inputFormat = SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)", Locale.ENGLISH) // Input format
        val outputFormat = SimpleDateFormat("dd/MM/yyyy h:mm:ss a", Locale.ENGLISH) // Desired output format

        val date: Date = inputFormat.parse(inputDateString) as Date
        return outputFormat.format(date)
    }

    fun alertDialog(context: Context?, msg: String?) {
        var message = msg
        message = message?.replace("\r".toRegex(), "\n") ?: ""
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_alert_dialog)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        val text = dialog.findViewById<TextView>(R.id.txtDisplayName)
        text.text = message
        val okButton = dialog.findViewById<TextView>(R.id.button_ok)
        okButton.setOnClickListener { dialog.dismiss() }
    }




}