package com.ithm.lotteryhm28.view;

import com.ithm.lotteryhm28.net.NetUtil;
import com.ithm.lotteryhm28.net.protocal.Message;
import com.ithm.lotteryhm28.util.PromptManager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * 通用页面
 * 
 * @author Administrator
 * 
 */
public abstract class BaseUI implements View.OnClickListener {
	protected Bundle bundle;
	protected Context context;
	// 显示到中间容器
	protected ViewGroup showInMiddle;

	public BaseUI(Context context) {
		this.context = context;
		init();
		setListener();
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	public Bundle getBundle() {
		return bundle;
	}

	/**
	 * 给中间容件加载数据
	 * 
	 * @return
	 */

	public abstract void init();

	public abstract void setListener();

	/**
	 * 返回一个自己的标识
	 * 
	 */
	public abstract int getId();

	/**
	 * 获取需要在中间容器加载的内容
	 * 
	 * @return
	 */
	public View getChild() {
		// 设置layout参数

		// root=null
		// showInMiddle.getLayoutParams()=null
		// root!=null
		// return root

		// 当LayoutParams类型转换异常，向父容器看齐
		if (showInMiddle.getLayoutParams() == null) {
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.FILL_PARENT,
					RelativeLayout.LayoutParams.FILL_PARENT);
			showInMiddle.setLayoutParams(params);
		}

		return showInMiddle;
	}

	@Override
	public void onClick(View v) {

	}

	public View findViewById(int id) {
		return showInMiddle.findViewById(id);
	}

	/**
	 * 要出去的时候调用
	 */
	public void onPause() {
	}

	/**
	 * 进入到界面之后
	 */
	public void onResume() {
	}

	/**
	 * 访问网络的异步方法
	 * 
	 * @author Administrator
	 * 
	 * @param <Params>
	 */
	protected abstract class MyHttpTask<Params> extends
			AsyncTask<Params, Void, Message> {
		public final AsyncTask<Params, Void, Message> executeProxy(
				Params... params) {
			if (NetUtil.checkNet(context)) {
				return super.execute(params);
			} else {
				PromptManager.showNoNetWork(context);
			}
			return null;
		}
	}
}
