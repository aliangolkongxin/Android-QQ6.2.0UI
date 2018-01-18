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
	
	// listview的数据源
		public List<Map<String, Object>> getOneData() {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("icon", R.drawable.ic_dynamic_game);
			map.put("title", "游戏");
			map.put("right", R.drawable.ic_next);
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("icon", R.drawable.ic_dynamic_shopping);
			map.put("title", "购物");
			map.put("right", R.drawable.ic_next);
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("icon", R.drawable.ic_dynamic_read);
			map.put("title", "阅读");
			map.put("right", R.drawable.ic_next);
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("icon", R.drawable.ic_dynamic_music);
			map.put("title", "音乐");
			map.put("right", R.drawable.ic_next);
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("icon", R.drawable.ic_dynamic_app);
			map.put("title", "应用宝");
			map.put("right", R.drawable.ic_next);
			list.add(map);

			return list;
		}

		// listview的数据源
				public List<Map<String, Object>> getTwoData() {
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("icon", R.drawable.ic_dynamic_group);
					map.put("title", "附近的群");
					map.put("right", R.drawable.ic_next);
					list.add(map);

					map = new HashMap<String, Object>();
					map.put("icon", R.drawable.ic_dynamic_play);
					map.put("title", "吃喝玩乐");
					map.put("right", R.drawable.ic_next);
					list.add(map);

					map = new HashMap<String, Object>();
					map.put("icon", R.drawable.ic_dynamic_samecity);
					map.put("title", "同城服务");
					map.put("right", R.drawable.ic_next);
					list.add(map);

					map = new HashMap<String, Object>();
					map.put("icon", R.drawable.ic_dynamic_health);
					map.put("title", "健康");
					map.put("right", R.drawable.ic_next);
					list.add(map);

					map = new HashMap<String, Object>();
					map.put("icon", R.drawable.ic_dynamic_school);
					map.put("title", "OO课堂");
					map.put("right", R.drawable.ic_next);
					list.add(map);

					return list;
				}
		
		// 设置listview的高度 否则在scrollview中只显示一行
		public void setListViewHeightBasedOnChildren(ListView listView) {
			// 获取ListView对应的Adapter
			ListAdapter listAdapter = listView.getAdapter();
			if (listAdapter == null) {
				return;
			}
			int totalHeight = 0;
			for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
				// listAdapter.getCount()返回数据项的数目
				View listItem = listAdapter.getView(i, null, listView);
				// 计算子项View 的宽高
				listItem.measure(0, 0);
				// 统计所有子项的总高度
				totalHeight += listItem.getMeasuredHeight();
			}

			ViewGroup.LayoutParams params = listView.getLayoutParams();
			params.height = totalHeight
					+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
			// listView.getDividerHeight()获取子项间分隔符占用的高度
			// params.height最后得到整个ListView完整显示需要的高度
			listView.setLayoutParams(params);
		}
}
