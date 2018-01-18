package com.hz.oo;

import java.util.ArrayList;
import java.util.List;

import com.hz.oo.fragment.CallFragment;
import com.hz.oo.fragment.ContactFragment;
import com.hz.oo.fragment.DynamicFragment;
import com.hz.oo.fragment.MessageFragment;
import com.hz.oo.fragment.SlideFragment;
import com.hz.oo.utils.FragmentUtil;
import com.hz.oo.view.AddPupopWindow;
import com.hz.oo.view.CircleImageView;

import edu.zx.slidingmenu.SlidingMenu;
import edu.zx.slidingmenu.SlidingMenu.CanvasTransformer;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SlidingPaneLayout.LayoutParams;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements OnClickListener {

	private SlidingMenu mSlidingMenu;// 侧滑栏
	private RelativeLayout rel_msg, rel_contact, rel_dynamic;// 下面的三个导航按钮
	private TextView txt_msg, txt_contact, txt_dynamic;
	private ImageView img_msg, img_contact, img_dynamic;
	private FragmentManager manager;// Fragment碎片管理器
	private FragmentTransaction transaction;
	private List<Fragment> fragmentArry;
	private int tabPressedColor;
	private int tabNormalColor;
	private int oldTabIndex = FragmentUtil.TabIndex.TAB_MSG;// 上次选中的tab索引
	private int newTabIndex = FragmentUtil.TabIndex.TAB_MSG;// 新选中的tab索引

	// 这个是声明页面上的头像的那个image控件的ID
	private CircleImageView img_click;
	private LinearLayout lin_middle;
	private TextView txt_title;
	private TextView txt_right;
	private ImageView img_head_right;
	private TextView txt_head_msg, txt_head_call;

	private CanvasTransformer mTransformer;
	// 定义一个变量，来标识是否退出
	private static boolean isExit = false;

	private AddPupopWindow menuWindow;
	private long startTime, endTime;
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initSlideMenu();
		// SlideMenu的缩放动画
		mTransformer = new CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (percentOpen * 0.25 + 0.75);
				canvas.scale(scale, scale, canvas.getWidth() / 2,
						canvas.getHeight() / 2);
			}
		};
		mSlidingMenu.setBehindCanvasTransformer(mTransformer);

		// 创建一个AnimationSet对象，参数为Boolean型
		// true表示使用Animation的interpolator，false则是使用自己的
		AnimationSet animationSet = new AnimationSet(true);
		// 创建一个AlphaAnimation对象，参数从完全的透明度，到完全的不透明
		AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
		// 设置动画执行的时间
		alphaAnimation.setDuration(500);
		// 将alphaAnimation对象添加到AnimationSet当中
		animationSet.addAnimation(alphaAnimation);

		// 创建一个AnimationSet对象，参数为Boolean型，
		// true表示使用Animation的interpolator，false则是使用自己的
		AnimationSet animationSet1 = new AnimationSet(true);
		// 创建一个AlphaAnimation对象，参数从完全的透明度，到完全的不透明
		AlphaAnimation alphaAnimation1 = new AlphaAnimation(0, 1);
		// 设置动画执行的时间
		alphaAnimation1.setDuration(500);
		// 将alphaAnimation对象添加到AnimationSet当中
		animationSet1.addAnimation(alphaAnimation);

		tabPressedColor = getResources().getColor(R.color.tab_select_color);
		tabNormalColor = getResources().getColor(R.color.tab_uncelect_color);
		initView();

	}

	private void initSlideMenu() {
		int width = getWindow().getWindowManager().getDefaultDisplay()
				.getWidth();

		mSlidingMenu = new SlidingMenu(this);
		mSlidingMenu.setBackgroundResource(R.drawable.ic_slidemenu_bg);
		// 关联
		mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);// 设置样式和内容平行的显示
		mSlidingMenu.setBehindOffset(width / 4);// 距离后面屏幕的距离
		// 设置菜单的模式
		mSlidingMenu.setMode(SlidingMenu.LEFT);
		// 设置左边的
		mSlidingMenu.setMenu(R.layout.left_slide);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 设置触摸的模式
		// 添加左边的菜单
		FragmentTransaction frans = getSupportFragmentManager()
				.beginTransaction();
		frans.add(R.id.left_slide, new SlideFragment());
		frans.commit();// 提交
	}

	private void initView() {
		img_click = (CircleImageView) findViewById(R.id.img_click);
		img_click.setOnClickListener(this);

		lin_middle = (LinearLayout) findViewById(R.id.lin_title);
		txt_title = (TextView) findViewById(R.id.txt_head_title);
		txt_right = (TextView) findViewById(R.id.txt_head_right);
		txt_right.setOnClickListener(this);
		img_head_right=(ImageView) findViewById(R.id.img_head_right);
		img_head_right.setOnClickListener(this);
		img_head_right.setBackgroundResource(R.drawable.ic_add);
		img_head_right.setVisibility(View.VISIBLE);

		txt_head_msg = (TextView) findViewById(R.id.txt_head_msg);
		txt_head_call = (TextView) findViewById(R.id.txt_head_call);
		txt_head_msg.setOnClickListener(this);
		txt_head_call.setOnClickListener(this);

		rel_msg = (RelativeLayout) findViewById(R.id.rel_msg);
		rel_contact = (RelativeLayout) findViewById(R.id.rel_contact);
		rel_dynamic = (RelativeLayout) findViewById(R.id.rel_dynamic);

		rel_msg.setOnClickListener(this);
		rel_dynamic.setOnClickListener(this);
		rel_contact.setOnClickListener(this);

		img_msg = (ImageView) findViewById(R.id.img_msg);
		img_contact = (ImageView) findViewById(R.id.img_contact);
		img_dynamic = (ImageView) findViewById(R.id.img_dynamic);

		txt_msg = (TextView) findViewById(R.id.txt_msg);
		txt_contact = (TextView) findViewById(R.id.txt_contact);
		txt_dynamic = (TextView) findViewById(R.id.txt_dynamic);

		// 初始化Fragment碎片管理
		fragmentArry = new ArrayList<Fragment>();
		fragmentArry.add(new MessageFragment());
		fragmentArry.add(new ContactFragment());
		fragmentArry.add(new DynamicFragment());

		manager = getSupportFragmentManager();// 获取Fragment管理器
		transaction = manager.beginTransaction();// 从管理器中得到一个Fragment事务
		transaction.add(R.id.mainFragment,
				fragmentArry.get(FragmentUtil.TabIndex.TAB_MSG));// 将得到的fragment替换当前的viewGroup内
		transaction.commit();
		lin_middle.setVisibility(View.VISIBLE);
		txt_title.setVisibility(View.GONE);
	}

	/**
	 * 切换Fragment视图
	 * 
	 * @param contentId
	 *            容器ID
	 * @param fragment
	 *            切换对象
	 * @param index
	 *            tab索引位置
	 */
	private void replace(int contentId, Fragment fragment, int index) {
		if (!fragment.isAdded()) {
			changeTabState(index);
			System.out.println("newIndex:" + newTabIndex + ",oldIndex:"
					+ oldTabIndex);

			FragmentTransaction fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();

			fragmentTransaction.replace(contentId, fragment).commit();

			oldTabIndex = newTabIndex;
		}
	}

	/**
	 * 改变Tab状态
	 */
	private void changeTabState(int index) {
		newTabIndex = index;
		switch (index) {
		case FragmentUtil.TabIndex.TAB_MSG:
			txt_msg.setTextColor(tabPressedColor);
			txt_contact.setTextColor(tabNormalColor);
			txt_dynamic.setTextColor(tabNormalColor);

			 img_msg.setBackgroundResource(R.drawable.ic_main_msg_select);
			 img_contact.setBackgroundResource(R.drawable.ic_main_contact_unselect);
			 img_dynamic.setBackgroundResource(R.drawable.ic_main_dynamic_unselect);
			
			lin_middle.setVisibility(View.VISIBLE);
			txt_title.setVisibility(View.GONE);
			txt_right.setText("");
			txt_right.setVisibility(View.GONE);
			img_head_right.setVisibility(View.VISIBLE);
			img_head_right.setBackgroundResource(R.drawable.ic_add);
			break;
		case FragmentUtil.TabIndex.TAB_CONTACT:
			txt_msg.setTextColor(tabNormalColor);
			txt_contact.setTextColor(tabPressedColor);
			txt_dynamic.setTextColor(tabNormalColor);

			 img_msg.setBackgroundResource(R.drawable.ic_main_msg_unselect);
			 img_contact.setBackgroundResource(R.drawable.ic_main_contact_select);
			 img_dynamic.setBackgroundResource(R.drawable.ic_main_dynamic_unselect);
			
			lin_middle.setVisibility(View.GONE);
			txt_title.setVisibility(View.VISIBLE);
			txt_title.setText("联系人");
			txt_right.setText("添加");
			txt_right.setVisibility(View.VISIBLE);
			img_head_right.setVisibility(View.GONE);
			break;
		case FragmentUtil.TabIndex.TAB_DYNAMIC:
			txt_msg.setTextColor(tabNormalColor);
			txt_contact.setTextColor(tabNormalColor);
			txt_dynamic.setTextColor(tabPressedColor);

			 img_msg.setBackgroundResource(R.drawable.ic_main_msg_unselect);
			 img_contact.setBackgroundResource(R.drawable.ic_main_contact_unselect);
			 img_dynamic.setBackgroundResource(R.drawable.ic_main_dynamic_select);
			
			lin_middle.setVisibility(View.GONE);
			txt_title.setVisibility(View.VISIBLE);
			txt_title.setText("动态");
			txt_right.setText("更多");
			txt_right.setVisibility(View.VISIBLE);
			img_head_right.setVisibility(View.GONE);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rel_msg:// 切换到消息的fragment
			replace(R.id.mainFragment,
					fragmentArry.get(FragmentUtil.TabIndex.TAB_MSG),
					FragmentUtil.TabIndex.TAB_MSG);
			break;
		case R.id.rel_contact:// 切换到联系人的fragment
			replace(R.id.mainFragment,
					fragmentArry.get(FragmentUtil.TabIndex.TAB_CONTACT),
					FragmentUtil.TabIndex.TAB_CONTACT);
			break;
		case R.id.rel_dynamic:// 切换到动态的fragment
			replace(R.id.mainFragment,
					fragmentArry.get(FragmentUtil.TabIndex.TAB_DYNAMIC),
					FragmentUtil.TabIndex.TAB_DYNAMIC);
			break;
		case R.id.txt_head_msg:// 消息
			txt_head_msg.setTextColor(getResources().getColor(R.color.white));
			txt_head_msg.setBackgroundResource(R.drawable.left_shape1);
			txt_head_call.setTextColor(getResources().getColor(
					R.color.tab_select_color));
			txt_head_call.setBackgroundResource(R.drawable.right_shape2);

			fragmentArry.remove(0);
			fragmentArry.add(0, new MessageFragment());
			replace(R.id.mainFragment,
					fragmentArry.get(FragmentUtil.TabIndex.TAB_MSG),
					FragmentUtil.TabIndex.TAB_MSG);
			break;
		case R.id.txt_head_call:// 打电话
			txt_head_msg.setTextColor(getResources().getColor(
					R.color.tab_select_color));
			txt_head_msg.setBackgroundResource(R.drawable.left_shape2);
			txt_head_call.setTextColor(getResources().getColor(R.color.white));
			txt_head_call.setBackgroundResource(R.drawable.right_shape1);

			fragmentArry.remove(0);
			fragmentArry.add(0, new CallFragment());
			replace(R.id.mainFragment,
					fragmentArry.get(FragmentUtil.TabIndex.TAB_MSG),
					FragmentUtil.TabIndex.TAB_MSG);
			break;
		case R.id.txt_head_right:
			switch (newTabIndex) {
			case FragmentUtil.TabIndex.TAB_MSG:
				// Toast.makeText(this, "我是加号", 0).show();
				
				break;
			case FragmentUtil.TabIndex.TAB_CONTACT:
				Toast.makeText(this, "我是添加", 0).show();
				break;
			case FragmentUtil.TabIndex.TAB_DYNAMIC:
				Toast.makeText(this, "我是更多", 0).show();
				break;
			}
			break;
		case R.id.img_click:// 用户头像
			mSlidingMenu.toggle();
			break;
		case R.id.img_head_right://加号
			menuWindow = new AddPupopWindow(MainActivity.this, itemsOnClick);
			menuWindow.showAtLocation(
					findViewById(R.id.activity_main),
					Gravity.FILL , 0, 0);
			break;
		}
	}

	// 为弹出窗口实现监听类
		private OnClickListener itemsOnClick = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menuWindow.dismiss();
				switch (v.getId()) {
				case R.id.lin_sao://扫一扫
					
					break;
				case R.id.lin_add_friend://加好友
					
					break;
				case R.id.lin_creat_group://讨论组
					
					break;
				case R.id.lin_send_to_computer://发送到电脑
					
					break;
				case R.id.lin_face_to_face://面对面快传
					
					break;
				case R.id.lin_fast_money://快钱
					
					break;
				}
			}
		};
	
	// 点击两次退出
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			// 利用handler延迟发送更改状态信息
			mHandler.sendEmptyMessageDelayed(0, 3000);
		} else {
			finish();
			System.exit(0);
		}
	}
}
