package com.candice.interviewquestionofandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class Main2Activity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main2)
		Log.e(MainActivity.TAG, "onCreate(2)")
	}

	override fun onStart() {
		super.onStart()
		Log.e(MainActivity.TAG, "onStart(2)")


	}

	override fun onResume() {
		super.onResume()
		Log.e(MainActivity.TAG, "onResume(2)")

	}

	override fun onPause() {
		super.onPause()
		Log.e(MainActivity.TAG, "onPause(2)")

	}

	override fun onStop() {
		super.onStop()
		Log.e(MainActivity.TAG, "onStop(2)")

	}

	override fun onRestart() {
		super.onRestart()
		Log.e(MainActivity.TAG, "onRestart(2)")

	}

	override fun onDestroy() {
		super.onDestroy()
		Log.e(MainActivity.TAG, "onDestroy(2)")

	}

	companion object {
		@JvmStatic
		fun launch(context: Context) {
			val intent = Intent()
			intent.setClass(context,Main2Activity::class.java)
			context.startActivity(intent)
		}
	}


}
