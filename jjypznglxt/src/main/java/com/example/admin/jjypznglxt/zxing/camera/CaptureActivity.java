package com.example.admin.jjypznglxt.zxing.camera;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.jjypznglxt.R;
import com.example.admin.jjypznglxt.activity.FirstAidResult;
import com.example.admin.jjypznglxt.activity.InputBorcodeActivity;
import com.example.admin.jjypznglxt.activity.MedicineSupplyActivity;
import com.example.admin.jjypznglxt.activity.ShowResultActivity;
import com.example.admin.jjypznglxt.bean.MedicineInfo;
import com.example.admin.jjypznglxt.bean.SmallCar;
import com.example.admin.jjypznglxt.dbcontrol.MedicineConntrol;
import com.example.admin.jjypznglxt.dbcontrol.SmallCarTableControl;
import com.example.admin.jjypznglxt.zxing.camera.camera.CameraManager;
import com.example.admin.jjypznglxt.zxing.camera.decoding.CaptureActivityHandler;
import com.example.admin.jjypznglxt.zxing.camera.decoding.InactivityTimer;
import com.example.admin.jjypznglxt.zxing.camera.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

public class CaptureActivity extends Activity implements Callback {
	public static final String QR_RESULT = "RESULT";

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private SurfaceView surfaceView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	// private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	CameraManager cameraManager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*
		 * this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
		 * 
		 * RelativeLayout layout = new RelativeLayout(this);
		 * layout.setLayoutParams(new
		 * ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,
		 * LayoutParams.FILL_PARENT));
		 * 
		 * this.surfaceView = new SurfaceView(this); this.surfaceView
		 * .setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,
		 * LayoutParams.FILL_PARENT));
		 * 
		 * layout.addView(this.surfaceView);
		 * 
		 * this.viewfinderView = new ViewfinderView(this);
		 * this.viewfinderView.setBackgroundColor(0x00000000);
		 * this.viewfinderView.setLayoutParams(new
		 * ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,
		 * LayoutParams.FILL_PARENT)); layout.addView(this.viewfinderView);
		 * 
		 * TextView status = new TextView(this); RelativeLayout.LayoutParams
		 * params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
		 * LayoutParams.WRAP_CONTENT);
		 * params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		 * params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		 * status.setLayoutParams(params);
		 * status.setBackgroundColor(0x00000000);
		 * status.setTextColor(0xFFFFFFFF); status.setText("请将条码置于取景框内扫描。");
		 * status.setTextSize(14.0f);
		 * 
		 * layout.addView(status); setContentView(layout);
		 */

		setContentView(R.layout.activity_capture);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinderview);

		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);

		ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
		TextView tv_input_borcode = (TextView) findViewById(R.id.tv_input_borcode);
		iv_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tv_input_borcode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CaptureActivity.this, InputBorcodeActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		// CameraManager.init(getApplication());
		cameraManager = new CameraManager(getApplication());

		viewfinderView.setCameraManager(cameraManager);

		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		cameraManager.closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			// CameraManager.get().openDriver(surfaceHolder);
			cameraManager.openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();
	}

	public void handleDecode(Result obj, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		Bundle bundle = getIntent().getExtras();
		String type = getIntent().getStringExtra("type");
		if(!TextUtils.isEmpty(type) && type.equals("急救")){
			List<String> cars = (List<String>) bundle.getSerializable("cars");
			SmallCar smallCar = SmallCarTableControl.getInstance(this).queryByBarcode(obj.getText());
			if(cars != null && !cars.contains(smallCar.getNum())){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);

				Drawable drawable = new BitmapDrawable(barcode);
				builder.setIcon(drawable);
				builder.setTitle("小推车选取错误");
				builder.setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						restartPreviewAfterDelay(0L);
					}
				});
//				builder.setNegativeButton("取消", new OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//
//					}
//				});
				builder.setCancelable(false);
				builder.show();
				return;
			}
		}
		if(!TextUtils.isEmpty(type) && type.equals("saoyao2")){
			MedicineInfo medicineInfo = MedicineConntrol.getInstance(CaptureActivity.this).queryByBarcode(obj.getText());
			if(medicineInfo == null){
				return;
			}
		}
		showResult(obj, barcode);
	}

	private void showResult(final Result rawResult, Bitmap barcode) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		Drawable drawable = new BitmapDrawable(barcode);
		builder.setIcon(drawable);

		builder.setTitle("类型:" + rawResult.getBarcodeFormat() + "\n 结果：" + rawResult.getText());
		builder.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
//				intent.putExtra("result", rawResult.getText());
//				setResult(RESULT_OK, intent);
//				finish();
				String type = CaptureActivity.this.getIntent().getStringExtra("type");
				if(type.equals("急救")) {//急救项目选择后扫小推车进来的
					Bundle bundle = CaptureActivity.this.getIntent().getExtras();
					Intent intent = new Intent(CaptureActivity.this, FirstAidResult.class);
					intent.putExtra("type", rawResult.getBarcodeFormat().toString());
					intent.putExtra("result", rawResult.getText());
					intent.putExtras(bundle);
					startActivity(intent);
//					CaptureActivity.this.finish();
				}else if(type.equals("saoma")){
					Intent intent = new Intent(CaptureActivity.this, ShowResultActivity.class);
					intent.putExtra("type", rawResult.getBarcodeFormat().toString());
					intent.putExtra("result", rawResult.getText());
					startActivity(intent);
					CaptureActivity.this.finish();
				}else if(type.equals("supply")){
					Intent intent = new Intent(CaptureActivity.this, MedicineSupplyActivity.class);
					intent.putExtra("type", rawResult.getBarcodeFormat().toString());
					intent.putExtra("result", rawResult.getText());
					startActivity(intent);
					CaptureActivity.this.finish();
				}else if(type.equals("supplyMedicine")){
					String result = rawResult.getText();
					//ToDo 这里补药扫到药品条码后要访问服务器得到该药品信息，包括药品名称，生产日期这些，
					// ToDo 如果是所点击补药的那种药并且没有过期 再跳回补药列表页面  得到药品信息的同时也要保存到数据库
					Intent intent = new Intent(CaptureActivity.this, MedicineSupplyActivity.class);
					intent.putExtra("type", "buyao");
					intent.putExtra("result", rawResult.getText());
					startActivity(intent);
					CaptureActivity.this.finish();
				}else if(type.equals("saomiao")){ // 第一个条码扫描进来的  目的展示所扫到的小推车或者药品详细信息

				}else if(type.equals("saoyao")){//急救中列出所用药品   这里是扫描是否是所需药品的  FirstAidResullt页面进来的
					//
//					MedicineInfo medicineInfo = MedicineConntrol.getInstance(CaptureActivity.this).queryByBarcode(rawResult.getText());
//					if(medicineInfo != null){
					Intent intent = new Intent(CaptureActivity.this, FirstAidResult.class);
					intent.putExtra("type", "saoyao");
//					Intent intent = new Intent();
					intent.putExtra("num", true);
//					intent.putExtra("position", 0);
//					setResult(0, intent);
//					CaptureActivity.this.finish();
					startActivity(intent);
//					}else {

//					}
				}else if(type.equals("saoyao2")){
					Intent intent = new Intent();
					intent.putExtra("num", true);
					intent.putExtra("position", 0);
					setResult(0, intent);
					CaptureActivity.this.finish();
				}
			}
		});
		builder.setNegativeButton("重新扫描", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				restartPreviewAfterDelay(0L);
			}
		});
		builder.setCancelable(false);
		builder.show();

		// Intent intent = new Intent();
		// intent.putExtra(QR_RESULT, rawResult.getText());
		// setResult(RESULT_OK, intent);
		// finish();
	}

	public void restartPreviewAfterDelay(long delayMS) {
		if (handler != null) {
			handler.sendEmptyMessageDelayed(MessageIDs.restart_preview, delayMS);
		}
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			try {
				AssetFileDescriptor fileDescriptor = getAssets().openFd("qrbeep.ogg");
				this.mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(),
						fileDescriptor.getLength());
				this.mediaPlayer.setVolume(0.1F, 0.1F);
				this.mediaPlayer.prepare();
			} catch (IOException e) {
				this.mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResult(RESULT_CANCELED);
			finish();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_FOCUS || keyCode == KeyEvent.KEYCODE_CAMERA) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}