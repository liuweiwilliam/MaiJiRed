package com.lzz.maijired.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LzzSendByGet {
	public static String sendByGet(String url, String name, String value){
		List<String> par_name = new ArrayList<String>();
		List<String> par_value = new ArrayList<String>();
		
		par_name.add(name);
		par_value.add(value);
		
		return sendByGet(url, par_name, par_value);
	}
	
	public static String sendByGet(String url, List<String> par_name, List<String> par_value)
	{
		String response = "";
		
		BufferedReader in = null;
		
		String complete_url = url;
		
		for(int i=0; i<par_name.size(); i++){
			if(i==0){
				complete_url += "?";
			}
			else{
				complete_url += "&";
			}
			complete_url += par_name.get(i);
			complete_url += "=";
			complete_url += par_value.get(i);
		}
		
		try{
			URL realUrl = new URL(complete_url);
			URLConnection connection = realUrl.openConnection();
			
			connection.setRequestProperty("accept", "*/*");
	        connection.setRequestProperty("connection", "Keep-Alive");
	        connection.setRequestProperty("user-agent",
	                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	
	        // 建立实际的链接
	        connection.connect();
	        
	        // 获取响应头字段
	        Map<String, List<String>> map = connection.getHeaderFields();
//	        for (String key : map.keySet()) 
//	        {
//	            LzzLogger.logDebug(key + "--->" + map.get(key));
//	        }
	        
	        in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
	
	        String line;
	        while ((line = in.readLine()) != null) 
	        {
	            response += line;
	        }
		}catch(Exception e){
			response = "ERROR";
            e.printStackTrace();
		}finally{
			try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
		}
		
		return response;
	}
}
