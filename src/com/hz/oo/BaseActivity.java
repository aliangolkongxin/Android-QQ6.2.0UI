package com.hz.oo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.hz.oo.system.SystemBarTintManager;
import com.hz.oo.utils.FlymeUtils;
import com.hz.oo.utils.MIUIUtils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class BaseActivity extends FragmentActivity {

	private String versionNum;
	private int versionMIUI;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_base);
		Log.e("miui", ""+MIUIUtils.isMIUI());
		Log.e("miui", ""+MIUIUtils.MIUIVersin());
		String miuiVersion=MIUIUtils.MIUIVersin();
		if(miuiVersion!=null&&!miuiVersion.equals("")){
			versionNum=miuiVersion.substring(1, 2);
			Log.e("miui", versionNum);
			versionMIUI=Integer.parseInt(versionNum);
		}
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
				&& MIUIUtils.isMIUI()&&versionMIUI>5) {
			
			setTranslucentStatus(true);
			 SystemBarTintManager tintManager = new
			 SystemBarTintManager(this);
			 tintManager.setStatusBarTintEnabled(true);
			 tintManager.setStatusBarTintResource(R.color.white);// 通知栏所需颜色
			
			Window window = getWindow();
			Class clazz = window.getClass();
			try {
				int tranceFlag = 0;
				int darkModeFlag = 0;
				Class layoutParams = Class
						.forName("android.view.MiuiWindowManager$LayoutParams");

				Field field = layoutParams
						.getField("EXTRA_FLAG_STATUS_BAR_TRANSPARENT");
				tranceFlag = field.getInt(layoutParams);

				field = layoutParams
						.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
				darkModeFlag = field.getInt(layoutParams);

				Method extraFlagField = clazz.getMethod("setExtraFlags",
						int.class, int.class);
				// 只需要状态栏透明
				//extraFlagField.invoke(window, tranceFlag, tranceFlag);
				// 状态栏透明且黑色字体
				extraFlagField.invoke(window, tranceFlag | darkModeFlag,
						tranceFlag | darkModeFlag);
				// 清除黑色字体
				//extraFlagField.invoke(window, 0, darkModeFlag);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT&&FlymeUtils.isFlyme()){
			 setTranslucentStatus(true);
			 SystemBarTintManager tintManager = new
			 SystemBarTintManager(this);
			 tintManager.setStatusBarTintEnabled(true);
			 tintManager.setStatusBarTintResource(R.color.white);// 通知栏所需颜色
		}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
			setTranslucentStatus(true);
			 SystemBarTintManager tintManager = new
			 SystemBarTintManager(this);
			 tintManager.setStatusBarTintEnabled(true);
			 tintManager.setStatusBarTintResource(R.color.status_color);// 通知栏所需颜色
		}
	}

	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
}
