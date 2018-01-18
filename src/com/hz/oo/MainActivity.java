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

	private SlidingMenu mSlidingMenu;// �໬��
	private RelativeLayout rel_msg, rel_contact, rel_dynamic;// ���������������ť
	private TextView txt_msg, txt_contact, txt_dynamic;
	private ImageView img_msg, img_contact, img_dynamic;
	private FragmentManager manager;// Fragment��Ƭ������
	private FragmentTransaction transaction;
	private List<Fragment> fragmentArry;
	private int tabPressedColor;
	private int tabNormalColor;
	private int oldTabIndex = FragmentUtil.TabIndex.TAB_MSG;// �ϴ�ѡ�е�tab����
	private int newTabIndex = FragmentUtil.TabIndex.TAB_MSG;// ��ѡ�е�tab����

	// ���������ҳ���ϵ�ͷ����Ǹ�image�ؼ���ID
	private CircleImageView img_click;
	private LinearLayout lin_middle;
	private TextView txt_title;
	private TextView txt_right;
	private ImageView img_head_right;
	private TextView txt_head_msg, txt_head_call;

	private CanvasTransformer mTransformer;
	// ����һ������������ʶ�Ƿ��˳�
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
		// SlideMenu�����Ŷ���
		mTransformer = new CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (percentOpen * 0.25 + 0.75);
				canvas.scale(scale, scale, canvas.getWidth() / 2,
						canvas.getHeight() / 2);
			}
		};
		mSlidingMenu.setBehindCanvasTransformer(mTransformer);

		// ����һ��AnimationSet���󣬲���ΪBoolean��
		// true��ʾʹ��Animation��interpolator��false����ʹ���Լ���
		AnimationSet animationSet = new AnimationSet(true);
		// ����һ��AlphaAnimation���󣬲�������ȫ��͸���ȣ�����ȫ�Ĳ�͸��
		AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
		// ���ö���ִ�е�ʱ��
		alphaAnimation.setDuration(500);
		// ��alphaAnimation������ӵ�AnimationSet����
		animationSet.addAnimation(alphaAnimation);

		// ����һ��AnimationSet���󣬲���ΪBoolean�ͣ�
		// true��ʾʹ��Animation��interpolator��false����ʹ���Լ���
		AnimationSet animationSet1 = new AnimationSet(true);
		// ����һ��AlphaAnimation���󣬲�������ȫ��͸���ȣ�����ȫ�Ĳ�͸��
		AlphaAnimation alphaAnimation1 = new AlphaAnimation(0, 1);
		// ���ö���ִ�е�ʱ��
		alphaAnimation1.setDuration(500);
		// ��alphaAnimation������ӵ�AnimationSet����
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
		// ����
		mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);// ������ʽ������ƽ�е���ʾ
		mSlidingMenu.setBehindOffset(width / 4);// ���������Ļ�ľ���
		// ���ò˵���ģʽ
		mSlidingMenu.setMode(SlidingMenu.LEFT);
		// ������ߵ�
		mSlidingMenu.setMenu(R.layout.left_slide);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// ���ô�����ģʽ
		// �����ߵĲ˵�
		FragmentTransaction frans = getSupportFragmentManager()
				.beginTransaction();
		frans.add(R.id.left_slide, new SlideFragment());
		frans.commit();// �ύ
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

		// ��ʼ��Fragment��Ƭ����
		fragmentArry = new ArrayList<Fragment>();
		fragmentArry.add(new MessageFragment());
		fragmentArry.add(new ContactFragment());
		fragmentArry.add(new DynamicFragment());

		manager = getSupportFragmentManager();// ��ȡFragment������
		transaction = manager.beginTransaction();// �ӹ������еõ�һ��Fragment����
		transaction.add(R.id.mainFragment,
				fragmentArry.get(FragmentUtil.TabIndex.TAB_MSG));// ���õ���fragment�滻��ǰ��viewGroup��
		transaction.commit();
		lin_middle.setVisibility(View.VISIBLE);
		txt_title.setVisibility(View.GONE);
	}

	/**
	 * �л�Fragment��ͼ
	 * 
	 * @param contentId
	 *            ����ID
	 * @param fragment
	 *            �л�����
	 * @param index
	 *            tab����λ��
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
	 * �ı�Tab״̬
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
			txt_title.setText("��ϵ��");
			txt_right.setText("���");
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
			txt_title.setText("��̬");
			txt_right.setText("����");
			txt_right.setVisibility(View.VISIBLE);
			img_head_right.setVisibility(View.GONE);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rel_msg:// �л�����Ϣ��fragment
			replace(R.id.mainFragment,
					fragmentArry.get(FragmentUtil.TabIndex.TAB_MSG),
					FragmentUtil.TabIndex.TAB_MSG);
			break;
		case R.id.rel_contact:// �л�����ϵ�˵�fragment
			replace(R.id.mainFragment,
					fragmentArry.get(FragmentUtil.TabIndex.TAB_CONTACT),
					FragmentUtil.TabIndex.TAB_CONTACT);
			break;
		case R.id.rel_dynamic:// �л�����̬��fragment
			replace(R.id.mainFragment,
					fragmentArry.get(FragmentUtil.TabIndex.TAB_DYNAMIC),
					FragmentUtil.TabIndex.TAB_DYNAMIC);
			break;
		case R.id.txt_head_msg:// ��Ϣ
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
		case R.id.txt_head_call:// ��绰
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
				// Toast.makeText(this, "���ǼӺ�", 0).show();
				
				break;
			case FragmentUtil.TabIndex.TAB_CONTACT:
				Toast.makeText(this, "�������", 0).show();
				break;
			case FragmentUtil.TabIndex.TAB_DYNAMIC:
				Toast.makeText(this, "���Ǹ���", 0).show();
				break;
			}
			break;
		case R.id.img_click:// �û�ͷ��
			mSlidingMenu.toggle();
			break;
		case R.id.img_head_right://�Ӻ�
			menuWindow = new AddPupopWindow(MainActivity.this, itemsOnClick);
			menuWindow.showAtLocation(
					findViewById(R.id.activity_main),
					Gravity.FILL , 0, 0);
			break;
		}
	}

	// Ϊ��������ʵ�ּ�����
		private OnClickListener itemsOnClick = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menuWindow.dismiss();
				switch (v.getId()) {
				case R.id.lin_sao://ɨһɨ
					
					break;
				case R.id.lin_add_friend://�Ӻ���
					
					break;
				case R.id.lin_creat_group://������
					
					break;
				case R.id.lin_send_to_computer://���͵�����
					
					break;
				case R.id.lin_face_to_face://�����촫
					
					break;
				case R.id.lin_fast_money://��Ǯ
					
					break;
				}
			}
		};
	
	// ��������˳�
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
			Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
					Toast.LENGTH_SHORT).show();
			// ����handler�ӳٷ��͸���״̬��Ϣ
			mHandler.sendEmptyMessageDelayed(0, 3000);
		} else {
			finish();
			System.exit(0);
		}
	}
}
