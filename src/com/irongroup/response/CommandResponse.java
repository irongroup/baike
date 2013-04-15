package com.irongroup.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.irongroup.unit.PrivateCache;
import com.sina.sae.fetchurl.SaeFetchurl;

public class CommandResponse {
	private static Logger logger = Logger.getLogger(CommandResponse.class);

	/**
	 * m：top200 n：下一页 a:新书 b:好评 c:完本 d:太监
	 * 
	 * @return
	 */
	public static String execute(Map<String, String> map) {
		String response = "";
		String queryString = map.get("Content");
		if (map.get("MsgType").equals("event")
				&& map.get("Event").equals("subscribe")) {
			StringBuffer temp = new StringBuffer("<xml>");
			temp.append("<ToUserName><![CDATA[");
			temp.append(map.get("FromUserName"));
			temp.append("]]></ToUserName>");
			temp.append("<FromUserName><![CDATA[");
			temp.append(map.get("ToUserName"));
			temp.append("]]></FromUserName>");
			temp.append("<CreateTime>");
			temp.append(System.currentTimeMillis());
			temp.append("</CreateTime>");
			temp.append("<MsgType><![CDATA[");
			temp.append("text");
			temp.append("]]></MsgType>");
			temp.append("<Content><![CDATA[");
			temp.append("欢迎订阅网文推荐，基于<a href=\"http://www.lkong.net/book.php\">龙空数据</a>方便书友查阅最新网文推荐榜。\nm:top200网文\nn:查看下十条。\n"
					+ "a:潜力新书\n" + "b:好评连载\n" + "c:完本好书\n" + "d:葵花宝典\n" + "\n任意字符:快捷键\n欢迎反馈");
			temp.append("]]></Content>");
			temp.append("<FuncFlag>");
			temp.append("0");
			temp.append("</FuncFlag>");
			temp.append("</xml>");
			response = temp.toString();
		} else if (queryString.equalsIgnoreCase("M")) {
			// SaeMemcache mc=new SaeMemcache("127.0.0.1", 11211);
			// mc.init();
			response = PicMessageResponse.getPicMessageResponse(map);
		} else if (queryString.equalsIgnoreCase("n")) {
			response = PicMessageResponse.getNextPicMessageResponse(map);
		} else if (queryString.equalsIgnoreCase("a")) {
			response = QlxsResponse.getPicMessageResponse(map);
		} else if (queryString.equalsIgnoreCase("b")) {
			response = HslzResponse.getPicMessageResponse(map);
		} else if (queryString.equalsIgnoreCase("c")) {
			response = WbhsResponse.getPicMessageResponse(map);
		} else if (queryString.equalsIgnoreCase("d")) {
			response = KhbdResponse.getPicMessageResponse(map);
		}else {
			StringBuffer temp = new StringBuffer("<xml>");
			temp.append("<ToUserName><![CDATA[");
			temp.append(map.get("FromUserName"));
			temp.append("]]></ToUserName>");
			temp.append("<FromUserName><![CDATA[");
			temp.append(map.get("ToUserName"));
			temp.append("]]></FromUserName>");
			temp.append("<CreateTime>");
			temp.append(System.currentTimeMillis());
			temp.append("</CreateTime>");
			temp.append("<MsgType><![CDATA[");
			temp.append("text");
			temp.append("]]></MsgType>");
			temp.append("<Content><![CDATA[");
			temp.append("Welcome\nm:top200\n" + "n:下十条\n" + "a:潜力新书\n" + "b:好评连载\n"
					+ "c:完本好书\n" + "d:葵花宝典\n");
			temp.append("]]></Content>");
			temp.append("<FuncFlag>");
			temp.append("0");
			temp.append("</FuncFlag>");
			temp.append("</xml>");
			response = temp.toString();
		}
		return response;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
