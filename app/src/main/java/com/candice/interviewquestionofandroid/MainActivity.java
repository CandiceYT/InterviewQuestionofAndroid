package com.candice.interviewquestionofandroid;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
	public static final String TAG =MainActivity.class.getSimpleName();

	private final  static int REQUEST_CODE_CAPTURE = 0;
	private Button mBtnTakePic;
	private Button mBtnOpen;
	private  String name ="";
	private String text ="测试啊本月已成功邀请 <strong><font color=\"#FF0000\">" + 100 + "</font><strong>人";
	@TargetApi( Build.VERSION_CODES.N )
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//拍照
		mBtnTakePic = ( Button ) findViewById(R.id.btn_take_pics);
		//打开相册
		mBtnOpen = ( Button ) findViewById(R.id.btn_open);
		TextView content = ( TextView ) findViewById(R.id.tv_content);

		mBtnTakePic.setOnClickListener(this);
		mBtnOpen.setOnClickListener(this);
		Log.e(TAG,"onCreate");

		Spanned fromHtml = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);
		content.setText(fromHtml);
	}


	@Override
	protected void onStart() {
		super.onStart();
		Log.e(TAG,"onStart");

	}


	@Override
	protected void onResume() {
		super.onResume();
		Log.e(TAG,"onResume");

	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.e(TAG,"onPause");

	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.e(TAG,"onStop");

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.e(TAG,"onRestart");

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(TAG,"onDestroy");

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
//				takePhotos();
           Main2Activity.launch(MainActivity.this);
				break;
			case R.id.btn_open:
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("提示")
					   .setMessage("测试弹出dialog时，Activity的声明周期")
					   .setCancelable(false)
					   .setPositiveButton("确定", new DialogInterface.OnClickListener() {
						   @Override
						   public void onClick(DialogInterface dialog, int which) {
							   dialog.dismiss();
						   }
					   }).show();

				break;
		}
	}
}

