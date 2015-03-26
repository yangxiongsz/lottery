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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 购彩大厅
 * 
 * @author Administrator
 * 
 */
public class HallUI2 extends BaseUI {
	private ListView ll;
	private CategroyAdapter adapter;

	// private List<View> needUpdate;

	public HallUI2(Context context) {
		super(context);
	}

	public void init() {
		showInMiddle = (LinearLayout) View.inflate(context, R.layout.il_hall2,
				null);
		ll = (ListView) findViewById(R.id.ii_hall_lottery_list);
		// needUpdate = new ArrayList<View>();
		adapter = new CategroyAdapter();
		ll.setAdapter(adapter);
	}

	// 资源信息
	private int[] logoResIds = new int[] { R.drawable.id_ssq, R.drawable.id_3d,
			R.drawable.id_qlc };
	private int[] titleResIds = new int[] { R.string.is_hall_ssq_title,
			R.string.is_hall_3d_title, R.string.is_hall_qlc_title };

	private class CategroyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(context,
						R.layout.il_hall_lottery_item, null);
				holder.logo = (ImageView) convertView
						.findViewById(R.id.ii_hall_lottery_logo);
				holder.title = (TextView) convertView
						.findViewById(R.id.ii_hall_lottery_title);
				holder.summary = (TextView) convertView
						.findViewById(R.id.ii_hall_lottery_summary);
				// needUpdate.add(holder.summary);
				// needUpdate.add(holder.summary);
				holder.bet = (ImageView) convertView
						.findViewById(R.id.ii_hall_lottery_bet);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.logo.setImageResource(logoResIds[position]);
			holder.title.setText(titleResIds[position]);
			holder.summary.setTag(position);
			return convertView;
		}

		class ViewHolder {
			ImageView logo;
			TextView title;
			TextView summary;
			ImageView bet;
		}

	}

	@Override
	public void onResume() {
		// 处理耗时操作
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
		String lastTime = getLasttime(currentIssueElement.getLasttime());
		String text = context.getResources().getString(
				R.string.is_hall_common_summary);
		text = StringUtils.replaceEach(text, new String[] { "ISSUE", "TIME" },
				new String[] { issue, lastTime });

		// 方式一：所有的item更新
		// adapter.notifyDataSetChanged();
		// 方式二：更新需要更新内容（没有必要刷新所有的信息）
		// 获取到需要更新控件的应用
		// TextView view = (TextView) needUpdate.get(0);
		// 第一个采种的数据
		TextView view = (TextView) ll.findViewWithTag(0);
		if (view != null) {
			view.setText(text);
		}
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

	@Override
	public void setListener() {

	}

}
