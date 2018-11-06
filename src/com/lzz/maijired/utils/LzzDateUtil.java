package com.lzz.maijired.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;


public class LzzDateUtil {
    
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
	public static String getPreDay(String specifiedDay){ 
		
		Calendar c = Calendar.getInstance(); 
		Date date=null; 
		try { 
			date = format.parse(specifiedDay); 
		} catch (ParseException e) { 
			e.printStackTrace(); 
		} 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day-1); 

		String preDay = format.format(c.getTime()); 
		return preDay; 
	} 
	
	public static String getNextDay(String specifiedDay){ 
		
		Calendar c = Calendar.getInstance(); 
		Date date=null; 
		try { 
			date = format.parse(specifiedDay); 
		} catch (ParseException e) { 
			e.printStackTrace(); 
		} 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day+1); 

		String nextDay = format.format(c.getTime()); 
		return nextDay; 
	} 
    
	public static List<Dictionary<String, String>> getDateBetween(String sdate, String edate) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<Dictionary<String, String>> rslt = new ArrayList<Dictionary<String,String>>();
		
		String last_day = lastDayOfMonth(sdate);
		String first_day = sdate;
		while(last_day.compareTo(edate)<0){
			Dictionary<String, String> dic = new Hashtable<String, String>();
			dic.put("sdate", first_day);
			dic.put("edate", last_day);
			rslt.add(dic);
			last_day = lastDayOfNextMonth(last_day);
			first_day = firstDayOfMonth(last_day);
		}
		
		Dictionary<String, String> dic = new Hashtable<String, String>();
		dic.put("sdate", first_day);
		dic.put("edate", edate);
		rslt.add(dic);
		
		return rslt;
	}
	
	public static String lastDayOfMonth(String date) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    
	    Calendar calendar = Calendar.getInstance();
        calendar.setTime(df.parse(date));
        calendar.add(Calendar.MONTH, 1);  
        calendar.set(Calendar.DATE, 0);  
	    return df.format(calendar.getTime());
	}
	
	private static String lastDayOfNextMonth(String date) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    
	    Calendar calendar = Calendar.getInstance();
        calendar.setTime(df.parse(date));
        calendar.add(Calendar.MONTH, 2);  
        calendar.set(Calendar.DATE, 0);  
	    return df.format(calendar.getTime());
	}

	private static String firstDayOfMonth(String date) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    
	    Calendar calendar = Calendar.getInstance();
        calendar.setTime(df.parse(date));
        calendar.set(Calendar.DATE, 1);
	    return df.format(calendar.getTime());
	}
	
	public static String lastDayOfWeek(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal =Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		try {
			if(sdf.parse(date).after(cal.getTime())){
				cal.add(Calendar.WEEK_OF_YEAR, 1);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String rslt = sdf2.format(cal.getTime());
		return rslt;
	}
	
	public static String firstDayOfWeek(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal =Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		try {
			if(cal.getTime().after(sdf.parse(date))){
				cal.add(Calendar.WEEK_OF_YEAR, -1);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String rslt = sdf2.format(cal.getTime());
		return rslt;
	}
	
	public static String getNow(String type){
		SimpleDateFormat sdf = null;
		switch(type){
		case "y":
			sdf = new SimpleDateFormat("yyyy");
			break;
		case "M":
			sdf = new SimpleDateFormat("yyyy-MM");
			break;
		case "d":
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			break;
		case "H":
			sdf = new SimpleDateFormat("yyyy-MM-dd HH");
			break;
		case "m":
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			break;
		case "month":
			sdf = new SimpleDateFormat("MM-dd HH:mm");
			break;
		case "s":
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		case "dtl":
			sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
			break;
		default:
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		}
		Date date = new Date();
		String rslt = sdf.format(date);
		
		if(null!=type
				&& type.equals("dtl")){
			rslt = rslt.replace("_", "T");
		}
		
		return rslt;
	}

	/**
	 * 将localdatetime控件提交的时间格式转成系统使用的time格式
	 * @param time 格式 yyyy-MM-ddTHH:mm
	 * @return 格式: yyyy-MM-dd HH:mm:00
	 */
	public static String ldt2SysTime(String time) {
		// TODO Auto-generated method stub
		return time.replace("T", " ")+":00";
	}
	
	/**
	 * 将系统使用的time格式转成localdatetime控件支持时间格式
	 * @param time 格式 yyyy-MM-dd HH:mm:ss 
	 * @return 格式: yyyy-MM-ddTHH:mm
	 */
	public static String sysTime2Ldt(String time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");
		try {
			Date date = sdf.parse(time);
			String date_str = sdf2.format(date);
			String time_str = sdf3.format(date);
			return date_str+"T"+time_str;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	//获取当前时间后几分钟的时间
	public static String getNextMinute(int n) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, n);
		return sdf.format(c.getTime());
	}
	
}