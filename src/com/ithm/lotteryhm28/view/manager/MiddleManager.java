package com.ithm.lotteryhm28.view.manager;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.ithm.lotteryhm28.ConstantValue;
import com.ithm.lotteryhm28.R;
import com.ithm.lotteryhm28.util.PromptManager;
import com.ithm.lotteryhm28.view.BaseUI;
import com.ithm.lotteryhm28.view.BetSSQUI;
import com.ithm.lotteryhm28.view.HallUI;

//把中间容器变成了被观察的对象
public class MiddleManager extends Observable {
	private static final String TAG = "MiddleManager";
	// 单例模式
	private static MiddleManager instance;

	private MiddleManager() {
	}

	private RelativeLayout middle;

	public RelativeLayout getMiddle() {
		return middle;
	}

	public void setMiddle(RelativeLayout middle) {
		this.middle = middle;
	}

	public Context getContext() {
		return middle.getContext();
	}

	public static MiddleManager getInstance() {
		if (instance == null) {
			instance = new MiddleManager();
		}
		return instance;
	}

	public void changeUI1(BaseUI ui) {
		middle.removeAllViews();
		View child = ui.getChild();
		middle.addView(child);
		child.startAnimation(AnimationUtils.loadAnimation(getContext(),
				R.anim.ia_view_change));

	}

	// view对象的集合
	private Map<String, BaseUI> viewcache = new HashMap<String, BaseUI>();
	private BaseUI currentUI;// 当前正在展示

	public void setCurrentUI(BaseUI currentUI) {
		this.currentUI = currentUI;
	}

	public BaseUI getCurrentUI() {
		return currentUI;
	}

	public void changeUI(Class<? extends BaseUI> clazz) {
		BaseUI targerUI = null;
		// 一旦创建了，考虑重用，复杂创建
		String key = clazz.getSimpleName();
		if (viewcache.containsKey(key)) {
			// 创建了，重用
			targerUI = viewcache.get(key);
		} else {
			try {
				// 否则，创建
				// 构造器
				Constructor<? extends BaseUI> constructor = clazz
						.getConstructor(Context.class);
				// 使用构造器创建对象
				targerUI = constructor.newInstance(getContext());
				viewcache.put(key, targerUI);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("constructor is to failed");
			}
		}
		Log.i(TAG, targerUI.toString());
		// 在页面被调用之前呀进行的操作onPause()
		if (currentUI != null) {
			currentUI.onPause();
		}
		middle.removeAllViews();
		View child = targerUI.getChild();
		middle.addView(child);
		child.startAnimation(AnimationUtils.loadAnimation(getContext(),
				R.anim.ia_view_change));
		// 在加载完界面之后——onResume
		targerUI.onResume();
		currentUI = targerUI;
		// 放当前页面放到栈顶
		history.addFirst(key);
		// 当中间容器切换成功是，处理另外的二个容器的变化
		changeTitleAndBottom();

	}

	/**
	 * 处理头部和尾部的二个容器的联动操作
	 */
	private void changeTitleAndBottom() {
		// 1.界面一对应未登录标题和通用导航
		// 2.界面二对应通用标题和玩法导航
		// 当前正在展示的如果是第一个界面的时候
		// if (currentUI.getClass() == FirstUI.class) {
		// TitleMananger.getInstance().showUnLoginTitle();
		// BottomManager.getInstrance().showCommonBottom();
		// }
		// if (currentUI.getClass() == SecondUI.class) {
		// TitleMananger.getInstance().showCommonTitle();
		// BottomManager.getInstrance().showGameBottom();
		// }
		// 根据id比对
		// switch (currentUI.getId()) {
		// case ConstantValue.VIEW_FIRST:
		// // TitleMananger.getInstance().showUnLoginTitle();
		// // BottomManager.getInstrance().showCommonBottom();
		// break;
		// case ConstantValue.VIEW_SECOND:
		// // TitleMananger.getInstance().showCommonTitle();
		// // BottomManager.getInstrance().showGameBottom();
		// break;
		// default:
		// break;
		// }
		setChanged(); // 变动了
		notifyObservers(currentUI.getId()); // 传递id值，告诉他们中间容器变动了

	}

	public void changeUI1(Class<? extends BaseUI> clazz) {
		BaseUI targerUI = null;
		// 一旦创建了，考虑重用，复杂创建
		String key = clazz.getSimpleName();
		if (viewcache.containsKey(key)) {
			// 创建了，重用
			targerUI = viewcache.get(key);
		} else {
			try {
				// 否则，创建
				// 构造器
				Constructor<? extends BaseUI> constructor = clazz
						.getConstructor(Context.class);
				// 使用构造器创建对象
				targerUI = constructor.newInstance(getContext());
				viewcache.put(key, targerUI);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("constructor is to failed");
			}
		}
		Log.i(TAG, targerUI.toString());
		middle.removeAllViews();
		View child = targerUI.getChild();
		middle.addView(child);
		child.startAnimation(AnimationUtils.loadAnimation(getContext(),
				R.anim.ia_view_change));
		currentUI = targerUI;
		// 放当前页面放到栈顶
		history.addFirst(key);
	}

	/**
	 * 返回键的处理
	 * 
	 * @return
	 */
	private LinkedList<String> history = new LinkedList<String>();

	/**
	 * 返回键处理
	 * 
	 * @return
	 */
	public boolean goBack() {
		// 记录一下用户操作历史
		// 频繁操作栈顶（添加）——在界面切换成功
		// 获取栈顶
		// 删除了栈顶
		// 有序集合
		if (history.size() > 0) {
			// 当用户误操作返回键（不退出应用）
			if (history.size() == 1) {
				return false;
			}

			// Throws:NoSuchElementException - if this LinkedList is empty.
			history.removeFirst();

			if (history.size() > 0) {
				// Throws:NoSuchElementException - if this LinkedList is empty.
				String key = history.getFirst();

				BaseUI targetUI = viewcache.get(key);
				if (targetUI != null) {
					currentUI.onPause();
					middle.removeAllViews();
					middle.addView(targetUI.getChild());
					targetUI.onResume();
					currentUI = targetUI;
					changeTitleAndBottom();
				} else {
					// 处理方式一：创建一个新的目标界面：存在问题——如果有其他的界面传递给被删除的界面
					// 处理方式二：寻找一个不需要其他界面传递数据——跳转到首页
					changeUI(HallUI.class);
					PromptManager.showToast(getContext(), "应用在低内存下运行");
				}

				return true;

			}
		}

		return false;
	}

	public void clear() {
		history.clear();
	}

	/**
	 * 改变页面，传递数据
	 * 
	 * @param clazz
	 * @param ssqBundle
	 */
	public void changeUI(Class<? extends BaseUI> clazz, Bundle ssqBundle) {
		BaseUI targerUI = null;
		// 一旦创建了，考虑重用，复杂创建
		String key = clazz.getSimpleName();
		if (viewcache.containsKey(key)) {
			// 创建了，重用
			targerUI = viewcache.get(key);
		} else {
			try {
				// 否则，创建
				// 构造器
				Constructor<? extends BaseUI> constructor = clazz
						.getConstructor(Context.class);
				// 使用构造器创建对象
				targerUI = constructor.newInstance(getContext());
				viewcache.put(key, targerUI);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("constructor is to failed");
			}
		}
		if (targerUI != null) {
			targerUI.setBundle(ssqBundle);
		}
		Log.i(TAG, targerUI.toString());
		// 在页面被调用之前呀进行的操作onPause()
		if (currentUI != null) {
			currentUI.onPause();
		}
		middle.removeAllViews();
		View child = targerUI.getChild();
		middle.addView(child);
		child.startAnimation(AnimationUtils.loadAnimation(getContext(),
				R.anim.ia_view_change));
		// 在加载完界面之后——onResume
		targerUI.onResume();
		currentUI = targerUI;
		// 放当前页面放到栈顶
		history.addFirst(key);
		// 当中间容器切换成功是，处理另外的二个容器的变化
		changeTitleAndBottom();
	}
}
