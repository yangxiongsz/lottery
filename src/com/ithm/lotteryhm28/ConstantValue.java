package com.ithm.lotteryhm28;



public interface ConstantValue {
	// class :public static final
	String ENCONDING = "UTF-8";
	/**
	 * 代理的id
	 */
	String AGENTERID = "889931";
	// <source>ivr</source>
	/**
	 * 信息来源
	 */
	String SOURCE = "ivr";
	// <compress>DES</compress>
	/**
	 * body里面的数据加密算法
	 */
	String COMPRESS = "DES";

	/**
	 * 子代理商的密钥(.so) JNI
	 */
	String AGENTER_PASSWORD = NativeValue.getAgenterPassword();

	/**
	 * des加密用密钥
	 */
	String DES_PASSWORD = NativeValue.getDesPassword();
	/**
	 * 服务器地址
	 */
	String LOTTERY_URI = NativeValue.getLotteryUrl();

	int VIEW_FIRST = 1;
	int VIEW_SECOND = 2;

	/**
	 * 购彩大厅
	 */
	int VIEW_HALL = 10;
	/**
	 * 双色球选号界面
	 */
	int VIEW_SSQ = 15;
	/**
	 * 购物车
	 */
	int VIEW_SHOPPING = 20;
	/**
	 * 追期和倍投的设置界面
	 */
	int VIEW_PREBET = 25;
	/**
	 * 用户登录
	 */
	int VIEW_LOGIN = 30;
	/**
	 * 双色球标示
	 */
	int SSQ = 118;
	/**
	 * 服务器返回成功状态码
	 */
	String SUCCESS = "0";

}
