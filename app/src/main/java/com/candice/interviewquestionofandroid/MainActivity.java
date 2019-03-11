package com.candice.interviewquestionofandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private final  static int REQUEST_CODE_CAPTURE = 0;
	private Button mBtnTakePic;
	private Button mBtnOpen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//拍照
		mBtnTakePic = ( Button ) findViewById(R.id.btn_take_pics);
		//打开相册
		mBtnOpen = ( Button ) findViewById(R.id.btn_open);

		mBtnTakePic.setOnClickListener(this);
		mBtnOpen.setOnClickListener(this);
		Log.e("TAG","onCreate");


	}


	@Override
	protected void onStart() {
		super.onStart();
		Log.e("TAG","onStart");

	}


	@Override
	protected void onResume() {
		super.onResume();
		Log.e("TAG","onResume");

	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.e("TAG","onStop");

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.e("TAG","onRestart");

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e("TAG","onDestroy");

	}

	private void takePhotos() {
//		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		//省略检测相机，权限申请...
//		File file = new File(imagePath);
//		Uri uri = Uri.fromFile(file);
//		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//		startActivityForResult(intent, REQUEST_CODE_CAPTURE);
	}


	@Override
	public void onClick(View v) {
		switch ( v.getId() ){
			case R.id.btn_take_pics:
				takePhotos();
				break;
			case R.id.btn_open:
				break;
		}
	}
}

