package com.ithm.lotteryhm28.view.custom;

import com.ithm.lotteryhm28.R;
import com.ithm.lotteryhm28.util.DensityUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 自定义gridview
 */
public class MyGridView extends GridView {
	private PopupWindow pop;
	private TextView hall;
	private OnActionUpListener listener;

	public void setListener(OnActionUpListener listener) {
		this.listener = listener;
	}

	public MyGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 手指按下：显示放大的号码
		// 手指滑动：更新号码内容|号码显示位置
		// 手指抬起：修改手指下面的球背景
		View view = View.inflate(context, R.layout.il_gridview_item_pop, null);
		hall = (TextView) findViewById(R.id.ii_pretextView);
		pop = new PopupWindow();
		pop.setContentView(view);
		// 设置pop window窗体大小
		pop.setWidth(DensityUtil.px2dip(context, 55)); // 一般是像素
		pop.setHeight(DensityUtil.px2dip(context, 53));
		// 设置pop的背景图为无
		pop.setBackgroundDrawable(null);
		// 无动画
		pop.setAnimationStyle(0);

	}

	public MyGridView(Context context) {
		super(context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// 当手指嗯下去的时候，获取点击的那个球
		int position = pointToPosition((int) ev.getX(), (int) ev.getY());
		// 如果是无效的方位就不需要往下了
		if (position == INVALID_POSITION) {
			return false;
		}
		// 拿到被点击的textview
		TextView child = (TextView) this.getChildAt(position);
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 当手指摁下去的时候，接管了scrollview滑动
			this.getParent().getParent()
					.requestDisallowInterceptTouchEvent(true);
			showPop(child);
			break;
		case MotionEvent.ACTION_MOVE:
			Update(child);
			break;
		case MotionEvent.ACTION_UP:
			// 当手指放下的时候，放行
			this.getParent().getParent()
					.requestDisallowInterceptTouchEvent(false);
			hiddenPop();
			// 增加一个监听，修改颜色
			if (listener != null) {
				listener.onActionUp(child, position);
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 显示popwindow
	 */
	private void showPop(TextView child) {
		// x的偏移量
		// y的偏移量
		int offsetX = (pop.getWidth() - child.getWidth()) / 2;
		int offsetY = pop.getHeight() - child.getHeight();
		// 必须是在头上，但是给的 是左下脚,在showAsDropDown中有另一个偏移量的方法
		// 设置球的文本
		hall.setText(child.getText());
		pop.showAsDropDown(child, offsetX, offsetY);
		// pop.showAsDropDown(child);
	}

	private void Update(TextView child) {
		// 设置文本信息
		hall.setText(child.getText());
		int offsetX = (pop.getWidth() - child.getWidth()) / 2;
		int offsetY = pop.getHeight() - child.getHeight();
		pop.update(child, offsetX, offsetY, -1, -1);// -1,-1表示忽略不计
		// pop.update();
	}

	/**
	 * 隐藏popwindow
	 */
	private void hiddenPop() {
		pop.dismiss();
	}

	/**
	 * 监听用户手指抬起来了
	 */
	public interface OnActionUpListener {
		/**
		 * 手指抬起
		 * 
		 * @param view
		 *            当前手底下的球
		 * @param position
		 *            位置
		 */
		void onActionUp(View view, int position);
	}
}
