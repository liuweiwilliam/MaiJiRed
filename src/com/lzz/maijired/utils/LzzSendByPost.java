package com.lzz.maijired.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class LzzSendByPost {
	public static String sendByPost(String url, List<String> par_name, List<String> par_value, String contents)
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
			JSONObject json_obj = JSONObject.fromObject(contents);
            byte[] requestStringBytes = json_obj.toString().getBytes("utf-8");
            
			URL realUrl = new URL(complete_url);

			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			
			connection.setDoOutput(true); 
            connection.setDoInput(true);
			connection.setRequestMethod("POST"); 
			connection.setUseCaches(false); 
			connection.setRequestProperty("Content-length", "" + requestStringBytes.length);
	        connection.setRequestProperty("Content-Type", 
	                   "application/json;encoding=utf-8"); 
	        connection.setRequestProperty("Charset", "utf-8");
	
	        connection.connect();
	        
	        DataOutputStream out = new DataOutputStream(connection.getOutputStream()); 

            out.write(requestStringBytes);
            out.flush(); 
            out.close(); 
            
	        Map<String, List<String>> map = connection.getHeaderFields();
//	        for (String key : map.keySet())
//	        {
//	            LzzLogger.logDebug(key + "--->" + map.get(key));
//	        }
	        
	        in = new BufferedReader(new InputStreamReader(
	                connection.getInputStream()));
	
	        String line;
	        while ((line = in.readLine()) != null) 
	        {
	            response += line;
	        }
		}catch(Exception e){
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
