package com.ithm.lotteryhm28.view;

import java.util.ArrayList;
import java.util.List;

import com.ithm.lotteryhm28.ConstantValue;
import com.ithm.lotteryhm28.R;
import com.ithm.lotteryhm28.view.adapter.PoolAdapter;
import com.ithm.lotteryhm28.view.manager.TitleMananger;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

/**
 * 双色球选号界面
 * 
 * @author Administrator
 * 
 */
public class BetSSQUI1 extends BaseUI {
	// 机选
	private Button randomRed;
	private Button randomBlue;
	// 选号容器
	private GridView redContainer;
	private GridView blueContainer;
	private PoolAdapter redAdapter;
	private PoolAdapter blueAdapter;
	// 红球集合
	private List<Integer> redNumList;
	private List<Integer> blueNumList;

	public BetSSQUI1(Context context) {
		super(context);
	}

	@Override
	public void init() {
		showInMiddle = (ViewGroup) View.inflate(context, R.layout.il_playssq,
				null);
		redContainer = (GridView) findViewById(R.id.ii_ssq_red_number_container);
		blueContainer = (GridView) findViewById(R.id.ii_ssq_blue_number_container);
		randomRed = (Button) findViewById(R.id.ii_ssq_random_red);
		randomBlue = (Button) findViewById(R.id.ii_ssq_random_blue);
		redNumList = new ArrayList<Integer>();
		blueNumList = new ArrayList<Integer>();
		redAdapter = new PoolAdapter(context, 33);
		blueAdapter = new PoolAdapter(context, 16);
		redContainer.setAdapter(redAdapter);
		blueContainer.setAdapter(blueAdapter);
	}

	@Override
	public void onResume() {
		changeTitle();
		super.onResume();
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
		redContainer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 当前点击的item是否被选中了
				// 如果没有被选中
				// 背景图片切换到红色
				// 摇晃的动画
				if (!redNumList.contains(position + 1)) {
					// 设置红色
					view.setBackgroundResource(R.drawable.id_redball);
					// 摇晃手机
					view.startAnimation(AnimationUtils.loadAnimation(context,
							R.anim.ia_ball_shake));
					redNumList.add(position + 1);
					// 被选中
					// 还原背景图片
				} else {
					view.setBackgroundResource(R.drawable.id_defalut_ball);
					// 注：一定是object对象，不是int对象
					redNumList.remove((Object) (position + 1));
				}

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

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ii_ssq_random_red:

			break;
		case R.id.ii_ssq_random_blue:

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

}
