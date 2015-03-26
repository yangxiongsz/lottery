package com.ithm.lotteryhm28.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Random;

import com.ithm.lotteryhm28.ConstantValue;
import com.ithm.lotteryhm28.R;
import com.ithm.lotteryhm28.bean.ShoppingCart;
import com.ithm.lotteryhm28.bean.Ticket;
import com.ithm.lotteryhm28.engine.CommonInfoEngine;
import com.ithm.lotteryhm28.net.protocal.Message;
import com.ithm.lotteryhm28.net.protocal.Oelement;
import com.ithm.lotteryhm28.net.protocal.element.CurrentIssueElement;
import com.ithm.lotteryhm28.util.BeanFactory;
import com.ithm.lotteryhm28.util.PromptManager;
import com.ithm.lotteryhm28.view.BaseUI.MyHttpTask;
import com.ithm.lotteryhm28.view.adapter.PoolAdapter;
import com.ithm.lotteryhm28.view.custom.MyGridView;
import com.ithm.lotteryhm28.view.custom.MyGridView.OnActionUpListener;
import com.ithm.lotteryhm28.view.manager.BottomManager;
import com.ithm.lotteryhm28.view.manager.MiddleManager;
import com.ithm.lotteryhm28.view.manager.TitleMananger;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.style.BulletSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

/**
 * 双色球选号界面
 * 
 * @author Administrator
 * 
 */
public class BetSSQUI extends BaseUI implements PlayGame {
	// 机选
	private Button randomRed;
	private Button randomBlue;
	// 选号容器
	private MyGridView redContainer;
	private GridView blueContainer;
	private PoolAdapter redAdapter;
	private PoolAdapter blueAdapter;
	// 蓝球集合
	private List<Integer> blueNumList;
	// 红球集合
	private List<Integer> redNumList;
	// 传感器guanli
	private SensorManager sensor;
	// 传感器监听
	private SensorListener listener;

	public BetSSQUI(Context context) {
		super(context);
	}

	@Override
	public void init() {
		sensor = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		listener = new SensorListener(context) {
			@Override
			public void randomRec() {
				randomSSQ();
			}
		};
		showInMiddle = (ViewGroup) View.inflate(context, R.layout.il_playssq,
				null);
		redContainer = (MyGridView) findViewById(R.id.ii_ssq_red_number_container);
		blueContainer = (GridView) findViewById(R.id.ii_ssq_blue_number_container);
		randomRed = (Button) findViewById(R.id.ii_ssq_random_red);
		randomBlue = (Button) findViewById(R.id.ii_ssq_random_blue);
		blueNumList = new ArrayList<Integer>();
		redNumList = new ArrayList<Integer>();
		redAdapter = new PoolAdapter(context, 33, redNumList,
				R.drawable.id_redball);
		blueAdapter = new PoolAdapter(context, 16, blueNumList,
				R.drawable.id_blueball);
		redContainer.setAdapter(redAdapter);
		blueContainer.setAdapter(blueAdapter);
	}

	/**
	 * 机选双色球
	 */
	protected void randomSSQ() {
		// 清空集合
		Random rd = new Random();
		redNumList.clear();
		blueNumList.clear();
		// 机选红球
		while (redNumList.size() < 6) {
			int num = rd.nextInt(33) + 1;
			if (redNumList.contains(num)) {
				continue;
			}
			redNumList.add(num);
		}
		// 机选篮球
		int num = rd.nextInt(16) + 1;
		blueNumList.add(num);

		// 处理展示界面
		redAdapter.notifyDataSetChanged();
		blueAdapter.notifyDataSetChanged();
		changeNotice();
	}

	@Override
	public void onResume() {
		changeTitle();
		// 改变地步导航
		changeNotice();
		// 注册传感器
		sensor.registerListener(listener,
				sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_FASTEST);
		super.onResume();
	}

	@Override
	public void onPause() {
		// 注销传感器
		sensor.unregisterListener(listener);
		super.onPause();
	}

	/**
	 * 修改标题
	 */
	private void changeTitle() {
		String titleInfo = "";
		if (bundle != null) {
			// 如果获取到了，拼装标题
			titleInfo = "双色球第" + bundle.getString("issue") + "期";
		} else {
			// 默认标题
			titleInfo = "双色球选号";
		}
		TitleMananger.getInstance().changeTitle(titleInfo);
	}

	@Override
	public void setListener() {
		randomRed.setOnClickListener(this);
		randomBlue.setOnClickListener(this);
		redContainer.setListener(new OnActionUpListener() {

			@Override
			public void onActionUp(View view, int position) {
				// 当前点击的item是否被选中了
				// 如果没有被选中
				// 背景图片切换到红色
				// 摇晃的动画
				if (!redNumList.contains(position + 1)) {
					// 设置红色
					view.setBackgroundResource(R.drawable.id_redball);
					redNumList.add(position + 1);
					// 被选中
					// 还原背景图片
				} else {
					view.setBackgroundResource(R.drawable.id_defalut_ball);
					// 注：一定是object对象，不是int对象
					redNumList.remove((Object) (position + 1));
				}
				changeNotice();
			}
		});
		blueContainer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 当前点击的item是否被选中了
				// 如果没有被选中
				// 背景图片切换到红色
				// 摇晃的动画
				if (!blueNumList.contains(position + 1)) {
					// 设置红色
					view.setBackgroundResource(R.drawable.id_blueball);
					// 摇晃手机
					view.startAnimation(AnimationUtils.loadAnimation(context,
							R.anim.ia_ball_shake));
					blueNumList.add(position + 1);
					// 被选中
					// 还原背景图片
				} else {
					view.setBackgroundResource(R.drawable.id_defalut_ball);
					// 注：一定是object对象，不是int对象
					blueNumList.remove((Object) (position + 1));
				}
				changeNotice();
			}
		});
	}

	@Override
	public void onClick(View v) {
		Random rd = new Random();
		switch (v.getId()) {
		case R.id.ii_ssq_random_red:
			redNumList.clear();
			while (redNumList.size() < 6) {
				// 机选红球
				int num = rd.nextInt(33) + 1;
				// 添加到集合中去
				if (redNumList.contains(num)) {
					continue;
				}
				redNumList.add(num);
			}
			// 处理展示界面
			redAdapter.notifyDataSetChanged();
			changeNotice();
			break;
		case R.id.ii_ssq_random_blue:
			// 机选篮球
			blueNumList.clear();
			int num = rd.nextInt(16) + 1;
			blueNumList.add(num);
			blueAdapter.notifyDataSetChanged();
			changeNotice();
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	@Override
	public int getId() {
		return ConstantValue.VIEW_SSQ;
	}

	/**
	 * 改变底部导航
	 */
	protected void changeNotice() {
		// 以1注为分割
		String notice = "";
		if (redNumList.size() < 6) {
			notice = "你还需要选择" + (6 - redNumList.size()) + "个红球";
		} else if (blueNumList.size() == 0) {
			notice = "你还需要选择" + 1 + "个蓝球";
			// 其他情况
		} else {
			notice = "共" + calc() + "注" + calc() * 2 + "元";

		}
		BottomManager.getInstrance().changeGameBottomNotice(notice);
	}

	/**
	 * 计算注数
	 * 
	 * @return
	 */
	private int calc() {
		int redC = (int) (factorial(redNumList.size()) / (factorial(6) * factorial(redNumList
				.size() - 6)));
		int blueC = blueNumList.size();
		return redC * blueC;
	}

	/**
	 * 计算一个数的阶乘
	 * 
	 * @param num
	 * @return
	 */
	private long factorial(int num) {
		// num=7 7*6*5...*1

		if (num > 1) {
			return num * factorial(num - 1);
		} else if (num == 1 || num == 0) {
			return 1;
		} else {
			throw new IllegalArgumentException("num >= 0");
		}
	}

	/**
	 * 清除彩票
	 */
	@Override
	public void clear() {
		redNumList.clear();
		blueNumList.clear();
		changeNotice();
		redAdapter.notifyDataSetChanged();
		blueAdapter.notifyDataSetChanged();
	}

	/**
	 * 选好了
	 */
	@Override
	public void done() {
		// 判断用户是否选择了一注
		// 一个购物车里只能放一个菜种，并且是当前期的，获取到了当前销售期的信息
		// 封装了用户的投注信息，创建彩票信息，将投注信息放到购物车中
		// 设置采种的标识，和采种的期次
		if (redNumList.size() >= 6 && blueNumList.size() >= 1) {
			if (bundle != null) {
				// 封装用户投注信息：红球，篮球，注数
				Ticket ticket = new Ticket();
				StringBuffer redbuffer = new StringBuffer();
				DecimalFormat format = new DecimalFormat("00");
				for (Integer item : redNumList) {
					redbuffer.append(" ").append(format.format(item));
				}
				ticket.setRedNum(redbuffer.substring(1));

				StringBuffer bulebuffer = new StringBuffer();
				for (Integer item : blueNumList) {
					redbuffer.append(" ").append(format.format(item));
				}

				ticket.setBlueNum(bulebuffer.substring(1));
				ticket.setNum(calc());
				// 创建彩票购物车，将投注信息添加到购物车里
				ShoppingCart.getinstance.getTickets().add(ticket);
				// 设置菜种的标识，采种的期次
				ShoppingCart.getinstance.setIssue(bundle.getString("issue"));
				ShoppingCart.getinstance.setLotteryid(ConstantValue.SSQ);
				// 页面跳转到购物车展示
				MiddleManager.getInstance().changeUI(Shopping.class, bundle);

			} else {
				getCurrentIssueInfo();
			}
		} else {
			Toast.makeText(context, "请选择一注", 0).show();

		}
	}

	private void getCurrentIssueInfo() {
		new MyHttpTask<Integer>() {
			// 在方法之前显示滚动条
			protected void onPreExecute() {
				PromptManager.showProgressDialog(context);

			};

			@Override
			protected Message doInBackground(Integer... params) {
				// 获取数据唯一业务的调用
				CommonInfoEngine engine = BeanFactory
						.getImpl(CommonInfoEngine.class);
				return engine.getCurrentIssueInfo(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {
				PromptManager.closeProgressDialog();
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						CurrentIssueElement element = (CurrentIssueElement) result
								.getBody().getElements().get(0);
						// 创建bundle
						bundle = new Bundle();
						bundle.putString("issue", element.getIssue());
						// 更新标题
						changeTitle();
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
}
