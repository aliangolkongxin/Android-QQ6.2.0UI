package com.hz.oo.utils;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Autjcode {
	private static Autjcode bmpCode;
	private int width = 100, height = 60;
	private int base_padding_left = 15, range_padding_left = 5,
			base_padding_top = 25, range_padding_top = 30;
	private int codeLength = 4, line_number = 2, font_size = 25;
	private String code;
	private int padding_left, padding_top;
	private static final char[] CHARS = { '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm',
			'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A',
			'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	/**
	 * 获得实例
	 * 
	 * @return
	 */
	public static Autjcode getInstance() {
		if (bmpCode == null)
			bmpCode = new Autjcode();
		return bmpCode;
	}

	private Random random = new Random();

	/**
	 * 创建位图
	 * 
	 * @return
	 */
	public Bitmap createBitmap() {
		padding_left = 0;
		// ARGB_8888表示为32位的ARGB位图
		Bitmap bp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas c = new Canvas(bp);
		code = createCode();
		c.drawColor(Color.WHITE);
		Paint paint = new Paint();
		paint.setTextSize(font_size);
		paint.setFakeBoldText(true);

		for (int i = 0; i < code.length(); i++) {
			randomTextStyle(paint);
			randomPadding();
			c.drawText(code.charAt(i) + "", padding_left, padding_top, paint);
		}
		for (int i = 0; i <= line_number; i++) {
			drawLine(c, paint);
		}
		c.save(Canvas.ALL_SAVE_FLAG);// 保存
		c.restore();
		return bp;
	}

	public String getCode() {
		return code;
	}

	// 验证码
	private String createCode() {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < codeLength; i++) {
			buffer.append(CHARS[random.nextInt(CHARS.length)]);
		}
		return buffer.toString();
	}

	/**
	 * 划线
	 * 
	 * @param canvas
	 * @param paint
	 */
	private void drawLine(Canvas canvas, Paint paint) {
		int color = randomColor();
		int startX = random.nextInt(width);
		int startY = random.nextInt(height);
		int stopX = random.nextInt(width);
		int stopY = random.nextInt(height);
		paint.setStrokeWidth(1);
		paint.setColor(color);
		canvas.drawLine(startX, startY, stopX, stopY, paint);
	}

	private int randomColor() {
		return randomColor(1);
	}

	private int randomColor(int rate) {
		int red = random.nextInt(256) / rate;
		int green = random.nextInt(256) / rate;
		int blue = random.nextInt(256) / rate;
		return Color.rgb(red, green, blue);
	}

	private void randomTextStyle(Paint paint) {
		int color = randomColor();
		paint.setColor(color);
		paint.setFakeBoldText(random.nextBoolean());
		float skewX = random.nextInt(11) / 10;
		skewX = random.nextBoolean() ? skewX : -skewX;
		paint.setTextSkewX(skewX);
	}

	private void randomPadding() {
		padding_left += base_padding_left + random.nextInt(range_padding_left);
		padding_top = base_padding_top + random.nextInt(range_padding_top);
	}

}
