package com.ithm.lotteryhm28.bean;

/**
 * 用户投注信息封装
 * 
 * @author Administrator
 * 
 */
public class Ticket {
	private String redNum;
	private String blueNum;
	// 注数
	private int num;

	public String getRedNum() {
		return redNum;
	}

	public void setRedNum(String redNum) {
		this.redNum = redNum;
	}

	public String getBlueNum() {
		return blueNum;
	}

	public void setBlueNum(String blueNum) {
		this.blueNum = blueNum;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
