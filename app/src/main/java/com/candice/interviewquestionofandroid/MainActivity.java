package com.candice.interviewquestionofandroid;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	LruCache< String, Bitmap > mLruCache = new LruCache<String, Bitmap>((int)Runtime.getRuntime().maxMemory()/8){
		@Override
		protected int sizeOf(String key, Bitmap value) {
			return super.sizeOf(key, value);
		}

		@Override
		protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
			super.entryRemoved(evicted, key, oldValue, newValue);
		}
	};
}
