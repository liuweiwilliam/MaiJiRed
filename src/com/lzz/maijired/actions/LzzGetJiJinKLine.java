package com.lzz.maijired.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Hashtable;
import java.util.List;

import com.lzz.maijired.mgr.LzzJiJinDataMgr;
import com.lzz.maijired.pojo.LzzNetWorthTrend;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LzzGetJiJinKLine extends LzzBaseManagerAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7934641457875865727L;

	public String jNum;
	public String getKLine() {
		JSONObject rsl = new JSONObject();
		JSONObject error = new JSONObject();
		try {
			error.put("isError", false);

			Hashtable<String, List<String>> data = LzzJiJinDataMgr.getJiJinData(jNum);
			List<String> NetWorthTrend = data.get("Data_netWorthTrend");
			JSONObject rslt_data = new JSONObject();
			JSONArray xData = new JSONArray();
			JSONArray yData = new JSONArray();
			for(int i=0; i<NetWorthTrend.size(); i++){
				JSONObject sin = JSONObject.fromObject(NetWorthTrend.get(i));
				xData.add(sin.get("x"));
				yData.add(sin.get("y"));
			}
			rslt_data.put("xData", xData);
			rslt_data.put("yData", yData);
			
			rsl.put("result", rslt_data);
			
			System.out.println(rsl);
		} catch (Exception e) {
			rsl.put("result", JSONObject.fromObject("{\"success\":false}"));
			e.printStackTrace();
			error.put("isError", true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			error.put("content", sw.toString());
		} finally {
			PrintWriter writer;
			try {
				rsl.put("error", error);
				response.setCharacterEncoding("UTF-8");
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}
