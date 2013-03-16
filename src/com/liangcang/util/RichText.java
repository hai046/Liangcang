package com.liangcang.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

public class RichText {
	private Context mContext;
	private SpannableStringBuilder spannable = null;

	public RichText(Context context) {
		spannable = new SpannableStringBuilder();
		this.mContext = context;
	}

	public void addText(String text) {
		if (text==null) {
			return;
		}
		spannable.append(text);

	}

	public SpannableStringBuilder getSpannable() {
		return spannable;
	}

	public void addText(String text, int color, int Typeface) {
		if (text==null) {
			return;
		}
		int start = spannable.toString().length();
		int end = start + text.length();
		spannable.append(text);
		spannable.setSpan(new StyleSpan(Typeface), start, end,
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		spannable.setSpan(new ForegroundColorSpan(color), start, end,
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);
	}

	public void addText(String text, int color, int Typeface, float textSize) {

		if (text==null) {
			return;
		}
		int start = spannable.toString().length();
		int end = start + text.length();
		spannable.append(text);
		spannable.setSpan(new StyleSpan(Typeface), start, end,
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		spannable.setSpan(new ForegroundColorSpan(color), start, end,
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		spannable.setSpan(new RelativeSizeSpan(textSize), start, end,
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);

	}

	public void addTextColor(String text, int color) {
		if (text==null) {
			return;
		}
		int start = spannable.toString().length();
		int end = start + text.length();
		spannable.append(text);
		spannable.setSpan(new ForegroundColorSpan(color), start, end,
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);

	}

	public void clearText() {
		spannable.clear();

	}

	public void addTextType(String text, int Typeface) {
		if (text==null) {
			return;
		}
		int start = spannable.toString().length();
		int end = start + text.length();
		spannable.append(text);
		spannable.setSpan(new StyleSpan(Typeface), start, end,
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);

	}

	public void addImage(Bitmap bitmap) {

		addImage(bitmap, bitmap.getWidth(), bitmap.getHeight());

	}


	public void addImage(Bitmap bitmap, int width, int height) {
		
		
		spannable.append(" # ");
		ImageSpan span = new ImageSpan(mContext, BitmapUtil.formatBitmap(
				bitmap, width, height));
		spannable.setSpan(span, spannable.length() - " # ".length(),
				spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

	}

	public void nextLine() {

		spannable.append("\n");

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

		spannable.append(" # \n");
		ImageSpan span = new ImageSpan(mContext, BitmapUtil.formatBitmap(
				bitmap, width, height));
		spannable.setSpan(span, spannable.length() - " #".length(),
				spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		Log.i("hai", "addImage  start=" + spannable.length() + "  end="
//				+ (spannable.length() + "#".length()));

	}

}
