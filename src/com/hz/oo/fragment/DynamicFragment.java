package com.hz.oo.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hz.oo.R;
import com.hz.oo.adapter.DynamicAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class DynamicFragment extends Fragment{

	private ListView lst_one,lst_two;
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dynamic, null);
		initView(view);
		lst_one.setAdapter(new DynamicAdapter(getActivity(), getOneData()));
		setListViewHeightBasedOnChildren(lst_one);
		lst_two.setAdapter(new DynamicAdapter(getActivity(), getTwoData()));
		setListViewHeightBasedOnChildren(lst_two);
		return view;
	}
	
	private void initView(View view){
		lst_one=(ListView) view.findViewById(R.id.lst_dynamic_one);
		lst_two=(ListView) view.findViewById(R.id.lst_dynamic_two);
	}
	
	// listview������Դ
		public List<Map<String, Object>> getOneData() {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("icon", R.drawable.ic_dynamic_game);
			map.put("title", "��Ϸ");
			map.put("right", R.drawable.ic_next);
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("icon", R.drawable.ic_dynamic_shopping);
			map.put("title", "����");
			map.put("right", R.drawable.ic_next);
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("icon", R.drawable.ic_dynamic_read);
			map.put("title", "�Ķ�");
			map.put("right", R.drawable.ic_next);
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("icon", R.drawable.ic_dynamic_music);
			map.put("title", "����");
			map.put("right", R.drawable.ic_next);
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("icon", R.drawable.ic_dynamic_app);
			map.put("title", "Ӧ�ñ�");
			map.put("right", R.drawable.ic_next);
			list.add(map);

			return list;
		}

		// listview������Դ
				public List<Map<String, Object>> getTwoData() {
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("icon", R.drawable.ic_dynamic_group);
					map.put("title", "������Ⱥ");
					map.put("right", R.drawable.ic_next);
					list.add(map);

					map = new HashMap<String, Object>();
					map.put("icon", R.drawable.ic_dynamic_play);
					map.put("title", "�Ժ�����");
					map.put("right", R.drawable.ic_next);
					list.add(map);

					map = new HashMap<String, Object>();
					map.put("icon", R.drawable.ic_dynamic_samecity);
					map.put("title", "ͬ�Ƿ���");
					map.put("right", R.drawable.ic_next);
					list.add(map);

					map = new HashMap<String, Object>();
					map.put("icon", R.drawable.ic_dynamic_health);
					map.put("title", "����");
					map.put("right", R.drawable.ic_next);
					list.add(map);

					map = new HashMap<String, Object>();
					map.put("icon", R.drawable.ic_dynamic_school);
					map.put("title", "OO����");
					map.put("right", R.drawable.ic_next);
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
