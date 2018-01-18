package com.hz.oo;

import com.hz.oo.utils.Autjcode;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FindPwdActivity extends BaseActivity{

	private LinearLayout btn_back;
	private ImageView findPasswordimg;
	private Button findPasswordCheck;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_pwd);
		btn_back=(LinearLayout) findViewById(R.id.lin_back);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		findPasswordimg = (ImageView) findViewById(R.id.findPasswordimg);
		// 生成验证码
		findPasswordimg.setImageBitmap(Autjcode.getInstance().createBitmap());
		
		findPasswordCheck=(Button) findViewById(R.id.findPasswordCheck);
		findPasswordCheck.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				findPasswordimg.setImageBitmap(Autjcode.getInstance()
						.createBitmap());
			}
		});
	}
}
