package com.liangcang.managers;

public class ColorManager {
	private static ColorManager mColorManager;

	public synchronized static ColorManager getInsance() {
		if (mColorManager == null) {
			mColorManager = new ColorManager();
		}
		return mColorManager;
	}

	ColorStyle mColorStyle = ColorStyle.BLUE;

	public void setColorStyle(int value) {
		mColorStyle = ColorStyle.valueOf(value);
	}

	/**
	 * 获取深色的color
	 * 
	 * @return
	 */
	public int getDeepColor() {
		return mColorStyle.getDeepColor();
	}

	public int getLightColor() {
		return mColorStyle.getLightColor();
	}

	public enum ColorStyle {
		BLUE(0), GREEN(1), RED(2), YELLOW(3), LIGHT(4);
		private int value;
		private int[][] colors = { { 0xFF0000FF, 0x900000FF },// blue
				{ 0xFF0000FF, 0x900000FF },//
				{ 0xFF0000FF, 0x900000FF },//
				{ 0xFF0000FF, 0x900000FF },//
				{ 0xFF0000FF, 0x900000FF },//
		};

		ColorStyle(int value) {
			this.value = value;
		}

		/**
		 * 获取深色的color
		 * 
		 * @return
		 */
		public int getDeepColor() {
			return colors[value][0];
		}

		public int getLightColor() {
			return colors[value][1];
		}

		public static ColorStyle valueOf(int value) {
			switch (value) {
			case 0:
				return BLUE;
			case 1:
				return GREEN;
			case 2:
				return RED;
			case 3:
				return YELLOW;
			case 4:
				return LIGHT;
			}
			return ColorStyle.BLUE;
		}

	}
}
