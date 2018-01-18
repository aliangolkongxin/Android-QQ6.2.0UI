package com.hz.oo.fragment;

import com.hz.oo.R;
import com.hz.oo.view.RefreshableView;
import com.hz.oo.view.RefreshableView.RefreshListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class CallFragment extends Fragment implements
RefreshListener{

	private RefreshableView mRefreshableView;
	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			super.handleMessage(message);
			mRefreshableView.finishRefresh();
			Toast.makeText(getActivity(), R.string.toast_text, Toast.LENGTH_SHORT).show();
		};
	};
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_call, null);
		initView(view);
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		mRefreshableView = (RefreshableView) view.findViewById(R.id.refresh_call);
		initData();
	}
	private void initData() {
		mRefreshableView.setRefreshListener(this);
		
	}
	
	//实现刷新RefreshListener 中方法
		public void onRefresh(RefreshableView view) {
			//伪处理
			handler.sendEmptyMessageDelayed(1, 2000);
			
		}
}
