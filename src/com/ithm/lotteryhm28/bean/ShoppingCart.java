package com.ithm.lotteryhm28.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 * 
 * @author Administrator
 * 
 */
public class ShoppingCart {
	public static ShoppingCart getinstance = new ShoppingCart();

	private ShoppingCart() {

	}

	public static ShoppingCart getGetinstance() {
		return getinstance;
	}

	// 投注
	// lotteryid string * 玩法编号
	// issue string * 期号（当前销售期）
	// lotterycode string * 投注号码，注与注之间^分割
	// lotterynumber string 注数
	// lotteryvalue string 方案金额，以分为单位

	// appnumbers string 倍数
	// issuesnumbers string 追期
	// issueflag int * 是否多期追号 0否，1多期
	// bonusstop int * 中奖后是否停止：0不停，1停
	private Integer lotteryid;
	private String issue;
	private List<Ticket> tickets = new ArrayList<Ticket>();
	private Integer lotterynumber;// 计算
	private Integer lotteryvalue;

	public Integer getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Integer lotteryid) {
		this.lotteryid = lotteryid;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public Integer getLotterynumber() {
		lotterynumber = 0;
		for (Ticket item : tickets) {
			lotterynumber += item.getNum();
		}

		return lotterynumber;
	}

	public Integer getLotteryvalue() {
		lotteryvalue = 2 * getLotterynumber();
		return lotteryvalue;
	}
}
