package com.hz.oo.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hz.oo.DressUpActivity;
import com.hz.oo.MyFileActivity;
import com.hz.oo.MyPhotoActivity;
import com.hz.oo.QQMoneyActivity;
import com.hz.oo.R;
import com.hz.oo.SaveActivity;
import com.hz.oo.VIPActivity;
import com.hz.oo.adapter.SlideMenuAdapter;
import com.hz.oo.view.XXListView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class SlideFragment extends Fragment implements OnClickListener {

	private XXListView lst_slide;
	private RelativeLayout rel_night;
	private LinearLayout lin_slide_wendu_location;
	private RelativeLayout rel_slide_set;

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(getContext()).inflate(
				R.layout.fragment_slide, null);
		initView(view);
		lst_slide.setAdapter(new SlideMenuAdapter(getActivity(), getData()));
		lst_slide.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:// �����Ա
					startActivity(new Intent().setClass(getActivity(),
							VIPActivity.class));
					break;
				case 1:// QQǮ��
					startActivity(new Intent().setClass(getActivity(),
							QQMoneyActivity.class));
					break;
				case 2:// ����װ��
					startActivity(new Intent().setClass(getActivity(),
							DressUpActivity.class));
					break;
				case 3:// �ҵ��ղ�
					startActivity(new Intent().setClass(getActivity(),
							SaveActivity.class));
					break;
				case 4:// �ҵ����
					startActivity(new Intent().setClass(getActivity(),
							MyPhotoActivity.class));
					break;
				case 5:// �ҵ��ļ�
					startActivity(new Intent().setClass(getActivity(),
							MyFileActivity.class));
					break;
				}
			}
		});
		return view;
	}

	private void initView(View view) {
		lst_slide = (XXListView) view.findViewById(R.id.lst_slide);
		// ҹ��
		rel_night = (RelativeLayout) view.findViewById(R.id.lin_night);
		rel_night.setOnClickListener(this);
		// �¶Ⱥ�λ��
		lin_slide_wendu_location = (LinearLayout) view
				.findViewById(R.id.lin_slide_wendu_location);
		lin_slide_wendu_location.setOnClickListener(this);
		// ����
		rel_slide_set = (RelativeLayout) view.findViewById(R.id.rel_slide_set);
		rel_slide_set.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	// listview������Դ
	public List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("icon", R.drawable.ic_tara);
		map.put("title", "�����Ա");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.ic_wallet);
		map.put("title", "OOǮ��");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.ic_slide_paint);
		map.put("title", "����װ��");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.ic_slide_save);
		map.put("title", "�ҵ��ղ�");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.ic_slide_photo);
		map.put("title", "�ҵ����");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.ic_slide_file);
		map.put("title", "�ҵ��ļ�");
		list.add(map);

		return list;
	}

	// ����listview�ĸ߶� ������scrollview��ֻ��ʾһ��
	public void setListViewHeightBasedOnChildren(ListView listView) {
		// ��ȡListView��Ӧ��Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()�������������Ŀ
			View listItem = listAdapter.getView(i, null, listView);
			// ��������View �Ŀ��
			listItem.measure(0, 0);
			// ͳ������������ܸ߶�
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()��ȡ�����ָ���ռ�õĸ߶�
		// params.height���õ�����ListView������ʾ��Ҫ�ĸ߶�
		listView.setLayoutParams(params);
	}
}