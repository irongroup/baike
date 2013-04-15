package com.irongroup.unit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sina.sae.memcached.SaeMemcache;

public class PrivateCache {
	private static Map<Object, Object> cache=new ConcurrentHashMap<Object, Object>();
	public static boolean set(Object key, Object value){
		SaeMemcache mc=new SaeMemcache("127.0.0.1", 11211);
		mc.init();
		
		mc.set(key.toString(), value);
		return cache.put(key, value)==null?false:true;
	}
	public static Object get(Object key){
		SaeMemcache mc=new SaeMemcache("127.0.0.1", 11211);
		mc.init();
		if (cache.get(key)==null) {
			return mc.get(key.toString());
		}else {
			return cache.get(key);
		}
	}
}
