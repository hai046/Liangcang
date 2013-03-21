package com.liangcang.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

public class RichText extends SpannableStringBuilder{
	private Context mContext;
//	private SpannableStringBuilder spannable = null;

	public RichText(Context context) {
//		spannable = new SpannableStringBuilder();
		this.mContext = context;
	}

	public void addText(String text) {
		if (text==null) {
			return;
		}
		append(text);

	}

	

	public void addText(String text, int color, int Typeface) {
		if (text==null) {
			return;
		}
		int start = toString().length();
		int end = start + text.length();
		append(text);
		setSpan(new StyleSpan(Typeface), start, end,
				SPAN_INCLUSIVE_INCLUSIVE);
		setSpan(new ForegroundColorSpan(color), start, end,
				SPAN_INCLUSIVE_INCLUSIVE);
	}

	public void addText(String text, int color, int Typeface, float textSize) {

		if (text==null) {
			return;
		}
		int start = toString().length();
		int end = start + text.length();
		append(text);
		setSpan(new StyleSpan(Typeface), start, end,
				SPAN_INCLUSIVE_INCLUSIVE);
		setSpan(new ForegroundColorSpan(color), start, end,
				SPAN_INCLUSIVE_INCLUSIVE);
		setSpan(new RelativeSizeSpan(textSize), start, end,
				SPAN_INCLUSIVE_INCLUSIVE);

	}

	public void addTextColor(String text, int color) {
		if (text==null) {
			return;
		}
		int start = toString().length();
		int end = start + text.length();
		append(text);
		setSpan(new ForegroundColorSpan(color), start, end,
				SPAN_INCLUSIVE_INCLUSIVE);

	}

	public void clearText() {
		clear();

	}

	public void addTextType(String text, int Typeface) {
		if (text==null) {
			return;
		}
		int start = toString().length();
		int end = start + text.length();
		append(text);
		setSpan(new StyleSpan(Typeface), start, end,
				SPAN_INCLUSIVE_INCLUSIVE);

	}

	public void addImage(Bitmap bitmap) {

		addImage(bitmap, bitmap.getWidth(), bitmap.getHeight());

	}


	public void addImage(Bitmap bitmap, int width, int height) {
		
		
		append(" # ");
		ImageSpan span = new ImageSpan(mContext, BitmapUtil.formatBitmap(
				bitmap, width, height));
		setSpan(span, length() - " # ".length(),
				length(), SPAN_EXCLUSIVE_EXCLUSIVE);

	}

	public void nextLine() {

		append("\n");

	}

	public void addTextln(String text) {

		addText(text + '\n');

	}

	public void addTextln(String text, int color, int Typeface) {

		addText(text + '\n', color, Typeface);
	}

	public void addTextln(String text, int color, int Typeface, float textSize) {
		addText(text + '\n', color, Typeface, textSize);
	}

	public void addTextColorln(String text, int color) {
		addTextColor(text + "\n", color);
	}

	public void addTextTypeln(String text, int Typeface) {
		addTextType(text + "\n", Typeface);
	}

	public void addImageln(Bitmap bitmap) {

		addImageln(bitmap, bitmap.getWidth(), bitmap.getHeight());

	}

	public void addImageln(Bitmap bitmap, int width, int height) {
		if (bitmap == null)
			return;

		append(" # \n");
		ImageSpan span = new ImageSpan(mContext, BitmapUtil.formatBitmap(
				bitmap, width, height));
		setSpan(span, length() - " #".length(),
				length(), SPAN_EXCLUSIVE_EXCLUSIVE);
//		Log.i("hai", "addImage  start=" + length() + "  end="
//				+ (length() + "#".length()));

	}

}
