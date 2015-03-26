package com.ithm.lotteryhm28.view;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.os.Vibrator;

/**
 * 传感器监听处理
 * 
 * @author Administrator
 * 
 */
public abstract class SensorListener implements SensorEventListener {
	private Context context;// 上下文
	private float lastX;
	private float lastY;
	private float lastZ;
	private long lastTime;
	private Vibrator vibrator;
	// 时间间隔
	private long duration = 100;
	// 累加
	private float total;
	// 摇晃手机的值
	private float switchValue = 200;

	public SensorListener(Context context) {
		super();
		this.context = context;
		vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
	}

	// ①记录第一个数据：三个轴的加速度，为了屏蔽掉不同手机采样的时间间隔，记录第一个点的时间
	// ②当有新的传感器数据传递后，判断时间间隔（用当前时间与第一个采样时间进行比对，如果满足了时间间隔要求，认为是合格的第二个点，否则舍弃该数据包）
	// 进行增量的计算：获取到新的加速度值与第一个点上存储的进行差值运算，获取到一点和二点之间的增量
	// ③以此类推，获取到相邻两个点的增量，一次汇总
	// ④通过汇总值与设定好的阈值比对，如果大于等于，用户摇晃手机，否则继续记录当前的数据（加速度值和时间）s
	@Override
	public void onSensorChanged(SensorEvent event) {
		// 如何判断第一个点
		if (lastTime == 0) {
			lastX = event.values[SensorManager.DATA_X];
			lastY = event.values[SensorManager.DATA_Y];
			lastZ = event.values[SensorManager.DATA_Z];
			lastTime = System.currentTimeMillis();

		} else {
			// 第二个点及以后
			long currentTime = System.currentTimeMillis();
			// 尽可能的屏蔽掉不同手机的差异
			if (currentTime - lastTime >= duration) {
				float x = event.values[SensorManager.DATA_X];
				float y = event.values[SensorManager.DATA_Y];
				float z = event.values[SensorManager.DATA_Z];
				float dX = Math.abs(x - lastX);
				float dY = Math.abs(y - lastY);
				float dZ = Math.abs(z - lastZ);
				//屏蔽到微小的增量
				if(dX<1){
					dX=0;
				}if(dY<1){
					dY=0;
				}if(dZ<1){
					dZ=0;
				}
				//极个别的手机
//				if(dX==0||dY==0||dZ==0){
//					init();
//				}
				// 第一点到第二点的增量
				// 总的增量
				float shake = dX + dY + dZ;
				if(shake==0){
					init();
				}
				total += shake;
				if (total >= switchValue) {
					// 摇晃手机的处理
					// 机选一注彩票
					randomRec();
					// 提示用户，震动或者声音提示用户
					vibrator.vibrate(100);
					// 所有数据都要初始化
					init();

				} else {
					// 否则记录下一个数据
					lastX = event.values[SensorManager.DATA_X];
					lastY = event.values[SensorManager.DATA_Y];
					lastZ = event.values[SensorManager.DATA_Z];
					lastTime = System.currentTimeMillis();
				}
			}
		}
	}

	/**
	 * 机选一注
	 */
	public abstract void randomRec();

	/**
	 * 初始化数据
	 */
	private void init() {
		lastX = 0;
		lastY = 0;
		lastZ = 0;
		lastTime = 0;
		total = 0;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

}
