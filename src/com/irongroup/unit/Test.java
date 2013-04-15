package com.irongroup.unit;

import java.util.List;

import net.sf.json.JSONObject;

import com.sina.sae.memcached.SaeMemcache;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ml="http://www.yuanchuang.com/bookcatalog/10160795.html";
		System.out.println(ml.substring(ml.lastIndexOf("/")+1, ml.lastIndexOf(".")));
		SaeMemcache mc=new SaeMemcache("127.0.0.1", 11211);
		mc.init();
		List<JSONObject> wbhs=mc.get("qlxs");
		for (int i = 0; i < wbhs.size(); i++) {
			System.out.println(wbhs.get(i).toString());
		}
		
	
		
	}

}
