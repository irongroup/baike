package com.irongroup.unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.irongroup.response.PicMessageResponse;
import com.sina.sae.fetchurl.SaeFetchurl;
import com.sina.sae.memcached.SaeMemcache;

public class SaeFetchUrl {
	
	public static void main(String[] args) {
		SaeFetchurl fetchUrl = new SaeFetchurl();
		String content = fetchUrl.fetch("http://api.lkong.net/top250.json");
//		SaeMemcache mc=new SaeMemcache("127.0.0.1", 11211);
//		mc.init();
//		mc.add("top250", content);
//		System.err.println(content.getBytes().length);
//		JSONArray jsonArray=JSONArray.fromObject(mc.get("top250"));
//		for (int i = 0; i < jsonArray.size(); i++) {
//			JSONObject json=jsonArray.getJSONObject(i);
//			System.err.println(json.get("bid"));
//			System.err.println(jsonArray.get(i).toString());
//		}
		List<JSONObject> list=new ArrayList<JSONObject>();
		JSONArray jsonArray=JSONArray.fromObject(content);
		for (int i = 0; i < jsonArray.size(); i++) {
			list.add(jsonArray.getJSONObject(i));
		}
		PrivateCache.set("top250", list);
		Map<String, String> map=new HashMap<String, String>();
		map.put("ToUserName", "test");
		map.put("FromUserName", "test12");
		String temp=PicMessageResponse.getPicMessageResponse(map);
		System.out.println(temp);
		temp=PicMessageResponse.getPicMessageResponse(map);
		System.out.println(temp);
	}
}
