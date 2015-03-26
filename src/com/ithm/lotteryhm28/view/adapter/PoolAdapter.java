package com.ithm.lotteryhm28.view.adapter;

import java.text.DecimalFormat;
import java.util.List;

import com.ithm.lotteryhm28.R;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 选号容器
 * 
 * @author Administrator
 * 
 */
public class PoolAdapter extends BaseAdapter {
	private Context context;
	private int num;
	// 选择的集合
	private List<Integer> selectList;
	// 背景图片id信息
	private int selectedId;

	public PoolAdapter(Context context, int num) {
		super();
		this.num = num;
		this.context = context;
	}

	public PoolAdapter(Context context, int num, List<Integer> selectList,
			int selectedId) {
		super();
		this.context = context;
		this.num = num;
		this.selectList = selectList;
		this.selectedId = selectedId;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return num;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView roll = new TextView(context);
		DecimalFormat decimal = new DecimalFormat("00");
		roll.setText(decimal.format(position + 1));
		roll.setTextSize(16);
		// 获取到用户已选号码的集合
		if (selectList.contains(position + 1)) {
			// 设置机选号码的背景为red
			roll.setBackgroundResource(selectedId);
		} else {
			roll.setBackgroundResource(R.drawable.id_defalut_ball);
		}

		// 居中
		roll.setGravity(Gravity.CENTER);
		return roll;
	}

}
