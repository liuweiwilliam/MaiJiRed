package com.lzz.maijired.pojo;

public class LzzNetWorthTrend {
	/**
	 * 时间
	 */
	private String x;
	/**
	 * 净值
	 */
	private String y;
	/**
	 * 净值回报
	 */
	private String equityReturn;
	/**
	 * 每份派送金
	 */
	private String unitMoney;
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getEquityReturn() {
		return equityReturn;
	}
	public void setEquityReturn(String equityReturn) {
		this.equityReturn = equityReturn;
	}
	public String getUnitMoney() {
		return unitMoney;
	}
	public void setUnitMoney(String unitMoney) {
		this.unitMoney = unitMoney;
	}
	public LzzNetWorthTrend(String x, String y, String equityReturn,
			String unitMoney) {
		super();
		this.x = x;
		this.y = y;
		this.equityReturn = equityReturn;
		this.unitMoney = unitMoney;
	}
	
	@Override
	public String toString() {
		return "{\"x\":\"" + x + "\", \"y\":\"" + y + "\", \"equityReturn\":\""
				+ equityReturn + "\", \"unitMoney\":\"" + unitMoney + "\"}";
	}
	
}
