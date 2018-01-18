package com.hz.oo;

import com.hz.oo.utils.Autjcode;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class RegisterActivity extends BaseActivity implements OnClickListener{

	private LinearLayout rel_back;
	private Button btn_registerCheck;
	private ImageView registerAuthimg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
	}
	
	private void initView(){
		rel_back= (LinearLayout) findViewById(R.id.rel_back);
		rel_back.setOnClickListener(this);
		btn_registerCheck=(Button) findViewById(R.id.registerCheck);
		btn_registerCheck.setOnClickListener(this);
		registerAuthimg=(ImageView) findViewById(R.id.registerAuthimg);
		registerAuthimg.setImageBitmap(Autjcode.getInstance().createBitmap());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rel_back:
			finish();
			break;
		case R.id.registerCheck:
			registerAuthimg.setImageBitmap(Autjcode.getInstance()
					.createBitmap());
			break;
		default:
			break;
		}
	}
}
