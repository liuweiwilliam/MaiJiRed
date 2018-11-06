package com.lzz.maijired.utils;

public class LzzStringUtil {
	
	/**
	 * 获取介于两个字符串标志之间的字符串
	 * @param orig 原始字符串
	 * @param start 开始字符串
	 * @param end 结束字符串
	 * @return 介于start 和 end 之间的字符串
	 */
	public static String getStrBetween(String orig, String start, String end){
		int idx1 = orig.indexOf(start);
		
		if(idx1==-1) return null;
		
		int idx2 = orig.indexOf(end, idx1);
		if(idx2==-1) return null;
		
		return orig.substring(idx1 + start.length(), idx2);
	}
	
	public static void main(String[] args) {
		System.out.println(getStrBetween("123sadfhksdhfk;", "123", ";"));
	}
}
