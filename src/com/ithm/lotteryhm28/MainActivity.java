package com.ithm.lotteryhm28;

import com.ithm.lotteryhm28.util.FadeUtil;
import com.ithm.lotteryhm28.util.PromptManager;
import com.ithm.lotteryhm28.view.BaseUI;
import com.ithm.lotteryhm28.view.FirstUI;
import com.ithm.lotteryhm28.view.HallUI;
import com.ithm.lotteryhm28.view.SecondUI;
import com.ithm.lotteryhm28.view.manager.BottomManager;
import com.ithm.lotteryhm28.view.manager.MiddleManager;
import com.ithm.lotteryhm28.view.manager.TitleMananger;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class MainActivity extends Activity{
	private RelativeLayout middle;
	private View child1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.il_main);
		middle = (RelativeLayout) findViewById(R.id.ii_middle);
		//获取屏幕的宽度
		DisplayMetrics outMetrics=new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		GlobalParams.WIN_WIDTH=outMetrics.widthPixels;
		init();
	}

	private void init() {
		// 头部
		TitleMananger title = TitleMananger.getInstance();
		title.init(this);
		title.showUnLoginTitle();
		// 尾部
		BottomManager bottom = BottomManager.getInstrance();
		bottom.init(this);
		bottom.showCommonBottom();
		// 不使用handler，使用按钮切换
		MiddleManager.getInstance().setMiddle(middle);
		// 建立观察者与被观察者的关系
		MiddleManager.getInstance().addObserver(BottomManager.getInstrance());
		MiddleManager.getInstance().addObserver(TitleMananger.getInstance());

		MiddleManager.getInstance().changeUI(HallUI.class);
		// 中间
		// loadFirstView();
		// 发送延时消息
		// handler.sendEmptyMessageDelayed(0, 2000);

	}

	private void loadFirstView() {
		// 加载第一个页面
		FirstUI first = new FirstUI(this);
		child1 = first.getChild();
		middle.addView(child1);

	}

	private void loadSecondView() {
		// 加载第一个页面
		SecondUI second = new SecondUI(this);
		View child = second.getChild();
		middle.addView(child);
		// 执行切换动画
		// Animation am = AnimationUtils.loadAnimation(this,
		// R.anim.ia_view_change);
		// childe.startAnimation(am);
		// 淡入
		FadeUtil.fadeIn(child, 0, 1000);
	}

	// private Handler handler = new Handler() {
	// @Override
	// public void handleMessage(Message msg) {
	// changeUI(new SecondUI(MainActivity.this));
	// super.handleMessage(msg);
	// }
	// };

	/**
	 * 切换页面
	 */
	protected void changeUI() {
		// 清理上一个页面，切换到下一个页面
		// middle.removeAllViews();
		// 淡出
		FadeUtil.fadeOut(child1, 1000);
		loadSecondView();
	}

	/**
	 * 切换页面通用方法
	 * 
	 * @param ui
	 */
	protected void changeUI(BaseUI ui) {
		middle.removeAllViews();
		View child = ui.getChild();
		middle.addView(child);
		child.startAnimation(AnimationUtils.loadAnimation(this,
				R.anim.ia_view_change));

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			boolean result = MiddleManager.getInstance().goBack();
			// 返回键操作失败
			if (!result) {
				// Toast.makeText(MainActivity.this, "是否退出系统", 1).show();
				PromptManager.showExitSystem(this);
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
