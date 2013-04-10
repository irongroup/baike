package com.irongroup.unit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PrivateCache {
	private static Map<Object, Object> cache=new ConcurrentHashMap<Object, Object>();
	public static boolean set(Object key, Object value){
		return cache.put(key, value)==null?false:true;
	}
	public static Object get(Object key){
		return cache.get(key);
	}
}
