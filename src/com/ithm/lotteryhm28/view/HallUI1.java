package com.ithm.lotteryhm28.view;

import org.apache.commons.lang3.StringUtils;

import com.ithm.lotteryhm28.ConstantValue;
import com.ithm.lotteryhm28.R;
import com.ithm.lotteryhm28.engine.CommonInfoEngine;
import com.ithm.lotteryhm28.net.protocal.Element;
import com.ithm.lotteryhm28.net.protocal.Message;
import com.ithm.lotteryhm28.net.protocal.Oelement;
import com.ithm.lotteryhm28.net.protocal.element.CurrentIssueElement;
import com.ithm.lotteryhm28.util.BeanFactory;
import com.ithm.lotteryhm28.util.PromptManager;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 购彩大厅
 * 
 * @author Administrator
 * 
 */
public class HallUI1 extends BaseUI {
	private TextView ssq_summary;
	private ImageView ssq_bet;

	public HallUI1(Context context) {
		super(context);

	}

	public void setListener() {
		ssq_bet.setOnClickListener(this);
	}

	public void init() {
		showInMiddle = (LinearLayout) View.inflate(context, R.layout.il_hall1,
				null);
		ssq_summary = (TextView) findViewById(R.id.ii_hall_ssq_summary);
		ssq_bet = (ImageView) findViewById(R.id.ii_hall_ssq_bet);
		
	}
    @Override
    public void onResume() {
    	//处理耗时操作
    	getCurrentIssInfo();
    	super.onResume();
    }
	@Override
	public int getId() {
		return ConstantValue.VIEW_HALL;
	}

	@Override
	public void onClick(View v) {

	}
    
	/**
	 * 获取当前销售期的信息Integer
	 */
	private void getCurrentIssInfo() {
		// new MyThread().start();
		// new MyAsyncTask().execute(ConstantValue.SSQ);
		new MyHttpTask<Integer>() {

			@Override
			protected Message doInBackground(Integer... params) {
				// 获取数据唯一业务的调用
				CommonInfoEngine engine = BeanFactory
						.getImpl(CommonInfoEngine.class);
				return engine.getCurrentIssueInfo(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {

				if (result != null) {
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						// 更新页面
						changeNotice(result.getBody().getElements().get(0));
					} else {
						PromptManager
								.showToast(context, oelement.getErrormsg());
					}
				} else {
					// 出错
					PromptManager.showToast(context, "服务器忙,请稍后重试");
				}
				super.onPostExecute(result);
			}

		}.executeProxy(ConstantValue.SSQ);
	}

	/**
	 * 更新界面
	 * 
	 * @param element
	 */
	protected void changeNotice(Element element) {
		CurrentIssueElement currentIssueElement = (CurrentIssueElement) element;
		String issue = currentIssueElement.getIssue();
		String lastTime =getLasttime(currentIssueElement.getLasttime());
		String text = context.getResources().getString(
				R.string.is_hall_common_summary);
		text=StringUtils.replaceEach(text, new String[] { "ISSUE", "TIME" },
				new String[] { issue, lastTime });
		ssq_summary.setText(text);
	}

	/**
	 * 将秒时间转换成日时分格式
	 * 
	 * @param lasttime
	 * @return
	 */
	public String getLasttime(String lasttime) {
		StringBuffer result = new StringBuffer();
		if (StringUtils.isNumericSpace(lasttime)) {
			int time = Integer.parseInt(lasttime);
			int day = time / (24 * 60 * 60);
			result.append(day).append("天");
			if (day > 0) {
				time = time - day * 24 * 60 * 60;
			}
			int hour = time / 3600;
			result.append(hour).append("时");
			if (hour > 0) {
				time = time - hour * 60 * 60;
			}
			int minute = time / 60;
			result.append(minute).append("分");
		}
		return result.toString();
	}
	/**
	 * 
	 * @author Administrator
	 * @Params 传递的参数
	 * @Progress 下载相关的进度提示<Integer,Float>
	 * @Result 数据库回复的数据
	 */
	// // integer表示传递的是采种的唯一标识，Void表示不需要什么下载提示,Message表示返回的记录
	// private class MyAsyncTask extends AsyncTask<Integer, Void, Message> {
	// /**
	// * 同run方法在子线程使用
	// */
	// @Override
	// protected Message doInBackground(Integer... params) {
	// return null;
	// }
	//
	// /**
	// * UI Thread before doInBackground Method show Progress
	// */
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// }
	//
	// /**
	// * UI Thread after doInBackground Method Update View Information
	// */
	// @Override
	// protected void onPostExecute(Message result) {
	// super.onPostExecute(result);
	// }
	// /**
	// * 类似与thread.start方法
	// * 由于final修饰，无法Override,方法重命名
	// * 省略掉了网络判断
	// * @param params
	// * @return
	// */
	// public final AsyncTask<Integer, Void, Message> executeProxy(
	// Integer... params) {
	// if (NetUtil.checkNet(context)) {
	// return super.execute(params);
	// } else {
	// PromptManager.showNoNetWork(context);
	// }
	// return null;
	// }
	// }

	// private class MyThread extends Thread {
	// public void run() {
	// // 访问网络获取数据
	// super.run();
	// }
	//
	// @Override
	// public synchronized void start() {
	// // 如果有网络去开启线程，如果没有的话去设置网络
	// if (NetUtil.checkNet(context)) {
	// super.start();
	// } else {
	// PromptManager.showNoNetWork(context);
	// }
	// }
	// }

}
