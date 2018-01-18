package com.hz.oo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends BaseActivity implements OnClickListener{

	private Button btn_login;
	private Button loginNewUser;
	private Button loginMissps;
	private Button loginChangePw;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		initView();
	}
	
	private void initView(){
		btn_login=(Button) findViewById(R.id.loginBtn);
		btn_login.setOnClickListener(this);
		loginMissps=(Button) findViewById(R.id.loginMissps);
		loginMissps.setOnClickListener(this);
		loginNewUser=(Button) findViewById(R.id.loginNewUser);
		loginNewUser.setOnClickListener(this);
		loginChangePw=(Button) findViewById(R.id.loginChangePw);
		loginChangePw.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.loginBtn:
			startActivity(new Intent().setClass(LoginActivity.this, MainActivity.class));
			finish();
			break;
		case R.id.loginNewUser:
			startActivity(new Intent().setClass(LoginActivity.this, RegisterActivity.class));
			break;
		case R.id.loginMissps:
			startActivity(new Intent().setClass(LoginActivity.this, FindPwdActivity.class));
			break;
		case R.id.loginChangePw:
			startActivity(new Intent().setClass(LoginActivity.this, ChangePwdActivity.class));
			break;
		default:
			break;
		}
	}
}
