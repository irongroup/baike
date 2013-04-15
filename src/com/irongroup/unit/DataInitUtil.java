package com.irongroup.unit;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sina.sae.fetchurl.SaeFetchurl;

public class DataInitUtil {
	private static Logger logger = Logger.getLogger(DataInitUtil.class);
	public static void initTop200(){
		SaeFetchurl fetchUrl = new SaeFetchurl();
		String content = fetchUrl
				.fetch("http://api.lkong.net/top250.json");
		logger.debug("json:" + content);
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONArray jsonArray = JSONArray.fromObject(content);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json=jsonArray.getJSONObject(i);
			String url="http://www.lkong.net/book/"+json.getString("bid")+".html";
			content = fetchUrl
					.fetch(url);
			String bookUrl=SaeFetchUrl.getBookUrl(content);
			json.put("bookUrl", bookUrl);
			list.add(json);
			if (i%10==0) {
				logger.info("top200 init."+i);
				PrivateCache.set("top250", list);
			}
		}
		PrivateCache.set("top250", list);
	}
	public static void initCache(){
		SaeFetchurl fetchUrl = new SaeFetchurl();
		String content = fetchUrl.fetch("http://www.lkong.net/book.php");
//		System.out.println(content);
//		logger.info(content);
		String qlxs_1="<div id=\"portal_block_123_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		String qlxs_2="<div id=\"portal_block_124_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		String qlxs_3="<div id=\"portal_block_125_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		String qlxs_4="<div id=\"portal_block_126_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		List<JSONObject> qlxs= new ArrayList<JSONObject>();
		SaeFetchUrl.getBook(qlxs, qlxs_1, content);
		SaeFetchUrl.getBook(qlxs, qlxs_2, content);
		SaeFetchUrl.getBook(qlxs, qlxs_3, content);
		SaeFetchUrl.getBook(qlxs, qlxs_4, content);
		PrivateCache.set("qlxs", qlxs);
		String hslz_1="<div id=\"portal_block_165_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		String hslz_2="<div id=\"portal_block_166_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		String hslz_3="<div id=\"portal_block_167_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		String hslz_4="<div id=\"portal_block_168_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		List<JSONObject> hslz= new ArrayList<JSONObject>();
		SaeFetchUrl.getBook(hslz, hslz_1, content);
		SaeFetchUrl.getBook(hslz, hslz_2, content);
		SaeFetchUrl.getBook(hslz, hslz_3, content);
		SaeFetchUrl.getBook(hslz, hslz_4, content);
		PrivateCache.set("hslz", hslz);
		List<JSONObject> wbhs= new ArrayList<JSONObject>();
		//奇幻玄幻
		String wbhs_1="<div id=\"portal_block_127_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		//仙侠武侠
		String wbhs_2="<div id=\"portal_block_128_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		//军事历史
		String wbhs_3="<div id=\"portal_block_129_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		//社会言情
		String wbhs_4="<div id=\"portal_block_130_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		SaeFetchUrl.getBook(wbhs, wbhs_1, content);
		SaeFetchUrl.getBook(wbhs, wbhs_2, content);
		SaeFetchUrl.getBook(wbhs, wbhs_3, content);
		SaeFetchUrl.getBook(wbhs, wbhs_4, content);
		PrivateCache.set("wbhs", wbhs);
		List<JSONObject> khbd= new ArrayList<JSONObject>();
		//奇幻玄幻
		String khbd_1="<div id=\"portal_block_141_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		//仙侠武侠
		String khbd_2="<div id=\"portal_block_142_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		//军事历史
		String khbd_3="<div id=\"portal_block_143_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		//社会言情
		String khbd_4="<div id=\"portal_block_144_content\" class=\"content\"><div class=\"module cl xl xl1\">([\\s\\S]*?)</div></div>";
		SaeFetchUrl.getBook(khbd, khbd_1, content);
		SaeFetchUrl.getBook(khbd, khbd_2, content);
		SaeFetchUrl.getBook(khbd, khbd_3, content);
		SaeFetchUrl.getBook(khbd, khbd_4, content);
		PrivateCache.set("khbd", khbd);
		logger.info("init done.");
	}
}
