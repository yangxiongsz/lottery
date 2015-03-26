package com.ithm.lotteryhm28;

public class NativeValue {
	/**
	 * 子代理商的密钥
	 */
	public native static String getAgenterPassword();

	/**
	 * des加密用密钥
	 */
	public native static String getDesPassword();

	/**
	 * 服务器地址
	 */
	public native static String getLotteryUrl();

	static {
		System.loadLibrary("des");
	}
}
