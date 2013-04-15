package com.irongroup.response;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.irongroup.unit.PrivateCache;

import net.sf.json.JSONObject;

public class WbhsResponse {
	private static Logger  logger= Logger.getLogger(WbhsResponse.class);
	public  static String getPicMessageResponse(Map<String, String> map){
		StringBuffer response=new StringBuffer("<xml>");
		response.append("<ToUserName><![CDATA[");
		response.append(map.get("FromUserName"));
		response.append("]]></ToUserName>");
		response.append("<FromUserName><![CDATA[");
		response.append(map.get("ToUserName"));
		response.append("]]></FromUserName>");
		response.append("<CreateTime>");
		response.append(System.currentTimeMillis());
		response.append("</CreateTime>");
		response.append("<MsgType><![CDATA[news]]></MsgType>");
		response.append("<ArticleCount>10</ArticleCount>");
		response.append("<Articles>");
//		SaeMemcache mc=new SaeMemcache("127.0.0.1", 11211);
//		mc.init();
		String index_key=map.get("ToUserName")+"_next";
		if (PrivateCache.get(index_key)==null) {
			PrivateCache.set(index_key, 0);
		}
		Integer index=(Integer) PrivateCache.get(index_key);
		List<JSONObject> list=(List<JSONObject>) PrivateCache.get("wbhs");
		Integer end=0;
		for (int i = 0; i < list.size(); i++) {
			//{"bid":"492","bookpic":"http://img.lkong.cn/fujian/dzx/201101/141239yjrccaprrpeonpy6.jpg.small.jpg","name":"都市妖奇谈","author":"可蕊","rate":"9.4","bookstat":"已完结","year":"2001","tag":""}
			JSONObject json=list.get(i);
			logger.debug(json.toString());
			logger.debug(json.getString("name"));
			response.append("<item>");
			response.append("<Title><![CDATA[");
			response.append(i+"."+json.getString("name")+"("+json.getString("rate")+")");
			response.append("]]></Title>");
			response.append("<Description><![CDATA[]]></Description>");
			response.append("<PicUrl><![CDATA[]]></PicUrl>");
			response.append("<Url><![CDATA["+json.getString("bookUrl")+"]]></Url>");
			response.append("</item>");
			end=end+1;
			if (end==9) {
				break;
			}
		}
		response.append("<item>");
		response.append("<Title><![CDATA[");
		response.append("查看全部");
		response.append("]]></Title>");
		response.append("<Description><![CDATA[]]></Description>");
		response.append("<PicUrl><![CDATA[]]></PicUrl>");
		response.append("<Url><![CDATA[http://irong.sinaapp.com/mock?action=wbhs]]></Url>");
		response.append("</item>");
		PrivateCache.set(index_key, index+10);
		response.append("</Articles>");
		response.append("<FuncFlag>1</FuncFlag>");
		response.append("</xml>");
		return response.toString();
	}
	public  static String getNextPicMessageResponse(Map<String, String> map){
		StringBuffer response=new StringBuffer("<xml>");
		response.append("<ToUserName><![CDATA[");
		response.append(map.get("FromUserName"));
		response.append("]]></ToUserName>");
		response.append("<FromUserName><![CDATA[");
		response.append(map.get("ToUserName"));
		response.append("]]></FromUserName>");
		response.append("<CreateTime>");
		response.append(System.currentTimeMillis());
		response.append("</CreateTime>");
		response.append("<MsgType><![CDATA[news]]></MsgType>");
		response.append("<ArticleCount>10</ArticleCount>");
		response.append("<Articles>");
//		SaeMemcache mc=new SaeMemcache("127.0.0.1", 11211);
//		mc.init();
		String index_key=map.get("ToUserName")+"_next";
		if (PrivateCache.get(index_key)==null) {
			PrivateCache.set(index_key, 0);
		}
		Integer index=(Integer) PrivateCache.get(index_key);
		List<JSONObject> list=(List<JSONObject>) PrivateCache.get("hslz");
		Integer end=0;
		for (int i = index; i < list.size(); i++) {
			//{"bid":"492","bookpic":"http://img.lkong.cn/fujian/dzx/201101/141239yjrccaprrpeonpy6.jpg.small.jpg","name":"都市妖奇谈","author":"可蕊","rate":"9.4","bookstat":"已完结","year":"2001","tag":""}
			JSONObject json=list.get(i);
			logger.debug(json.toString());
			logger.debug(json.getString("name"));
			response.append("<item>");
			response.append("<Title><![CDATA[");
			response.append(i+"."+json.getString("name")+"("+json.getString("rate")+")");
			response.append("]]></Title>");
			response.append("<Description><![CDATA[]]></Description>");
			response.append("<PicUrl><![CDATA[]]></PicUrl>");
			response.append("<Url><![CDATA["+json.getString("bookUrl")+"]]></Url>");
			response.append("</item>");
			end=end+1;
			if (end==10) {
				break;
			}
		}
		PrivateCache.set(index_key, index+10);
		response.append("</Articles>");
		response.append("<FuncFlag>1</FuncFlag>");
		response.append("</xml>");
		return response.toString();
	}
	
}
