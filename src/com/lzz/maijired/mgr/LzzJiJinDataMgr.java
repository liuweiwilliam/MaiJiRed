package com.lzz.maijired.mgr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateUtils;

import com.lzz.maijired.pojo.LzzNetWorthTrend;
import com.lzz.maijired.utils.LzzSendByGet;
import com.lzz.maijired.utils.LzzStringUtil;


public class LzzJiJinDataMgr {
	public static Hashtable<String, Hashtable<String, List<String>>> kline = new Hashtable<String, Hashtable<String, List<String>>>();
	public static Hashtable<String, Date> maxDate = new Hashtable<String, Date>();
	
	/**
	 * 获取基金K线数据
	 * @param jnum 基金编号
	 * @return
	 */
	public static Hashtable<String, List<String>> getJiJinData(String jnum){
		if(kline.containsKey(jnum)){
			System.out.println("get from local");
			return getJiJinFromLocal(jnum);
		}
		
		System.out.println("get from remote");
		return getJiJinDataFromRemote(jnum);
	}
	
	/**
	 * 从本地内存获取基金数据
	 * @param jnum 基金编号
	 * @return
	 */
	private static Hashtable<String, List<String>> getJiJinFromLocal(String jnum) {
		if(DateUtils.isSameDay(new Date(), maxDate.get(jnum))){
			return kline.get(jnum);
		}
		
		return getJiJinDataFromRemote(jnum);
	}

	/**
	 * 从远程获取基金数据
	 * @param jnum 基金编号
	 * @return
	 */
	private static Hashtable<String, List<String>> getJiJinDataFromRemote(String jnum) {
		// TODO Auto-generated method stub
		String rslt = getJiJinStrDataFromRemote(jnum);
		if(rslt==null) return null;
		
		Hashtable<String, List<String>> data = parseJiJinStrData(rslt);
		
		kline.put(jnum, data);
		maxDate.put(jnum, new Date());
		
		return data;
	}

	/**
	 * 解析基金数据字符串
	 * @param rslt
	 * @return
	 */
	private static Hashtable<String, List<String>> parseJiJinStrData(String rslt) {
		Hashtable<String, List<String>> rslt_data = new Hashtable<String, List<String>>();
		
		/*单位净值走势 x-时间  y-净值  equityReturn-净值回报  unitMoney-每份派送金*/
		String Data_netWorthTrend = LzzStringUtil.getStrBetween(rslt, "var Data_netWorthTrend = ", ";");
		if(null==Data_netWorthTrend) return null;
		JSONArray Data_netWorthTrend_arr = JSONArray.fromObject(Data_netWorthTrend);
		List<String> Data_netWorthTrend_hash = new ArrayList<String>();
		for(int i=0; i<Data_netWorthTrend_arr.size(); i++){
			JSONObject sin = Data_netWorthTrend_arr.getJSONObject(i);
			String x = sin.getString("x");
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(Long.valueOf(x)); 
			Date date = c.getTime();
			x = new SimpleDateFormat("yyyy-MM-dd").format(date);
			String y = sin.getString("y");
			String equityReturn = sin.getString("equityReturn");
			String unitMoney = sin.getString("unitMoney");
			LzzNetWorthTrend obj = new LzzNetWorthTrend(x, y, equityReturn, unitMoney);
			Data_netWorthTrend_hash.add(obj.toString());
		}
		rslt_data.put("Data_netWorthTrend", Data_netWorthTrend_hash);
		
		return rslt_data;
	}

	/**
	 * 从远程获取基金数据
	 * @param jnum 基金号
	 * @return 
	 */
	private static String getJiJinStrDataFromRemote(String jnum){
		String rslt = "";
		
		String url = "http://fund.eastmoney.com/pingzhongdata/" + jnum + ".js";
		rslt = LzzSendByGet.sendByGet(url, "v", System.currentTimeMillis() + "");
		
		if(rslt.equals("ERROR")){
			return null;
		}
		
		return rslt;
	}
	
	public static void main(String[] args) {
		getJiJinDataFromRemote("166005");
	}
}
