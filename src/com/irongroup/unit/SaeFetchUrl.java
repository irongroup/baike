package com.irongroup.unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.irongroup.response.PicMessageResponse;
import com.sina.sae.fetchurl.SaeFetchurl;
import com.sina.sae.memcached.SaeMemcache;

public class SaeFetchUrl {
	private static Logger logger=Logger.getLogger(SaeFetchurl.class);
	public static void getBook(List<JSONObject> list, String regex, String input){
		Pattern pattern= Pattern.compile(regex);
		Matcher matcher=pattern.matcher(input);
		while (matcher.find()) {
			String temp = matcher.group(1);
			Pattern pattern2= Pattern.compile("<li><em>([\\s\\S]*?)</em><a href=\"([\\s\\S]*?)\" title=\"([\\s\\S]*?)\" target=\"_blank\" c=\"1\">([\\s\\S]*?)</a></li>");
			Matcher matcher2=pattern2.matcher(temp);
			while (matcher2.find()) {
				String rate=matcher2.group(1);
				String href=matcher2.group(2);
				String title=matcher2.group(3);
				logger.info(rate+"-"+href+"-"+title);
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("rate", rate);
				jsonObject.put("name", title);
				jsonObject.put("href", href);
				list.add(jsonObject);
			}
		}
	}
	public static void main(String[] args) {
		SaeFetchurl fetchUrl = new SaeFetchurl();
		String content = fetchUrl.fetch("http://www.lkong.net/book.php");
//		System.out.println(content);
//		logger.info(content);
		String qlxs_1="<div id=\"portal_block_123_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		String qlxs_2="<div id=\"portal_block_124_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		String qlxs_3="<div id=\"portal_block_125_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		String qlxs_4="<div id=\"portal_block_126_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		List<JSONObject> qlxs= new ArrayList<JSONObject>();
		getBook(qlxs, qlxs_1, content);
		getBook(qlxs, qlxs_2, content);
		getBook(qlxs, qlxs_3, content);
		getBook(qlxs, qlxs_4, content);
		String hslz_1="<div id=\"portal_block_165_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		String hslz_2="<div id=\"portal_block_166_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		String hslz_3="<div id=\"portal_block_167_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		String hslz_4="<div id=\"portal_block_168_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		List<JSONObject> hslz= new ArrayList<JSONObject>();
		getBook(hslz, hslz_1, content);
		getBook(hslz, hslz_2, content);
		getBook(hslz, hslz_3, content);
		getBook(hslz, hslz_4, content);
		List<JSONObject> wbhs= new ArrayList<JSONObject>();
		//奇幻玄幻
		String wbhs_1="<div id=\"portal_block_127_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		//仙侠武侠
		String wbhs_2="<div id=\"portal_block_128_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		//军事历史
		String wbhs_3="<div id=\"portal_block_129_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		//社会言情
		String wbhs_4="<div id=\"portal_block_130_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		getBook(wbhs, wbhs_1, content);
		getBook(wbhs, wbhs_2, content);
		getBook(wbhs, wbhs_3, content);
		getBook(wbhs, wbhs_4, content);
		List<JSONObject> khbd= new ArrayList<JSONObject>();
		//奇幻玄幻
		String khbd_1="<div id=\"portal_block_141_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		//仙侠武侠
		String khbd_2="<div id=\"portal_block_142_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		//军事历史
		String khbd_3="<div id=\"portal_block_143_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		//社会言情
		String khbd_4="<div id=\"portal_block_144_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		getBook(khbd, khbd_1, content);
		getBook(khbd, khbd_2, content);
		getBook(khbd, khbd_3, content);
		getBook(khbd, khbd_4, content);
		logger.info("-------------潜力新书----------");
		for (int j = 0; j < qlxs.size(); j++) {
			JSONObject json= qlxs.get(j);
			logger.info(json.get("name")+"-"+json.getString("rate")+"-"+json.getString("href"));
		}
		logger.info("-------------好评连载----------");
		for (int j = 0; j < hslz.size(); j++) {
			JSONObject json= hslz.get(j);
			logger.info(json.get("name")+"-"+json.getString("rate")+"-"+json.getString("href"));
		}
		logger.info("-------------完本好书----------");
		for (int j = 0; j < wbhs.size(); j++) {
			JSONObject json= wbhs.get(j);
			logger.info(json.get("name")+"-"+json.getString("rate")+"-"+json.getString("href"));
		}
		logger.info("-------------葵花宝典----------");
		for (int j = 0; j < khbd.size(); j++) {
			JSONObject json= khbd.get(j);
			logger.info(json.get("name")+"-"+json.getString("rate")+"-"+json.getString("href"));
		}
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
//		List<JSONObject> list=new ArrayList<JSONObject>();
//		JSONArray jsonArray=JSONArray.fromObject(content);
//		for (int i = 0; i < jsonArray.size(); i++) {
//			list.add(jsonArray.getJSONObject(i));
//		}
//		PrivateCache.set("top250", list);
//		Map<String, String> map=new HashMap<String, String>();
//		map.put("ToUserName", "test");
//		map.put("FromUserName", "test12");
//		String temp=PicMessageResponse.getPicMessageResponse(map);
//		System.out.println(temp);
//		temp=PicMessageResponse.getPicMessageResponse(map);
//		System.out.println(temp);
	}
}
