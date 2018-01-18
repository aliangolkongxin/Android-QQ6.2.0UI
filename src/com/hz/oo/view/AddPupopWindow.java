package com.hz.oo.view;

import com.hz.oo.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * @author  hz    
 * 2016-1-7
 */
public class AddPupopWindow extends PopupWindow {

	private LinearLayout lin_sao,lin_add_friend,lin_creat_group,lin_send_to_computer,lin_face_to_face,lin_fast_money;
	private View mMenuView;

	@SuppressLint("InflateParams")
	public AddPupopWindow(Context context, OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.add_popup_dialog, null);
		lin_sao = (LinearLayout) mMenuView.findViewById(R.id.lin_sao);
		lin_add_friend = (LinearLayout) mMenuView.findViewById(R.id.lin_add_friend);
		lin_creat_group =  (LinearLayout) mMenuView.findViewById(R.id.lin_creat_group);
		lin_send_to_computer=(LinearLayout) mMenuView.findViewById(R.id.lin_send_to_computer);
		lin_face_to_face=(LinearLayout) mMenuView.findViewById(R.id.lin_face_to_face);
		lin_fast_money=(LinearLayout) mMenuView.findViewById(R.id.lin_fast_money);
		// 设置按钮监听
		lin_sao.setOnClickListener(itemsOnClick);
		lin_add_friend.setOnClickListener(itemsOnClick);
		lin_creat_group.setOnClickListener(itemsOnClick);
		lin_send_to_computer.setOnClickListener(itemsOnClick);
		lin_face_to_face.setOnClickListener(itemsOnClick);
		lin_fast_money.setOnClickListener(itemsOnClick);
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimationPreview);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x1F000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {

			@Override
			@SuppressLint("ClickableViewAccessibility")
			public boolean onTouch(View v, MotionEvent event) {

				int height = mMenuView.findViewById(R.id.pop_layout2).getTop();
				int bottom=mMenuView.findViewById(R.id.pop_layout2).getBottom();
				int left=mMenuView.findViewById(R.id.pop_layout2).getLeft();
				int y = (int) event.getY();
				int x =(int) event.getX();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height||x<left||y>bottom) {
						dismiss();
					}
				}
				return true;
			}
		});
	}
}