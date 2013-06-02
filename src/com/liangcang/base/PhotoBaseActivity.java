package com.liangcang.base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.ContextThemeWrapper;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.liangcang.R;
import com.liangcang.util.BitmapUtil;
import com.liangcang.util.MyLog;
import com.liangcang.util.MyToast;

public abstract class PhotoBaseActivity extends BaseActivity {
	String TAG = "PhotoBaseActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

	// 下面的代码是取图像
	// private static final int PHOTORESOULT = 2024;
	/* 用来标识请求照相功能的activity */
	private static final int CAMERA_WITH_DATA = 3023;

	/* 用来标识请求gallery的activity */
	private static final int PHOTO_PICKED_WITH_DATA = 3021;

	public void doPickPhotoAction() {

		final Context context = this;
		final Context dialogContext = new ContextThemeWrapper(context,
				android.R.style.Theme_Light);
		String cancel = getString(R.string.back);
		String[] choices;
		choices = new String[2];
		choices[0] = getString(R.string.take_photos);
		choices[1] = getString(R.string.choosePhoto);
		final ListAdapter adapter = new ArrayAdapter<String>(dialogContext,
				android.R.layout.simple_list_item_1, choices);

		final AlertDialog.Builder builder = new AlertDialog.Builder(
				dialogContext);
		// builder.setTitle(R.string.getPhoto);
		builder.setSingleChoiceItems(adapter, -1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						switch (which) {
						case 0: {
							String status = Environment
									.getExternalStorageState();
							if (status.equals(Environment.MEDIA_MOUNTED)) {
								MyLog.D(TAG, "11111111111111111");
								doTakePhoto();
							} else {
								MyToast.showMsgLong(context,
										getString(R.string.noSDCard));
							}
							break;
						}
						case 1:
							doPickPhotoFromGallery();// 从相册中去获取
							break;
						}
					}
				});
		builder.setNegativeButton(cancel,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}

				});
		builder.create().show();
	}

	private Uri photoUri;
	private ContentResolver mContentResolver;

	/**
	 * 拍照获取图片
	 * 
	 */
	protected void doTakePhoto() {
		MyLog.D(TAG, "选择了图片拍摄");
		String filename = null;
		try {
			// android.media.action.IMAGE_CAPTURE
			int count = 0;
			do {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				SimpleDateFormat timeStampFormat = new SimpleDateFormat(
						"'IMG'_yyyy_MMddHHmmss");
				filename = timeStampFormat.format(new Date());
				ContentValues values = new ContentValues();
				values.put(Media.TITLE, filename);
				mContentResolver = getContentResolver();
				photoUri = mContentResolver.insert(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
				filename = getRealPathFromURI(photoUri, mContentResolver);
				Bundle myExtras = new Bundle();

				myExtras.putParcelable(MediaStore.EXTRA_OUTPUT, photoUri);
				intent.putExtras(myExtras);

				startActivityForResult(intent, CAMERA_WITH_DATA);
				MyLog.D(TAG, "filename=" + filename + "  photoUri=" + photoUri);
			} while (count++ < 3 && filename == null);

		} catch (ActivityNotFoundException e) {
			MyToast.showMsgLong(this, "没有找到图片选择程序");
		}
	}

	public static String getRealPathFromURI(Uri uri, ContentResolver resolver) {

		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = resolver.query(uri, proj, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String str = cursor.getString(column_index);
		cursor.close();

		return str;

	}

	// 请求Gallery程序
	protected void doPickPhotoFromGallery() {
		try {

			final Intent intent = getPhotoPickIntent();

			startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);

		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, "R.string.photoPickerNotFoundText1" /*
																	 * R.string.
																	 * photoPickerNotFoundText
																	 */,
					Toast.LENGTH_LONG).show();
		}
	}

	// / 封装请求Gallery的intent
	private Intent getPhotoPickIntent() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		return intent;

	}

	/**
	 * 返回图片的路径
	 * 
	 * @param path
	 */
	public abstract void callBackPath(String path);

	public abstract void setImageBitmap(Bitmap bitmap);

	String selectedImagePath = null;

	// 因为调用了Camera和Gally所以要判断他们各自的返回情况,他们启动时是这样的startActivityForResult
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		MyLog.E(TAG, "requestCode=" + requestCode + "   resultCode="
				+ resultCode);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case PHOTO_PICKED_WITH_DATA:
			Uri uri = data.getData();
			selectedImagePath = getPath(uri);
			MyLog.E(TAG, "selectedImagePath=" + selectedImagePath);

			break;

		case CAMERA_WITH_DATA:
			// editItem.setUpLoadImgAndDelet(filename, true);
			selectedImagePath = getRealPathFromURI(photoUri, mContentResolver);

			break;

		}

		if (selectedImagePath != null && new File(selectedImagePath).exists()) {
//			callBackPath(selectedImagePath);
			new MyAsynTask().execute(selectedImagePath);
			MyToast.showMsgShort(this, "正在努力为你加载照片...");
			MyLog.d(TAG, "正在努力为你加载图片");
		}
	}

	private String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String name = cursor.getString(column_index);
		cursor.close();
		return name;
	}

	class MyAsynTask extends AsyncTask<String, Void, Bitmap> {
		long start;

		@Override
		protected Bitmap doInBackground(String... params) {
			start = System.currentTimeMillis();
			float degree = 0;
			if (params[0] == null)
				return null;
			// 让图片显示正确的方向
			do {
				if (!(params[0].toLowerCase().endsWith(".jpg") || params[0]
						.toLowerCase().endsWith(".jpeg"))) {
					break;
				}
				try {
					ExifInterface exifInterface = new ExifInterface(params[0]);
					int orc = exifInterface.getAttributeInt(
							ExifInterface.TAG_ORIENTATION, -1);
					double lat = exifInterface.getAttributeDouble(
							ExifInterface.TAG_GPS_LATITUDE, 0);
					double lon = exifInterface.getAttributeDouble(
							ExifInterface.TAG_GPS_LONGITUDE, 0);

					MyLog.D("hai",
							"org="
									+ orc
									+ "  lat="
									+ lat
									+ "   lon="
									+ lon
									+ "  "
									+ exifInterface
											.getAttribute(ExifInterface.TAG_MODEL));
					// 然后旋转：

					if (orc == ExifInterface.ORIENTATION_ROTATE_90) {
						degree = 90;
					} else if (orc == ExifInterface.ORIENTATION_ROTATE_180) {
						degree = 180;
					} else if (orc == ExifInterface.ORIENTATION_ROTATE_270) {
						degree = 270;
					}

				} catch (IOException e) {
					// e.();
				}
			} while (false);
			// end
			String status = Environment.getExternalStorageState();
			if (!status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
				return null;
			}

			Bitmap bitmap = null;

			MyLog.D("hai", "time as=" + (System.currentTimeMillis() - start));
			bitmap = BitmapUtil.compressPhotoFileToBitmap(params[0]);

			if (degree != 0 && bitmap != null) {
				Matrix m = new Matrix();
				m.setRotate(degree, (float) bitmap.getWidth() / 2,
						(float) bitmap.getHeight() / 2);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), m, false);
			}
			MyLog.D("hai", "time 11111as="
					+ (System.currentTimeMillis() - start));
			// BitmapUtil.savaBitmap(bitmap, params[0]);

			selectedImagePath = BitmapUtil.saveUserName(bitmap);
			MyLog.D("hai", "selectedImagePath as=" + selectedImagePath);
			MyLog.D("hai",
					"time a11121131232133333333333333s="
							+ (System.currentTimeMillis() - start));

			MyLog.D("hai",
					"time33333333333333 as="
							+ (System.currentTimeMillis() - start));

			// new File(params[0]).delete();
			return ThumbnailUtils.extractThumbnail(bitmap, 180, 180,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			setImageBitmap(result);
			callBackPath(selectedImagePath);
			MyLog.D("hai",
					"time222222222222222222222222 as="
							+ (System.currentTimeMillis() - start));
			super.onPostExecute(result);
		}

	}

}