package com.ithm.lotteryhm28.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.net.BCodec;
import org.apache.commons.lang3.StringUtils;

import com.ithm.lotteryhm28.ConstantValue;
import com.ithm.lotteryhm28.GlobalParams;
import com.ithm.lotteryhm28.R;
import com.ithm.lotteryhm28.engine.CommonInfoEngine;
import com.ithm.lotteryhm28.net.protocal.Element;
import com.ithm.lotteryhm28.net.protocal.Message;
import com.ithm.lotteryhm28.net.protocal.Oelement;
import com.ithm.lotteryhm28.net.protocal.element.CurrentIssueElement;
import com.ithm.lotteryhm28.util.BeanFactory;
import com.ithm.lotteryhm28.util.PromptManager;
import com.ithm.lotteryhm28.view.manager.MiddleManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
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
public class HallUI extends BaseUI {
	private ListView ll;
	private CategroyAdapter adapter;
	// ViewPager配置
	private ViewPager pager;
	private PagerAdapter pagerAdapter;
	private List<View> pagers;

	public HallUI(Context context) {
		super(context);
	}

	public void init() {
		showInMiddle = (LinearLayout) View.inflate(context, R.layout.il_hall,
				null);
		pager = (ViewPager) findViewById(R.id.ii_viewpager);
		pagerAdapter = new MyAdapter();
		ll = new ListView(context);
		adapter = new CategroyAdapter();
		ll.setAdapter(adapter);
		// 删除黑边（上下）
		ll.setFadingEdgeLength(0);
		initPager();
		// 设置viewpage适配器
		pager.setAdapter(pagerAdapter);
		// 初始选项卡
		initPageTag();

	}

	// 下划线
	private ImageView underline;
	private TextView fcTitle;// 福彩
	private TextView tcTitle;// 体彩
	private TextView gpcTitle;// 高频彩

	/**
	 * 初始选项卡
	 */
	private void initPageTag() {
		underline = (ImageView) findViewById(R.id.ii_category_selector);
		fcTitle = (TextView) findViewById(R.id.ii_category_fc);
		tcTitle = (TextView) findViewById(R.id.ii_category_tc);
		gpcTitle = (TextView) findViewById(R.id.ii_category_gpc);
		fcTitle.setTextColor(Color.RED);
		// 屏幕宽度
		// GlobalParams.WIN_WIDTH;
		// 小图片的宽度
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.id_category_selector);
		// bitmap.getWidth();
		int offset = (GlobalParams.WIN_WIDTH / 3 - bitmap.getWidth()) / 2;
		// 设置图片初始位置——向右偏移
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		underline.setImageMatrix(matrix);
	}

	/**
	 * 初始化pager
	 */
	private void initPager() {
		pagers = new ArrayList<View>();
		pagers.add(ll);

		TextView item = new TextView(context);
		item.setText("体彩");
		pagers.add(item);

		item = new TextView(context);
		item.setText("高频彩");
		pagers.add(item);

	}

	private class MyAdapter extends PagerAdapter {
		/**
		 * 返回总的个数
		 */
		@Override
		public int getCount() {
			return pagers.size();
		}

		/**
		 * 比较二个对象
		 */
		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		/**
		 * 销毁的方法
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View view = pagers.get(position);
			container.removeView(view);
		}

		/**
		 * container代表的是viewpager本身 position当前 添加view方法
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = pagers.get(position);
			container.addView(view);
			return view;
		}

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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
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
				holder.bet = (ImageView) convertView
						.findViewById(R.id.ii_hall_lottery_bet);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.logo.setImageResource(logoResIds[position]);
			holder.title.setText(titleResIds[position]);
			holder.summary.setTag(position);
			holder.bet.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (position == 0) {
						MiddleManager.getInstance().changeUI(BetSSQUI.class,
								ssqBundle);
					}
				}
			});
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
						changeNotice(result.getBody().getElements().get(0));
					} else {
						PromptManager
								.showToast(context, oelement.getErrormsg());
					}
				} else {
					PromptManager.showToast(context, "服务器忙,请稍后重试");
				}
				super.onPostExecute(result);
			}

		}.executeProxy(ConstantValue.SSQ);
	}

	private Bundle ssqBundle;

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
		TextView view = (TextView) ll.findViewWithTag(0);
		if (view != null) {
			view.setText(text);
		}
		// 设置需要传输的数据
		ssqBundle = new Bundle();
		ssqBundle.putString("issue", issue);
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

	// 记录viewpage上一个界面的position
	int lastPosition = 0;

	@Override
	public void setListener() {
		fcTitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pager.setCurrentItem(0);
			}
		});
		tcTitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pager.setCurrentItem(1);
			}
		});
		gpcTitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pager.setCurrentItem(2);
			}
		});
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			/**
			 * 滑动完成时
			 */
			public void onPageSelected(int position) {
				TranslateAnimation trans = new TranslateAnimation(lastPosition,
						position * GlobalParams.WIN_WIDTH / 3, 0, 0);
				trans.setDuration(300);
				trans.setFillAfter(true);
				underline.startAnimation(trans);
				lastPosition = position;
				fcTitle.setTextColor(Color.BLACK);
				tcTitle.setTextColor(Color.BLACK);
				gpcTitle.setTextColor(Color.BLACK);
				switch (position) {
				case 0:
					fcTitle.setTextColor(Color.RED);
					break;
				case 1:
					tcTitle.setTextColor(Color.RED);
					break;
				case 2:
					gpcTitle.setTextColor(Color.RED);
					break;
				}
				/*
				 * switch (position) { case 1:// 当position0移动到1的时候 // fromX
				 * toX相对于图片初始位置的增量 TranslateAnimation trans = new
				 * TranslateAnimation(0, GlobalParams.WIN_WIDTH / 3, 0, 0);
				 * trans.setDuration(400); // 移动完成后停留到终点
				 * trans.setFillAfter(true); // 开始动画
				 * underline.startAnimation(trans); break; case 2://
				 * 当position1移动到2的时候 trans = new
				 * TranslateAnimation(GlobalParams.WIN_WIDTH / 3,
				 * GlobalParams.WIN_WIDTH / 3 * 2, 0, 0);
				 * trans.setDuration(400); // 移动完成后停留到终点
				 * trans.setFillAfter(true); // 开始动画
				 * underline.startAnimation(trans); break; default: break; }
				 */
			}

			/**
			 * 页面滚动时候
			 */
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			/**
			 * 状态发生改变
			 */
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}
}
